# Notification Mechanisms — System Design Deep Dive
### Webhook vs WebSocket vs Polling (Staff-level explanation)

---

## The Core Question the Interviewer is Asking

> "Your system scan completes. The user is on the product webpage. How does the user find out? What if you send the notification and the user's network breaks? What if you send it twice by accident?"

This is testing:
- **Delivery guarantees** (at-least-once vs exactly-once vs at-most-once)
- **Failure handling** (retry logic, exponential backoff)
- **Idempotency** (deduplication of duplicate notifications)
- **Scalability** (can you notify 100K users simultaneously?)
- **Latency** (P99 time to notification)

---

## Option 1: Webhook (Most Common for B2B)

### What It Is

You (service) make an **outbound HTTP POST** to the client's endpoint when an event happens.

```
Timeline:
  
  T=0:  Scan completes (ClamAV returns verdict)
  T=1:  Decision engine publishes event to Kafka
  T=2:  Notification service consumes event
  T=3:  Notification service makes POST to customer_webhook_url
  T=4:  Customer receives webhook call
  
  Total latency: 1-3 seconds (reasonable for non-real-time)
```

### Simple Implementation

```python
# notification_service.py
import requests
from requests.adapters import HTTPAdapter
from urllib3.util.retry import Retry
import json
import hashlib
import hmac
import time

class WebhookNotifier:
    def __init__(self):
        self.session = self._create_session()
    
    def _create_session(self):
        """Create session with retry strategy"""
        session = requests.Session()
        
        # Exponential backoff: 1s, 2s, 4s, 8s, 16s (max 5 retries)
        retry_strategy = Retry(
            total=5,
            backoff_factor=1,
            status_forcelist=[429, 500, 502, 503, 504],
            allowed_methods=["POST"]
        )
        
        adapter = HTTPAdapter(max_retries=retry_strategy)
        session.mount("http://", adapter)
        session.mount("https://", adapter)
        return session
    
    def send_webhook(self, webhook_url, payload):
        """
        Send webhook with signature verification
        
        Args:
            webhook_url: customer's endpoint (e.g., https://customer.com/webhook)
            payload: event data
        """
        
        # Create HMAC signature (security: proves this came from us)
        secret = "webhook-secret-key"  # stored in key vault
        body_json = json.dumps(payload, sort_keys=True)
        signature = hmac.new(
            secret.encode(),
            body_json.encode(),
            hashlib.sha256
        ).hexdigest()
        
        headers = {
            'Content-Type': 'application/json',
            'X-Signature': signature,
            'X-Timestamp': str(int(time.time())),
            'User-Agent': 'MalwareScannerWebhook/1.0'
        }
        
        try:
            response = self.session.post(
                webhook_url,
                json=payload,
                headers=headers,
                timeout=10
            )
            response.raise_for_status()
            
            return {
                'status': 'delivered',
                'response_code': response.status_code
            }
        
        except requests.exceptions.RequestException as e:
            # Will retry automatically (via Retry strategy)
            return {
                'status': 'failed',
                'error': str(e)
            }

# Usage in scan completion handler
def on_scan_complete(upload_id, verdict, threat_name):
    """Called when scan finishes"""
    
    # Get customer webhook URL from database
    customer = db.query("SELECT webhook_url FROM customers WHERE id = ?", user_id)
    
    if not customer.webhook_url:
        print(f"No webhook configured for user {user_id}")
        return
    
    payload = {
        'event': 'scan.completed',
        'upload_id': upload_id,
        'verdict': verdict,
        'threat_name': threat_name,
        'timestamp': datetime.utcnow().isoformat()
    }
    
    notifier = WebhookNotifier()
    result = notifier.send_webhook(customer.webhook_url, payload)
    
    # Log result for audit trail
    db.insert('webhook_logs', {
        'upload_id': upload_id,
        'webhook_url': customer.webhook_url,
        'payload': json.dumps(payload),
        'status': result['status'],
        'attempts': 5 if result['status'] == 'failed' else 1,
        'timestamp': datetime.utcnow()
    })
```

### Webhook Delivery Guarantee: "At-Least-Once"

