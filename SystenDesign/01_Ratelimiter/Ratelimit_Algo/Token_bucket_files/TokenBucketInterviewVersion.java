/**
 * TOKEN BUCKET RATE LIMITER - INTERVIEW VERSION
 * 
 * Clean, well-commented implementation perfect for system design interviews.
 * Start with this simple version and add Redis support when asked about scalability.
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

// ============================================================================
// SIMPLE VERSION (Start here in interviews)
// ============================================================================

/**
 * Basic Token Bucket implementation
 * 
 * Algorithm Overview:
 * 1. Each client has a bucket containing tokens
 * 2. Tokens are added at a constant rate (refill_rate tokens per second)
 * 3. Each request consumes 1 token
 * 4. If bucket is empty, request is rejected (429 Too Many Requests)
 * 5. Bucket capacity limits burst size
 */
public class TokenBucket {
    private final String clientId;
    private long tokens;                    // Current number of tokens
    private long lastRefillTime;           // Timestamp of last refill
    private final long capacity;           // Max tokens (burst size)
    private final long refillRatePerSecond; // Tokens added per second
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    /**
     * Constructor
     * 
     * @param clientId Unique identifier (user ID, IP, API key)
     * @param capacity Maximum burst capacity (e.g., 100 tokens)
     * @param refillRatePerSecond Steady-state rate (e.g., 10 tokens/sec)
     */
    public TokenBucket(String clientId, long capacity, long refillRatePerSecond) {
        this.clientId = clientId;
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.tokens = capacity;              // Start with full bucket
        this.lastRefillTime = System.currentTimeMillis();
    }
    
