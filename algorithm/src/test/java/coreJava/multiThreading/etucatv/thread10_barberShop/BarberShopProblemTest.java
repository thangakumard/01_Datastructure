package coreJava.multiThreading.etucatv.thread10_barberShop;

import org.testng.annotations.Test;

import java.util.HashSet;

public class BarberShopProblemTest {

    @Test
    public void barberShopTest_01() throws InterruptedException {
        HashSet<Thread> set = new HashSet<Thread>();
        final BarberShopProblem barberShopProblem = new BarberShopProblem();

        Thread barberThread = new Thread(new Runnable() {
            public void run() {
                try {
                    barberShopProblem.barber();
                } catch (InterruptedException ie) {

                }
            }
        });
        barberThread.setName("barberThread");
        barberThread.start();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        barberShopProblem.customerWalksIn();
                    } catch (InterruptedException ie) {

                    }
                }
            });
            t.setName("Thread "+i+1);
            set.add(t);
        }

        for (Thread t : set) {
            t.start();
        }

        for (Thread t : set) {
            t.join();
        }

        set.clear();
        Thread.sleep(500);

        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        barberShopProblem.customerWalksIn();
                    } catch (InterruptedException ie) {

                    }
                }
            });
            t.setName("Thread II:"+i+1);
            set.add(t);
        }
        for (Thread t : set) {
            t.start();
            Thread.sleep(5);
        }

        barberThread.join();
    }
}
