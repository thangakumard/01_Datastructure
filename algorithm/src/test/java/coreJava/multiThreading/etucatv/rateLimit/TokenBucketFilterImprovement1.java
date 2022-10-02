package coreJava.multiThreading.etucatv.rateLimit;

/**
 * 1. Improvement is creating daemonThread to add a token/sec
 *
 * //*** BAD CODE ***
 * Never start a thread in a constructor as the child thread can attempt to use the not-yet-fully constructed object using this.
 * This is an anti-pattern
 */
public class TokenBucketFilterImprovement1 {
    int maxBucketCount;
    int possibleTokens;

    public TokenBucketFilterImprovement1(int bucketSize){
        maxBucketCount = bucketSize;

        //*** BAD CODE: Never start a thread in a constructor as the child thread can attempt to use the not-yet-fully constructed object using this.
        // This is an anti-pattern
        Thread dt = new Thread(()->{
            daemonThread();
        });
        dt.setDaemon(true);
        dt.start();
    }

    private void daemonThread(){
        while (true){
            synchronized (this){
                if(possibleTokens < maxBucketCount){
                    possibleTokens++;
                }
                this.notify();
            }
            try{
                //Sleep for a second
                Thread.sleep(1000);
            }catch (InterruptedException ex){

            }
        }
    }

    void getToken() throws InterruptedException {
        synchronized (this){
            while (possibleTokens == 0){
                this.wait();
            }
            possibleTokens--;
        }
        System.out.println("Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis()/1000);
    }


}
