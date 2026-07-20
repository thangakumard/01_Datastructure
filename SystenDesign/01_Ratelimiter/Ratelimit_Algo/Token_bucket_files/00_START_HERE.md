# Token Bucket Rate Limiter - Complete Interview Package

## 🎯 What This Is

A complete, production-ready Token Bucket implementation in Java with everything you need for system design interviews. Includes explanations, code examples, quick reference guides, and interview strategies.

---

## 📦 Files in This Package

### 1. **READ THIS FIRST: TokenBucketGuide.md** ⭐
**Your main interview guide** (12 KB)
- How to use these resources in an interview
- Interview approach by seniority level (junior, senior, staff)
- Time allocation strategies
- Checklist before interview day
- Common follow-up questions with answers
- Success criteria

**👉 START HERE before your interview**

---

### 2. **TokenBucketInterviewVersion.java** ⭐ (Most Important)
**Clean, interview-focused code** (15 KB)
- Simple in-memory implementation (copy-paste ready)
- Redis distributed version
- Minimal, readable code with detailed comments
- Interview talking points embedded
- ~350 lines, very approachable

**👉 STUDY THIS for code writing during interview**

---

### 3. **TokenBucketImplementation.java** (Complete)
**Production-ready implementation** (20 KB)
- Full in-memory token bucket with locks
- Complete Redis implementation with Lua script
- Global rate limiter manager for multiple clients
- Configuration and rule management
- Usage examples and middleware integration
- ~450 lines, enterprise-grade

**👉 REFERENCE THIS for deeper understanding**

---

### 4. **TokenBucketQuickReference.md** (Cheatsheet)
**Quick lookup guide** (8.7 KB)
- 30-second algorithm summary
- Code snippets ready to copy
- Interview Q&A cheatsheet
- Common pitfalls and solutions
- Complexity analysis
- Code templates

**👉 USE THIS as quick reference during prep**

---

### 5. **rate_limiter_system_design.md** (Complete System Design)
**Full system design document** (37 KB)
- Comprehensive rate limiter design guide
- All 5 algorithms (Fixed Window, Sliding Window Log, Sliding Window Counter, Token Bucket, Leaky Bucket)
- Architecture diagrams and request flows
- Deep dives on scalability, HA, latency, configuration
- Implementation details
- 16 sections covering everything

**👉 REFERENCE THIS for complete system design context**

---

## 🚀 Quick Start (30 minutes before interview)

### Step 1: Read (5 min)
Read **TokenBucketGuide.md** → sections "How to Use This in Your Interview" and "Different Seniority Approaches"

### Step 2: Study (10 min)
Study **TokenBucketInterviewVersion.java** → Focus on:
- `allowRequest()` method (10 lines)
- Lua script in distributed version
- Comment block explaining race conditions

### Step 3: Memorize (10 min)
Memorize from **TokenBucketQuickReference.md**:
- "Core Algorithm in 30 Seconds" section
- "Interview Q&A Cheatsheet" section
- 1-2 code templates you can write from memory

### Step 4: Practice (5 min)
Try writing in-memory version from scratch (no looking)

---

## 📊 File Size & Complexity

| File | Size | Read Time | Complexity | Use Case |
|------|------|-----------|-----------|----------|
| TokenBucketGuide.md | 12 KB | 10 min | Strategy guide | Interview prep |
| TokenBucketInterviewVersion.java | 15 KB | 15 min | Medium | Interview coding |
| TokenBucketImplementation.java | 20 KB | 25 min | High | Reference/production |
| TokenBucketQuickReference.md | 8.7 KB | 5 min | Low | Quick lookup |
| rate_limiter_system_design.md | 37 KB | 45 min | High | System design depth |

---

## 🎓 Interview Roadmap

