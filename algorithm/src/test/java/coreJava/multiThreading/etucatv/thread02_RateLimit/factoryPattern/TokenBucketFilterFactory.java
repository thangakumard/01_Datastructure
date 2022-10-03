package coreJava.multiThreading.etucatv.thread02_RateLimit.factoryPattern;

public final class TokenBucketFilterFactory {
    // Force users to interact with the factory
    // only through the static methods
    private TokenBucketFilterFactory() {
    }

    public static TokenBucketFilter makeTokenBucketFilter(int capacity){
        MultithreadedTokenBucketFilter tbf = new MultithreadedTokenBucketFilter(capacity);
        tbf.startDaemonThread();
        return tbf;
    }

    private static class MultithreadedTokenBucketFilter extends TokenBucketFilter{
        int maxBucketCount;
        int possibleTokens;

        // MultithreadedTokenBucketFilter object can only
        public MultithreadedTokenBucketFilter(int bucketSize){
            maxBucketCount = bucketSize;
        }

        void startDaemonThread(){
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

        public void getToken() throws InterruptedException {
            synchronized (this){
                while (possibleTokens == 0){
                    this.wait();
                }
                possibleTokens--;
            }
            System.out.println("Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis()/1000);
        }
    }
}
