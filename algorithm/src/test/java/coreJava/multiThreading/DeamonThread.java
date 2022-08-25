package coreJava.multiThreading;

public class DeamonThread {


    /**
     * Reference : https://www.youtube.com/watch?v=tLF1wYZs4Bk
     * @param args
     */
    public static void main(String[] args) {

        /**
         * IMPORTANT : Daemon thread is a low priority thread.
         * JVM does not care whether Daemon thread is completed or not.
         * If JVM process finds running daemon thread upon completion of user thread, it terminates the daemon thread and it shutdown itself
         */

        //By ExtendThread
        ExtendThread usingThread = new ExtendThread(1);
        usingThread.setDaemon(true);
        usingThread.start();


        //Using Runnable
        ImplementRunnable implementRunnable = new ImplementRunnable(1);
        Thread thread = new Thread(implementRunnable);
        thread.setDaemon(true);
        thread.start();

        for(int i=0; i < 10; i++){
            System.out.println("From main class :" + i);
        }



    }
}
