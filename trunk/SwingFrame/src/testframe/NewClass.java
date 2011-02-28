/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testframe;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author admin
 */
public class NewClass {

    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(3);
//        frame.setSize(200, 300);
//        frame.setContentPane(new DownButton());
//        frame.setVisible(true);
        System.out.println(Enum.class.getClass());
    }

    static class DownButton extends JButton {

        public DownButton() {
            super();
            this.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    System.out.println("1223123");
                }
            });
            this.setLayout(new BorderLayout());
            JButton button = new JButton("Test");
//            button.setBorder(null);
            this.add(button, BorderLayout.CENTER);
            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    System.out.println("1223123");
                }
            });
//            button = new JButton("..");
//            button.setBorder(null);
//            this.add(button, BorderLayout.EAST);
        }
    }
}
