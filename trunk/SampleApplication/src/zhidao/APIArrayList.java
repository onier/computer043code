/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.util.*;

class APIArrayList {

    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<Integer>();
        int b = 0;
        int sum = 0;
        int c = 0;
        System.out.println("please Enter");
        while (true) {
            try {
                b = System.in.read();
            } catch (Exception e) {
            }
            switch (b) {
                case '\r':
                    break;
                case '\n':
                    System.out.println(sum);
                    sum = 0;
                    break;
                case '\t':
                    return;
                default: {
                    c = b - '0';
                    a.add(Integer.valueOf(c));
                    Iterator i = a.iterator();
                    while (i.hasNext()) {
                        Integer it = (Integer) i.next();
                        sum += it.intValue();
                    }
                }
            }
        }
    }
}


