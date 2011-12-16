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
import javax.swing.UIManager;
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

    @Override
    protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text) {
        Graphics2D g2d = (Graphics2D) g;
        g.translate(32, 0);
        super.paintText(g, menuItem, textRect, text);
        g.translate(-32, 0);
    }

    protected void paintBackground(Graphics g, JMenuItem c, Color bgColor) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fill(new Rectangle(0, 0, c.getWidth(), c.getHeight()));
        g2d.setColor(UIManager.getColor(ColorLookAndFeelProperties.MENU_ITEM_ICON_BackGround_KEY).darker());
        g2d.draw(new Rectangle(0, 0, 32, c.getHeight()));
        g2d.setPaint(new GradientPaint(0, 0, UIManager.getColor(ColorLookAndFeelProperties.MENU_ITEM_ICON_BackGround_KEY), 0, 32, UIManager.getColor(ColorLookAndFeelProperties.MENU_ITEM_ICON_BackGround_KEY), true));
        g2d.fill(new Rectangle(0, 0, 32, c.getHeight()));
        g2d.setColor(Color.BLACK);
    }
}
