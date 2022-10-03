package coreJava.multiThreading.etucatv.thread05_readWrite;

public class ReadWriteTest {

    public static void main(String[] args) throws Exception {
        final ReadWriteLock rwl = new ReadWriteLock();

        Thread thread1 = new Thread(() -> {
            System.out.println("Thread1 - Attempting to acquire write lock!");
            try {
                rwl.aquireWriteLock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread1 - Write lock is acquired");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread1 - Releasing the write lock!");
            rwl.releaseWriteLock();

        });
        Thread thread2 = new Thread(() -> {
            System.out.println("Thread2 - Attempting to acquire write lock!");
            try {
                rwl.aquireWriteLock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread2 - Write lock is acquired");
        });

        Thread read1 = new Thread(() -> {
            System.out.println("read1 - Attempting to acquire read lock!");
            try {
                rwl.aquireReadLock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread read2 = new Thread(() -> {
            System.out.println("read2 - Releasing the read lock!");
            rwl.releaseReadLock();
        });

        thread1.start();
        thread2.start();
        read1.start();
        read2.start();
    }

}
