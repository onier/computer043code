/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author admin
 */
public class EllipseButtonUI extends BasicButtonUI {

    public static final EllipseButtonUI buttonUI = new EllipseButtonUI();

    public static ComponentUI createUI(JComponent c) {
        return buttonUI;
    }

    @Override
    public boolean contains(JComponent c, int x, int y) {
        Ellipse2D.Float ellipse = new Ellipse2D.Float(0, 0, c.getWidth(), c.getHeight());
        return ellipse.contains(x, y);
    }

    protected class ButtonListener extends BasicButtonListener {

        private AbstractButton button;

        public ButtonListener(AbstractButton button) {
            super(button);
            this.button = button;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            button.putClientProperty("mouseIn", true);
            button.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            button.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.putClientProperty("mouseIn", false);
            button.repaint();
        }
    }

    @Override
    protected BasicButtonListener createButtonListener(final AbstractButton button) {
        return new ButtonListener(button);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Ellipse2D.Float ellipse = new Ellipse2D.Float(1, 1, c.getWidth() - 2, c.getHeight() - 2);
        Graphics2D g2d = (Graphics2D) g;
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(map);
        GradientPaint p;
        Color color;
        Object flag = c.getClientProperty("mouseIn");
        if (flag != null && Boolean.parseBoolean(flag.toString())) {
            color = new Color(255, 212, 73);
            p = new GradientPaint(0, 0, new Color(255, 250, 212), 0, c.getHeight(), color, true);
        } else {
            color = new Color(199, 199, 199);
            p = new GradientPaint(0, 0, new Color(250, 250, 250), 0, c.getHeight(), color, true);
        }
        if (c.getClientProperty(ColorLookAndFeel.BUTTON_BORDER) != null) {
            color = (Color) c.getClientProperty(ColorLookAndFeel.BUTTON_BORDER);
        }
        g2d.setPaint(p);
        g2d.fill(ellipse);
        g2d.setColor(color.brighter());
        g2d.draw(ellipse);
        super.paint(g, c);
    }

    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        Color color = new Color(253, 238, 169);
        Ellipse2D.Float ellipse = new Ellipse2D.Float(1, 1, b.getWidth() - 2, b.getHeight() - 2);
        if (b.getClientProperty(ColorLookAndFeel.BUTTON_BORDER) != null) {
            color = (Color) b.getClientProperty(ColorLookAndFeel.BUTTON_BORDER);
        }
        GradientPaint p = new GradientPaint(0, 0, new Color(251, 161, 58), 0, b.getHeight(), new Color(253, 238, 169), true);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(p);
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(map);
        g2d.fill(ellipse);
        g2d.setColor(color.brighter());
        g2d.draw(ellipse);
        super.paintButtonPressed(g, b);
    }
}
