/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author Administrator
 */
public class ButtonReadererUI extends BasicButtonUI {

    protected int iconWidth = 8;

    @Override
    protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
        super.paintText(g, b, textRect, text);
    }

    @Override
    protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect) {
        super.paintIcon(g, c, iconRect);
//        if (c instanceof ButtonHeaderRenderer) {
//            ButtonHeaderRenderer button = (ButtonHeaderRenderer) c;
//            int align = button.getIconAlignment();
//            if (align == SwingConstants.LEFT) {
//                button.getIcon().paintIcon(c, g, 0, 0);
//            } else {
//                button.getIcon().paintIcon(c, g, c.getPreferredSize().width - iconRect.width, 0);
//            }
//        }
    }
}
