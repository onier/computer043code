/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MaskImage;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Scale extends JFrame {

    BufferedImage image;
    Insets insets;

    public Scale() {
        super();
        Image i = new ImageIcon("C:\\未命.png").getImage();
        image = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        image.createGraphics().drawImage(i, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        BufferedImage temp = ImageUtils.createHeadlessSmoothBufferedImage(image, BufferedImage.TYPE_INT_ARGB, image.getWidth() + 30, image.getHeight() + 30);
        g.drawImage(temp, 50, 50, null);
    }

    private void rest() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    public static void main(String args[]) {
        for (String i : args) {
        }
        Scale f = new Scale();
        f.setDefaultCloseOperation(3);
        f.setSize(400, 400);
        f.show();
    }
}
