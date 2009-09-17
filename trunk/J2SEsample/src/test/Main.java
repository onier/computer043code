/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.awt.Rectangle;

/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Rectangle rect1 = new Rectangle(0,0,10,10);
        Rectangle rect2 = new Rectangle(0,0,20,20);
        System.out.println(rect1.intersection(rect2));
    }

}