### Minute 1-5: Explain Algorithm
```
User asks: "Design a rate limiter"

Your response (2-3 min):
- Ask clarifying questions (scale? single server? distributed?)
- Explain Token Bucket concept:
  "Each client has a bucket holding tokens. Tokens refill 
   at a steady rate (e.g., 10/sec). Each request costs 1 token.
   If bucket empty, reject the request. Bucket capacity (e.g., 100) 
   allows burst traffic."
- Why it's good: "Handles bursts, simple, memory efficient"
- Draw diagram on whiteboard
```

### Minute 5-25: Design & Code
```
High-level design (5 min):
- Where to place: API Gateway (edge)
- How to identify: JWT token (user_id)
- How to store: Redis for distributed
- What to return: HTTP 429 + headers

Implementation (15 min):
- Start: Write in-memory version (10 lines)
- Add complexity: Explain Redis version
- Discuss: Thread safety, Lua scripts
```

### Minute 25-50: Deep Dives
```
Pick one based on progress:
1. Scalability: "For 1M RPS, need sharding (~20 instances)"
2. Thread Safety: "Race condition prevented by Lua script atomicity"
3. Failure Modes: "Fail-closed vs fail-open trade-off"
4. Optimization: "Connection pooling reduces TCP overhead"
```

### Minute 50-60: Q&A
```
Be ready for:
- "How would you monitor this?"
- "What if Redis goes down?"
- "How to test it?"
- "How to tune capacity and refill rate?"
```

---

## 💻 Code You Need to Know

### The Absolute Minimum (write in 5 minutes)
```java
public synchronized boolean allowRequest() {
    // Refill tokens
    long now = System.currentTimeMillis();
    long elapsed = (now - lastRefillTime) / 1000;
    tokens = Math.min(capacity, tokens + elapsed * rate);
    lastRefillTime = now;
    
    // Check and consume
    if (tokens > 0) {
        tokens--;
        return true;
    }
    return false;
}
```

### The Important Stuff (Redis + Lua)
See **TokenBucketInterviewVersion.java** → DistributedTokenBucket class
- LUA_SCRIPT constant (Lua script for atomicity)
- checkRateLimit() method (how to execute script)

### The Explanations
All in comments in **TokenBucketInterviewVersion.java**

---

## ✅ Pre-Interview Checklist

### Knowledge
- [ ] Can explain token bucket in 2 minutes
- [ ] Can write in-memory version in 5 minutes
- [ ] Understand Lua script prevents race conditions
- [ ] Know 1M RPS needs sharding
- [ ] Familiar with fail-open vs fail-closed

### Code
- [ ] Can write basic allowRequest() from memory
- [ ] Know how to create JedisPool
- [ ] Understand redis.eval() with Lua script
- [ ] Know HTTP 429 status code

### Design
- [ ] Whiteboard diagram of architecture ready
- [ ] Examples of burst vs steady state ready
- [ ] Comparison with other algorithms ready
- [ ] Failure scenario responses ready

---

## 🎯 What Interviewers Look For

### Good Signs ✓
- [ ] Explains algorithm clearly and concisely
- [ ] Starts simple, adds complexity on demand
- [ ] Writes working code
- [ ] Discusses trade-offs proactively
- [ ] Handles follow-up questions well
- [ ] Shows knowledge of production concerns (monitoring, failures)

### Red Flags ✗
- [ ] Doesn't explain why token bucket is good
- [ ] Can't write working code
- [ ] Doesn't mention Redis for scale
- [ ] Ignores race conditions
- [ ] Defensive about design choices
- [ ] Doesn't ask clarifying questions at start

---

## 🏆 Success Looks Like

**Minimum Passing:**
- ✓ Explained algorithm
- ✓ Wrote working in-memory code
- ✓ Answered follow-up questions

**Strong Performance:**
- ✓ Above, plus
- ✓ Added Redis for scalability
- ✓ Discussed threading/atomicity
- ✓ Mentioned monitoring

**Excellent:**
- ✓ All of above, plus
- ✓ Implemented both versions completely
- ✓ Explained sharding strategy
- ✓ Addressed multiple failure modes
- ✓ Discussed optimization (connection pooling, latency)

---

## 📚 How These Files Relate

