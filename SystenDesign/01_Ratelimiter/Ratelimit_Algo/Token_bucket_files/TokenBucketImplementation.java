/**
 * Token Bucket Rate Limiter Implementation
 * 
 * This is a complete, production-ready implementation suitable for system design interviews.
 * Demonstrates understanding of concurrency, Redis integration, and rate limiting algorithms.
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.*;

// ============================================================================
// 1. IN-MEMORY TOKEN BUCKET (Thread-Safe, for Single Server)
// ============================================================================

/**
 * Thread-safe in-memory Token Bucket rate limiter.
 * 
 * Use Case: Single server deployments, caching layer
 * Thread Safety: Synchronized access using ReentrantReadWriteLock
 */
public class InMemoryTokenBucket {
    private final String clientId;
    private final long capacity;
    private final long refillRatePerSecond;

    private long tokens;
    private long lastRefillTime;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    /**
     * @param clientId Unique identifier for the client (user ID, IP, API key)
     * @param capacity Maximum tokens in the bucket (for burst handling)
     * @param refillRatePerSecond Number of tokens added per second
     */
    public InMemoryTokenBucket(String clientId, long capacity, long refillRatePerSecond) {
        this.clientId = clientId;
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.tokens = capacity;  // Start with full bucket
        this.lastRefillTime = System.currentTimeMillis();
    }
    