```
Problem:
  POST to customer webhook succeeds
  BUT network fails before we get the 200 response
  → We retry and send AGAIN
  
Result: Customer receives duplicate notifications

Solution: Idempotency
  Include idempotency_key = hash(upload_id + event_type)
  
  Customer webhook should:
    1. Check: "Have I seen this idempotency_key before?"
    2. If yes: return 200 (don't reprocess)
    3. If no: process event, store idempotency_key

Example (customer's side):
  
  @app.post("/webhook")
  def receive_webhook():
      idempotency_key = request.headers.get('X-Idempotency-Key')
      
      # Check cache (Redis)
      if redis.exists(f"webhook:{idempotency_key}"):
          return 200  # Already processed
      
      # Process the event
      upload_id = request.json['upload_id']
      verdict = request.json['verdict']
      
      # Update user's file status
      db.update('uploads', {'status': verdict}, 
                where={'id': upload_id})
      
      # Mark as processed
      redis.setex(f"webhook:{idempotency_key}", 86400, "processed")
      
      return 200
```

### Webhook Retry Strategy (Critical Detail)

```
Scenario: Customer's webhook is down during scan completion

Timeline:
  T=0:   Scan finishes, event published
  T=1:   Webhook attempt 1 → 503 Service Unavailable
  T=2:   Wait 1 second
  T=3:   Webhook attempt 2 → 503 Service Unavailable
  T=4:   Wait 2 seconds
  T=6:   Webhook attempt 3 → 503 Service Unavailable
  T=7:   Wait 4 seconds
  T=11:  Webhook attempt 4 → 503 Service Unavailable
  T=12:  Wait 8 seconds
  T=20:  Webhook attempt 5 → SUCCESS ✓
  
Total time to successful delivery: 20 seconds (acceptable)

Status codes to retry on:
  429 (Rate Limited)       → Exponential backoff
  500 (Internal Server)    → Exponential backoff
  502 (Bad Gateway)        → Exponential backoff
  503 (Service Unavailable)→ Exponential backoff
  504 (Gateway Timeout)    → Exponential backoff

Status codes NOT to retry:
  400 (Bad Request)        → Webhook expects different format
  401 (Unauthorized)       → Wrong API key (will always fail)
  403 (Forbidden)          → Access denied (will always fail)
  404 (Not Found)          → Wrong endpoint (will always fail)
```

### Webhook Problems (What Interviewer Will Ask)

```
Problem 1: "What if my webhook URL is wrong?"
  
  Solution:
    • Send test webhook on URL registration
    • If test fails: reject the URL
    • Periodically health-check webhook URLs
    • Disable webhook after 10 consecutive failures

Problem 2: "What if the customer's server is slow?"
  
  Concern:
    If every webhook call takes 10 seconds, and we have 100K
    customers, notifying all simultaneously = 1M seconds = 11 days
  
  Solution:
    • Async queue (SQS/Kafka) decouples notification service
    • Scale notification workers horizontally
    • Per-customer rate limiting (e.g., max 100 webhooks/min)
    • Timeout per webhook (10 seconds) — kill stuck connections

Problem 3: "What if network partition? My service can't reach customer?"
  
  Concern:
    Can't deliver webhook for hours, users don't know scan result
  
  Solution:
    • Use long polling or WebSocket as fallback
    • Store event in database with status: [pending, delivered, failed]
    • User polls: "Give me my notifications" (fallback to polling)
    • After 5 failures: mark webhook as disabled, notify customer

Problem 4: "Duplicate notifications — user gets email twice"
  
  Concern:
    Idempotency key works on customer side, but they may be
    processing webhooks in parallel
  
  Solution:
    • Include idempotency_key in webhook
    • Customer uses Redis/cache with COMPARE-AND-SET atomicity
    • OR: Customer database has unique constraint on (upload_id, event_type)
    • If duplicate INSERT: database rejects it (idempotent)

Problem 5: "What's our liability if notification never arrives?"
  
  Concern:
    Customer's webhook endpoint is completely offline. We've retried
    5 times over 20 minutes. User never finds out scan completed.
    Compliance issue? SLA violation?
  
  Solution:
    • Document SLA: "Webhooks delivered within 60 minutes"
    • Define "best-effort" (not guaranteed)
    • OR: Add fallback — email notification on webhook failure
    • Track webhook delivery rate (target: 99.9%)
    • Alert if delivery rate drops
```

### Webhook Architecture (Scalable)

