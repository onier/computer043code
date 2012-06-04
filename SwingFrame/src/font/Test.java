/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package font;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        frame.setContentPane(new GrapPanel());
        frame.setSize(500, 400);
        frame.setVisible(true);
    }

    static class GrapPanel extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            Dimension size = this.getSize();
            QuadCurve2D.Double q1 = new QuadCurve2D.Double(0, size.height, 100, size.height - 50, 200, size.height);
            g2d.draw(q1);
        }
    }
}
