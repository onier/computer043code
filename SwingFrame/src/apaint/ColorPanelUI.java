/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;

/**
 *
 * @author admin
 */
public class ColorPanelUI extends BasicPanelUI {

    private static final ColorPanelUI panelUI = new ColorPanelUI();

    public static ComponentUI createUI(JComponent c) {
        return panelUI;
    }

    @Override
    protected void installDefaults(JPanel p) {
        super.installDefaults(p);
        p.setBackground(Color.WHITE);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        GradientPaint p = new GradientPaint(0, 0, new Color(177, 202, 235), 0, c.getHeight(), new Color(113, 149, 197), true);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(p);
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(map);
//        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0.5f));
        g2d.fill(new Rectangle(0, 0, c.getWidth(), c.getHeight()));
    }
}
