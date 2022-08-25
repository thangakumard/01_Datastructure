package coreJava.multiThreading;

public class ExtendThread extends Thread {
    private int threadNumber;

    public ExtendThread(int threadNumber){
        this.threadNumber = threadNumber;
    }

    @Override
    public void run(){
        for (int i=1; i <= 1000; i++){
            System.out.println(i + "- From ExtendThread thread - " + this.threadNumber + "isDaemon :" + Thread.currentThread().isDaemon());
            //System.out.println(i + " -" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
