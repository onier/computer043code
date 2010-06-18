/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2d;

import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class ThreadTest {

    static final String SYN = "123";

    public static void main(String[] args) {
        HashMap m = new HashMap();
        m.isEmpty();
        java.util.Collections

        while (true) {
            new Thread(new Runnable() {

                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    synchronized (SYN) {
                        System.out.println("Thread 1 running");
                    }
                }
            }).start();
            new Thread(new Runnable() {  

                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    synchronized (SYN) {
                        System.out.println("Thread 2 running");
                    }
                }
            }).start();
        }
    }
}
