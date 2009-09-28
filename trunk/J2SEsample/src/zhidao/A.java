/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class A extends JFrame {

    public A() {
        JButton b = new JButton();
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                throw new RuntimeException("error");
            }
        });
        this.getContentPane().setLayout(new GridLayout());
        this.getContentPane().add(b);
    }

    public void launch() {
        this.setVisible(true);
    }
}

