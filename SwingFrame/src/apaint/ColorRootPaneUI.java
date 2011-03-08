/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.RootPaneUI;
import javax.swing.plaf.basic.BasicRootPaneUI;

/**
 *
 * @author admin
 */
public class ColorRootPaneUI extends BasicRootPaneUI {

    private static RootPaneUI rootPaneUI = new ColorRootPaneUI();

    public static ComponentUI createUI(JComponent c) {
        return rootPaneUI;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        c.setSize(c.getWidth() + 20, c.getHeight() + 20);
        g.setColor(Color.RED);
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
    }
}
