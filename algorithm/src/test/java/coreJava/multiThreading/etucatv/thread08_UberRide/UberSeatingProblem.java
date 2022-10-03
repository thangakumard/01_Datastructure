package coreJava.multiThreading.etucatv.thread08_UberRide;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class UberSeatingProblem {
    int team1Count = 0;
    int team2Count = 0;

    Semaphore team1Waiting = new Semaphore(0);
    Semaphore team2Waiting = new Semaphore(0);


    CyclicBarrier barrier = new CyclicBarrier(4);
    ReentrantLock lock = new ReentrantLock();

    /***
     *
     * Case 1: If team1Waiting == 4
     * Case 2: If team1Waiting == 2 and team2Waiting >= 2
     * Case 3: If team1Waiting < 2 and team2Waiting < 2
     */
    void team1BooksCab() throws InterruptedException, BrokenBarrierException {
        boolean isRideLeader = false;
        lock.lock();
        team1Count++;
        if(team1Count == 4){
            team1Waiting.release(3);
            team1Count -= 4;
            isRideLeader = true;
        }else if(team1Count == 2 && team2Count >= 2){
            team1Waiting.release(1);
            team2Waiting.release(2);
            isRideLeader = true;
            team1Count -= 2;
            team2Count -= 2;
        }else {
            lock.unlock();
            team1Waiting.acquire();
        }

        occupySeats();
        barrier.await();

        if(isRideLeader){
            driveCab();
            lock.unlock();
        }
    }

    void team2BooksCab() throws InterruptedException, BrokenBarrierException {
        boolean isRideLeader = false;
        lock.lock();
        team2Count++;

        if(team2Count == 4){
            team2Waiting.release(3);
            team2Count -= 4;
            isRideLeader = true;
        }else if(team2Count == 2 && team1Count >= 2){
            team1Waiting.release(2);
            team2Waiting.release(1);
            isRideLeader = true;
            team1Count -= 2;
            team2Count -= 2;
        }else{
            lock.unlock();
            team2Waiting.acquire();
        }
        occupySeats();
        barrier.await();

        if(isRideLeader){
            driveCab();
            lock.unlock();
        }
    }

    void occupySeats(){
        System.out.println(Thread.currentThread().getName() + " is seated!");
        System.out.flush();
    }

    void driveCab(){
        System.out.println("The ride starts....The ride leader is :" + Thread.currentThread().getName());
        System.out.flush();
    }
}
