/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import component.ColorArrowButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author admin
 */
public class ColorScrollBarUI extends BasicScrollBarUI {

    private Hanlder hanlder = new Hanlder();
    private boolean rollover = false, armed = false;

    public static ComponentUI createUI(JComponent c) {
        return new ColorScrollBarUI();
    }

    @Override
    public boolean contains(JComponent c, int x, int y) {
        Rectangle rect = new Rectangle(0, 0, c.getWidth(), c.getHeight());
        return rect.contains(x, y);
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.addMouseListener(hanlder);
        c.addMouseMotionListener(hanlder);
    }

    @Override
    public void paint(Graphics gg, JComponent c) {
        Graphics2D g2d = (Graphics2D) gg;
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(map);
        paintTrack(g2d, c, getTrackBounds());
        Rectangle thumbBounds = getThumbBounds();
        if (thumbBounds.intersects(g2d.getClipBounds())) {
            paintThumb(g2d, c, thumbBounds);
        }
    }

    protected JButton createDecreaseButton(int orientation) {
        ColorArrowButton b = new ScrollButton(orientation);
        b.addMouseMotionListener(hanlder);
        b.addMouseListener(hanlder);
        return b;
    }

    protected JButton createIncreaseButton(int orientation) {
        ColorArrowButton b = new ScrollButton(orientation);
        b.addMouseMotionListener(hanlder);
        b.addMouseListener(hanlder);
        return b;
    }

    protected void paintTrack(Graphics2D g2d, JComponent c, Rectangle trackBounds) {
        g2d.setColor(new Color(121, 153, 194));
        g2d.fill(new Rectangle(0, 0, c.getWidth(), c.getHeight()));
        g2d.setColor(new Color(178, 208, 246));
        g2d.draw(new Rectangle(0, 0, c.getWidth(), c.getHeight()));
    }

    protected void paintThumb(Graphics2D g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        int w = thumbBounds.width;
        int h = thumbBounds.height;
        JScrollBar bar = (JScrollBar) c;
        g.translate(thumbBounds.x, thumbBounds.y);
        switch (bar.getOrientation()) {
            case JScrollBar.VERTICAL:
                if (rollover) {
                    g.setColor(new Color(229, 234, 238));
                    g.fill(new Rectangle(1, 0, w / 2, h));

                    g.setColor(new Color(189, 202, 219));
                    g.fill(new Rectangle(w / 2, 0, w / 2, h));
                }
                if (armed) {
                    g.setColor(new Color(159, 192, 235));
                    g.fill(new Rectangle(1, 0, w / 2, h));
                    g.setColor(new Color(110, 166, 240));
                    g.fill(new Rectangle(w / 2, 0, w / 2, h));
                } else {
                    g.setColor(new Color(231, 236, 241));
                    g.fill(new Rectangle(1, 0, w / 2, h));
                    g.setColor(new Color(189, 202, 219));
                    g.fill(new Rectangle(w / 2, 0, w / 2, h));
                }
                g.setColor(new Color(23, 73, 138));
                g.draw(new Rectangle(1, 0, w - 1, h - 1));
                break;
            case JScrollBar.HORIZONTAL:
                if (rollover) {
                    g.setColor(new Color(229, 234, 238));
                    g.fill(new Rectangle(0, 1, w, h / 2));
                    g.setColor(new Color(189, 202, 219));
                    g.fill(new Rectangle(0, h / 2, w, h / 2));
                }
                if (armed) {
                    g.setColor(new Color(159, 192, 235));
                    g.fill(new Rectangle(0, 1, w, h / 2));
                    g.setColor(new Color(110, 166, 240));
                    g.fill(new Rectangle(0, h / 2, w, h / 2));
                } else {
                    g.setColor(new Color(231, 236, 241));
                    g.fill(new Rectangle(0, 1, w, h / 2));
                    g.setColor(new Color(189, 202, 219));
                    g.fill(new Rectangle(0, h / 2, w, h / 2));
                }
                g.setColor(new Color(23, 73, 138));
                g.draw(new Rectangle(0, 1, w - 1, h - 1));
        }
        g.translate(-thumbBounds.x, -thumbBounds.y);
    }

    protected class ScrollButton extends ColorArrowButton {

        public ScrollButton(int d) {
            super(d);
        }

        protected void paintButton(Graphics2D g) {
            int w = getSize().width;
            int h = getSize().height;
            switch (scrollbar.getOrientation()) {
                case JScrollBar.VERTICAL:
                    g.setColor(new Color(231, 236, 241));
                    g.fill(new Rectangle(1, 0, w / 2, h));
                    g.setColor(new Color(189, 202, 219));
                    g.fill(new Rectangle(w / 2, 0, w / 2, h));
                    g.setColor(new Color(23, 73, 138));
                    g.draw(new Rectangle(1, 0, w - 1, h - 1));
                    break;
                case JScrollBar.HORIZONTAL:
                    g.setColor(new Color(231, 236, 241));
                    g.fill(new Rectangle(0, 1, w, h / 2));
                    g.setColor(new Color(189, 202, 219));
                    g.fill(new Rectangle(0, h / 2, w, h / 2));
                    g.setColor(new Color(23, 73, 138));
                    g.draw(new Rectangle(0, 1, w - 1, h - 1));
            }
        }

        public void paint(Graphics gg) {
            Color origColor;
            boolean isPressed, isEnabled;
            int w, h, size;
            Graphics2D g = (Graphics2D) gg;
            Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
            map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHints(map);
            w = getSize().width;
            h = getSize().height;
            origColor = g.getColor();
            isPressed = getModel().isPressed();
            isEnabled = isEnabled();

            // If there's no room to draw arrow, bail
            if (h < 5 || w < 5) {
                g.setColor(origColor);
                return;
            }
            if (rollover || this.getModel().isRollover() || armed) {
                this.paintButton(g);
            }
            if (isPressed) {
                g.translate(1, 1);
            }

            // Draw the arrow
            size = Math.min((h - 4) / 3, (w - 4) / 3);
            size = Math.max(size, 2);
            paintTriangle(g, (w - size) / 2, (h - size) / 2,
                    size, direction, isEnabled);

            // Reset the Graphics back to it's original settings
            if (isPressed) {
                g.translate(-1, -1);
            }
            g.setColor(origColor);

        }
    }

    protected class Hanlder extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            rollover = true;
            scrollbar.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            rollover = false;
            scrollbar.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            armed = true;
            scrollbar.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            armed = false;
            scrollbar.repaint();
        }
    }
}
