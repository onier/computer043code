/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Button;
import java.awt.Color;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class window extends JFrame {

    JTextField T1;
    JButton B1;

    public window() {
        this.setLayout(null);

        T1 = new JTextField("在这里输入");
        T1.setBounds(75, 60, 90, 25);
        this.add(T1);

        B1 = new JButton("按钮");
        B1.setBounds(75, 110, 90, 25);
        B1.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, T1.getText().trim());
            }
        });

        this.add(B1);

        this.setBackground(Color.yellow);
        this.setBounds(200, 100, 250, 180);
        this.setTitle("窗口");
        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
    }

    public static void main(String[] para) {
        new window();
    }
}
