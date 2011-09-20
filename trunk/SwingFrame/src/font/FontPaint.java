/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package font;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FontPaint extends JApplet {

    public void init() {
        FontPanel fontPanel = new FontPanel();
        getContentPane().add(fontPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        FontPanel starPanel = new FontPanel();

        JFrame f = new JFrame("Font");
        f.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.getContentPane().add(starPanel, BorderLayout.CENTER);
        f.setSize(new Dimension(550, 200));
        f.setVisible(true);
    }
}

class FontPanel extends JPanel {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.white);
        int width = getSize().width;
        int height = getSize().height;
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        FontRenderContext frc = g2.getFontRenderContext();
        Font f = new Font("Helvetica", 1, 60);
        String s = new String("Java Source and Support.");
        TextLayout textTl = new TextLayout(s, f, frc);
        AffineTransform transform = new AffineTransform();
        Shape outline = textTl.getOutline(null);
        Rectangle outlineBounds = outline.getBounds();
        transform = g2.getTransform();
        transform.translate(width / 2 - (outlineBounds.width / 2), height / 2
                + (outlineBounds.height / 2));
        g2.transform(transform);
        g2.setColor(Color.blue);
        g2.draw(outline);
        g2.setClip(outline);
    }
}
