package coreJava.multiThreading.etucatv.thread10_barberShop;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShopProblem {
    final int Chairs = 3;
    int waitingCustomers = 0, hairCutsGiven = 0;
    Semaphore waitForCustomerToEnter = new Semaphore(1);
    Semaphore waitForBarberToGetReady = new Semaphore(1);

    Semaphore waitForCustomerToLeave = new Semaphore(1);

    Semaphore waitForBarberToCutTheHair = new Semaphore(1);
    ReentrantLock lock = new ReentrantLock();

    void customerWalksIn() throws InterruptedException{
        lock.lock();
        if(waitingCustomers == Chairs){
            System.out.println("Customer walks out. All the chairs are occupied!");
            lock.unlock();
            return;
        }
        waitingCustomers++;
        lock.unlock();

        waitForCustomerToEnter.release();
        waitForBarberToGetReady.acquire();

        lock.lock();
        waitingCustomers--;
        lock.unlock();

        waitForBarberToCutTheHair.acquire();
        waitForCustomerToLeave.release();
    }

    void barber() throws InterruptedException{
        while (true){
            waitForCustomerToEnter.acquire();
            waitForBarberToGetReady.release();
            hairCutsGiven++;
            System.out.println("hairCutsGiven : " + hairCutsGiven + ".Barber is cutting the hair for "+ Thread.currentThread().getName());
            waitForBarberToCutTheHair.release();
            waitForCustomerToLeave.acquire();
        }
    }
}
