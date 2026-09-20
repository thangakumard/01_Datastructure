package coreJava.multiThreading.interviewQuest.threadsafeRunningAvg;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadsafeRunningAverage_automic {
   private AtomicInteger counter = new AtomicInteger();
   private AtomicInteger totalTime = new AtomicInteger();
   public void addResponseTime(int resTime) throws InterruptedException {
       totalTime.addAndGet(resTime);
       counter.getAndIncrement();
       Thread.sleep(100);
   }
   public double getAverageResponseTime() throws InterruptedException {
       Thread.sleep(100);
       return (double) (totalTime.get()/ counter.get());
   }
}
class ThreadsafeRunningAverage_automicTest {
    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ThreadsafeRunningAverage_automic threadsafeRunningAverage = new ThreadsafeRunningAverage_automic();
        Random rand = new Random();
        Runnable task = () -> {
            try {
                threadsafeRunningAverage.addResponseTime(rand.nextInt(1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                System.out.println("Thread :" + Thread.currentThread().getName() + "- Avg:" + threadsafeRunningAverage.getAverageResponseTime());
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