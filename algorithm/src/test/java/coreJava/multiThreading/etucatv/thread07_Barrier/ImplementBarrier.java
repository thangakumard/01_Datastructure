package coreJava.multiThreading.etucatv.thread07_Barrier;

public class ImplementBarrier {
    int threadsCount = 0;
    int threadsToRelease = 0;
    int barrierLimit = 0;

    public ImplementBarrier(int barrierSize){
        barrierLimit = barrierSize;
    }

    public synchronized void await() throws InterruptedException {
        while(threadsCount == barrierLimit)
            wait(); //once threads count reached the barrierLimit, the new threads will wait (until line 29 called to awake)

        threadsCount++;
        if(threadsCount == barrierLimit){
            System.out.println(Thread.currentThread().getName() + "- barrier limit is reached. Releasing the waiting threads!");
            threadsToRelease = barrierLimit;
            notifyAll();
        }else{
            System.out.println(Thread.currentThread().getName() + "- is waiting in the barrier.");
            while(threadsCount < barrierLimit){
                wait();
            }
        }

        threadsToRelease--;
        if(threadsToRelease == 0){
            threadsCount = 0;
            notifyAll();
        }
    }
}
