/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package color;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;



import java.awt.TexturePaint;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorComposite extends JFrame {

    MyCanvas canvas;
    JTextField textField;
    float alphaValue = 0.65f;

    public ColorComposite() {
        super();
        Container container = getContentPane();

        canvas = new MyCanvas();
        container.add(canvas);

        JPanel panel = new JPanel();

        JLabel label = new JLabel("Color-Composite: ");

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 65);
        slider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                JSlider tempSlider = (JSlider) e.getSource();
                alphaValue = (float) (tempSlider.getValue() / 100.0);
                textField.setText(Float.toString(alphaValue));
                canvas.repaint();
            }
        });

        textField = new JTextField("0.65", 4);

        panel.add(label);
        panel.add(slider);
        panel.add(textField);

        container.add(BorderLayout.SOUTH, panel);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(450, 450);
        setVisible(true);
    }

    public static void main(String arg[]) {
        new ColorComposite();
    }

    class MyCanvas extends JLabel {

        Rectangle2D rec1, rec2, rec3, rec4, rec5;

        MyCanvas() {
            rec1 = new Rectangle2D.Float(25, 25, 75, 150);
            rec2 = new Rectangle2D.Float(125, 25, 100, 75);
            rec3 = new Rectangle2D.Float(75, 125, 125, 75);
            rec4 = new Rectangle2D.Float(225, 125, 125, 75);
            rec5 = new Rectangle2D.Float(150, 50, 125, 175);

            setBackground(Color.white);
            setSize(400, 225);
        }

        public void paint(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;

            AlphaComposite ac = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alphaValue);
            g2D.setComposite(ac);

            g2D.setStroke(new BasicStroke(5.0f));
            g2D.draw(rec1);

            GradientPaint gp = new GradientPaint(125f, 25f, Color.yellow, 225f,
                    100f, Color.blue);
            g2D.setPaint(gp);
            g2D.fill(rec2);

            BufferedImage bi = new BufferedImage(5, 5,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D big = bi.createGraphics();
            big.setColor(Color.magenta);
            big.fillRect(0, 0, 5, 5);
            big.setColor(Color.black);
            big.drawLine(0, 0, 5, 5);
            Rectangle r = new Rectangle(0, 0, 5, 5);

            TexturePaint tp = new TexturePaint(bi, r);

            g2D.setPaint(tp);
            g2D.fill(rec3);

            g2D.setColor(Color.green);
            g2D.fill(rec4);
            g2D.setColor(Color.red);
            g2D.fill(rec5);
        }
    }
}
