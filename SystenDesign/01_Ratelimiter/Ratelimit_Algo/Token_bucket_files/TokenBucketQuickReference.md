# Token Bucket Implementation - Quick Reference for Interviews

## Core Algorithm in 30 Seconds

```
1. Each user has a bucket: tokens + last_refill_time
2. Calculate refill: tokens_to_add = (now - last_refill) × refill_rate
3. Add tokens: tokens = min(capacity, tokens + tokens_to_add)
4. If tokens > 0: allow request, decrement tokens
   Else: reject request (HTTP 429)
```

---

## Minimal Implementation (Copy-Paste Ready)

### Version 1: In-Memory (Single Server)
```java
public class TokenBucket {
    private long tokens;
    private long lastRefillTime;
    private final long capacity;
    private final long refillRatePerSecond;
    
    public TokenBucket(long capacity, long refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.tokens = capacity;
        this.lastRefillTime = System.currentTimeMillis();
    }
    
    public synchronized boolean allowRequest() {
        // Step 1: Refill tokens
        long now = System.currentTimeMillis();
        long elapsedSeconds = (now - lastRefillTime) / 1000;
        tokens = Math.min(capacity, tokens + (elapsedSeconds * refillRatePerSecond));
        lastRefillTime = now;
        
        // Step 2: Check and consume
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }
}
```

### Version 2: Redis (Distributed)
```java
public class RedisTokenBucket {
    private final JedisPool pool;
    private final String key;
    private final long capacity;
    private final long refillRate;
    
    private static final String LUA = 
        "local tokens = tonumber(redis.call('HGET', KEYS[1], 'tokens') or ARGV[1])\n" +
        "local last = tonumber(redis.call('HGET', KEYS[1], 'last') or ARGV[3])\n" +
        "local now = tonumber(ARGV[3])\n" +
        "local capacity = tonumber(ARGV[1])\n" +
        "local rate = tonumber(ARGV[2])\n" +
        "\n" +
        "local elapsed = (now - last) * rate\n" +
        "local new_tokens = math.min(capacity, tokens + elapsed)\n" +
        "local allowed = new_tokens > 0 and 1 or 0\n" +
        "\n" +
        "redis.call('HSET', KEYS[1], 'tokens', new_tokens - allowed, 'last', now)\n" +
        "return {allowed, new_tokens - 1}";
    
    public boolean allowRequest() {
        try (Jedis jedis = pool.getResource()) {
            List<Long> result = (List<Long>) jedis.eval(LUA, 1, key,
                    capacity, refillRate, System.currentTimeMillis() / 1000);
            return result.get(0) == 1;
        }
    }
}
```

---

## Interview Q&A Cheatsheet

### Q1: "How does token bucket work?"
**A:** "Imagine a bucket that holds tokens. Tokens are added at a constant rate (e.g., 10/sec). Each request costs 1 token. If bucket empty, reject. Bucket capacity (e.g., 100) allows burst traffic."

### Q2: "Why token bucket over other algorithms?"
**A:** 
- **vs Fixed Window**: No boundary effects
- **vs Sliding Window Log**: Memory efficient (2 values vs array)
- **vs Leaky Bucket**: Allows bursts

### Q3: "How to handle 1M RPS?"
**A:** "Single Redis can't handle it. Need:
1. Shard by client_id (hash(clientId) % num_shards)
2. ~20-30 Redis instances for 1M RPS
3. Connection pooling to reduce TCP overhead"

### Q4: "How to prevent race conditions?"
**A:** "Use Lua script. Without it, two concurrent requests might both read 1 token, both think they can proceed. Lua script makes read+modify+write atomic."

### Q5: "What if Redis fails?"
**A:** "Fail-closed: reject all requests (safer). Or fail-open with local in-memory fallback."

### Q6: "How to tune capacity and refill rate?"
**A:** "
- Capacity = acceptable burst size (e.g., 100)
- Refill rate = desired steady rate (e.g., 10/sec)
- For 100 requests/minute: capacity=100, rate=1.67"

---

## Data Structures

### Redis Storage Format
```
Key: "rate_limit:user_alice"
Value: {
  "tokens": 87,
  "last_refill": 1705322500
}
```

### HTTP Response Headers
```
X-RateLimit-Limit: 100           # Max requests per window
X-RateLimit-Remaining: 42        # Requests left
X-RateLimit-Reset: 1705322560    # Unix timestamp when resets

HTTP 429 Too Many Requests       # When rate limited
```

---

## Complexity Analysis

| Aspect | Complexity | Notes |
|--------|-----------|-------|
| **Time per request** | O(1) | Constant operations |
| **Space per client** | O(1) | Just 2 values (tokens, last_refill) |
| **Space total** | O(N) | N = number of unique clients |
| **Redis latency** | 1-5ms | Typically sub-millisecond ops |
| **Network overhead** | 2-4ms | TCP round-trip, connection pooling helps |

