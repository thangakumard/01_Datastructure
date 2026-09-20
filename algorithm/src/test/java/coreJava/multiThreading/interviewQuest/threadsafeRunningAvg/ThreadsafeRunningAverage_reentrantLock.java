package coreJava.multiThreading.interviewQuest.threadsafeRunningAvg;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadsafeRunningAverage_reentrantLock {
    ReentrantLock lock = new ReentrantLock();
    int counter = 0;
    int totalTime = 0;
    public void addResponseTime(int resTime){
        lock.lock();
        try{
            totalTime += resTime;
            counter++;
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
    public double getAverageResponseTime(){
        return (double) totalTime/counter;
    }
}
class ThreadsafeRunningAverage_reentrantLock_Test{
    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ThreadsafeRunningAverage_reentrantLock threadsafeRunningAverage_reentrantLock = new ThreadsafeRunningAverage_reentrantLock();
        Random rand = new Random();

        Runnable task = () -> {
            threadsafeRunningAverage_reentrantLock.addResponseTime(rand.nextInt(100));
            System.out.println("Thread: " + Thread.currentThread().getName() + "- Avg:" + threadsafeRunningAverage_reentrantLock.getAverageResponseTime());
        };

        executor.submit(task);
        executor.submit(task);
        executor.submit(task);

        executor.shutdown();
    }
}