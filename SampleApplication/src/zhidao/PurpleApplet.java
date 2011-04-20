/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.applet.*;
import java.awt.*;
import java.awt.image.*; // Don't forget this!

public class PurpleApplet extends Applet {

    private Image theImage;

    public void init() {

        int red = 217;
        int green = 10;
        int blue = 186;
        int opaque = 255;
        int purple = (opaque << 24) | (red << 16) | (green <<  16) | blue;
        int[] pixels = new int[5000];
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = purple;
        }
        MemoryImageSource purpleMIS = new MemoryImageSource(100, 50, pixels, 0, 50);
        theImage = createImage(purpleMIS);

    }

    public void paint(Graphics g) {
        g.drawImage(theImage, 0, 0, this);
    }
}

