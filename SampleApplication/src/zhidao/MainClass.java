/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageConsumer;
import java.awt.image.MemoryImageSource;

public class MainClass extends Applet {

    Image image;

    public void init() {
        int blackInt = Color.black.getRGB();
        int pix[] = new int[100 * 100];
        int n = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                pix[n] = blackInt;
                n++;
            }
        }
        image = createImage(new MyImageSource(100, 100, pix));
    }

    public void paint(Graphics g) {
        g.drawImage(image, getSize().width / 2 - 50, 5, this);
    }
}

class MyImageSource extends MemoryImageSource {

    MyImageSource(int w, int h, int[] pix) {
        super(w, h, pix, 0, w);
    }

    public void addConsumer(ImageConsumer ic) {
        super.addConsumer(ic);
    }

    public boolean isConsumer(ImageConsumer ic) {
        return super.isConsumer(ic);
    }

    public void removeConsumer(ImageConsumer ic) {
        super.removeConsumer(ic);
    }

    public void startProduction(ImageConsumer ic) {
        super.startProduction(ic);
    }

    public void requestTopDownLeftRightResend(ImageConsumer ic) {
        requestTopDownLeftRightResend(ic);
    }
}
