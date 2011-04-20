/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2d;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class ImageTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        JPanel panel = new JPanel() {

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2D = (Graphics2D) g;
                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN));
                g2D.drawImage(ImageTest.createImage(Color.WHITE), 11, 11, null);
            }
        };
        panel.setBackground(Color.BLACK);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    public static BufferedImage createImage(Color color) {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
        image.createGraphics().fill(new Rectangle(0, 0, 100, 100));
        return image;
    }
}
