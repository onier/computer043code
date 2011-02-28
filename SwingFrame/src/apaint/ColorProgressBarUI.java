/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import sun.swing.SwingUtilities2;

/**
 *
 * @author admin
 */
public class ColorProgressBarUI extends BasicProgressBarUI {

    public static ComponentUI createUI(JComponent x) {
        return new ColorProgressBarUI();
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        progressBar.setBorderPainted(false);
        progressBar.setOpaque(false);
    }

    public void paint(Graphics gg, JComponent c) {
        Graphics2D g = (Graphics2D) gg;
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(map);
        progressBar.setBorderPainted(false);
        if (progressBar.isIndeterminate()) {
            paintIndeterminate(g, c);
        } else {
            paintDeterminate(g, c);
        }
    }

    protected void paintIndeterminate(Graphics2D g, JComponent c) {
        if (!(g instanceof Graphics2D)) {
            return;
        }

        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth() - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        Color color1 = UIManager.getColor("progressBar.color");
        Color color2 = UIManager.getColor("progressBar.bordercolor");
        boxRect = getBox(boxRect);
        if (boxRect != null) {
            g2.setColor(progressBar.getForeground());
            GradientPaint p = new GradientPaint(boxRect.x, boxRect.y, color1, boxRect.x + boxRect.width, boxRect.y + boxRect.height, Color.RED, true);
            g.setPaint(p);
            g2.fill(new RoundRectangle2D.Float(boxRect.x, boxRect.y,
                    boxRect.width, boxRect.height, 10, 10));
        }

        if (progressBar.isStringPainted()) {
            if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
                paintString(g2, b.left, b.top,
                        barRectWidth, barRectHeight,
                        boxRect.x, boxRect.width, b);
            } else {
                paintString(g2, b.left, b.top,
                        barRectWidth, barRectHeight,
                        boxRect.y, boxRect.height, b);
            }
        }
        Dimension size = new Dimension(progressBar.getWidth(), progressBar.getHeight());
        Rectangle rect = new Rectangle(size);
        g.setColor(color2);
        g.draw(new RoundRectangle2D.Float(0, 0, rect.width - 1, rect.height - 1, 10, 10));
    }

    protected void paintDeterminate(Graphics2D g, JComponent c) {
        if (!(g instanceof Graphics2D)) {
            return;
        }
        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth();
        int barRectHeight = progressBar.getHeight();

        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }

        int amountFull = getAmountFull(b, barRectWidth, barRectHeight);
        Dimension size = new Dimension(progressBar.getWidth(), progressBar.getHeight());
        Rectangle rect = new Rectangle(size);
        Color color1 = UIManager.getColor("progressBar.color");
        Color color2 = UIManager.getColor("progressBar.bordercolor");
        if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
            GradientPaint p = new GradientPaint(0, 0, color1, amountFull, 0, Color.RED, true);
            g.setPaint(p);
            g.fill(new RoundRectangle2D.Float(0, 0, amountFull - 1, rect.height - 1, 10, 10));
        } else { // VERTICAL
            GradientPaint p = new GradientPaint(0, 0, color1, 0, amountFull, Color.RED, true);
            g.setPaint(p);
            g.fill(new RoundRectangle2D.Float(0, 0, rect.width, amountFull, 10, 10));
        }
        g.setColor(color2);
        g.draw(new RoundRectangle2D.Float(0, 0, rect.width - 1, rect.height - 1, 10, 10));
        // Deal with possible text painting
        if (progressBar.isStringPainted()) {
            g.setColor(Color.BLACK);
            paintString(g, b.left, b.top,
                    barRectWidth, barRectHeight,
                    amountFull, b);
        }
    }

    protected void paintString(Graphics g, int x, int y,
            int width, int height,
            int amountFull, Insets b) {
        if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
            if (BasicGraphicsUtils.isLeftToRight(progressBar)) {
                if (progressBar.isIndeterminate()) {
                    boxRect = getBox(boxRect);
                    paintString(g, x, y, width, height,
                            boxRect.x, boxRect.width, b);
                } else {
                    paintString(g, x, y, width, height, x, amountFull, b);
                }
            } else {
                paintString(g, x, y, width, height, x + width - amountFull,
                        amountFull, b);
            }
        } else {
            if (progressBar.isIndeterminate()) {
                boxRect = getBox(boxRect);
                paintString(g, x, y, width, height,
                        boxRect.y, boxRect.height, b);
            } else {
                paintString(g, x, y, width, height, y + height - amountFull,
                        amountFull, b);
            }
        }
    }

    private void paintString(Graphics g, int x, int y, int width, int height,
            int fillStart, int amountFull, Insets b) {
        if (!(g instanceof Graphics2D)) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        String progressString = progressBar.getString();
        g2.setFont(progressBar.getFont());
        Point renderLocation = getStringPlacement(g2, progressString,
                x, y, width, height);
        Rectangle oldClip = g2.getClipBounds();

        if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
            g2.setColor(getSelectionBackground());
            SwingUtilities2.drawString(progressBar, g2, progressString,
                    renderLocation.x, renderLocation.y);
            g2.setColor(Color.BLACK);
            g2.clipRect(fillStart, y, amountFull, height);
            SwingUtilities2.drawString(progressBar, g2, progressString,
                    renderLocation.x, renderLocation.y);
        } else { // VERTICAL
            g2.setColor(getSelectionBackground());
            AffineTransform rotate =
                    AffineTransform.getRotateInstance(Math.PI / 2);
            g2.setFont(progressBar.getFont().deriveFont(rotate));
            renderLocation = getStringPlacement(g2, progressString,
                    x, y, width, height);
            SwingUtilities2.drawString(progressBar, g2, progressString,
                    renderLocation.x, renderLocation.y);
            g2.setColor(Color.BLACK);
            g2.clipRect(x, fillStart, width, amountFull);
            SwingUtilities2.drawString(progressBar, g2, progressString,
                    renderLocation.x, renderLocation.y);
        }
        g2.setClip(oldClip);
    }
}

