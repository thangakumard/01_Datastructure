package coreJava.multiThreading.interviewQuest.threadsafeCounter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadsafeCounter_syncronized {
    private int counter = 0;
    public void increment() throws InterruptedException {
        synchronized (this){
            counter++;
            Thread.sleep(40);
        }
    }
    public int getCounter(){
        return counter;
    }
}
class TestThreadsafeCounter_syncronized{
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ThreadsafeCounter_syncronized threadsafeCounter_syncronized = new ThreadsafeCounter_syncronized();
        Runnable task = () -> {
            try {
                threadsafeCounter_syncronized.increment();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread :" + Thread.currentThread().getName() + "- Counter: " + threadsafeCounter_syncronized.getCounter());
        };
        executor.submit(task);
        executor.submit(task);
        executor.submit(task);
        executor.shutdown();
    }
}
