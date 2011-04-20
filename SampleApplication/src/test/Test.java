package test;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Test {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JButton button = new JButton();
        button.setLayout(new java.awt.FlowLayout());
        button.add(new JButton(1 + ""));
        button.add(new JButton(2 + ""));
        button.add(new JButton(3 + ""));
        button.add(new JButton(4 + ""));
        frame.setContentPane(button);
        frame.setVisible(true);
    }

    @Position
    @Override
    public String toString() {
        return super.toString();
    }

}
