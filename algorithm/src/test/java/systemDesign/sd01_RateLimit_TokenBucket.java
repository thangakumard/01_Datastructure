package systemDesign;

public class sd01_RateLimit_TokenBucket {

	private final long maxBucketSize;
	private final long refillRate;
	
	private long currentBucketSize = 0;
	private long lastRefilltimeStamp = 0;
	
	public sd01_RateLimit_TokenBucket(int maxSize, int refill) {
		
		this.maxBucketSize = maxSize;
		this.refillRate = refill;
		
		currentBucketSize = maxBucketSize;
		lastRefilltimeStamp = System.nanoTime();
		
	}
	
	private synchronized boolean allowRequest(int token) {
		refill();
		
		if(currentBucketSize > token) {
			currentBucketSize -= token;
			return true;
		}
		return false;
	}
	
	private void refill() {
		long now = System.nanoTime();
		double tokenToAdd = (now - lastRefilltimeStamp) * refillRate / 1e9;
		currentBucketSize = (long) Math.min(currentBucketSize + tokenToAdd, maxBucketSize);
		lastRefilltimeStamp = now;
	}
}
