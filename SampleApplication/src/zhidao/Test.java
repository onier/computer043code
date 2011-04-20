/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

class MyThread extends Thread {

    static Test t = new Test();

    @Override
    public void run() {
        t.test();
        System.out.println("Thread say:Hello,World!");
    }
}

public class Test {

    int x = 0;

    public synchronized void test() {
        notifyAll();
        if (x == 0) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new MyThread().start();
        new MyThread().start();
    }
} 