```
┌──────────────────────────────────┐
│  Scan Completion                 │
│  (ClamAV returns CLEAN/MALWARE)  │
└────────────┬─────────────────────┘
             │
             ▼
    ┌────────────────────┐
    │  Kafka Topic:      │
    │  scan-completed    │
    │  (durable buffer)  │
    └────────┬───────────┘
             │
             ▼
    ┌────────────────────────────────┐
    │  Notification Service          │
    │  (5-10 worker processes)       │
    │  Consumes from Kafka           │
    └────────┬───────────────────────┘
             │
    ┌────────▼─────────────────────┐
    │  For each event:              │
    │  1. Lookup customer webhook   │
    │  2. POST with retries (5×)    │
    │  3. Log result to DB          │
    │  4. Send email fallback on 5x fail
    └──────────────────────────────┘

Storage:
  • webhook_logs table
  • Schema: {upload_id, webhook_url, status, attempts, timestamp}
  • Query: "Which webhooks are failing?" (alert on high error rate)
```

---

## Option 2: WebSocket (Real-time, Low Latency)

### What It Is

**Persistent bidirectional connection** between browser and server. Server pushes notifications over the same connection.

```
Setup:
  T=0:  User opens browser, navigates to upload page
  T=1:  Browser establishes WebSocket connection
  T=2:  Scan starts
  T=3:  Scan completes (ClamAV verdict)
  T=4:  Server pushes notification over WebSocket (10-50ms latency)
  T=5:  Browser receives, updates UI instantly
  
Total latency: 10-50ms (real-time feel)
```

### Implementation (Using Socket.IO or Native WebSocket)

```python
# Flask + python-socketio
from flask import Flask, emit, disconnect
from flask_socketio import SocketIO, rooms, join_room, leave_room
import json

app = Flask(__name__)
socketio = SocketIO(app, cors_allowed_origins="*")

# Track active connections per user
active_connections = {}  # user_id → [sid1, sid2, ...]

@socketio.on('connect')
def handle_connect(auth):
    """Client connects (browser opens upload page)"""
    user_id = auth.get('user_id')
    sid = request.sid
    
    print(f"User {user_id} connected: {sid}")
    
    # Track connection
    if user_id not in active_connections:
        active_connections[user_id] = []
    active_connections[user_id].append(sid)
    
    # Join room (broadcast to all connections for this user)
    join_room(f"user_{user_id}")
    
    emit('connection_ack', {'message': 'Connected to notification service'})

@socketio.on('disconnect')
def handle_disconnect(auth):
    """Client disconnects (browser closes or network breaks)"""
    user_id = auth.get('user_id')
    sid = request.sid
    
    print(f"User {user_id} disconnected: {sid}")
    
    if user_id in active_connections:
        active_connections[user_id].remove(sid)
        if not active_connections[user_id]:
            del active_connections[user_id]

# Called when scan completes
def notify_scan_complete(user_id, upload_id, verdict, threat_name):
    """Push notification to all user's browser tabs"""
    
    payload = {
        'event': 'scan.completed',
        'upload_id': upload_id,
        'verdict': verdict,
        'threat_name': threat_name,
        'timestamp': datetime.utcnow().isoformat()
    }
    
    # Broadcast to all connections for this user
    socketio.emit('scan_result', payload, room=f"user_{user_id}")
    
    # Still log to database (audit trail)
    db.insert('notification_logs', {
        'user_id': user_id,
        'upload_id': upload_id,
        'notification_type': 'websocket',
        'payload': json.dumps(payload),
        'timestamp': datetime.utcnow()
    })

# Browser-side JavaScript
# <script src="https://cdn.socket.io/4.5.4/socket.io.min.js"></script>
```

```javascript
// Client-side WebSocket connection
const socket = io('https://your-service.com', {
  auth: {
    user_id: getCurrentUserId()
  }
});

socket.on('connection_ack', (data) => {
  console.log('Connected to notification service');
  showStatusBadge('Connected');
});

socket.on('scan_result', (data) => {
  // Received notification from server
  console.log('Scan complete:', data);
  
  // Update UI instantly
  if (data.verdict === 'CLEAN') {
    showNotification('✓ File scanned successfully', 'success');
    enableDownloadButton(data.upload_id);
  } else {
    showNotification('✗ Malware detected: ' + data.threat_name, 'danger');
    showQuarantineInfo(data.upload_id);
  }
});

socket.on('disconnect', () => {
  console.log('Disconnected from server');
  showStatusBadge('Disconnected (will retry)');
  // Browser will auto-reconnect with exponential backoff
});
```

