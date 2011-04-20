/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jndi;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Sample {

    public Socpket s = new Socpket();
    public Thread thread = new Thread(s);

    public void a() {
        thread.start();
    }

    public void b() {
        s.flag = false;
    }

    public static void main(String args[]) {
        Sample t = new Sample();
        t.a();
        t.b();
//        Thread th = new Thread(new Socpket());
//        th.start();
//        th.stop();
    }
}

class Socpket implements Runnable {

    static int i;
    boolean flag = true;

    public void run() {
        if (!flag) {
            return;
        }
        for (i = 0; i <= 100; i++) {
            System.out.println(i);
        }
    }
}
