package coreJava.multiThreading.etucatv.thread12_AsynchronousToSynchronous;

import org.testng.annotations.Test;

public class SynchronousExecutorTests {

    @Test
    public void SynchronousExecutorTest_01() throws Exception {
        SynchronousExecutor executor = new SynchronousExecutor();
        executor.asynchronousExecution(() -> {
            System.out.println("I am done");
        });

        System.out.println("main thread exiting...");
    }
}
