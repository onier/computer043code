/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollPaneUI;

/**
 *
 * @author admin
 */
public class ColorScrollPaneUI extends BasicScrollPaneUI {

    public static ComponentUI createUI(JComponent x) {
        return new ColorScrollPaneUI();
    }

    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(map);
        g2d.setColor(new Color(165, 195, 239));
        g2d.fill(new Rectangle(0, 0, c.getWidth(), c.getHeight()));
    }
}
