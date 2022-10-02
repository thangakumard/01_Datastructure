package coreJava.multiThreading.etucatv.unisexBathroomProblem;

import java.util.concurrent.Semaphore;

public class UnisexBathroom {
    static final String WOMEN = "women";
    static final String MEN = "men";
    static final String NONE = "none";

    String inUserBy = NONE;
    int useCount = 0;
    Semaphore maxCapacity = new Semaphore(3);

   protected void maleUseBathroom(String name) throws InterruptedException {
        synchronized (this){
            while (inUserBy.equals(WOMEN)){
                this.wait();
            }
            maxCapacity.acquire();
            useCount++;
            inUserBy = MEN;
        }

        useBathRoom(name);
        maxCapacity.release();

        synchronized (this){
            useCount--;
            if(useCount == 0){
                inUserBy = NONE;
                this.notifyAll();
            }
        }
    }

    protected void femaleUseBathroom(String name) throws InterruptedException {
        synchronized (this){
            while (inUserBy.equals(MEN)){
                this.wait();
            }
            maxCapacity.acquire();
            useCount++;
            inUserBy = WOMEN;
        }

        useBathRoom(name);
        maxCapacity.release();

        synchronized (this){
            useCount--;
            if(useCount == 0){
                inUserBy = NONE;
                this.notifyAll();
            }
        }
    }

    void useBathRoom(String name) throws InterruptedException{
        System.out.println(name +" is using the bathroom. Usage count is :" + useCount + ".Used at :" + System.currentTimeMillis());
        Thread.sleep(3000);
        System.out.println(name + " is done using the bathroom.");
    }

}
