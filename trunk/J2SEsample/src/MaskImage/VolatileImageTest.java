/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MaskImage;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.VolatileImage;
import javax.swing.ImageIcon;

/**
 *
 * @author Administrator
 */
public class VolatileImageTest extends Applet implements ItemListener {

    private Thread animation;
    private Image offscreen;
    private Image tile;
    private int tileWidth;
    private int tileHeight;
    private Checkbox accelerated;

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    public void init() {
        tile = new ImageIcon("E:\\Sample\\Test\\logo.PNG").getImage();
//        while (tile.getWidth(this) <= 0)
        tileWidth = tile.getWidth(this);
        tileHeight = tile.getHeight(this);
        setLayout(new BorderLayout());
        accelerated = new Checkbox("use accelereted image ", null, true);
        accelerated.addItemListener(this);
        Panel p = new Panel();
        p.add(accelerated);
        add(p, BorderLayout.SOUTH);
        createOffscreenImage(accelerated.getState());
    }

    public void itemStateChanged(ItemEvent e) {
        if (accelerated == e.getSource()) {
            this.createOffscreenImage(accelerated.getState());
            repaint();
        }
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
        long time = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            paint(g);
        }
        if (offscreen instanceof VolatileImage) {
            System.out.println("VolatileImage it took" + (System.currentTimeMillis() - time));
        } else {
            System.out.println("BufferedIamge it took " + (System.currentTimeMillis() - time));
        }
    }

    @Override
    public void paint(Graphics g) {
//        super.paint(g);
        if (offscreen instanceof VolatileImage) {
            VolatileImage volatileImage = (VolatileImage) offscreen;
            do {
                if (volatileImage.validate(this.getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) {
                    this.createOffscreenImage(true);
                }
                paintScene(volatileImage.getGraphics());
            } while (volatileImage.contentsLost());
        } else {
            if (offscreen == null || offscreen.getWidth(null) != this.getSize().width ||
                    offscreen.getHeight(null) != this.getSize().height) {
                this.createOffscreenImage(false);
                this.paintScene(offscreen.getGraphics());
            }
        }
        g.drawImage(offscreen, 0, 0, this);
    }

    private void createOffscreenImage(boolean state) {
        if (state) {
            offscreen = this.getGraphicsConfiguration().createCompatibleVolatileImage(this.getSize().width, this.getSize().height);
        } else {
            offscreen = this.getGraphicsConfiguration().createCompatibleImage(this.getSize().width, this.getSize().height);
        }
    }

    private void paintScene(Graphics graphics) {
        Graphics2D g2D = (Graphics2D) graphics;
        int width = this.getSize().width;
        int height = this.getSize().height;
        for (int y = 0; y < height; y = y + tileHeight) {
            for (int x = 0; x < width; x = x + tileWidth) {
                g2D.drawImage(tile, x, y, this);
            }
        }
    }
}