    /**
     * Check if request is allowed and consume a token
     * This is the core rate limiting logic
     * 
     * @return true if request allowed, false if rate limited
     */
    public boolean allowRequest() {
        lock.writeLock().lock();
        try {
            // Step 1: Refill tokens based on elapsed time
            refillTokens();
            
            // Step 2: Check if tokens available
            if (tokens > 0) {
                tokens--;  // Consume one token
                return true;  // ✓ Request allowed
            }
            return false;  // ✗ Rate limit exceeded
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /**
     * Refill tokens based on time elapsed since last refill
     * 
     * Formula: tokens_to_add = elapsed_time_in_seconds × refill_rate
     */
    private void refillTokens() {
        long currentTime = System.currentTimeMillis();
        long elapsedMillis = currentTime - lastRefillTime;
        
        // Calculate tokens to add: convert ms to seconds, multiply by rate
        long tokensToAdd = (elapsedMillis * refillRatePerSecond) / 1000;
        
        if (tokensToAdd > 0) {
            // Add tokens but never exceed capacity
            tokens = Math.min(capacity, tokens + tokensToAdd);
            lastRefillTime = currentTime;
        }
    }
    
    /**
     * Get remaining tokens without consuming any
     * Useful for client feedback ("50 requests remaining")
     */
    public long getRemainingTokens() {
        lock.readLock().lock();
        try {
            long currentTime = System.currentTimeMillis();
            long elapsedMillis = currentTime - lastRefillTime;
            long tokensToAdd = (elapsedMillis * refillRatePerSecond) / 1000;
            return Math.min(capacity, tokens + tokensToAdd);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public String getClientId() { return clientId; }
    public long getCapacity() { return capacity; }
    public long getRefillRatePerSecond() { return refillRatePerSecond; }
}


// ============================================================================
// DISTRIBUTED VERSION (Add this when asked about scalability)
// ============================================================================

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.List;

/**
 * Distributed Token Bucket using Redis
 * 
 * Why Redis?
 * - Multiple gateways need shared state
 * - Atomic operations via Lua scripts
 * - Persistent across restarts
 * - High performance (sub-millisecond)
 * 
 * Thread Safety: Handled by Lua script atomicity
 */
public class DistributedTokenBucket {
    private final JedisPool jedisPool;
    private final String bucketKey;
    private final long capacity;
    private final long refillRatePerSecond;
    private final long ttlSeconds;
    
    // Lua script for atomic rate limit check
    // This ensures all operations happen together without race conditions
    private static final String LUA_SCRIPT = 
        "local key = KEYS[1]\n" +
        "local capacity = tonumber(ARGV[1])\n" +
        "local refill_rate = tonumber(ARGV[2])\n" +
        "local now = tonumber(ARGV[3])\n" +
        "local ttl = tonumber(ARGV[4])\n" +
        "\n" +
        "-- Fetch current bucket state from Redis\n" +
        "local current = redis.call('HMGET', key, 'tokens', 'last_refill')\n" +
        "local tokens = tonumber(current[1]) or capacity\n" +
        "local last_refill = tonumber(current[2]) or now\n" +
        "\n" +
        "-- Calculate how many tokens to add\n" +
        "local elapsed_seconds = math.max(0, now - last_refill)\n" +
        "local tokens_to_add = elapsed_seconds * refill_rate\n" +
        "local new_tokens = math.min(capacity, tokens + tokens_to_add)\n" +
        "\n" +
        "-- Check if request allowed\n" +
        "local allowed = (new_tokens > 0) and 1 or 0\n" +
        "\n" +
        "-- Update bucket state\n" +
        "if allowed == 1 then\n" +
        "  redis.call('HSET', key, 'tokens', new_tokens - 1, 'last_refill', now)\n" +
        "else\n" +
        "  redis.call('HSET', key, 'tokens', new_tokens, 'last_refill', now)\n" +
        "end\n" +
        "\n" +
        "-- Set TTL for auto-cleanup (prevent memory leak)\n" +
        "redis.call('EXPIRE', key, ttl)\n" +
        "\n" +
        "-- Return [allowed, remaining_tokens, capacity]\n" +
        "return {allowed, math.ceil(new_tokens - 1), capacity}";
    
    /**
     * Constructor
     */
    public DistributedTokenBucket(JedisPool jedisPool, String clientId, 
                                  long capacity, long refillRatePerSecond, long ttlSeconds) {
        this.jedisPool = jedisPool;
        this.bucketKey = "rate_limit:" + clientId;
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.ttlSeconds = ttlSeconds;
    }
    
    /**
     * Check rate limit using atomic Lua script
     * 
     * Why Lua? Prevents race conditions:
     * Without Lua, two concurrent requests might:
     * 1. Both read: tokens = 1
     * 2. Both check: 1 > 0? Yes
     * 3. Both decrement: tokens = 0
     * Result: 2 requests allowed when limit was 1
     * 
     * With Lua: All operations atomic, no race condition
     */
    public RateLimitResponse checkRateLimit() {
        try (Jedis jedis = jedisPool.getResource()) {
            // Execute Lua script atomically on Redis server
            List<Long> result = (List<Long>) jedis.eval(
                LUA_SCRIPT,
                1,                                          // Number of keys
                new String[]{bucketKey},                    // KEYS[1]
                String.valueOf(capacity),                   // ARGV[1]
                String.valueOf(refillRatePerSecond),        // ARGV[2]
                String.valueOf(System.currentTimeMillis()/1000),  // ARGV[3] - current time
                String.valueOf(ttlSeconds)                  // ARGV[4]
            );
            
            long allowed = result.get(0);
            long remaining = result.get(1);
            
            return new RateLimitResponse(allowed == 1, remaining, capacity);
            
        } catch (Exception e) {
            // Fail-closed: reject on error to protect backend
            // (Could also fail-open depending on requirements)
            System.err.println("Redis error: " + e.getMessage());
            return new RateLimitResponse(false, 0, capacity);
        }
    }
}

/**
 * Response object for rate limit checks
 */
public static class RateLimitResponse {
    public final boolean allowed;
    public final long remainingTokens;
    public final long limit;
    
    public RateLimitResponse(boolean allowed, long remainingTokens, long limit) {
        this.allowed = allowed;
        this.remainingTokens = remainingTokens;
        this.limit = limit;
    }
    
    @Override
    public String toString() {
        return String.format("%s (remaining: %d/%d)",
                allowed ? "✓ ALLOWED" : "✗ REJECTED",
                remainingTokens,
                limit);
    }
}


// ============================================================================
// USAGE EXAMPLES
// ============================================================================

public class TokenBucketDemo {
    
    /**
     * Example 1: Basic in-memory token bucket
     */
    public static void exampleBasic() {
        System.out.println("=== Example 1: Basic In-Memory Token Bucket ===\n");
        
        TokenBucket bucket = new TokenBucket(
            "user_alice",
            100,    // Burst capacity: 100 requests
            10      // Steady rate: 10 requests/second
        );
        
        // Make 5 requests immediately (should all succeed)
        System.out.println("Making 5 requests immediately:");
        for (int i = 1; i <= 5; i++) {
            boolean allowed = bucket.allowRequest();
            System.out.printf("  Request %d: %s (remaining: %d)\n",
                    i, allowed ? "✓" : "✗", bucket.getRemainingTokens());
        }
        
        // Wait 1 second for tokens to refill (should add 10 tokens)
        System.out.println("\nWaiting 1 second (10 tokens should be added)...");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.printf("Remaining tokens: %d\n", bucket.getRemainingTokens());
    }
    
    /**
     * Example 2: Distributed token bucket with Redis
     */
    public static void exampleDistributed() {
        System.out.println("\n=== Example 2: Distributed Token Bucket (Redis) ===\n");
        
        redis.clients.jedis.JedisPool pool = new redis.clients.jedis.JedisPool("localhost", 6379);
        
        DistributedTokenBucket bucket = new DistributedTokenBucket(
            pool,
            "user_bob",
            100,    // Capacity
            10,     // 10 tokens/second
            3600    // 1 hour TTL
        );
        
        // Make requests
        System.out.println("Making 5 requests via Redis:");
        for (int i = 1; i <= 5; i++) {
            RateLimitResponse response = bucket.checkRateLimit();
            System.out.printf("  Request %d: %s\n", i, response);
        }
        
        pool.close();
    }
    
    /**
     * Example 3: API Gateway usage
     */
    public static void exampleGateway() {
        System.out.println("\n=== Example 3: API Gateway Integration ===\n");
        
        redis.clients.jedis.JedisPool pool = new redis.clients.jedis.JedisPool("localhost", 6379);
        
        // Before forwarding request to microservice:
        // 1. Extract client ID from request
        // 2. Check rate limit
        // 3. If allowed, forward to service
        // 4. If rejected, return 429
        
        DistributedTokenBucket userABucket = new DistributedTokenBucket(
            pool, "user_alice", 100, 10, 3600
        );
        
        // Simulating multiple requests
        System.out.println("API Gateway processing requests from user_alice:");
        for (int i = 1; i <= 3; i++) {
            RateLimitResponse response = userABucket.checkRateLimit();
            
            if (response.allowed) {
                System.out.printf("  Request %d: Forward to microservice\n", i);
            } else {
                System.out.printf("  Request %d: Return HTTP 429 (Too Many Requests)\n", i);
            }
        }
        
        pool.close();
    }
    
    public static void main(String[] args) {
        exampleBasic();
        // exampleDistributed();  // Uncomment if Redis available
        // exampleGateway();      // Uncomment if Redis available
    }
}


// ============================================================================
// INTERVIEW TALKING POINTS
// ============================================================================

/**
 * KEY CONCEPTS TO DISCUSS IN INTERVIEW:
 * 
 * 1. ALGORITHM EXPLANATION
 *    Q: "Walk me through how token bucket works"
 *    A: "Each client has a bucket holding tokens. Tokens refill at a constant 
 *        rate. Each request costs 1 token. If bucket empty, reject the request.
 *        Bucket capacity limits burst size."
 * 
 * 2. BURST HANDLING
 *    Q: "How does token bucket handle bursts?"
 *    A: "The bucket capacity (e.g., 100) allows up to 100 immediate requests.
 *        Then rate is limited by refill rate (e.g., 10/sec). This is ideal
 *        for interactive APIs that need responsiveness with rate limiting."
 * 
 * 3. THREAD SAFETY (In-Memory)
 *    Q: "How do you handle concurrent requests?"
 *    A: "Use ReentrantReadWriteLock. Write lock for refill+consume operations.
 *        Read lock for checking remaining tokens (doesn't modify state)."
 * 
 * 4. DISTRIBUTED SYSTEMS (Redis)
 *    Q: "What if multiple gateways need to coordinate?"
 *    A: "Store token state in Redis. Use Lua scripts for atomic operations.
 *        This prevents race conditions where multiple threads read same state."
 * 
 * 5. RACE CONDITION PREVENTION
 *    Q: "Why not just read-modify-write in separate Redis calls?"
 *    A: "Gateway A reads tokens=10, Gateway B reads tokens=10. Both decrement
 *        and write tokens=9. Should be 8. Lua script makes all operations atomic."
 * 
 * 6. SCALABILITY
 *    Q: "Can this handle 1M RPS?"
 *    A: "Single Redis can't. Need to shard by client_id using consistent hashing.
 *        40 shards × 50k ops each = 2M throughput. Also use connection pooling
 *        to reduce TCP overhead from ~20ms to ~2ms per request."
 * 
 * 7. FAILURE MODES
 *    Q: "What if Redis goes down?"
 *    A: "Fail-closed (our implementation): Reject all requests to protect backend.
 *        Alternative: Fail-open and use local in-memory fallback, but then risk
 *        backend overload. Depends on requirements."
 * 
 * 8. COMPLEXITY ANALYSIS
 *    Time: O(1) per request (constant time operations)
 *    Space: O(N) where N = number of unique clients in Redis
 *    Network: 1-2 Redis operations per request (~2-4ms latency)
 * 
 * 9. COMPARISON WITH ALTERNATIVES
 *    Fixed Window: Simple but has boundary effects
 *    Sliding Window Log: Perfect accuracy but memory intensive
 *    Leaky Bucket: Smooth rate but no burst handling
 *    Token Bucket: ✓ Balances accuracy, memory, and burst handling
 */
