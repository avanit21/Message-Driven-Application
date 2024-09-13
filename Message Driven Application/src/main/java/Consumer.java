package main.java;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * The Consumer class retrieves messages from a BlockingQueue and processes them.
 * It implements Runnable to run in a separate thread.
 */
public class Consumer implements Runnable {
    private final BlockingQueue<String> queue;
    private final Logger logger = Logger.getLogger(Consumer.class.getName());;
    private final AtomicInteger successCount;
    private final AtomicInteger errorCount;

    /**
     * Constructor for Consumer.
     * 
     * @param queue The BlockingQueue from which messages will be consumed.
     * @param successCount AtomicInteger to track the number of successfully processed messages.
     * @param errorCount AtomicInteger to track the number of errors encountered.
     */
    public Consumer(BlockingQueue<String> queue, AtomicInteger successCount, AtomicInteger errorCount) {
        this.queue = queue;
        this.successCount = successCount;
        this.errorCount = errorCount;
    }

    /**
     * Runs the consumer thread. Continuously takes messages from the queue and processes them.
     * Logs any errors encountered during processing and updates counts accordingly.
     */
    @Override
    public void run() {
        try {
            while (true) {
                String message = queue.take();
                processMessage(message);
                successCount.incrementAndGet();
                System.out.println("Consumed: " + message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }

    /**
     * Processes a message. Simulates a processing error if the message contains "5".
     * 
     * @param message The message to process.
     */
    private void processMessage(String message) {
        try {
            // Simulate processing of the message
            if (message.contains("5")) { // Simulate an error condition
                throw new RuntimeException("Processing error");
            }
        } catch (Exception e) {
            logger.severe("Error processing message: " + message);
            errorCount.incrementAndGet();
        }
    }
}
