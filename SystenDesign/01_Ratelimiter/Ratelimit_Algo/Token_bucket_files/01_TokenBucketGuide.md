# Token Bucket Rate Limiter - Complete Java Implementation Guide

## 📚 What You Have

This package contains everything you need for Token Bucket rate limiting interviews:

### Files Included:
1. **TokenBucketImplementation.java** - Complete, production-ready implementation
   - In-memory single-server version
   - Distributed Redis version
   - Global rate limiter manager
   - Usage examples and middleware integration
   - ~450 lines, fully commented

2. **TokenBucketInterviewVersion.java** - Clean, interview-focused version
   - Simple in-memory implementation
   - Distributed Redis implementation
   - Key interview talking points
   - ~350 lines, very readable

3. **TokenBucketQuickReference.md** - Cheatsheet for quick lookup
   - Algorithm overview
   - Q&A pairs
   - Code templates
   - Common pitfalls

4. **rate_limiter_system_design.md** - Full system design document
   - Algorithm comparison table
   - Architecture diagrams
   - Deep dive sections
   - Appendix with all code examples

---

## 🎯 How to Use This in Your Interview

### Before the Interview (30 minutes)
1. **Read QuickReference.md** - Get algorithm in your head
2. **Study the minimal implementations** - Can you write from memory?
3. **Understand the talking points** - Practice explaining out loud

### During the Interview (60 minutes)

#### First 10 minutes: Explain Algorithm
**Interviewer:** "Design a rate limiter"

**Your approach:**
1. Ask clarifying questions (1M RPS? Single server? Distributed?)
2. Explain Token Bucket concept (2 min)
   - "Each user has a bucket holding tokens"
   - "Tokens refill at constant rate"
   - "Each request costs 1 token"
   - "If empty, reject (429)"
3. Sketch on whiteboard
4. Discuss why it's good (burst handling, simple)

#### Next 20 minutes: High-Level Design
1. Where to place rate limiter? (API Gateway)
2. How to identify clients? (JWT, IP, API key)
3. How to store tokens? (Redis for distributed)
4. Response format (HTTP 429 + headers)

#### Next 20 minutes: Implementation & Deep Dives
- "Let me write the code..."
- **Start simple (in-memory)**
- **Then add Redis** (when asked about scale)
- Discuss thread safety (locks, Lua scripts)
- Talk about failure modes (fail-closed vs open)

#### Last 10 minutes: Scalability & Optimization
- Redis sharding strategy
- Connection pooling
- Latency optimization
- Monitoring/observability

---

## 📝 Code You Can Write

### Minute 1-5: Basic Algorithm
```java
public class TokenBucket {
    private long tokens;
    private long lastRefillTime;
    private final long capacity;
    private final long refillRatePerSecond;
    
    public synchronized boolean allowRequest() {
        // Refill
        long now = System.currentTimeMillis();
        long elapsed = (now - lastRefillTime) / 1000;
        tokens = Math.min(capacity, tokens + elapsed * refillRatePerSecond);
        lastRefillTime = now;
        
        // Check and consume
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }
}
```

### Minute 5-15: Add Redis
```java
public class RedisTokenBucket {
    private final JedisPool pool;
    private final String key;
    
    private static final String LUA_SCRIPT = "..."; // See implementation files
    
    public boolean checkRateLimit() {
        try (Jedis jedis = pool.getResource()) {
            List<Long> result = (List<Long>) jedis.eval(LUA_SCRIPT, 1, key, ...);
            return result.get(0) == 1;
        }
    }
}
```

### Minute 15-20: Global Manager
```java
public class GlobalRateLimiter {
    private final Map<String, RedisTokenBucket> limiters = new ConcurrentHashMap<>();
    
    public RateLimitResult checkRateLimit(String clientId) {
        RedisTokenBucket limiter = limiters.computeIfAbsent(clientId, id -> 
            new RedisTokenBucket(pool, id, ...));
        return limiter.checkRateLimit();
    }
}
```

---

## 🚀 Different Seniority Approaches

### Junior Candidate (Mid-Level Ready)
**Focus:** Algorithm understanding + basic implementation

