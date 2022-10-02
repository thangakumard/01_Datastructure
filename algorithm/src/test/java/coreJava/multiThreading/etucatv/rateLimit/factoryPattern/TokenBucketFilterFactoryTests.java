package coreJava.multiThreading.etucatv.rateLimit.factoryPattern;


import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class TokenBucketFilterFactoryTests {

    @Test
    public void tokenBucketFactoryTest() throws InterruptedException {
        Set<Thread> allThreads = new HashSet<>();
        TokenBucketFilter tokenBucketFilter = TokenBucketFilterFactory.makeTokenBucketFilter(1);
        for(int i=1; i<=10; i++){
            Thread thread = new Thread(()->{
                try {
                    tokenBucketFilter.getToken();
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
