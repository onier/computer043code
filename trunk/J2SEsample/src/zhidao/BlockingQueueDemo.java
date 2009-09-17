/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {

    public static void main(String[] args) {
        //容器最大装载数
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(5);
        Thread producerThread = new Thread(new BQProduc(queue));
        Thread consumerThread = new Thread(new BQConsume(queue));
        producerThread.start();
        consumerThread.start();
    }
}

class BQProduc implements Runnable {

    private BlockingQueue<Integer> queue;

    public BQProduc(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public void run() {
        for (int product = 1; product <= 10; product++) {

            try {
                Thread.sleep((int) (Math.random() * 3000));
                queue.put(product);//放进去一个
                System.out.println("放在 --> " + this.queue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class BQConsume implements Runnable {

    private BlockingQueue<Integer> queue;

    public BQConsume(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep((int) (Math.random() * 1000));
                System.out.println("拿出 <-- " + this.queue.size());
                queue.take();//拿出来一个
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
