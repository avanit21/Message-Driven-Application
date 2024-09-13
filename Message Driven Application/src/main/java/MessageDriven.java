package main.java;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * The MessageDriven class initializes the Producer and Consumer and starts their threads.
 * It also tracks and logs the total number of messages processed and errors encountered.
 */
public class MessageDriven {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger errorCount = new AtomicInteger();

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue, successCount, errorCount);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join(); // Wait for producer to finish
            consumerThread.interrupt(); // Interrupt consumer to stop processing
            consumerThread.join(); // Wait for consumer to stop
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the final counts of processed messages and errors
        System.out.println("Total messages processed: " + successCount.get());
        System.out.println("Total errors encountered: " + errorCount.get());
    }
}
