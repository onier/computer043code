/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Administrator
 */
public class LabelTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        final Color[] colors = new Color[]{Color.RED, Color.GREEN};
        JComboBox comboBox = new javax.swing.JComboBox();
        final JLabel label = new JLabel("        ");
        comboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"2009.01.01", "2009.01.02"}));
        frame.getContentPane().add(comboBox, BorderLayout.NORTH);
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(3);
        comboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                label.setIcon(createIcon(colors[(int) (Math.random() * 2)]));
            }
        });
        frame.setVisible(true);
    }

    static Icon createIcon(Color color) {
        BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = image.createGraphics();
        g.setColor(color);
        g.fill(new Rectangle(0, 0, 20, 20));
        return new ImageIcon(image);
    }
}
