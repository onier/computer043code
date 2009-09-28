/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.util.Arrays;
import javax.swing.JButton;

class Main {

    static A a = new A();

    public static int SearchButtonIndex(JButton[] buttons, JButton button) {
        if (buttons.length == 0 || buttons == null) {
            return -1;
        }
        return Arrays.binarySearch(buttons, button);
    }

    public static void main(String[] args) {
        int c = 1;
        int b = c;
        c = 3;
        System.out.println(b);
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

        a.launch();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("uncaughtException() 捕获自线程 " + t.getName() + " 抛出的异常:" + e);
        Main.a.dispose(); // 摧毁
    }
}