### WebSocket Delivery Guarantee: "At-Most-Once"

```
Problem:
  If user is offline or connection breaks, notification is LOST.
  
  Timeline:
    T=0:  User closes laptop, WebSocket closes
    T=1:  Scan completes, server tries to push
    T=2:  No connection → notification discarded
    T=3:  User opens laptop, reconnects
    T=4:  User has no idea scan completed
  
  Result: User misses notification entirely

Solution: Fallback to polling
  
  On reconnect:
    1. Browser reconnects WebSocket
    2. Browser queries: "GET /api/uploads?status=pending"
    3. Server returns: "You have 2 scans completed"
    4. Browser updates UI
  
  This fills the gap (missed notifications).
```

### WebSocket Scaling Problem (Critical)

```
Issue: WebSocket connections are stateful and expensive

Scenario: 100K concurrent users on upload page

Load:
  • 100K WebSocket connections open simultaneously
  • Each connection: ~1-5 MB memory (connection state, buffers)
  • 100K × 3 MB = 300 GB RAM just for connections
  • Single machine can hold ~10K connections max
  
  → Need 10-20 machines just for WebSocket connections

Solution: WebSocket servers are stateless, but state is shared

  Architecture:
    
    Browser 1 ──┐
    Browser 2 ──┤
    Browser 3 ──┼─→ WebSocket Server A (handles 10K connections)
    Browser 4 ──┤
    ...         │
    Browser 10K┘
    
    Browser 10001 ──┐
    Browser 10002 ──┤
    ...            ├─→ WebSocket Server B (handles 10K connections)
    Browser 20K  ──┘
    
    Load Balancer (sticky routing)
    • Browser 1 always connects to Server A
    • Browser 10001 always connects to Server B

  Problem: How does scan completion on Server A notify users
           connected to Server B?
  
  Solution: Redis pub/sub
    
    Scan complete event:
      Server A publishes to Redis: channel="user_12345"
      Message: {upload_id, verdict, threat_name}
    
    All WebSocket servers subscribe to Redis:
      Server A: if (connection.user_id == 12345) emit(message)
      Server B: if (connection.user_id == 12345) emit(message)
      Server C: if (connection.user_id == 12345) emit(message)
    
    Each server checks local connections and sends to matching users
```

### WebSocket vs HTTP Polling (Latency Comparison)

```
HTTP Polling (every 2 seconds):
  
  T=0:   Browser polls: "Any new scans?"
  T=2:   Server responds: "No"
  T=2:   Scan actually completes
  T=4:   Browser polls again: "Any new scans?"
  T=4:   Server responds: "Yes, scan_123 is CLEAN"
  T=4:   Browser updates UI
  
  Worst case latency: 2 seconds (between polls)
  Network overhead: 1 poll every 2 seconds × 100K users = 50K req/sec

WebSocket (push):
  
  T=0:   WebSocket connection open
  T=0.5: Scan completes
  T=0.6: Server pushes notification
  T=0.7: Browser receives and updates UI
  
  Worst case latency: 0.1 seconds (network round-trip)
  Network overhead: Minimal (only on events)
```

---

## Option 3: HTTP Long Polling (Hybrid Approach)

### What It Is

Browser makes request, **server holds the connection open** until an event occurs.

```
Timeline:
  
  T=0:  Browser: "Do you have any notifications? (hold the line)"
  T=1:  Server: "Not yet, waiting..."
  T=2:  Server: "Not yet, waiting..."
  T=3:  Scan completes
  T=4:  Server: "YES! Here's your notification" → sends response
  T=5:  Browser receives and processes
  T=6:  Browser makes new request: "Any more notifications?"
  
Latency: 0-30 seconds (depends on event timing)
```

### Implementation

