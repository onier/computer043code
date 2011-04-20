/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

public class HelloWorld {

    private  static boolean initialized = false;
    static Thread t = new Thread(new Runnable() {

        public void run() {
            System.out.print(initialized);
            System.out.print("Hello ");
//            initialized = true;

        }
    });

    static {
        t.start();
        try {
            t.join(1);
        } catch (InterruptedException e) {
            throw new AssertionError(e);
        }

    }

    public static void main(String[] args) {
        System.out.println(" world!");
    }
}
