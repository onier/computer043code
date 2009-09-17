/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class NewClass {

    Ellipse2D.Float ellipse = new Ellipse2D.Float(0, 0, 50, 50);
    Point point;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        JPanel panel = new JPanel() {

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics g2D = g;
            }
        };
    }
}