```python
from flask import Flask, request, jsonify
from datetime import datetime, timedelta
import time

app = Flask(__name__)

@app.route('/api/notifications/poll', methods=['GET'])
def poll_notifications():
    """
    Long polling endpoint
    
    Client logic:
      1. Make GET request
      2. Server holds connection for up to 30 seconds
      3. If event arrives: respond immediately with event
      4. If 30 seconds pass: respond with empty array
      5. Client immediately makes new request
    """
    
    user_id = request.args.get('user_id')
    timeout = 30  # seconds
    start_time = time.time()
    
    while time.time() - start_time < timeout:
        # Check for new notifications
        notifications = db.query("""
            SELECT * FROM notifications
            WHERE user_id = %s AND seen = FALSE
            ORDER BY created_at DESC
            LIMIT 10
        """, user_id)
        
        if notifications:
            # Events found! Respond immediately
            db.update('notifications', {'seen': True},
                     where={'user_id': user_id})
            
            return jsonify({
                'status': 'success',
                'notifications': [n.to_dict() for n in notifications]
            }), 200
        
        # No events yet, wait a bit and check again
        time.sleep(1)  # Check every 1 second
    
    # Timeout reached, respond with empty list
    return jsonify({
        'status': 'timeout',
        'notifications': []
    }), 200
```

### Long Polling Problems

```
Problem 1: Thundering herd on server restart
  
  Scenario:
    • 100K users polling, all connections held open
    • Server restarts
    • All 100K connections drop
    • All 100K clients reconnect SIMULTANEOUSLY
    • 100K req/sec spike (DoS-like)
  
  Solution:
    • Gradual rollout (canary deploy)
    • Stagger client reconnects: add random 0-30 second jitter

Problem 2: Duplicate notifications
  
  Scenario:
    • User polls, gets notification
    • Network hiccup: timeout after 15 seconds
    • Client retries: makes new poll request
    • Server sends notification again (database still shows unseen)
  
  Solution:
    • Idempotency: only mark notification as "seen" AFTER client ACKs
    • Use notification ID: client says "I got notification #123"
    • Server removes from queue only after ACK

Problem 3: Database load from constant polling
  
  Scenario:
    • 100K users polling every 30 seconds
    • Each poll = SELECT query
    • 100K / 30 = 3,333 queries/second
    • Database gets hammered
  
  Solution:
    • Cache with Redis: store recent notifications in cache
    • Use Redis BRPOP (blocking pop) instead of DB query
    • Or: Move to WebSocket (more efficient)
```

---

## Comparison: Which to Choose?

```
                    Webhook          WebSocket        Long Polling
                    ──────────────────────────────────────────────────
Latency             P50: 1-5 sec     P50: 10-50ms     P50: 1-2 sec
                    P99: 20 sec      P99: 100ms       P99: 30 sec

Delivery            At-least-once    At-most-once     At-most-once
guarantee           (with retries)   (fallback needed)(fallback needed)

Scalability         Horizontal       Vertical         Horizontal
                    (stateless)      (stateful, hard) (stateless)

Server load         Medium           High             Medium-High
                    (retry queue)    (RAM per conn)   (DB queries)

Network overhead    Low              Very low         Medium
                    (1 req per event)(1 msg per event)(1 req per 30s)

Browser support     99%              95%              99%
                    (HTTP)           (WS might block) (HTTP)

Use case            B2B (third-party B2C (real-time, B2B/B2C hybrid
                    integrations)    in-app UI)       (fallback path)
```

### My Recommendation for File Upload System

```
Tier 1 (Primary): WebSocket
  • User is on upload page (in-browser)
  • Real-time notification (< 100ms)
  • Low network overhead
  • Fallback to polling on disconnect

Tier 2 (Fallback): Email
  • User left the page
  • Guaranteed delivery (SMTP retry queue)
  • Async (user finds out within 5 minutes)
  • Works if WebSocket never connected

Tier 3 (Optional): Webhook
  • Customer's own integration
  • Async notification to their system
  • Reliable delivery with retries
  • For enterprise customers only

Architecture:

  Scan completes → Kafka event
  
  ├─→ WebSocket service
  │   └─→ Redis pub/sub
  │       └─→ Broadcast to connected browsers
  │
  ├─→ Email service
  │   └─→ Queue to SQS
  │       └─→ Send email async (fallback if not dismissed in 5 min)
  │
  └─→ Webhook service
      └─→ Customer webhooks (if configured)
```

---

## Handling Duplicate Notifications (Critical)

### Scenario

