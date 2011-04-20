/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animatino;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class FutureTastTest {

    public static void main(String[] args) {
    }
}

class Task implements Runnable {

    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(FutureTastTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("sfjlkasjlkf");
    }
}
