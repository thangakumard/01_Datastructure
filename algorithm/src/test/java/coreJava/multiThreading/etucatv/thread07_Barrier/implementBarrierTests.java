package coreJava.multiThreading.etucatv.thread07_Barrier;

import org.testng.annotations.Test;

public class implementBarrierTests {

    @Test
    public void barrierTest_01() throws InterruptedException{
        ImplementBarrier barrier = new ImplementBarrier(3);

        Thread thread1 = new Thread(()->{
            try {
                barrier.await();
                barrier.await();
                barrier.await();
            }catch (InterruptedException ex){

            }
        });
        thread1.setName("Thread 1");
        Thread thread2 = new Thread(()->{
            try {
                barrier.await();
                barrier.await();
                barrier.await();
            }catch (InterruptedException ex){

            }
        });
        thread2.setName("Thread 2");
        Thread thread3 = new Thread(()->{
            try {
                barrier.await();
                barrier.await();
                barrier.await();
            }catch (InterruptedException ex){

            }
        });
        thread3.setName("Thread 3");
        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }
}