```
User's browser receives scan result notification twice.
This can happen because:

1. WebSocket reconnects + fallback polling both fire
2. Email + WebSocket both notify (user hasn't dismissed UI)
3. Webhook retry sends duplicate (customer is processing)
```

### Solution: Deduplication

```python
# Browser-side deduplication
const notificationCache = new Set();

socket.on('scan_result', (data) => {
    const notificationId = `${data.upload_id}_${data.verdict}`;
    
    // Skip if already seen
    if (notificationCache.has(notificationId)) {
        console.log('Duplicate notification, skipping');
        return;
    }
    
    notificationCache.add(notificationId);
    
    // Process notification
    showNotification(data);
    
    // Keep cache small: clear after 5 minutes
    setTimeout(() => {
        notificationCache.delete(notificationId);
    }, 5 * 60 * 1000);
});

// Similarly for polling
async function pollNotifications() {
    const response = await fetch(`/api/notifications/poll?user_id=${userId}`);
    const data = await response.json();
    
    data.notifications.forEach(notif => {
        // Browser de-dupes locally
        if (!notificationCache.has(notif.id)) {
            processNotification(notif);
            notificationCache.add(notif.id);
        }
    });
}
```

### Server-side Deduplication

```python
# Kafka consumer deduplicates within window
DEDUP_WINDOW = 60  # seconds

def process_scan_complete_event(event):
    upload_id = event['upload_id']
    user_id = event['user_id']
    
    # Check if we've seen this event recently
    cache_key = f"scan_complete:{upload_id}:{user_id}"
    
    if redis.exists(cache_key):
        print(f"Duplicate event for {upload_id}, skipping")
        return  # Skip reprocessing
    
    # Mark as seen (TTL: 60 seconds)
    redis.setex(cache_key, DEDUP_WINDOW, "1")
    
    # Process: send WebSocket, queue email, etc.
    notify_user(user_id, upload_id, event['verdict'])
```

---

## Interview Question Flowchart

```
Interviewer: "How do you notify the user?"

You: "Depends on context"

Interviewer: "User is on the upload page. How?"
You: "WebSocket for real-time (< 100ms latency)"

Interviewer: "What if network breaks?"
You: "Fallback to polling on reconnect. Query recent notifications"

Interviewer: "What if user leaves the page?"
You: "Email notification (fallback tier)"

Interviewer: "What if they get notified twice?"
You: "Deduplication with idempotency keys. Cache on browser + DB"

Interviewer: "What about third-party integrations?"
You: "Webhook with retry + exponential backoff. Customer must handle idempotency"

Interviewer: "How do you scale WebSocket to 100K users?"
You: "Stateless servers + Redis pub/sub for broadcasting. Sticky routing via LB"

Interviewer: "What if scan result arrives before user connects?"
You: "Store in database. On connect, check: 'Any scans I missed?'"

Interviewer: "What's your delivery guarantee?"
You: "Best-effort + fallback. WebSocket is at-most-once, webhook is at-least-once"

Interviewer: "How do you monitor notification health?"
You: "Track metrics: webhook delivery rate, email bounce rate, polling lag"
```

---

## Staff-Level Summary

| Aspect | Junior | Senior | Staff |
|---|---|---|---|
| Notification model | "Email" | "Webhook or polling" | "Tiered: WebSocket + email + webhook with dedup" |
| Scaling | Not considered | "Add servers" | "Stateful vs stateless tradeoff; Redis pub/sub for broadcast" |
| Failure handling | None | "Retry on 5xx" | "Retry strategy per service; idempotency keys; fallback tiers" |
| Deduplication | "Probably not needed" | "Maybe add request dedup" | "Multi-layer: browser cache + server-side + idempotency keys" |
| Delivery guarantee | Assumed | "At-least-once" | "Explicit per-tier: WebSocket=at-most, Webhook=at-least, Email=best-effort" |
| Monitoring | Not mentioned | "Log success/failure" | "Metrics dashboard: delivery rate, latency P50/P99, error rate by failure mode" |

**The Staff differentiator:** You proactively address **what happens when delivery fails**, not just the happy path. You design **multiple tiers** (WebSocket + email + webhook) so no single failure blocks the user. You understand **stateful vs stateless tradeoffs** and can explain why WebSocket scales with Redis pub/sub.

