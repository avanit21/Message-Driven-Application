package main.java;
import java.util.concurrent.BlockingQueue;

/**
 * The Producer class generates messages and puts them into a BlockingQueue.
 * It implements Runnable to run in a separate thread.
 */
public class Producer implements Runnable {
    private final BlockingQueue<String> queue;

    /**
     * Constructor for Producer.
     * 
     * @param queue The BlockingQueue to which messages will be added.
     */
    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    /**
     * Runs the producer thread. Generates messages and adds them to the queue.
     * Simulates message production by sleeping for a short period.
     */
    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                String message = "Message " + i;
                queue.put(message);
                System.out.println("Produced: " + message);
                Thread.sleep(100); // Simulate time taken to produce a message
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}
