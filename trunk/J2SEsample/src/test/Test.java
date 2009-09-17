package test;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Test {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        frame.setBounds(10, 10, 300, 300);
        final JLabel label = new JLabel("123");
        panel.setLayout(null);
        panel.add(label);
        label.setBounds(0, 0, 53, 19);
        label.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = label.getLocation();
                label.setLocation(e.getPoint().x + point.x, e.getPoint().y + point.y);
            }
        });
        frame.setVisible(true);
    }
}
