package coreJava.multiThreading.interviewQuest.threadsafeRunningAvg;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadsafeRunningAverage_syncronized {
    int counter = 0;
    int totalTime = 0;
    public void addResponseTime(int resTime) throws InterruptedException {
        synchronized (this){
            totalTime += resTime;
            counter++;
            Thread.sleep(300);
        }
    }
    public double getAverageResponseTime(){
        return (double) totalTime/counter;
    }

}
class ThreadsafeRunningAverage_syncronized_Test{
    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ThreadsafeRunningAverage_syncronized threadsafeRunningAverage_syncronized = new ThreadsafeRunningAverage_syncronized();
        Random rand = new Random();

        Runnable task = () -> {
            try {
                threadsafeRunningAverage_syncronized.addResponseTime(rand.nextInt(100));
                System.out.println("Thread: " + Thread.currentThread().getName() + "- Avg:" + threadsafeRunningAverage_syncronized.getAverageResponseTime());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        executor.submit(task);
        executor.submit(task);
        executor.submit(task);

        executor.shutdown();
    }
}
