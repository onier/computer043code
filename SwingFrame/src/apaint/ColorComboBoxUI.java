/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import component.ColorArrowButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import sun.swing.DefaultLookup;

/**
 *
 * @author admin
 */
public class ColorComboBoxUI extends BasicComboBoxUI {

//    protected ColorCellRendererPane currentValuePane = new ColorCellRendererPane();
    private Hanlder hanlder = new Hanlder();
    private boolean rollover = false, armed = false;

    public static ComponentUI createUI(JComponent c) {
        return new ColorComboBoxUI();
    }

    public ColorComboBoxUI() {
        currentValuePane = new ColorCellRendererPane();
    }

    @Override
    public boolean contains(JComponent c, int x, int y) {
        return super.contains(c, x, y);
    }

    @Override
    public void paint(Graphics gg, JComponent c) {
        hasFocus = comboBox.hasFocus();
        Graphics2D g = (Graphics2D) gg;
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(map);
        if (!comboBox.isEditable()) {
            Rectangle r = rectangleForCurrentValue();
            paintCurrentValueBackground(g, r, hasFocus);
            paintCurrentValue(g, r, hasFocus);
        }
    }

    public void paintCurrentValue(Graphics2D g, Rectangle bounds, boolean hasFocus) {
        ListCellRenderer renderer = comboBox.getRenderer();
        Component c;
        if (hasFocus && !isPopupVisible(comboBox)) {
            c = renderer.getListCellRendererComponent(listBox,
                    comboBox.getSelectedItem(),
                    -1,
                    true,
                    false);
        } else {
            c = renderer.getListCellRendererComponent(listBox,
                    comboBox.getSelectedItem(),
                    -1,
                    false,
                    false);
            c.setBackground(UIManager.getColor("ComboBox.background"));
        }
        c.setFont(comboBox.getFont());
        if (hasFocus && !isPopupVisible(comboBox)) {
            c.setForeground(listBox.getSelectionForeground());
            c.setBackground(listBox.getSelectionBackground());
        } else {
            if (comboBox.isEnabled()) {
                c.setForeground(comboBox.getForeground());
                c.setBackground(comboBox.getBackground());
            } else {
                c.setForeground(DefaultLookup.getColor(
                        comboBox, this, "ComboBox.disabledForeground", null));
                c.setBackground(DefaultLookup.getColor(
                        comboBox, this, "ComboBox.disabledBackground", null));
            }
        }

        boolean shouldValidate = false;
        if (c instanceof JPanel) {
            shouldValidate = true;
        }

        int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
//        if (padding != null) {
//            x = bounds.x + padding.left;
//            y = bounds.y + padding.top;
//            w = bounds.width - (padding.left + padding.right);
//            h = bounds.height - (padding.top + padding.bottom);
//        }

        currentValuePane.paintComponent(g, c, comboBox, x, y, w, h, shouldValidate);
        g.setColor(Color.BLUE);
        g.draw(new RoundRectangle2D.Float(0, 0, comboBox.getWidth() - 1, comboBox.getHeight() - 1, 10, 10));
    }

    public void paintCurrentValueBackground(Graphics2D g, Rectangle bounds, boolean hasFocus) {
        Color t = g.getColor();
        if (comboBox.isEnabled()) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GREEN);
        }
        g.setColor(Color.BLUE);
        g.draw(new RoundRectangle2D.Float(bounds.x, bounds.y, bounds.width, bounds.height, 10, 10));
        g.setColor(t);
    }

    protected JButton createArrowButton() {
        ColorArrowButton b = new ScrollButton(ScrollButton.SOUTH);
        b.addMouseMotionListener(hanlder);
        b.addMouseListener(hanlder);
        return b;
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        comboBox.getEditor().getEditorComponent().addMouseMotionListener(hanlder);
        comboBox.getEditor().getEditorComponent().addMouseListener(hanlder);
        comboBox.setBorder(null);
        comboBox.setOpaque(false);
    }

    protected class ScrollButton extends ColorArrowButton {

        public ScrollButton(int d) {
            super(d);
        }

        protected void paintButton(Graphics2D g) {
            int w = getSize().width;
            int h = getSize().height;
            g.setColor(new Color(231, 236, 241));
            g.fill(new Rectangle(0, 1, w, h / 2));
            g.setColor(new Color(189, 202, 219));
            g.fill(new Rectangle(0, h / 2, w, h / 2));
            g.setColor(new Color(23, 73, 138));
            g.draw(new Rectangle(0, 1, w - 1, h - 1));
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
            comboBox.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            rollover = false;
            comboBox.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            armed = true;
            comboBox.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            armed = false;
            comboBox.repaint();
        }
    }
}
