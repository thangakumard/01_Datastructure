package coreJava.multiThreading.interviewQuest.threadsafeCounter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.*;

public class ThreadsafeCounter_automic {
    private AtomicInteger counter = new AtomicInteger();
    public int incrementCounter(){
        return counter.incrementAndGet();
    }
    public int getCounter(){
        return counter.get();
    }
}

class TestThreadsafeCounter_automic {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ThreadsafeCounter_automic threadsafeCounter = new ThreadsafeCounter_automic();

        Runnable task = () -> {
            System.out.println("Running :" + Thread.currentThread().getName() + "- Counter :" + threadsafeCounter.incrementCounter());
        };
        executor.submit(task);
        executor.submit(task);
        executor.submit(task);

        executor.shutdown();
    }
}
