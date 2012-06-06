/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JDialog;

class PrintTask implements Runnable {

    private int sleepTime;
    private String threadName;
    private static Random generator = new Random();

    public PrintTask(String name) {
        threadName = name;
        sleepTime = generator.nextInt(5000);
    }

    public void run() {
        try {
            System.out.printf("%s going to sleep for %d milliseconds.\n", threadName, sleepTime);
            Thread.sleep(sleepTime);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        System.out.printf("%s done sleeping\n", threadName);
    }
}

public class RunnableTester {

    public static void main(String[] args) {
        // create and name each runnable
        PrintTask task1 = new PrintTask("thread1");
        PrintTask task2 = new PrintTask("thread2");
        PrintTask task3 = new PrintTask("thread3");

        System.out.println("Starting threads");

        // create ExecutorService to manage threads
        ExecutorService threadExecutor = Executors.newFixedThreadPool(3);

        // start threads and place in runnable state
        threadExecutor.execute(task1); // start task1
        threadExecutor.execute(task2); // start task2
        threadExecutor.execute(task3); // start task3

        threadExecutor.shutdown(); // shutdown worker threads

        System.out.println("Threads started, main ends\n");
    } // end main
} // end class RunnableTester