✓ Explain token bucket concept clearly  
✓ Write in-memory version from scratch  
✓ Understand why better than fixed window  
✓ Know it needs Redis for scale  
⚠️ Might not implement Redis version  
⚠️ May not discuss race conditions deeply  

**Time allocation:**
- 30 min: Explain + design
- 20 min: Code in-memory version
- 10 min: Discuss Redis

### Senior Candidate
**Focus:** Both implementations + trade-offs

✓ Explain algorithm with burst example  
✓ Implement in-memory AND Redis versions  
✓ Explain Lua script and race conditions  
✓ Discuss sharding strategy  
✓ Address failure modes  
✓ Mention monitoring/observability  

**Time allocation:**
- 10 min: Explain + design
- 15 min: In-memory code
- 20 min: Redis + Lua script
- 10 min: Sharding, failures, monitoring
- 5 min: Q&A

### Staff Candidate
**Focus:** Production considerations + optimization

✓ All of senior, plus:  
✓ Redis Cluster internals  
✓ Production incident discussion  
✓ Cost analysis  
✓ Multi-region strategy  
✓ Alternative designs (hierarchical buckets, etc.)  

**Time allocation:**
- 5 min: Quick explanation
- 15 min: Focused implementation
- 25 min: Deep dives (clusters, failures, optimization)
- 10 min: Discussion and edge cases
- 5 min: Q&A

---

## 🎓 Key Interview Concepts

### 1. Algorithm Explanation (2 min)
"Token bucket: each client has a bucket holding tokens. Tokens refill at a steady rate (e.g., 10/sec). Each request costs 1 token. Bucket capacity (e.g., 100) allows bursts. If bucket empty, reject."

### 2. Why Token Bucket? (2 min)
- **vs Fixed Window:** No boundary effects
- **vs Sliding Window Log:** Memory efficient (O(1) vs O(n))
- **vs Leaky Bucket:** Allows bursts (important for UX)

### 3. Thread Safety (3 min)
**In-Memory:** ReentrantReadWriteLock
```java
lock.writeLock().lock();
try { ... } finally { lock.writeLock().unlock(); }
```

**Distributed:** Lua script atomicity
```lua
redis.call('HGET', key, 'tokens')
-- ... calculate ...
redis.call('HSET', key, 'tokens', new_tokens)
-- All atomic, no race condition
```

### 4. Scalability to 1M RPS (5 min)
- Single Redis: ~100k ops/sec
- 1M RPS × 2 ops (HMGET + HSET) = 2M ops needed
- Solution: Shard by `hash(clientId) % num_shards`
- Need ~20 primary shards
- Add replicas for HA: ~40 total instances

### 5. Failure Scenarios (3 min)
- **Redis down:** Fail-closed (reject) is safer
- **Network latency spike:** Add timeout + circuit breaker
- **Redis slot migration:** Transparent to client (Cluster handles)

---

## ✅ Checklist Before Interview

- [ ] Can explain algorithm in 2 minutes
- [ ] Can write in-memory version in 10 minutes
- [ ] Can add Redis version in next 10 minutes
- [ ] Understand Lua script prevents race conditions
- [ ] Know 1M RPS needs sharding (~20 instances)
- [ ] Familiar with fail-open vs fail-closed trade-off
- [ ] Can discuss connection pooling optimization
- [ ] Know HTTP 429 + X-RateLimit-* headers
- [ ] Have examples of burst vs steady state
- [ ] Can compare with other algorithms

---

## 📊 Quick Reference Table

| Aspect | Answer |
|--------|--------|
| **Best for** | Interactive APIs, SaaS, REST APIs |
| **Worst for** | Background jobs (no burst), strict fairness |
| **Time complexity** | O(1) per request |
| **Space per client** | O(1) - just 2 numbers |
| **Thread safe?** | Yes (locks in-memory, Lua for Redis) |
| **Burst support** | ✓ Yes (bucket capacity) |
| **Boundary effects** | ✗ No |
| **Memory efficient** | ✓ Yes |
| **Redis needed** | Only for distributed (multiple servers) |
| **Latency target** | <10ms (usually 1-5ms with pooling) |
| **HTTP status** | 429 Too Many Requests |

---

## 🏗️ System Design Components

