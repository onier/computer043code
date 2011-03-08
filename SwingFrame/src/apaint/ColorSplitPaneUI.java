/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import component.ColorArrowButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 *
 * @author admin
 */
public class ColorSplitPaneUI extends BasicSplitPaneUI {

    JComponent cc;
    private boolean rollover = false, pressed = false;

    public static ComponentUI createUI(JComponent x) {
        return new ColorSplitPaneUI();
    }

    public BasicSplitPaneDivider createDefaultDivider() {
        return new BasicSplitPaneDivider(this);
    }

    protected class ScrollButton extends ColorArrowButton {

        public ScrollButton(int d) {
            super(d);
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

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        divider.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                pressed = true;
                cc.repaint();
            }

            public void mouseReleased(MouseEvent e) {
                pressed = false;
                cc.repaint();
            }

            public void mouseEntered(MouseEvent e) {
                rollover = true;
                cc.repaint();
            }

            public void mouseExited(MouseEvent e) {
                rollover = false;
                cc.repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(map);
        g2d.setColor(new Color(165, 195, 239));
        g2d.fill(new Rectangle(0, 0, c.getWidth(), c.getHeight()));
        super.paint(g, c);
    }

    protected Component createDefaultNonContinuousLayoutDivider() {
        cc = new JComponent() {

            public void paint(Graphics g) {
                System.out.println("rollover :" + rollover + "pressed :" + pressed);
                if (!isContinuousLayout() && getLastDragLocation() != -1) {
                    Dimension size = splitPane.getSize();
                    Graphics2D g2d = (Graphics2D) g;
                    Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
                    map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                    map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHints(map);
                    g2d.setColor(new Color(165, 195, 239));
                    if (splitPane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
                        if (rollover || pressed) {
                            g2d.setColor(Color.red);
                            g2d.draw3DRect(0, 0, dividerSize - 1, size.height - 1, false);
                        } else {
                            g2d.fill(new Rectangle(0, 0, dividerSize - 1, size.height - 1));
                        }
                    } else {

                        if (rollover || pressed) {
                            g2d.setColor(Color.red);
                            g2d.draw3DRect(0, 0, size.width - 1, dividerSize - 1, false);
                        } else {
                            g2d.fill(new Rectangle(0, 0, size.width - 1, dividerSize - 1));
                        }
                    }
                }
            }
        };
        return cc;
    }
}
