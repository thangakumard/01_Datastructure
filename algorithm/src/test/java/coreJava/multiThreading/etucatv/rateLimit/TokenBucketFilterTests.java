package coreJava.multiThreading.etucatv.rateLimit;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class TokenBucketFilterTests {

    @Test
    public void runTestMaxTokenIs1() throws InterruptedException {
        Set<Thread> allThreads = new HashSet<Thread>();
        final TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(1);
        for(int i=1; i <= 10; i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        tokenBucketFilter.getToken();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.setName("Thread :" + i);
            allThreads.add(thread);
        }

        for (Thread t: allThreads){
            t.start();
        }
        for (Thread t : allThreads) {
            t.join();
        }
    }

    /**
     * With bucket count as 5, the output will show that the first five threads are granted tokens immediately at the same second granularity instant.
     * After that, the subsequent threads are slowly given tokens at an interval of 1 second since one token gets generated every second.
     * @throws InterruptedException
     */
    @Test
    public void runTestMaxTokenIs5() throws InterruptedException {
        Set<Thread> allThreads = new HashSet<>();
        final TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(5);
        //Sleeps 10 Seconds
        Thread.sleep(10000);
        for(int i=1; i <= 10; i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        tokenBucketFilter.getToken();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.setName("Thread :" + i);
            allThreads.add(thread);
        }

        for (Thread t: allThreads){
            t.start();
        }
        for (Thread t : allThreads) {
            t.join();
        }
    }
}
