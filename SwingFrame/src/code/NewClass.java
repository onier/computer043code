/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Zhenhai.Xu
 */
public class NewClass {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        BufferedImage image = new BufferedImage(25, 25, BufferedImage.TYPE_INT_RGB);
        Rectangle rect = new Rectangle(0, 0, 25, 25);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.GREEN);
        g.fill(rect);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(0.5f));
        g.draw(rect);
        final TexturePaint paint = new TexturePaint(image, rect);
        frame.setContentPane(new JPanel() {

            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                Paint paint1 = g2d.getPaint();
                g2d.setPaint(paint);
                g2d.fill(new Rectangle(0, 0, getWidth(), getHeight()));
                g2d.setPaint(paint1);
            }
        });
        frame.setVisible(true);
    }
}
