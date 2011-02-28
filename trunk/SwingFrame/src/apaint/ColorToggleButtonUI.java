/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicToggleButtonUI;
import javax.swing.text.View;

/**
 *
 * @author admin
 */
public class ColorToggleButtonUI extends BasicToggleButtonUI {

    private final static ColorToggleButtonUI toggleButtonUI = new ColorToggleButtonUI();

    public static ComponentUI createUI(JComponent b) {
        return toggleButtonUI;
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setRolloverEnabled(false);
        b.setOpaque(false);
        b.setBorderPainted(false);
    }

    @Override
    public void paint(Graphics gg, JComponent c) {
        Graphics2D g = (Graphics2D) gg;
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(map);
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();

        Dimension size = b.getSize();
        FontMetrics fm = g.getFontMetrics();

        Insets i = c.getInsets();

        Rectangle viewRect = new Rectangle(size);

        viewRect.x += i.left;
        viewRect.y += i.top;
        viewRect.width -= (i.right + viewRect.x);
        viewRect.height -= (i.bottom + viewRect.y);

        Rectangle iconRect = new Rectangle();
        Rectangle textRect = new Rectangle();

        Font f = c.getFont();
        g.setFont(f);

        // layout the text and icon
        String text = SwingUtilities.layoutCompoundLabel(
                c, fm, b.getText(), b.getIcon(),
                b.getVerticalAlignment(), b.getHorizontalAlignment(),
                b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
                viewRect, iconRect, textRect,
                b.getText() == null ? 0 : b.getIconTextGap());

        if (model.isArmed() && model.isPressed() || model.isSelected()) {
            paintButtonPressed(g, b);
        } else if (c.isEnabled()) {
            Rectangle rect = new Rectangle(b.getSize());
            g.setPaint(new GradientPaint(0, 0, ColorLookAndFeel.ROLLOVER_Color[0], 0, rect.height, ColorLookAndFeel.ROLLOVER_Color[1]));
            g.fill(new RoundRectangle2D.Float(0, 0, rect.width, rect.height, 10, 10));
            g.setColor(ColorLookAndFeel.ROLLOVER_Color[1].brighter());
            g.setStroke(new BasicStroke(2f));
            g.draw(new RoundRectangle2D.Float(0, 0, rect.width, rect.height, 10, 10));
        } else {
            Rectangle rect = new Rectangle(b.getSize());
            g.setPaint(new GradientPaint(0, 0, Color.LIGHT_GRAY, 0, rect.height, Color.GRAY));
            g.fill(new RoundRectangle2D.Float(0, 0, rect.width, rect.height, 10, 10));
            g.setStroke(new BasicStroke(2f));
            g.setColor(Color.GRAY.brighter());
            g.draw(new RoundRectangle2D.Float(0, 0, rect.width, rect.height, 10, 10));
        }
        // Paint the Icon
        if (b.getIcon() != null) {
            paintIcon(g, b, iconRect);
        }

        // Draw the Text
        if (text != null && !text.equals("")) {
            View v = (View) c.getClientProperty(BasicHTML.propertyKey);
            if (v != null) {
                v.paint(g, textRect);
            } else {
                paintText(g, b, textRect, text);
            }
        }

        // draw the dashed focus line.
        if (b.isFocusPainted() && b.hasFocus()) {
            paintFocus(g, b, viewRect, textRect, iconRect);
        }
    }

    protected void paintButtonPressed(Graphics gg, AbstractButton b) {
        Graphics2D g = (Graphics2D) gg;
        Rectangle rect = new Rectangle(b.getSize());
        g.setPaint(new GradientPaint(0, 0, ColorLookAndFeel.PRESSED_Color[0], 0, rect.height, ColorLookAndFeel.PRESSED_Color[1]));
        g.fill(new RoundRectangle2D.Float(0, 0, rect.width, rect.height, 10, 10));
        g.setStroke(new BasicStroke(2f));
        g.setColor(ColorLookAndFeel.ROLLOVER_Color[1].brighter());
        g.draw(new RoundRectangle2D.Float(0, 0, rect.width, rect.height, 10, 10));
    }
}
