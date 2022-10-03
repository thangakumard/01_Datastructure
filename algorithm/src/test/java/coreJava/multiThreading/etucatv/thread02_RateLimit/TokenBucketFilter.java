package coreJava.multiThreading.etucatv.thread02_RateLimit;

public class TokenBucketFilter {
    private int maxTokens;
    private long lastRequestTime = System.currentTimeMillis();
    long possibleTokens = 0;
    public TokenBucketFilter(int maxTokens){
        this.maxTokens = maxTokens;
    }

    synchronized void getToken() throws InterruptedException{
        possibleTokens += (System.currentTimeMillis() - lastRequestTime)/1000;

        if(possibleTokens > maxTokens){
            possibleTokens = maxTokens;
        }

        if(possibleTokens == 0){
//            System.out.println("Sleeping : Thread " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis()/1000);
            Thread.sleep(1000);
        }else{
            possibleTokens--;
        }
        lastRequestTime = System.currentTimeMillis();
        System.out.println("Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis()/1000);
    }
}
