package coreJava.multiThreading.etucatv.readWrite;


public class ReadWriteLock {
    int readers = 0;
    boolean isWriteLock = false;

    public synchronized void aquireReadLock() throws InterruptedException{
        while(isWriteLock){
            wait();
        }
        readers++;
    }

    public synchronized void releaseReadLock() {
        readers--;
        notify();
    }

    public synchronized void aquireWriteLock() throws InterruptedException{
        while (readers > 0 || isWriteLock){
            wait();
        }
        isWriteLock = true;
    }

    public synchronized void releaseWriteLock(){
        isWriteLock = false;
        notify();
    }
}
