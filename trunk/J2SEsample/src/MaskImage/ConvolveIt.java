/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MaskImage;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ConvolveIt extends JFrame {

    Image image = getImage("C:\\未命.png");
    private static final float[] SHARP = {0.0f, -1.0f, 0.0f, -1.0f, 5.0f,
        -1.0f, 0.0f, -1.0f, 0.0f};
    BufferedImage bufferedImage;
    ConvolveOp convolveOp;

    public Image getImage(String imageFile) {
        ImageIcon icon = new ImageIcon(imageFile);
        return icon.getImage();
    }

    public ConvolveIt() {
        int width = image.getWidth(this);
        int height = image.getHeight(this);
        bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D big = bufferedImage.createGraphics();
        AffineTransform affineTransform = new AffineTransform();
        big.drawImage(image, affineTransform, this);
        Kernel kernel = new Kernel(3, 3, SHARP);
        convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (bufferedImage != null) {
            g2d.drawImage(bufferedImage, convolveOp, 10, 30);
        }
    }

    public static void main(String args[]) {
        Frame f = new ConvolveIt();
        f.setTitle("ConvolveIt");
        f.setSize(300, 250);
        f.show();
    }
}