---

## Common Pitfalls & Solutions

### Pitfall 1: Cold Start
**Problem:** New users start with full bucket
**Solution:** Initialize to `capacity/2` or load from config

### Pitfall 2: Integer Overflow
**Problem:** `(elapsedTime × refillRate)` might overflow
**Solution:** Use long for calculations, validate inputs

### Pitfall 3: Time Zone Issues
**Problem:** Local time vs server time
**Solution:** Use `System.currentTimeMillis()` consistently

### Pitfall 4: No TTL in Redis
**Problem:** Stale entries accumulate
**Solution:** Set `EXPIRE` key to 3600 seconds (auto-cleanup)

### Pitfall 5: Single Redis Instance Bottleneck
**Problem:** Can't scale past 100k RPS
**Solution:** Shard by client_id

---

## Performance Checklist

- [ ] Use Redis connection pooling (Jedis, Lettuce)
- [ ] Pool size tuned to your RPS (usually 50-100)
- [ ] Lua script for atomic operations (no race conditions)
- [ ] TTL set on Redis keys (prevent memory leak)
- [ ] Proper error handling (fail-closed is safer)
- [ ] Latency monitoring (<10ms target)
- [ ] Multiple Redis shards for scale (if needed)
- [ ] Client identification strategy (JWT, IP, API key)

---

## Talking Points for Different Seniority Levels

### Junior/Mid-Level
- ✓ Understand algorithm (refill, capacity, refill_rate)
- ✓ Implement in-memory version
- ✓ Know why better than fixed window
- ✓ Discuss briefly: "might need Redis for scale"

### Senior Level
- ✓ Implement both in-memory and Redis versions
- ✓ Explain race conditions and Lua script solution
- ✓ Discuss sharding strategy (consistent hashing)
- ✓ Address failure modes (fail-open vs fail-closed)
- ✓ Know connection pooling and latency optimization
- ✓ Discuss monitoring/observability

### Staff Level
- ✓ All of above, plus:
- ✓ Redis Cluster internals (hash slots, migration)
- ✓ Production incidents (e.g., connection pool exhaustion)
- ✓ Cost analysis (instance sizing, throughput per $)
- ✓ Multi-region consistency trade-offs
- ✓ Alternative designs (hierarchical token buckets, etc.)

---

## Code Templates

### Template 1: API Gateway Integration
```java
public class RateLimitMiddleware {
    private final GlobalRateLimiter limiter;
    
    public void handleRequest(String clientId, String endpoint) {
        RateLimitResult result = limiter.checkRateLimit(clientId);
        
        if (!result.isAllowed()) {
            return new Response(429, "Too Many Requests")
                .withHeader("Retry-After", "60");
        }
        
        // Forward to microservice
        return forwardToService(endpoint);
    }
}
```

### Template 2: Layered Rate Limiting
```java
// Different limits for different tiers
Map<String, RateLimitRule> rules = new HashMap<>();
rules.put("premium_user", new Rule(1000, 100, 3600));  // 1000 cap, 100/sec
rules.put("free_user",    new Rule(100,  10,  3600));  // 100 cap, 10/sec
rules.put("ip_address",   new Rule(50,   5,   3600));  // 50 cap, 5/sec

RateLimitRule rule = getRuleForClient(clientId);
TokenBucket bucket = new TokenBucket(rule.capacity, rule.refillRate);
```

### Template 3: Monitoring
```java
// Track metrics
long allowed = result.allowed ? 1 : 0;
long remaining = result.remaining;
long latency = System.currentTimeMillis() - startTime;

metrics.record("rate_limit.allowed", allowed);
metrics.record("rate_limit.remaining", remaining);
metrics.record("rate_limit.latency_ms", latency);
```

---

## Key Takeaways

1. **Algorithm**: Refill tokens at constant rate, check if available
2. **Burst**: Bucket capacity allows burst, refill rate controls steady load
3. **Scale**: Use Redis with Lua scripts for atomic operations
4. **Safety**: Thread-safe (locks) for in-memory, atomic (Lua) for Redis
5. **Errors**: Fail-closed is safer (reject if can't decide)

---

## Resources to Study

- Token Bucket Algorithm: [Wikipedia](https://en.wikipedia.org/wiki/Token_bucket)
- Redis Lua Scripts: [Redis Documentation](https://redis.io/commands/eval/)
- Rate Limiting Best Practices: [AWS](https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-request-throttling.html)
- Jedis Documentation: [GitHub](https://github.com/redis/jedis)

---

**Good luck with your interviews!** 🚀
