/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Point2D[] p1 = new Point2D[]{new Point2D.Float(180, 300), new Point2D.Float(220, 260), new Point2D.Float(380, 260), new Point2D.Float(420, 300)};
        Point2D[] p2 = new Point2D[]{new Point2D.Float(220, 260), new Point2D.Float(260, 220), new Point2D.Float(340, 220), new Point2D.Float(380, 260)};
        Point2D[] p3 = new Point2D[]{new Point2D.Float(260, 220), new Point2D.Float(300, 180), new Point2D.Float(340, 220)};
        Point2D[][] p = new Point2D[][]{p1, p2, p3};
        final Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE};
        final GeneralPath[] path = new GeneralPath[]{new GeneralPath(), new GeneralPath(), new GeneralPath()};
        for (int i = 0; i < path.length; i++) {
            fillPath(path[i], p[i]);
        }
        JPanel panel = new JPanel() {

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2D = (Graphics2D) g;
                for (int i = 0; i < path.length; i++) {
                    g2D.translate(0, -i * 20);
                    g2D.setColor(colors[i]);
                    g2D.fill(path[i]);
                    g2D.setColor(Color.LIGHT_GRAY);
                    g2D.draw(path[i]);
                    g2D.translate(0, i * 20);
                }
            }
        };
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }

    static void fillPath(GeneralPath path, Point2D[] points) {
        for (int i = 0; i < points.length; i++) {
            path.moveTo(points[i].getX(), points[i].getY());
            path.lineTo(points[(i + 1) % points.length].getX(), points[(i + 1) % points.length].getY());
            if (i < 1) {
                path.lineTo(points[(points.length - 1)].getX(), points[(points.length - 1)].getY());
            } else {
                path.lineTo(points[(i - 1) % points.length].getX(), points[(i - 1) % points.length].getY());
            }
        }
    }
}
