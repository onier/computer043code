/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author admin
 */
public class ColorButtonUI extends BasicButtonUI {

    public static final ColorButtonUI buttonUI = new ColorButtonUI();

    public ColorButtonUI() {
        super();
    }

    public static ComponentUI createUI(JComponent c) {
        return buttonUI;
    }

    public void installUI(final JComponent c) {
        super.installUI(c);
        c.addPropertyChangeListener("mouseIn", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                c.repaint();
            }
        });
        c.setBorder(new EmptyBorder(5, 5, 5, 5));
        c.setOpaque(false);
    }

    public void installDefaults(AbstractButton button) {
        super.installDefaults(button);
        button.setRolloverEnabled(true);
    }

    public void uninstallDefaults(AbstractButton button) {
        super.uninstallDefaults(button);
        button.setRolloverEnabled(false);
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

    protected BasicButtonListener createButtonListener(final AbstractButton button) {
        return new ButtonListener(button);
    }

    public void update(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        if ((c.getBackground() instanceof UIResource) && button.isContentAreaFilled() && c.isEnabled()) {
            ButtonModel model = button.getModel();
            if (!(c.getParent() instanceof JToolBar)) {
                if (!model.isArmed() && !model.isPressed()) {
                    paint(g, c);
                    return;
                }
            } else if (model.isRollover()) {
                paint(g, c);
                return;
            }
        }
        super.update(g, c);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(map);
        if (c.isEnabled()) {
            AbstractButton button = (AbstractButton) c;
            GradientPaint p;
            Color color;
            Object flag = button.getClientProperty("mouseIn");
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
            String layout = (String) c.getClientProperty(ColorLookAndFeel.BUTTON_LAYOUT);
            g2d.fill(new RoundRectangle2D.Float(0, 0, c.getWidth(), c.getHeight(), 10, 10));
            Object str = c.getClientProperty(ColorLookAndFeel.BUTTON_SEPARATOR);
            boolean separater = false;
            if (str != null) {
                separater = Boolean.parseBoolean(str.toString());
            }
            if (layout != null && layout.equals(ColorLookAndFeel.BUTTON_LAYOUT_RIGHT)) {
                g2d.fill(new Rectangle(10, 0, c.getWidth(), c.getHeight()));
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(color.brighter());
                g2d.draw(new RoundRectangle2D.Float(0, 0, c.getWidth() + 10, c.getHeight() - 1, 10, 10));
                if (separater) {
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawLine(c.getWidth() - 1, 0, c.getWidth() - 1, c.getHeight());
                }
            } else if (layout != null && layout.equals(ColorLookAndFeel.BUTTON_LAYOUT_LEFT)) {
                g2d.fill(new RoundRectangle2D.Float(0, 0, c.getWidth() - 1, c.getHeight(), 10, 10));
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(color.brighter());
                g2d.draw(new RoundRectangle2D.Float(-10, 0, c.getWidth() + 10, c.getHeight() - 1, 10, 10));
                if (separater) {
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawLine(0, 0, 0, c.getHeight());
                }
            } else if (layout != null && layout.equals(ColorLookAndFeel.BUTTON_LAYOUT_BOTH)) {
                g2d.fill(new Rectangle(0, 0, c.getWidth(), c.getHeight()));
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(color.brighter());
                g2d.draw(new Rectangle(0, 0, c.getWidth(), c.getHeight()));
                if (separater) {
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawLine(0, 0, 0, c.getHeight());
                }
            }
            if (flag != null && Boolean.parseBoolean(flag.toString()) && layout == null) {
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(color.brighter());
                g2d.draw(new RoundRectangle2D.Float(1, 1, c.getWidth() - 2, c.getHeight() - 2, 10, 10));
            }
        } else {
            g2d.setPaint(new GradientPaint(0, 0, Color.lightGray, 0, c.getHeight(), Color.GRAY, true));
            g2d.fill(new RoundRectangle2D.Float(1, 1, c.getWidth() - 2, c.getHeight() - 2, 10, 10));
        }
        super.paint(g, c);
    }

    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        Color color = new Color(253, 238, 169);
        if (b.getClientProperty(ColorLookAndFeel.BUTTON_BORDER) != null) {
            color = (Color) b.getClientProperty(ColorLookAndFeel.BUTTON_BORDER);
        }
        GradientPaint p = new GradientPaint(0, 0, new Color(251, 161, 58), 0, 30, new Color(253, 238, 169), true);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(p);
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(map);
        String layout = (String) b.getClientProperty(ColorLookAndFeel.BUTTON_LAYOUT);
        g2d.fill(new RoundRectangle2D.Float(0, 0, b.getWidth(), b.getHeight(), 10, 10));
        Object str = b.getClientProperty(ColorLookAndFeel.BUTTON_SEPARATOR);
        boolean separater = false;
        if (str != null) {
            separater = Boolean.parseBoolean(str.toString());
        }
        if (layout != null && layout.equals(ColorLookAndFeel.BUTTON_LAYOUT_RIGHT)) {
            g2d.fill(new Rectangle(10, 0, b.getWidth(), b.getHeight()));
            g2d.fill(new Rectangle(10, 0, b.getWidth(), b.getHeight()));
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(color.brighter());
            g2d.draw(new RoundRectangle2D.Float(0, 0, b.getWidth() + 10, b.getHeight() - 1, 10, 10));
            if (separater) {
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(b.getWidth() - 1, 0, b.getWidth() - 1, b.getHeight());
            }
        } else if (layout != null) {
            g2d.fill(new Rectangle(0, 0, b.getWidth() - 10, b.getHeight() - 10));
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(color.brighter());
            g2d.draw(new RoundRectangle2D.Float(-10, 0, b.getWidth() + 10, b.getHeight() - 1, 10, 10));
            if (separater) {
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(0, 0, 0, b.getHeight());
            }
        }
        if (layout == null) {
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(color.brighter());
            g2d.draw(new RoundRectangle2D.Float(0, 0, b.getWidth() - 1, b.getHeight() - 1, 10, 10));
        }
        super.paintButtonPressed(g, b);
    }
}
