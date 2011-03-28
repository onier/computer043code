/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuItemUI;

/**
 *
 * @author admin
 */
public class ColorMenuItemUI extends BasicMenuItemUI {

    public static ComponentUI createUI(JComponent c) {
        return new ColorMenuItemUI();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setBorder(null);
        c.setSize(c.getHeight(), 100);
    }

    protected void paintBackground(Graphics g, JMenuItem c, Color bgColor) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fill(new Rectangle(0, 0, c.getWidth(), c.getHeight()));
        g2d.setPaint(new GradientPaint(0, 0, new Color(233, 238, 238), 0, 50, new Color(233, 238, 238), true));
        g2d.fill(new Rectangle(0, 0, 50, c.getHeight()));
        g2d.setColor(Color.BLACK);
    }
}
