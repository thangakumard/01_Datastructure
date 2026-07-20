import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InMemoryTokenBucket {
    private final String clientId;
    private final long capacity;
    private final long refillRatePerSecond;

    private long tokens;
    private long lastRefillTime;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public InMemoryTokenBucket(String clientId, long capacity, long refillRatePerSecond){
        this.clientId = clientId;
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;

        this.tokens = capacity; //start with full capacity
        this.lastRefillTime = system.currentTimeMillis();
    }

    /**
     * Refill tokens based on elapsed time
     * Called internally before checking if request allowed
     */
    public boolean refillTokens(){
        long currentTime = System.currentTimeMillis();
        long elapsedMillis = currentTime - this.lastRefillTime;

        long tokensToAdd = (elapsedMillis * this.refillRatePerSecond)/1000;
        if(tokensToAdd > 0){
            this.tokens = Math.min(this.capacity, tokensToAdd);
            this.lastRefillTime = currentTime;
        }
    }

    public long getCurrentTokenCount(){
        lock.readLock.lock();
        try{
            long currentTime = system.currentTimeMillis();
            long elapsedMillis = currentTime - this.lastRefillTime;
            long tokensToAdd = (elapsedMillis * refillRatePerSecond) / 1000;

            long currenTokens = Math.min(this.capacity, tokens + tokensToAdd);
            return currenTokens;
        }finally {
            lock.reaLock.unlock();
        }
    }

    public synchronized boolean allowRequest(){
        lock.readLock.lock();
        try{
            refillTokens();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }finally {
            lock.readLock.unlock();
        }
    }


}