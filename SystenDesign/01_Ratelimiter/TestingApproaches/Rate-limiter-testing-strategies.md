# Rate Limiter Testing Strategies Guide

## Table of Contents
1. [Introduction](#introduction)
2. [Testing Strategies](#testing-strategies)
   - [1. Unit Testing](#1-unit-testing)
   - [2. Boundary Testing](#2-boundary-testing)
   - [3. Time-Window Testing](#3-time-window-testing)
   - [4. Concurrency Testing](#4-concurrency-testing)
   - [5. Load Testing](#5-load-testing)
   - [6. Stress Testing](#6-stress-testing)
   - [7. Distributed Testing](#7-distributed-testing)
   - [8. State & Persistence Testing](#8-state--persistence-testing)
   - [9. Configuration Testing](#9-configuration-testing)
   - [10. Integration Testing](#10-integration-testing)
   - [11. Failure Mode Testing](#11-failure-mode-testing)
   - [12. Monitoring & Observability](#12-monitoring--observability)
3. [Tools & Technologies](#tools--technologies)
4. [Sample Scripts](#sample-scripts)
5. [Best Practices](#best-practices)

---

## Introduction

A rate limiter controls the rate of requests to protect backend services from overload. Effective testing is critical to ensure the rate limiter works correctly across various scenarios—from normal operations to edge cases and failure modes.

This guide provides comprehensive testing strategies, recommended tools, and practical implementation examples in both Java and JavaScript.

---

## Testing Strategies

### 1. Unit Testing
Test individual components and algorithms in isolation.

**What to test:**
- Token bucket algorithm logic
- Sliding window calculation
- Counter increments/decrements
- Time-based token generation
- Edge cases (zero limit, negative values)

### 2. Boundary Testing
Validate behavior at critical thresholds.

**Scenarios:**
- Exactly at limit (e.g., 100 requests when limit is 100)
- Just below limit (99 requests)
- Just above limit (101 requests)
- Zero allowance
- Invalid inputs (negative, null)

### 3. Time-Window Testing
Ensure correct behavior across time boundaries.

**Test cases:**
- Requests at window boundaries
- Multiple consecutive windows
- Window expiry and reset
- Time advancement with mocked clocks
- Different time granularities (second, minute, hour)

### 4. Concurrency Testing
Validate thread-safety and race condition handling.

**Focus areas:**
- Simultaneous requests from multiple threads
- Atomic operations on shared counters
- Lock contention
- Stress with varying thread counts
- Data consistency across threads

### 5. Load Testing
Measure performance under sustained traffic.

**Metrics:**
- Throughput (requests/second)
- Response time latency
- Memory usage
- CPU utilization
- Behavior at/below limits

### 6. Stress Testing
Test system behavior under extreme conditions.

**Scenarios:**
- Traffic significantly above limits
- Burst patterns
- Gradual increase to peak load
- Recovery behavior after stress

### 7. Distributed Testing
For multi-instance, clustered deployments.

**Test cases:**
- Coordination across instances
- Redis/Memcached backend consistency
- Network partitions
- Eventual consistency
- State synchronization

### 8. State & Persistence Testing
Validate data integrity and recovery.

**Scenarios:**
- Persistence across restarts
- Failure recovery
- State consistency
- Cleanup of expired entries
- Corruption handling

### 9. Configuration Testing
Test with various configurations.

**Variations:**
- Different rate limit values
- Multiple limit types (per-user, per-IP, global)
- Tier-based limits (free vs premium)
- Runtime configuration changes
- Invalid configurations

### 10. Integration Testing
Test with full application stack.

**Components:**
- HTTP server integration
- Load balancers
- Reverse proxies
- Authentication/authorization layers
- Caching layers

### 11. Failure Mode Testing
Validate graceful degradation.

**Scenarios:**
- Backend (Redis) unavailable
- Partial failures in distributed setup
- Memory exhaustion
- Corrupted state
- Timeout handling

### 12. Monitoring & Observability
Verify metrics, logs, and alerts.

**Validation:**
- Correct metric reporting
- Alert thresholds
- Log event accuracy
- Request tracing
- Error reporting

---

## Tools & Technologies

### Java Testing Tools

| Tool | Purpose | Use Case |
|------|---------|----------|
| **JUnit 5** | Unit testing framework | Core unit tests |
| **Mockito** | Mocking library | Mock dependencies, time |
| **AssertJ** | Fluent assertions | Readable test assertions |
| **Awaitility** | Async testing | Concurrent scenarios |
| **JMH** | Micro-benchmarking | Performance benchmarks |
| **JCStress** | Concurrency stress tests | Race conditions |
| **Gatling** | Load testing tool | High-load scenarios |
| **Testcontainers** | Docker containers | Redis/Memcached testing |
| **Spring Boot Test** | Spring integration tests | Integration testing |

### JavaScript Testing Tools

| Tool | Purpose | Use Case |
|------|---------|----------|
| **Jest** | Testing framework | Unit & integration tests |
| **Mocha** | Testing framework | BDD-style tests |
| **Sinon.js** | Mocking/stubbing | Mock time and functions |
| **Chai** | Assertion library | Fluent assertions |
| **Lolex** | Time mocking | Clock manipulation |
| **Artillery** | Load testing | High-volume testing |
| **Autocannon** | HTTP benchmarking | Performance metrics |
| **Redis (Node)** | Redis client | Distributed tests |
| **Testcontainers-node** | Docker containers | Container management |

### Common Tools

| Tool | Purpose |
|------|---------|
| **Docker** | Container-based testing environments |
| **Redis/Memcached** | Distributed rate limiter backends |
| **Prometheus** | Metrics collection and monitoring |
| **Grafana** | Visualization of test metrics |
| **Apache JMeter** | Load and performance testing |
| **Postman** | API testing and load testing |

---

## Sample Scripts

### Java Examples

#### 1. Unit Test - Token Bucket Implementation

```java
package com.example.ratelimiter.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import com.example.ratelimiter.TokenBucketLimiter;

@DisplayName("Token Bucket Rate Limiter Tests")
public class TokenBucketLimiterTest {

    private TokenBucketLimiter limiter;
    private static final int CAPACITY = 10;
    private static final int REFILL_RATE = 5; // tokens per second

    @BeforeEach
    void setUp() {
        limiter = new TokenBucketLimiter(CAPACITY, REFILL_RATE);
    }

    @Test
    @DisplayName("Should allow requests within capacity")
    void testAllowRequestsWithinCapacity() {
        for (int i = 0; i < CAPACITY; i++) {
            Assertions.assertTrue(limiter.allowRequest(),
                "Request " + (i + 1) + " should be allowed");
        }
    }

    @Test
    @DisplayName("Should reject requests exceeding capacity")
    void testRejectRequestsExceedingCapacity() {
        // Consume all tokens
        for (int i = 0; i < CAPACITY; i++) {
            limiter.allowRequest();
        }
        // Next request should be rejected
        Assertions.assertFalse(limiter.allowRequest(),
            "Request exceeding capacity should be rejected");
    }

    @Test
    @DisplayName("Should refill tokens after time passes")
    void testTokenRefillAfterTimeElapse() {
        // Use all tokens
        for (int i = 0; i < CAPACITY; i++) {
            limiter.allowRequest();
        }
        Assertions.assertFalse(limiter.allowRequest(),
            "Should be exhausted initially");

        // Simulate time passage (1 second = 5 new tokens)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Should now allow requests
        Assertions.assertTrue(limiter.allowRequest(),
            "Should allow request after refill");
    }

    @Test
    @DisplayName("Should handle concurrent requests safely")
    void testConcurrentRequests() throws InterruptedException {
        int threadCount = 10;
        Thread[] threads = new Thread[threadCount];
        int[] allowedCount = new int[1];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                if (limiter.allowRequest()) {
                    synchronized (allowedCount) {
                        allowedCount[0]++;
                    }
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        Assertions.assertEquals(CAPACITY, allowedCount[0],
            "Should allow exactly CAPACITY concurrent requests");
    }

    @Test
    @DisplayName("Should provide correct rate limit info")
    void testRateLimitInfo() {
        limiter.allowRequest();
        Assertions.assertEquals(CAPACITY - 1, limiter.getAvailableTokens(),
            "Available tokens should decrease");
    }
}
```

#### 2. Load Test - Gatling Script

```java
package com.example.ratelimiter.load;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import java.time.Duration;

public class RateLimiterLoadTest extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080")
        .contentTypeHeader("application/json");

    private ScenarioBuilder apiCallScenario = scenario("Rate Limiter Load Test")
        .exec(
            http("API Call")
                .get("/api/resource")
                .check(status().is(200).or(status().is(429)))
        )
        .pause(Duration.ofMillis(100), Duration.ofMillis(500));

    {
        setUp(
            apiCallScenario.injectOpen(
                // Ramp up: 100 users over 10 seconds
                rampOpenUsersPerSec(1).to(100).during(Duration.ofSeconds(10)),
                // Hold: 100 users for 30 seconds
                constantUsersPerSec(100).during(Duration.ofSeconds(30)),
                // Ramp down: 100 to 0 users over 10 seconds
                rampOpenUsersPerSec(100).to(0).during(Duration.ofSeconds(10))
            )
        )
            .protocols(httpProtocol)
            .assertions(
                global().responseTime().max().lt(5000),
                global().failureRate().lt(0.1),
                global().successfulRequests().count().gt(1000)
            );
    }
}
```

#### 3. Concurrency Test with JCStress

```java
package com.example.ratelimiter.stress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;
import com.example.ratelimiter.AtomicRateLimiter;

@JCStressTest
@Outcome(id = {"100"}, expect = Expect.ACCEPTABLE, desc = "Expected result")
@Outcome(expect = Expect.FORBIDDEN, desc = "Forbidden outcome")
@State
public class ConcurrentRateLimiterStress {

    private AtomicRateLimiter limiter = new AtomicRateLimiter(100);

    @Actor
    public void actor1(I_Result r) {
        for (int i = 0; i < 50; i++) {
            limiter.allowRequest();
        }
    }

    @Actor
    public void actor2(I_Result r) {
        for (int i = 0; i < 50; i++) {
            limiter.allowRequest();
        }
    }

    @Arbiter
    public void arbiter(I_Result r) {
        r.r1 = limiter.getConsumedCount();
    }
}
```

#### 4. Integration Test with Testcontainers

```java
package com.example.ratelimiter.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import redis.clients.jedis.Jedis;
import com.example.ratelimiter.RedisRateLimiter;

@Testcontainers
public class RedisRateLimiterIntegrationTest {

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
        .withExposedPorts(6379);

    private RedisRateLimiter limiter;
    private Jedis jedisClient;

    @BeforeAll
    static void setup() {
        String host = redis.getHost();
        Integer port = redis.getFirstMappedPort();
        System.out.println("Redis available at " + host + ":" + port);
    }

    @Test
    void testRedisBackedRateLimiter() {
        String host = redis.getHost();
        int port = redis.getFirstMappedPort();

        jedisClient = new Jedis(host, port);
        limiter = new RedisRateLimiter(jedisClient, "api_limit", 100, 60);

        // Test basic limiting
        for (int i = 0; i < 100; i++) {
            Assertions.assertTrue(limiter.allowRequest("user123"),
                "Should allow 100 requests");
        }

        // 101st request should be rejected
        Assertions.assertFalse(limiter.allowRequest("user123"),
            "Should reject request exceeding limit");

        jedisClient.close();
    }

    @Test
    void testMultipleUsersIndependentLimits() {
        String host = redis.getHost();
        int port = redis.getFirstMappedPort();

        jedisClient = new Jedis(host, port);
        limiter = new RedisRateLimiter(jedisClient, "api_limit", 10, 60);

        // User 1 makes 10 requests
        for (int i = 0; i < 10; i++) {
            Assertions.assertTrue(limiter.allowRequest("user1"));
        }
        Assertions.assertFalse(limiter.allowRequest("user1"),
            "User1 should be rate limited");

        // User 2 should still be allowed (independent limits)
        Assertions.assertTrue(limiter.allowRequest("user2"),
            "User2 should not be affected by User1's limit");

        jedisClient.close();
    }
}
```

---

### JavaScript Examples

#### 1. Unit Test - Token Bucket with Jest

```javascript
// rateLimiter.test.js
const TokenBucketLimiter = require('./TokenBucketLimiter');

describe('Token Bucket Rate Limiter', () => {
    let limiter;
    const CAPACITY = 10;
    const REFILL_RATE = 5; // tokens per second

    beforeEach(() => {
        limiter = new TokenBucketLimiter(CAPACITY, REFILL_RATE);
    });

    test('should allow requests within capacity', () => {
        for (let i = 0; i < CAPACITY; i++) {
            expect(limiter.allowRequest()).toBe(true);
        }
    });

    test('should reject requests exceeding capacity', () => {
        // Consume all tokens
        for (let i = 0; i < CAPACITY; i++) {
            limiter.allowRequest();
        }
        // Next request should be rejected
        expect(limiter.allowRequest()).toBe(false);
    });

    test('should refill tokens after time passes', async () => {
        // Consume all tokens
        for (let i = 0; i < CAPACITY; i++) {
            limiter.allowRequest();
        }
        expect(limiter.allowRequest()).toBe(false);

        // Wait for refill
        await new Promise(resolve => setTimeout(resolve, 1100));

        // Should allow requests after refill
        expect(limiter.allowRequest()).toBe(true);
    });

    test('should handle zero capacity', () => {
        const zeroLimiter = new TokenBucketLimiter(0, 1);
        expect(zeroLimiter.allowRequest()).toBe(false);
    });

    test('should provide available tokens info', () => {
        limiter.allowRequest();
        expect(limiter.getAvailableTokens()).toBe(CAPACITY - 1);
    });

    test('should handle rapid consecutive requests', () => {
        const results = [];
        for (let i = 0; i < CAPACITY + 5; i++) {
            results.push(limiter.allowRequest());
        }

        expect(results.filter(r => r === true).length).toBe(CAPACITY);
        expect(results.filter(r => r === false).length).toBe(5);
    });
});
```

#### 2. Time Mocking Test with Sinon and Lolex

```javascript
// rateLimiter.timeMocking.test.js
const sinon = require('sinon');
const lolex = require('lolex');
const TokenBucketLimiter = require('./TokenBucketLimiter');

describe('Token Bucket with Time Mocking', () => {
    let clock;
    let limiter;

    beforeEach(() => {
        clock = lolex.install();
        limiter = new TokenBucketLimiter(10, 5); // 10 capacity, 5 tokens/sec
    });

    afterEach(() => {
        clock.uninstall();
    });

    test('should correctly refill tokens at specified rate', () => {
        // Consume all tokens
        for (let i = 0; i < 10; i++) {
            limiter.allowRequest();
        }
        expect(limiter.allowRequest()).toBe(false);

        // Advance time by 1 second (should gain 5 tokens)
        clock.tick(1000);
        expect(limiter.allowRequest()).toBe(true);
        expect(limiter.getAvailableTokens()).toBe(4);

        // Advance time by 2 more seconds (should gain 10 tokens, cap at 10)
        clock.tick(2000);
        expect(limiter.getAvailableTokens()).toBe(10);
    });

    test('should handle time jumps correctly', () => {
        // Consume all tokens
        for (let i = 0; i < 10; i++) {
            limiter.allowRequest();
        }

        // Jump forward by 10 seconds (50 tokens, but capped at 10)
        clock.tick(10000);
        expect(limiter.getAvailableTokens()).toBe(10);

        // Should allow 10 more requests
        for (let i = 0; i < 10; i++) {
            expect(limiter.allowRequest()).toBe(true);
        }
        expect(limiter.allowRequest()).toBe(false);
    });

    test('should handle backwards time changes gracefully', () => {
        limiter.allowRequest();
        expect(limiter.getAvailableTokens()).toBe(9);

        // Simulate backwards clock (negative tick)
        clock.tick(-100); // Go back 100ms
        expect(limiter.getAvailableTokens()).toBeGreaterThanOrEqual(9);
    });
});
```

#### 3. Concurrency Test with Promise.all

```javascript
// rateLimiter.concurrent.test.js
const TokenBucketLimiter = require('./TokenBucketLimiter');

describe('Token Bucket Concurrency', () => {
    test('should handle concurrent requests safely', async () => {
        const limiter = new TokenBucketLimiter(100, 10);
        const requestCount = 200;
        const promises = [];

        for (let i = 0; i < requestCount; i++) {
            promises.push(
                new Promise(resolve => {
                    setImmediate(() => {
                        resolve(limiter.allowRequest());
                    });
                })
            );
        }

        const results = await Promise.all(promises);
        const allowedCount = results.filter(r => r === true).length;
        const rejectedCount = results.filter(r => r === false).length;

        expect(allowedCount).toBe(100);
        expect(rejectedCount).toBe(100);
    });

    test('should maintain consistency across concurrent operations', async () => {
        const limiter = new TokenBucketLimiter(50, 5);
        const threadCount = 10;
        const requestsPerThread = 10;
        const promises = [];

        for (let t = 0; t < threadCount; t++) {
            promises.push(
                new Promise(resolve => {
                    let threadAllowed = 0;
                    for (let i = 0; i < requestsPerThread; i++) {
                        if (limiter.allowRequest()) {
                            threadAllowed++;
                        }
                    }
                    resolve(threadAllowed);
                })
            );
        }

        const results = await Promise.all(promises);
        const totalAllowed = results.reduce((a, b) => a + b, 0);

        expect(totalAllowed).toBeLessThanOrEqual(50);
    });
});
```

#### 4. Load Test with Artillery

```yaml
# load-test.yml
config:
  target: "http://localhost:8080"
  phases:
    - duration: 10
      arrivalRate: 10
      name: "Warm up"
    - duration: 30
      arrivalRate: 100
      name: "Sustained load"
    - duration: 10
      arrivalRate: 100
      rampTo: 0
      name: "Ramp down"
  processor: "./processor.js"
  variables:
    userId:
      - "user1"
      - "user2"
      - "user3"
  defaults:
    headers:
      Content-Type: "application/json"

scenarios:
  - name: "Rate Limiter Load Test"
    flow:
      - get:
          url: "/api/resource"
          capture:
            - json: "$.remainingRequests"
              as: "remaining"
          expect:
            - statusCode: [200, 429]
      - think: 2
```

```javascript
// processor.js
module.exports = {
    beforeRequest: function(requestParams, context, ee, next) {
        requestParams.headers['X-User-Id'] = context.vars.userId;
        return next();
    },

    afterResponse: function(requestParams, responseBody, responseCode, context, ee, next) {
        if (responseCode === 429) {
            console.log('Rate limit exceeded for user:', context.vars.userId);
        }
        return next();
    }
};
```

#### 5. Integration Test with Redis

```javascript
// rateLimiter.redis.test.js
const redis = require('redis');
const RedisRateLimiter = require('./RedisRateLimiter');

describe('Redis-backed Rate Limiter', () => {
    let client;
    let limiter;

    beforeEach(async () => {
        client = redis.createClient({
            host: 'localhost',
            port: 6379
        });
        await client.connect();
        
        limiter = new RedisRateLimiter(client, 'test_limit', 100, 60);
        
        // Clean up
        await client.del('test_limit:*');
    });

    afterEach(async () => {
        await client.quit();
    });

    test('should limit requests per user', async () => {
        const userId = 'user123';
        
        // Make 100 requests
        for (let i = 0; i < 100; i++) {
            expect(await limiter.allowRequest(userId)).toBe(true);
        }

        // 101st should be rejected
        expect(await limiter.allowRequest(userId)).toBe(false);
    });

    test('should isolate limits per user', async () => {
        // User1 maxes out
        for (let i = 0; i < 100; i++) {
            await limiter.allowRequest('user1');
        }
        expect(await limiter.allowRequest('user1')).toBe(false);

        // User2 should still be allowed
        expect(await limiter.allowRequest('user2')).toBe(true);
    });

    test('should reset limits after window expires', async () => {
        const userId = 'user_reset_test';
        
        // Use up limit
        for (let i = 0; i < 100; i++) {
            await limiter.allowRequest(userId);
        }
        expect(await limiter.allowRequest(userId)).toBe(false);

        // Wait for window to expire
        await new Promise(resolve => setTimeout(resolve, 61000));

        // Should allow requests again
        expect(await limiter.allowRequest(userId)).toBe(true);
    });

    test('should handle burst traffic correctly', async () => {
        const userId = 'burst_user';
        const promises = [];

        // Simulate 200 concurrent requests
        for (let i = 0; i < 200; i++) {
            promises.push(limiter.allowRequest(userId));
        }

        const results = await Promise.all(promises);
        const allowedCount = results.filter(r => r === true).length;

        expect(allowedCount).toBe(100);
    });
});
```

---

## Implementation Examples

### Java: Token Bucket Limiter

```java
package com.example.ratelimiter;

import java.util.concurrent.atomic.AtomicLong;

public class TokenBucketLimiter {
    private final int capacity;
    private final int refillRate; // tokens per second
    private AtomicLong availableTokens;
    private AtomicLong lastRefillTime;

    public TokenBucketLimiter(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.availableTokens = new AtomicLong(capacity);
        this.lastRefillTime = new AtomicLong(System.currentTimeMillis());
    }

    public synchronized boolean allowRequest() {
        refillTokens();
        
        if (availableTokens.get() > 0) {
            availableTokens.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refillTokens() {
        long now = System.currentTimeMillis();
        long timePassed = now - lastRefillTime.get();
        long tokensToAdd = (timePassed * refillRate) / 1000;

        if (tokensToAdd > 0) {
            long newTokens = Math.min(
                availableTokens.get() + tokensToAdd,
                capacity
            );
            availableTokens.set(newTokens);
            lastRefillTime.set(now);
        }
    }

    public long getAvailableTokens() {
        refillTokens();
        return availableTokens.get();
    }
}
```

### JavaScript: Token Bucket Limiter

```javascript
// TokenBucketLimiter.js
class TokenBucketLimiter {
    constructor(capacity, refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate; // tokens per second
        this.availableTokens = capacity;
        this.lastRefillTime = Date.now();
    }

    allowRequest() {
        this.refillTokens();
        
        if (this.availableTokens > 0) {
            this.availableTokens--;
            return true;
        }
        return false;
    }

    refillTokens() {
        const now = Date.now();
        const timePassed = (now - this.lastRefillTime) / 1000; // Convert to seconds
        const tokensToAdd = Math.floor(timePassed * this.refillRate);

        if (tokensToAdd > 0) {
            this.availableTokens = Math.min(
                this.availableTokens + tokensToAdd,
                this.capacity
            );
            this.lastRefillTime = now;
        }
    }

    getAvailableTokens() {
        this.refillTokens();
        return this.availableTokens;
    }
}

module.exports = TokenBucketLimiter;
```

---

## Best Practices

### 1. Test Organization
- Organize tests by strategy (unit, integration, load, stress)
- Use descriptive test names that clarify intent
- Group related tests using test suites/describe blocks
- Maintain test independence—no shared state between tests

### 2. Mocking & Isolation
- Mock external dependencies (Redis, databases)
- Use time mocking for deterministic time-based tests
- Isolate units under test for focused testing
- Use containers for integration tests

### 3. Realistic Data
- Use realistic request patterns (normal, burst, sustained)
- Test with actual expected load profiles
- Include variations in user behavior
- Test with multiple concurrency levels

### 4. Metrics & Assertions
- Assert on multiple dimensions (allowed count, latency, consistency)
- Capture and analyze test metrics
- Set meaningful threshold expectations
- Track both positive and negative cases

### 5. Continuous Integration
- Automate test execution on every commit
- Run different test types at different stages
  - Unit tests: on every commit (fast)
  - Integration tests: on PR merge
  - Load tests: scheduled/nightly
- Track test results and trends

### 6. Failure Analysis
- Document known failure modes
- Test failure recovery paths
- Verify graceful degradation
- Test fallback mechanisms

### 7. Documentation
- Document test purpose and expected outcomes
- Include setup and teardown requirements
- Comment complex test scenarios
- Maintain runbooks for load tests

---

## Running Tests

### Java Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TokenBucketLimiterTest

# Run with coverage
mvn test jacoco:report

# Run Gatling load tests
mvn gatling:test
```

### JavaScript Tests
```bash
# Run Jest tests
npm test

# Run with coverage
npm test -- --coverage

# Run Artillery load test
artillery run load-test.yml

# Run with detailed output
artillery run load-test.yml -o test-results.json
```

---

## Conclusion

Comprehensive testing of rate limiters requires multiple strategies and tools. Key takeaways:

1. **Start with unit tests** for core algorithms
2. **Add integration tests** for real-world scenarios
3. **Use load tests** to validate performance under pressure
4. **Test concurrency** thoroughly for distributed systems
5. **Monitor metrics** and set meaningful assertions
6. **Automate continuously** in CI/CD pipelines

By following these strategies and using the provided sample scripts, you can ensure your rate limiter is robust, performant, and reliable across all scenarios.