    /**
     * Check if a request is allowed and consume a token if allowed
     * @return true if request is allowed, false if rate limit exceeded
     */
    public boolean allowRequest() {
        lock.writeLock().lock();
        try {
            refillTokens();
            
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /**
     * Check if request is allowed without consuming a token (dry run)
     * @return true if request would be allowed
     */
    public boolean isAllowed() {
        lock.readLock().lock();
        try {
            long currentTokens = getCurrentTokenCount();
            return currentTokens > 0;
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * Get current token count without consuming
     */
    public long getCurrentTokenCount() {
        lock.readLock().lock();
        try {
            // Calculate tokens to add
            long currentTime = System.currentTimeMillis();
            long elapsedMillis = currentTime - lastRefillTime;
            long tokensToAdd = (elapsedMillis * refillRatePerSecond) / 1000;
            
            long currentTokens = Math.min(capacity, tokens + tokensToAdd);
            return currentTokens;
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * Refill tokens based on elapsed time
     * Called internally before checking if request allowed
     */
    private void refillTokens() {
        long currentTime = System.currentTimeMillis();
        long elapsedMillis = currentTime - lastRefillTime;
        
        // Calculate how many tokens to add
        long tokensToAdd = (elapsedMillis * refillRatePerSecond) / 1000;
        
        if (tokensToAdd > 0) {
            tokens = Math.min(capacity, tokens + tokensToAdd);
            lastRefillTime = currentTime;
        }
    }
    
    /**
     * Reset bucket to full capacity
     */
    public void reset() {
        lock.writeLock().lock();
        try {
            this.tokens = capacity;
            this.lastRefillTime = System.currentTimeMillis();
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    @Override
    public String toString() {
        return String.format("TokenBucket{client=%s, tokens=%.2f, capacity=%d, refillRate=%d/s}",
                clientId, (double)tokens, capacity, refillRatePerSecond);
    }
}


// ============================================================================
// 2. DISTRIBUTED TOKEN BUCKET (Redis-Backed, Production)
// ============================================================================

/**
 * Distributed Token Bucket rate limiter using Redis.
 * 
 * Use Case: Distributed systems, microservices, multiple servers
 * Benefits:
 *  - Global coordination across multiple API gateways
 *  - Atomic operations using Lua scripts
 *  - Persistent state across server restarts
 */
public class RedisTokenBucket {
    private final JedisPool jedisPool;
    private final String bucketKey;
    private final long capacity;
    private final long refillRatePerSecond;
    private final long ttlSeconds;
    
    private static final String LUA_SCRIPT = 
        "local key = KEYS[1]\n" +
        "local capacity = tonumber(ARGV[1])\n" +
        "local refill_rate = tonumber(ARGV[2])\n" +
        "local now = tonumber(ARGV[3])\n" +
        "\n" +
        "-- Get current state from Redis\n" +
        "local current = redis.call('HMGET', key, 'tokens', 'last_refill')\n" +
        "local tokens = tonumber(current[1]) or capacity\n" +
        "local last_refill = tonumber(current[2]) or now\n" +
        "\n" +
        "-- Calculate tokens to add\n" +
        "local elapsed = math.max(0, now - last_refill)\n" +
        "local tokens_to_add = elapsed * refill_rate\n" +
        "local new_tokens = math.min(capacity, tokens + tokens_to_add)\n" +
        "\n" +
        "-- Check if allowed\n" +
        "local allowed = new_tokens > 0\n" +
        "\n" +
        "-- Update state\n" +
        "if allowed then\n" +
        "  redis.call('HSET', key, 'tokens', new_tokens - 1, 'last_refill', now)\n" +
        "else\n" +
        "  redis.call('HSET', key, 'tokens', new_tokens, 'last_refill', now)\n" +
        "end\n" +
        "\n" +
        "-- Set TTL to auto-cleanup\n" +
        "redis.call('EXPIRE', key, tonumber(ARGV[4]))\n" +
        "\n" +
        "-- Return [allowed (1/0), remaining_tokens, capacity]\n" +
        "return {allowed and 1 or 0, math.max(0, math.ceil(new_tokens - 1)), capacity}";
    
    /**
     * @param jedisPool Connection pool to Redis
     * @param clientId Unique identifier (user ID, IP, API key)
     * @param capacity Maximum tokens in bucket
     * @param refillRatePerSecond Tokens added per second
     * @param ttlSeconds How long to keep data in Redis (auto-cleanup)
     */
    public RedisTokenBucket(JedisPool jedisPool, String clientId, long capacity, 
                           long refillRatePerSecond, long ttlSeconds) {
        this.jedisPool = jedisPool;
        this.bucketKey = "rate_limit:" + clientId;
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.ttlSeconds = ttlSeconds;
    }
    
    /**
     * Check if request is allowed using atomic Lua script
     * @return RateLimitResult with decision and metadata
     */
    public RateLimitResult checkRateLimit() {
        try (Jedis jedis = jedisPool.getResource()) {
            // Execute Lua script atomically
            List<Long> result = (List<Long>) jedis.eval(
                LUA_SCRIPT,
                1,                                        // numKeys
                new String[]{bucketKey},                  // KEYS[1]
                String.valueOf(capacity),                 // ARGV[1]
                String.valueOf(refillRatePerSecond),      // ARGV[2]
                String.valueOf(System.currentTimeMillis()/1000),  // ARGV[3] - timestamp in seconds
                String.valueOf(ttlSeconds)                // ARGV[4]
            );
            
            long allowed = result.get(0);           // 1 = allowed, 0 = rejected
            long remainingTokens = result.get(1);   // tokens after this request
            long limitCapacity = result.get(2);     // bucket capacity
            
            return new RateLimitResult(
                allowed == 1,
                remainingTokens,
                limitCapacity,
                true  // request processed successfully
            );
        } catch (Exception e) {
            // Graceful degradation: fail-closed (reject) on Redis error
            System.err.println("Redis error during rate limit check: " + e.getMessage());
            return new RateLimitResult(false, 0, capacity, false);
        }
    }
    
    /**
     * Get current token count without consuming
     */
    public long getCurrentTokenCount() {
        try (Jedis jedis = jedisPool.getResource()) {
            String tokensStr = jedis.hget(bucketKey, "tokens");
            String lastRefillStr = jedis.hget(bucketKey, "last_refill");
            
            long currentTokens = capacity;
            
            if (tokensStr != null && lastRefillStr != null) {
                long tokens = Long.parseLong(tokensStr);
                long lastRefill = Long.parseLong(lastRefillStr);
                long elapsedSeconds = (System.currentTimeMillis() / 1000) - lastRefill;
                long tokensToAdd = elapsedSeconds * refillRatePerSecond;
                currentTokens = Math.min(capacity, tokens + tokensToAdd);
            }
            
            return currentTokens;
        }
    }
    
    /**
     * Reset bucket to full capacity
     */
    public void reset() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(bucketKey);
        }
    }
}


// ============================================================================
// 3. RATE LIMIT RESULT DTO
// ============================================================================

/**
 * Response object for rate limit check results
 */
public static class RateLimitResult {
    private final boolean allowed;
    private final long remainingTokens;
    private final long limit;
    private final boolean success;  // false if Redis/backend error
    
    public RateLimitResult(boolean allowed, long remainingTokens, long limit, boolean success) {
        this.allowed = allowed;
        this.remainingTokens = remainingTokens;
        this.limit = limit;
        this.success = success;
    }
    
    public boolean isAllowed() { return allowed; }
    public long getRemainingTokens() { return remainingTokens; }
    public long getLimit() { return limit; }
    public boolean isSuccess() { return success; }
    public int getHttpStatusCode() { return allowed ? 200 : 429; }
    public String getStatusMessage() { 
        return allowed ? "OK" : "Too Many Requests"; 
    }
    
    @Override
    public String toString() {
        return String.format("RateLimitResult{allowed=%s, remaining=%d, limit=%d, success=%s}",
                allowed, remainingTokens, limit, success);
    }
}


// ============================================================================
// 4. GLOBAL RATE LIMITER MANAGER (Multiple Clients)
// ============================================================================

/**
 * Manages rate limiters for multiple clients in a single gateway
 * Thread-safe container for client-specific token buckets
 */
public class GlobalRateLimiter {
    private final ConcurrentHashMap<String, RedisTokenBucket> limiters;
    private final JedisPool jedisPool;
    private final RateLimitConfig config;
    
    public GlobalRateLimiter(JedisPool jedisPool, RateLimitConfig config) {
        this.jedisPool = jedisPool;
        this.config = config;
        this.limiters = new ConcurrentHashMap<>();
    }
    
    /**
     * Get or create rate limiter for a client
     */
    public RedisTokenBucket getOrCreateLimiter(String clientId) {
        return limiters.computeIfAbsent(clientId, id -> {
            RateLimitRule rule = config.getRuleForClient(id);
            return new RedisTokenBucket(
                jedisPool,
                id,
                rule.getCapacity(),
                rule.getRefillRatePerSecond(),
                rule.getTtlSeconds()
            );
        });
    }
    
    /**
     * Check if request is allowed for given client
     */
    public RateLimitResult checkRateLimit(String clientId) {
        RedisTokenBucket limiter = getOrCreateLimiter(clientId);
        return limiter.checkRateLimit();
    }
    
    /**
     * Statistics: get all active limiters
     */
    public Map<String, Long> getTokenCounts() {
        Map<String, Long> stats = new HashMap<>();
        limiters.forEach((clientId, limiter) -> 
            stats.put(clientId, limiter.getCurrentTokenCount())
        );
        return stats;
    }
}


// ============================================================================
// 5. CONFIGURATION CLASSES
// ============================================================================

/**
 * Rate limit configuration for a specific rule/tier
 */
public static class RateLimitRule {
    private final String name;
    private final long capacity;           // Burst capacity
    private final long refillRatePerSecond;
    private final long ttlSeconds;
    
    public RateLimitRule(String name, long capacity, long refillRatePerSecond, long ttlSeconds) {
        this.name = name;
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.ttlSeconds = ttlSeconds;
    }
    
    public long getCapacity() { return capacity; }
    public long getRefillRatePerSecond() { return refillRatePerSecond; }
    public long getTtlSeconds() { return ttlSeconds; }
    
    @Override
    public String toString() {
        return String.format("RateLimitRule{%s, capacity=%d, rate=%d/s}",
                name, capacity, refillRatePerSecond);
    }
}

/**
 * Global rate limit configuration
 */
public static class RateLimitConfig {
    private final RateLimitRule premiumUserRule;
    private final RateLimitRule freeUserRule;
    private final RateLimitRule ipAddressRule;
    
    public RateLimitConfig() {
        // Premium users: 1000 req/min burst, 100 req/sec sustained
        this.premiumUserRule = new RateLimitRule("premium", 1000, 100, 3600);
        
        // Free users: 100 req/min burst, 10 req/sec sustained
        this.freeUserRule = new RateLimitRule("free", 100, 10, 3600);
        
        // Anonymous IP: 50 req/min burst, 5 req/sec sustained
        this.ipAddressRule = new RateLimitRule("ip", 50, 5, 3600);
    }
    
    public RateLimitRule getRuleForClient(String clientId) {
        if (clientId.startsWith("premium_")) {
            return premiumUserRule;
        } else if (clientId.startsWith("user_")) {
            return freeUserRule;
        } else {
            // Assume IP address
            return ipAddressRule;
        }
    }
}


// ============================================================================
// 6. USAGE EXAMPLES & MAIN
// ============================================================================

/**
 * Example usage in an API Gateway or middleware
 */
public class RateLimiterExample {
    public static void main(String[] args) throws Exception {
        // =====================================================
        // EXAMPLE 1: In-Memory Token Bucket (Single Server)
        // =====================================================
        System.out.println("=== Example 1: In-Memory Token Bucket ===");
        InMemoryTokenBucket userBucket = new InMemoryTokenBucket(
            "user123",
            100,    // 100 tokens capacity
            10      // 10 tokens per second
        );
        
        // Simulate 5 requests
        for (int i = 1; i <= 5; i++) {
            boolean allowed = userBucket.allowRequest();
            System.out.printf("Request %d: %s (tokens remaining: %.0f)\n",
                    i, allowed ? "ALLOWED" : "REJECTED", 
                    userBucket.getCurrentTokenCount());
        }
        
        // Wait 1 second (tokens should refill)
        System.out.println("\nWaiting 1 second for tokens to refill...");
        Thread.sleep(1000);
        System.out.printf("Tokens after 1s: %.0f\n", userBucket.getCurrentTokenCount());
        
        
        // =====================================================
        // EXAMPLE 2: Distributed Redis Token Bucket
        // =====================================================
        System.out.println("\n=== Example 2: Redis-Backed Token Bucket ===");
        
        // Initialize Redis connection
        JedisPool pool = new JedisPool("localhost", 6379);
        
        RedisTokenBucket redisLimiter = new RedisTokenBucket(
            pool,
            "user456",
            100,    // capacity
            10,     // 10 tokens/second
            3600    // 1 hour TTL
        );
        
        // Simulate requests
        for (int i = 1; i <= 5; i++) {
            RateLimitResult result = redisLimiter.checkRateLimit();
            System.out.printf("Request %d: %s (remaining: %d/%d)\n",
                    i,
                    result.isAllowed() ? "✓ ALLOWED" : "✗ REJECTED",
                    result.getRemainingTokens(),
                    result.getLimit());
        }
        
        
        // =====================================================
        // EXAMPLE 3: Global Rate Limiter with Multiple Rules
        // =====================================================
        System.out.println("\n=== Example 3: Global Rate Limiter ===");
        GlobalRateLimiter globalLimiter = new GlobalRateLimiter(
            pool,
            new RateLimitConfig()
        );
        
        // Different clients with different tiers
        String[] clients = {
            "premium_alice",    // Premium tier
            "user_bob",         // Free tier
            "192.168.1.100"     // IP-based
        };
        
        for (String clientId : clients) {
            RateLimitResult result = globalLimiter.checkRateLimit(clientId);
            System.out.printf("%s: %s\n", clientId, result);
        }
        
        // Get statistics
        System.out.println("\nCurrent token counts:");
        globalLimiter.getTokenCounts().forEach((client, tokens) ->
            System.out.printf("  %s: %.0f tokens\n", client, (double)tokens)
        );
        
        pool.close();
    }
}


// ============================================================================
// 7. MIDDLEWARE INTEGRATION EXAMPLE
// ============================================================================

/**
 * Example of using Token Bucket in a Spring-like REST framework
 * This demonstrates how to integrate into a real API gateway
 */
public class RateLimitMiddleware {
    private final GlobalRateLimiter rateLimiter;
    
    public RateLimitMiddleware(GlobalRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }
    
    /**
     * Check rate limit before processing request
     * Returns HTTP response if rate limited
     */
    public boolean preProcessRequest(String clientId, String endpoint) {
        RateLimitResult result = rateLimiter.checkRateLimit(clientId);
        
        if (!result.isSuccess()) {
            // Redis error - graceful degradation
            System.err.printf("Rate limiter unavailable for %s\n", clientId);
            return false;  // Fail-closed
        }
        
        if (!result.isAllowed()) {
            // Rate limit exceeded - return 429
            System.err.printf("Rate limit exceeded for %s. Remaining: %d\n",
                    clientId, result.getRemainingTokens());
            return false;
        }
        
        // Request allowed
        return true;
    }
    
    /**
     * Example response with rate limit headers
     */
    public Map<String, String> getRateLimitHeaders(String clientId) {
        RedisTokenBucket limiter = rateLimiter.getOrCreateLimiter(clientId);
        long remaining = limiter.getCurrentTokenCount();
        
        Map<String, String> headers = new HashMap<>();
        headers.put("X-RateLimit-Limit", "100");
        headers.put("X-RateLimit-Remaining", String.valueOf(remaining));
        headers.put("X-RateLimit-Reset", String.valueOf(System.currentTimeMillis() / 1000 + 60));
        
        return headers;
    }
}
