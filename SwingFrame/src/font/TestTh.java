/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package font;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class TestTh extends JFrame {

    public static void main(String[] args) {
        TestTh t = new TestTh();
        t.add(new Thumbnail("C:\\Documents and Settings\\All Users\\Documents\\My Pictures\\Sample Pictures\\1262613Y910520-14436.jpg"),BorderLayout.CENTER);
        t.setSize(600, 400);
        t.setVisible(true);
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Thumbnail extends JComponent {

    private Image img;
    double ratio;
    String path;
    private AffineTransform af = AffineTransform.getScaleInstance(1, 1);

    public Thumbnail() {
        setSize(180, 200);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Thumbnail(String path) {
        this.path = path;
        setSize(180, 200);
        setImage();
    }

    private void setImage() {
        try {
            img = ImageIO.read(new File(path));
            ratio = culculateRatio(img.getWidth(null), img.getHeight(null));
            //paintImmediately(getBounds());
        } catch (IOException ex) {
            System.out.println("Can not find this file!");
        }
        //paintImmediately(getBounds());
        concateZoom(ratio);
    }

    private void concateZoom(double scale) {
        if (img == null) {
            return;
        }
        af.preConcatenate(AffineTransform.getScaleInstance(scale, scale));
        paintImmediately(getBounds());
        System.out.println(getWidth() + "&" + getHeight());//此处输出180 & 200
    }

    protected void paintComponent(Graphics g) {
        System.out.println(getWidth() + "*" + getHeight());//此处输出1 * 1，最终画出的图只有一个点
        super.paintComponent(g);
        if (img == null) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            Graphics2D g2d = (Graphics2D) g;
            Dimension size = this.getSize();
            g2d.drawImage(img.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH), 0, 0, null);
        }
    }//paintComponent

    final double culculateRatio(int width, int height) {//计算缩放比率
        //System.out.println(getWidth() + "*" + getHeight());
        if (getWidth() >= width && getHeight() >= height) {
            return 1;
        }
        if ((double) getWidth() / width > (double) getHeight() / height) {
            return (double) getHeight() / height;
        } else {
            return (double) getWidth() / width;
        }
    }//culculateRatio
}//Thumbnail