package coreJava.multiThreading.etucatv.thread12_AsynchronousToSynchronous;

interface Callback {

    public void done();
}

public class Executor {
    public void asynchronousExecution(Callback callback) throws Exception {
        Thread t = new Thread(() -> {
            // Do some useful work
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
            }
            callback.done();
        });
        t.start();
    }
}
