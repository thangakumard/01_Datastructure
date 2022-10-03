package coreJava.multiThreading.etucatv.thread09_Philosophers;

import org.testng.annotations.Test;

import static coreJava.multiThreading.etucatv.thread09_Philosophers.DiningPhilosophers_01.startPhilosoper;

public class DiningPhilosophers_01Tests {

    @Test
    public static void runTest() throws InterruptedException {
        final DiningPhilosophers_01 dp = new DiningPhilosophers_01();

        Thread p1 = new Thread(new Runnable() {

            public void run() {
                startPhilosoper(dp, 0);
            }
        });

        Thread p2 = new Thread(new Runnable() {

            public void run() {
                startPhilosoper(dp, 1);
            }
        });

        Thread p3 = new Thread(new Runnable() {

            public void run() {
                startPhilosoper(dp, 2);
            }
        });

        Thread p4 = new Thread(new Runnable() {

            public void run() {
                startPhilosoper(dp, 3);
            }
        });

        Thread p5 = new Thread(new Runnable() {

            public void run() {
                startPhilosoper(dp, 4);
            }
        });

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();

        p1.join();
        p2.join();
        p3.join();
        p4.join();
        p5.join();
    }
}
