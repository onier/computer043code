/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2d;

import java.util.logging.Level;
import java.util.logging.Logger;

class MyMethod {

    public void method1() {
        String str = "method1";
        for (int i = 1; i < 100; i++) {
            System.out.println(str);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println();
    }

    public void method2() {
        String str = "method2";
        for (int i = 01; i < 100; i++) {
            System.out.println(str);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println();
    }
}

public class MyThread extends Thread {

    private int method;

    public MyThread(MyMethod myMethod, int method) {
        this.myMethod = myMethod;
        this.method = method;
    }
    private MyMethod myMethod = null;

    public void run() {
        if (method == 1) {
            myMethod.method1();
        } else {
            myMethod.method2();
        }
    }

    public static void main(String[] args) {
        MyMethod myMethod = new MyMethod();
        MyThread thread1 = new MyThread(myMethod, 1);
        MyThread thread2 = new MyThread(myMethod, 2);
        thread1.start();
        thread2.start();
//        try {
//            thread1.join();
//            thread2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
