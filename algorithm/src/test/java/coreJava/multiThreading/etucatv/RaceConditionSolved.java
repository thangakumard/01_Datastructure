package coreJava.multiThreading.etucatv;

import java.util.*;

class RaceConditionSolvedDemonstration {

    public static void main(String args[]) throws InterruptedException {
        RaceConditionSolved.runTest();
    }
}

class RaceConditionSolved {

    int randInt;
    Random random = new Random(System.currentTimeMillis());

    void printer() {

        int i = 1000000;
        while (i != 0) {
            synchronized(this) {
                if (randInt % 5 == 0) {
                    if (randInt % 5 != 0)
                        System.out.println(randInt);
                }
            }
            i--;
        }
    }

    void modifier() {

        int i = 1000000;
        while (i != 0) {
            synchronized(this) {
                randInt = random.nextInt(1000);
                i--;
            }
        }
    }

    public static void runTest() throws InterruptedException {
        final RaceConditionSolved rc = new RaceConditionSolved();
        Thread thread1 = new Thread(() -> rc.printer());
        Thread thread2 = new Thread(() -> rc.modifier());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
