/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class NewClass {

    public static void main(String[] args) {
        int result = JOptionPane.showConfirmDialog(null, "U1");
        if (result == JOptionPane.YES_OPTION) {
            result = JOptionPane.showConfirmDialog(null, "U2");
            if (result == JOptionPane.NO_OPTION) {
                result = JOptionPane.showConfirmDialog(null, "U1");
            } else {
                if (result == JOptionPane.YES_OPTION) {
                    JFrame frame = new JFrame();
                    frame.setBounds(0, 0, 200, 200);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
            }
        }
    }
}
