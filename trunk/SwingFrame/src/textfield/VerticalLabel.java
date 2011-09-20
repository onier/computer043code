/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textfield;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import javax.swing.plaf.basic.*;
import java.awt.geom.*;

// This code uses Graphics2D which is supported only on Java 2
public class VerticalLabel {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                Window win = e.getWindow();
                win.setVisible(false);
                win.dispose();
                System.exit(0);
            }
        });

        frame.getContentPane().setLayout(new FlowLayout());

        ImageIcon icon = new ImageIcon("dukeWave.gif");
        JLabel l = new JLabel("Rotated anti-clockwise", icon, SwingConstants.LEFT);
        l.setUI(new VerticalLabelUI(false));
        l.setBorder(new EtchedBorder());
        frame.getContentPane().add(l);

        l = new JLabel("Rotated Clockwise", icon, SwingConstants.LEFT);
//		l.setHorizontalTextPosition( SwingConstants.LEFT );
        l.setUI(new VerticalLabelUI(true));
        l.setBorder(new EtchedBorder());
        frame.getContentPane().add(l);
        frame.getContentPane().add(new JButton("Button"));
        frame.pack();
        frame.show();


    }
}

class VerticalLabelUI extends BasicLabelUI {

    static {
        labelUI = new VerticalLabelUI(false);
    }
    protected boolean clockwise;

    VerticalLabelUI(boolean clockwise) {
        super();
        this.clockwise = clockwise;
    }

    public Dimension getPreferredSize(JComponent c) {
        Dimension dim = super.getPreferredSize(c);
        return new Dimension(dim.height, dim.width);
    }
    private static Rectangle paintIconR = new Rectangle();
    private static Rectangle paintTextR = new Rectangle();
    private static Rectangle paintViewR = new Rectangle();
    private static Insets paintViewInsets = new Insets(0, 0, 0, 0);

    public void paint(Graphics g, JComponent c) {


        JLabel label = (JLabel) c;
        String text = label.getText();
        Icon icon = (label.isEnabled()) ? label.getIcon() : label.getDisabledIcon();

        if ((icon == null) && (text == null)) {
            return;
        }

        FontMetrics fm = g.getFontMetrics();
        paintViewInsets = c.getInsets(paintViewInsets);

        paintViewR.x = paintViewInsets.left;
        paintViewR.y = paintViewInsets.top;

        // Use inverted height & width
        paintViewR.height = c.getWidth() - (paintViewInsets.left + paintViewInsets.right);
        paintViewR.width = c.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

        paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
        paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

        String clippedText =
                layoutCL(label, fm, text, icon, paintViewR, paintIconR, paintTextR);

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform tr = g2.getTransform();
        if (clockwise) {
            g2.rotate(Math.PI / 2);
            g2.translate(0, -c.getWidth());
        } else {
            g2.rotate(-Math.PI / 2);
            g2.translate(-c.getHeight(), 0);
        }

        if (icon != null) {
            icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
        }

        if (text != null) {
            int textX = paintTextR.x;
            int textY = paintTextR.y + fm.getAscent();

            if (label.isEnabled()) {
                paintEnabledText(label, g, clippedText, textX, textY);
            } else {
                paintDisabledText(label, g, clippedText, textX, textY);
            }
        }


        g2.setTransform(tr);
    }
}
