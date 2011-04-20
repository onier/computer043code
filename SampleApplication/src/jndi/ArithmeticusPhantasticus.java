/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jndi;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArithmeticusPhantasticus {

    public static void main(String[] args) {
        new ArithmeticusPhantasticus().run(1);
//怎样在输入J后重新运行程序，输入N后退出？
    }

    void run(int e) {
        System.out.println("---Arithmeticus Phantasticus---");
        System.out.println("Denke an eine Zahl zwischen 0 und 99 und");
        System.out.println("klicke anschliessend auf die Taste ENTER.");
        int code = 0;
        try {
//这里下面内容怎样再按了ENTER后才打出来？
            code = System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(ArithmeticusPhantasticus.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (code != 10) {
            return;
        }
        System.out.println("Ziehen von deiner Zahl,die Ziffer der Zehnerstelle ab und Klicke auf ENTER.");
        System.out.println("Ziehen von diesem Zwischenergebnis die Ziffer der Einerstelle ab und \nKlicke auf ENTER.");
        System.out.println("Einen Moment.Ich muss mich konzentrieren.");
        String[] a = {"!", "@", "#", "$", "%", "^", "&", "*", "Q", "/"};
        int y = (int) (10 * Math.random());
        for (int i = 0; i < 10; i++) {
            int j = 0;
            System.out.println();
            while (j < 10) {
                int h = 10 * j + i;
                int x = (int) (10 * Math.random());
                switch (h) {
                    case 9:
                    case 18:
                    case 27:
                    case 36:
                    case 45:
                    case 54:
                    case 63:
                    case 72:
                    case 81:
                    case 90:
                        System.out.print(j + "" + i + " " + a[y] + "  ");
                        break;
                    default:
                        System.out.print(j + "" + i + " " + a[x] + "  ");
//为什么这语句执行时会出现 ArrayIndexOutOfBoundsException:10的错误。。
                        break;
                }
                j++;
            }
        }
        System.out.println();
        System.out.println("Suche die von dir berechnete Zahl in der Tabelle und merke dir\n das Symbol daneben.Klicke auf ENTER.");
        System.out.println("Das Symbol neben der von dir berechneten Zahl ist ein" + a[y]);
        System.out.println("Hatte ich recht? Naja,vielleicht war es diesmal nur Glueck.");
        System.out.println("Willst du nochmal spielen?<j/n>");
        try {
            code = System.in.read();
        } catch (IOException ex) {
            Logger.getLogger(ArithmeticusPhantasticus.class.getName()).log(Level.SEVERE, null, ex);
        }
        Method[] methods;
        for (;;) {
            if (code == 'j') {
                try {
                    Class test = Class.forName("jndi.ArithmeticusPhantasticus");
                    methods = test.getDeclaredMethods();
                    for (int i = 0; i < methods.length; i++) {
                        if (methods[i].getName().equals("run")) {
                            methods[i].invoke(this, 1);
                        }
                    }
                } catch (Exception ex) {
                    System.exit(0);
                }

            }
        }
    }
}