```
TokenBucketGuide.md
├─ Strategy & approach
├─ References → TokenBucketInterviewVersion.java
├─ References → TokenBucketQuickReference.md
└─ Links to → rate_limiter_system_design.md

TokenBucketInterviewVersion.java
├─ Simple version (study this)
├─ Distributed version (add this for scale)
└─ Interview talking points (in comments)

TokenBucketImplementation.java
├─ Complete production version
├─ Everything you might need
└─ Reference for complex questions

TokenBucketQuickReference.md
├─ Quick lookup during prep
├─ Code snippets to copy
└─ Q&A pairs for common questions

rate_limiter_system_design.md
├─ Context for the whole system
├─ Algorithm comparison
└─ Deep dive sections (sharding, HA, config, etc.)
```

---

## 🚀 Usage Examples

### Example 1: Single-Server API Gateway
```java
TokenBucket bucket = new TokenBucket("user_alice", 100, 10);
if (bucket.allowRequest()) {
    // Forward to microservice
    return forwardToService();
} else {
    // Rate limited
    return new Response(429, "Too Many Requests");
}
```

### Example 2: Distributed System
```java
JedisPool pool = new JedisPool("redis-host", 6379);
DistributedTokenBucket bucket = new DistributedTokenBucket(
    pool, "user_alice", 100, 10, 3600);

if (bucket.checkRateLimit().allowed) {
    return forwardToService();
} else {
    return new Response(429, "Too Many Requests");
}
```

---

## 📞 Common Questions Answered

**Q: How long should this take in interview?**
A: 20-30 minutes to explain and code basic version, 40-50 minutes for full implementation.

**Q: Should I memorize the Lua script?**
A: No. Understand the concept (atomic operations), know where to look.

**Q: What if they ask about other algorithms?**
A: Briefly compare to token bucket. See **rate_limiter_system_design.md** section 4.

**Q: What if I get stuck?**
A: Say "let me think about that" or "let me look at my notes". It's OK.

**Q: Can I use these files in the interview?**
A: Some interviews allow notes/references. Ask your recruiter. Either way, study them beforehand so you can explain without reading.

---

## 🎯 Next Steps

1. **Right now:** Read TokenBucketGuide.md (10 min)
2. **Today:** Study TokenBucketInterviewVersion.java (20 min)
3. **Tomorrow:** Practice writing code from memory (30 min)
4. **Day before:** Quick review of QuickReference.md (5 min)
5. **Interview day:** Trust your preparation! 🚀

---

## 📊 File Dependencies

```
📌 START HERE
  ↓
TokenBucketGuide.md
  ├─ Links to practice code
  ├─ References interview questions
  └─ Points to deeper resources
       ↓
TokenBucketInterviewVersion.java ← STUDY THIS
       ↓
TokenBucketQuickReference.md ← QUICK LOOKUP
       ↓
TokenBucketImplementation.java ← FOR REFERENCE
       ↓
rate_limiter_system_design.md ← CONTEXT
```

---

## 💡 Final Tips

1. **Don't try to memorize everything** - Understand the concept
2. **Practice explaining out loud** - Interview is conversation, not recitation
3. **Draw on whiteboard early** - Helps both of you understand
4. **Address trade-offs proactively** - Shows mature thinking
5. **Ask clarifying questions** - Don't assume requirements
6. **Start simple, add complexity** - Better than trying to be too clever
7. **Test your code mentally** - Walk through an example
8. **Be honest about gaps** - "I'd need to look up Redis cluster internals"

---

## 🎉 You've Got This!

You now have:
- ✅ Complete algorithm explanation
- ✅ Working code implementations (in-memory and Redis)
- ✅ Interview strategy guide
- ✅ Quick reference cheatsheet
- ✅ Q&A examples
- ✅ Production considerations

**Good luck with your interview! 🚀**

---

**Last updated:** July 2026  
**Package size:** ~100 KB of comprehensive interview material  
**Estimated interview prep time:** 1-2 hours  
**System design scope:** Single server → 1M RPS distributed system
