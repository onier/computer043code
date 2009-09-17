/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MaskImage;

import java.awt.image.VolatileImage;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;

public class MyComponent
        extends JFrame {

    VolatileImage volImage;
    Image origImage = null;
    Graphics2D offScreenGraphics = null;
    int x = 0;
    int y = 0;
    int incX = 1;
    int incY = 1;

    MyComponent() {
        // Get image to move into accelerated image memory
        origImage = new ImageIcon("E:\\Sample\\Test\\cover.png").getImage();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        super.paint(g);
        // Draw accelerated image
//        volImage = drawVolatileImage((Graphics2D) g, volImage, x, y, origImage);
//        offScreenGraphics = (Graphics2D) volImage.getGraphics();
        for (int j = 0; j < 10000; j++) {
            System.out.println(j);
            g.drawImage(origImage, x, y, null);
        }
    }


// This method draws a volatile image and returns it or possibly a
// newly created volatile image object. Subsequent calls to this method
// should always use the returned volatile image.
// If the contents of the image is lost, it is recreated using orig.
// img may be null, in which case a new volatile image is created.
    public VolatileImage drawVolatileImage(Graphics2D g, VolatileImage img,
            int x, int y, Image orig) {
        final int MAX_TRIES = 100;
        for (int i = 0; i < MAX_TRIES; i++) {
            if (img != null) {
                // Draw the volatile image
                for (int j = 0; j < 10000; j++) {
                    System.out.println(j);
                    g.drawImage(img, x, y, null);
                }

                // Check if it is still valid
                if (!img.contentsLost()) {
                    System.out.println("CONTENT LOST");
                    return img;
                }
            } else {
                // Create the volatile image
                img = g.getDeviceConfiguration().createCompatibleVolatileImage(
                        orig.getWidth(null), orig.getHeight(null));
            }

            // Determine how to fix the volatile image
            switch (img.validate(g.getDeviceConfiguration())) {
                case VolatileImage.IMAGE_OK:

                    // This should not happen
                    break;
                case VolatileImage.IMAGE_INCOMPATIBLE:

                    // Create a new volatile image object;
                    // this could happen if the component was moved to another device
                    img.flush();
                    img = g.getDeviceConfiguration().createCompatibleVolatileImage(
                            orig.getWidth(null), orig.getHeight(null));
                case VolatileImage.IMAGE_RESTORED:
                    System.out.println("RESTORED");
                    // Copy the original image to accelerated image memory
                    Graphics2D gc = (Graphics2D) img.createGraphics();
                    gc.drawImage(orig, 0, 0, null);
                    gc.dispose();
                    break;
            }
        }

        // The image failed to be drawn after MAX_TRIES;
        // draw with the non-accelerated image
        System.out.println("SOFT RENDERED");
        return img;
    }

    public static void main(String[] args) {
        MyComponent m = new MyComponent();
        m.setSize(512, 512);
        m.setVisible(true);
    }
}
