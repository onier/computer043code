/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class WindowIcons extends JFrame {

    final static int BIG_ICON_RENDER_WIDTH = 32;
    final static int SMALL_ICON_WIDTH = 32;
    final static int SMALL_ICON_HEIGHT = 32;
    final static int SMALL_ICON_RENDER_WIDTH = 32;

    public WindowIcons() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();

        BufferedImage bi = new BufferedImage(SMALL_ICON_WIDTH, SMALL_ICON_HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, SMALL_ICON_RENDER_WIDTH, SMALL_ICON_HEIGHT);
        g.dispose();
        images.add(bi);
        images.add(bi);
        images.add(bi);
        images.add(bi);
        setIconImages(images);

        setSize(250, 100);
        setVisible(true);

        new JDialog(this, "Arbitrary Dialog") {

            {
                setSize(200, 100);
                setVisible(true);
            }
        };
    }

    public static void main(String[] args) {
        new WindowIcons();
    }
}
