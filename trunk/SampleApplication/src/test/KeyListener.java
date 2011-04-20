/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.*;
import javax.swing.*;


public class KeyListener {

    public static void main(String[] agrs) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                TetrisFrame frame = new TetrisFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
class TetrisFrame extends JFrame {

    public TetrisFrame() {
        setSize(300, 200);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    add(new MessagePanel(), BorderLayout.EAST);//代码一
    add(messagePanel, BorderLayout.EAST);//代码二
    }
    private MessagePanel messagePanel = new MessagePanel();//代码三
}

class MessagePanel extends JPanel {

    public MessagePanel() {
        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        infoPanel.add(new JLabel("得分"));
        infoPanel.add(new JTextField(" 0"));
        infoPanel.add(new JLabel("消行"));
        infoPanel.add(new JTextField(" 0"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(new JButton("没有的按钮"));
        buttonPanel.add(new JButton("没有的按钮"));

        setLayout(new GridLayout(2, 1, 0, 4));
        add(infoPanel);
        add(buttonPanel);
    }
}