package coreJava.multiThreading.etucatv.thread02_RateLimit;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class TokenBucketFilterImprovement1Tests {
    @Test
    public void maxToken5Test() throws InterruptedException {
        Set<Thread> allThreads = new HashSet<>();
        TokenBucketFilterImprovement1 tokenBucketFilterImprovement1 = new TokenBucketFilterImprovement1(5);
        for(int i=1; i<=10; i++){
            Thread thread = new Thread(()->{
                try {
                    tokenBucketFilterImprovement1.getToken();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.setName("Thread :" + i);
            allThreads.add(thread);
        }

        for(Thread tr: allThreads){
            tr.start();
        }
        for(Thread tr: allThreads){
            tr.join();
        }
    }
}
