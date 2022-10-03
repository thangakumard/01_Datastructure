package coreJava.multiThreading.etucatv.barberShop;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShopProblem {
    final int Chairs = 3;
    int waitingCustomers = 0;
    Semaphore waitForCustomerToEnter = new Semaphore(1);
    Semaphore waitForBarberToGetReady = new Semaphore(1);
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
    }

    void barber() throws InterruptedException{

    }
}
