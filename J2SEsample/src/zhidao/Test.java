/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;

class Test {

    public static int SearchButtonIndex(Object[] buttons, Object button) {
        if (buttons.length == 0 || buttons == null) {
            return -1;
        }
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].equals(button)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        final JButton[] buttons = new JButton[20];
        frame.setLayout(new GridLayout(4, 5));
        for (int i = 0; i < 20; i++) {
            buttons[i] = new JButton(i + "");
            buttons[i].addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    System.out.println("这是第" + ((JButton) e.getSource()).getActionCommand());
                    System.out.println(SearchButtonIndex(buttons, e.getSource()));
                }

                public void mousePressed(MouseEvent e) {
                    System.out.println("这是第" + ((JButton) e.getSource()).getActionCommand());
                    System.out.println(SearchButtonIndex(buttons, e.getSource()));
                }

                public void mouseReleased(MouseEvent e) {
                    System.out.println("这是第" + ((JButton) e.getSource()).getActionCommand());
                    System.out.println(SearchButtonIndex(buttons, e.getSource()));
                }

                public void mouseEntered(MouseEvent e) {
                    System.out.println("这是第" + ((JButton) e.getSource()).getActionCommand());
                    System.out.println(SearchButtonIndex(buttons, e.getSource()));
                }

                public void mouseExited(MouseEvent e) {
                    System.out.println("这是第" + ((JButton) e.getSource()).getActionCommand());
                    System.out.println(SearchButtonIndex(buttons, e.getSource()));
                }
            });
            frame.getContentPane().add(buttons[i]);
            frame.setDefaultCloseOperation(3);
            frame.setVisible(true);
        }

    }
}
