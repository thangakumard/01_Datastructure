package coreJava.multiThreading;

public class ImplementRunnable implements Runnable {
    private int threadNumber;
    public ImplementRunnable(int threadNumber){
        this.threadNumber = threadNumber;
    }
    @Override
    public void run() {
        for (int i=1; i <= 5; i++){
            System.out.println(i + "- From ImplementRunnable thread - " + this.threadNumber);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
