/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MaskImage;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

class GetRedFilter extends RGBImageFilter {

    public GetRedFilter() {
        canFilterIndexColorModel = true;
    }

    public int filterRGB(int x, int y, int rgb) {
        if (x == -1) {
        }
        return rgb & 0xffff0000;
    }
}

public class Main {

    public static void main(String[] argv) throws Exception {
        Image image = new ImageIcon("c:\\view.jpg").getImage();
        ImageFilter filter = new GetRedFilter();
        FilteredImageSource filteredSrc = new FilteredImageSource(image.getSource(), filter);
        image = Toolkit.getDefaultToolkit().createImage(filteredSrc);
        JOptionPane.showConfirmDialog(null, "2134", null, JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, new ImageIcon(image));
    }
}
