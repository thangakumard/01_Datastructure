package coreJava.multiThreading;

public class MultiThreading {

    /***
     * Difference between Runnable vs Thread in Java`a`1
     * https://howtodoinjava.com/java/multi-threading/java-runnable-vs-thread/
     *
     * https://www.youtube.com/watch?v=r_MbozD32eo
         1. Implementing Runnable is the preferred way to do it. Here, you’re not really specializing or modifying the thread’s behavior. You’re just giving the thread something to run. That means composition is the better way to go.
         2. Java only supports single inheritance, so you can only extend one class.
         3. Instantiating an interface gives a cleaner separation between your code and the implementation of threads.
         4. Implementing Runnable makes your class more flexible. If you extend Thread then the action you’re doing is always going to be in a thread. However, if you implement Runnable it doesn’t have to be. You can run it in a thread, or pass it to some kind of executor service, or just pass it around as a task within a single threaded application.     *
     * @param args
     */

    public static void main(String[] args){

        //By ExtendThread
        for(int i=1; i <=5; i++) {
            ExtendThread usingThread = new ExtendThread(i);
            usingThread.start();
        }

        //By implementing Runnable
        for(int i=1; i <=5; i++) {
            ImplementRunnable implementRunnable = new ImplementRunnable(i);
            Thread thread = new Thread(implementRunnable);
            thread.start();
        }
    }

}
