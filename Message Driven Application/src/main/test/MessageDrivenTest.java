package main.test;


import main.java.Consumer;
import main.java.Producer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


import org.junit.jupiter.api.Test;

/**
 * The MessageDrivenTest class contains unit tests for the Producer and Consumer.
 * It verifies the functionality of message processing and error handling.
 */
public class MessageDrivenTest {
    /**
     * Tests successful message processing and error handling.
     * 
     * @throws InterruptedException If the test is interrupted while waiting for threads to finish.
     */
    @Test
    void testSuccessfulMessageProcessing() throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger errorCount = new AtomicInteger();

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue, successCount, errorCount);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        producerThread.join(); // Wait for producer to finish
        consumerThread.interrupt(); // Interrupt consumer to stop processing
        consumerThread.join(); // Wait for consumer to stop

        assertEquals(10, successCount.get(), "The number of successful messages should be 10.");
        assertEquals(1, errorCount.get(), "The number of errors should be 1, based on the simulated error condition.");
    }
}
