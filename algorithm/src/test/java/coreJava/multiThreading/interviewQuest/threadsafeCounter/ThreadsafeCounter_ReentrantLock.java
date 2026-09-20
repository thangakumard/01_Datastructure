package coreJava.multiThreading.interviewQuest.threadsafeCounter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class ThreadsafeCounter_ReentrantLock {
    ReentrantLock lock = new ReentrantLock();
    int counter = 0;
    public void incrementCounter(){
       lock.lock();
       try{
           counter++;
           Thread.sleep(50);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       } finally {
           lock.unlock();
       }
    }
    public int getCounter(){
        return counter;
    }
}
class TestThreadsafeCounter_ReentrantLock {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ThreadsafeCounter_ReentrantLock threadsafeCounter_ReentrantLock = new ThreadsafeCounter_ReentrantLock();
        Runnable task = () -> {
            threadsafeCounter_ReentrantLock.incrementCounter();
            System.out.println("Running :" + Thread.currentThread().getName() + "- Counter :" + threadsafeCounter_ReentrantLock.getCounter());
        };
        executor.submit(task);
        executor.submit(task);
        executor.submit(task);

        executor.shutdown();
    }
}