```
User Request
    ↓
API Gateway (with Rate Limiter)
    ├─ 1. Extract clientId (JWT, IP, API key)
    ├─ 2. Check TokenBucket for clientId
    │   ├─ If allowed: Forward to service
    │   └─ If rejected: Return 429
    ↓
Rate Limiter (In-Memory or Redis)
    ├─ In-Memory: Fast, single server only
    └─ Redis: Slower (~2-4ms), distributed, scalable
    
Rate Limit Response
    ├─ X-RateLimit-Limit: 100
    ├─ X-RateLimit-Remaining: 45
    ├─ X-RateLimit-Reset: 1705322400
    └─ Retry-After: 47 (on 429)
```

---

## 💡 Pro Tips

1. **Start simple, add complexity on demand**
   - Begin with in-memory single-server
   - "For distributed systems, we'd use Redis..."

2. **Use examples to explain**
   - "Capacity 100, refill 10/sec. At t=0, user makes 100 requests (all OK, bucket empty). At t=1s, 10 new tokens added, 1 request OK. Steady state: 1 request per 100ms"

3. **Draw on whiteboard**
   - Token bucket diagram
   - Timeline showing burst then steady rate
   - Architecture: Client → Gateway → Redis → Microservice

4. **Address trade-offs proactively**
   - "Token bucket vs sliding window: TB simpler, SW more accurate"
   - "Redis adds latency but enables scale"
   - "Lua script prevents race condition at cost of complexity"

5. **Have a failure story ready**
   - "At my last company, Redis connection pool was undersized..."
   - Shows production experience

---

## 📚 Further Reading

If interviewer asks deep questions:

### Redis Internals
- [Redis Lua Scripting](https://redis.io/commands/eval/)
- [Redis Cluster Specification](https://redis.io/docs/reference/cluster-spec/)
- [Connection Pooling Best Practices](https://redis.io/docs/reference/client-side-caching/protocol/)

### Rate Limiting
- [AWS API Gateway Throttling](https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-request-throttling.html)
- [Stripe Rate Limiting](https://stripe.com/docs/rate-limits)
- [GitHub Rate Limiting](https://docs.github.com/en/rest/overview/rate-limits-for-the-rest-api)

### Java Libraries
- [Jedis Connection Pooling](https://github.com/redis/jedis)
- [Guava RateLimiter](https://guava.dev/releases/snapshot-jvm/api/docs/com/google/common/util/concurrent/RateLimiter.html)

---

## 🎯 Success Criteria

**You passed the interview if:**
- ✓ Explained algorithm clearly
- ✓ Wrote working in-memory code
- ✓ Discussed distributed (Redis) approach
- ✓ Addressed at least one deep dive (threading, sharding, failures)
- ✓ Answered all follow-up questions reasonably

**You nailed it if:**
- ✓ All of above, plus:
- ✓ Implemented both in-memory and Redis
- ✓ Explained Lua script race condition solution
- ✓ Discussed sharding strategy
- ✓ Mentioned monitoring/observability

---

## 🤝 Common Follow-Up Questions

**Q: "What if we need per-endpoint rate limits?"**
A: Extend key: `rate_limit:user_alice:post_tweet` and `rate_limit:user_alice:get_timeline`

**Q: "How to reset rate limits?"**
A: `jedis.del(bucketKey)` or just wait for TTL

**Q: "Can users bypass limits?"**
A: Not if clientId is secure (JWT signature verified). IP-based can be spoofed.

**Q: "What if need different limits per time of day?"**
A: Load rules from config service (Zookeeper, Consul). Update rules in gateway memory.

**Q: "How to test this?"**
A: Mock Jedis, test concurrent access, verify race conditions fixed by Lua

---

## 📞 Interview Day Checklist

- [ ] Print these files or have on second monitor
- [ ] Compile the code before interview (catch any syntax errors)
- [ ] Practice writing code by hand
- [ ] Prepare to draw diagrams
- [ ] Have Jedis/Redis documentation handy
- [ ] Be ready to pivot (fast vs distributed, simple vs complex)
- [ ] Know your trade-offs
- [ ] Practice saying "let me think about that" when stuck

---

**You've got this! Good luck! 🚀**
