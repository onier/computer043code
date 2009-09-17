/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

class Test {

    public static void main(String[] args) {
        System.getProperties().list(System.out);
        System.out.println(System.getProperty("user.home") + "/Desktop");
        JLabel label = new JLabel();
        label.setText("123456");
        Dimension size = label.getPreferredSize();
        BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_BGR);
//        label.paint(image.createGraphics()); /*这个方法只有label显示的时候才能用,而且图片样子和label显示的是一模一样的*/
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fill(new Rectangle(0,0,size.width,size.height));
        g.setColor(Color.BLACK);
        g.drawString(label.getText(), 0, 10);
        try {
            ImageIO.write(image, "jpg", new File("D:\\1.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
