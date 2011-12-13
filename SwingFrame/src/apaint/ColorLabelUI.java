/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicLabelUI;

/**
 *
 * @author admin
 */
public class ColorLabelUI extends BasicLabelUI {

    protected JLabel label;
    protected boolean mouseIn = false;

    public ColorLabelUI(JLabel label) {
        this.label = label;
    }

    public static ComponentUI createUI(JComponent c) {
        return new ColorLabelUI((JLabel) c);
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
    }

    protected class Handler extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            mouseIn = true;
            label.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            mouseIn = false;
            label.repaint();
        }
    }
}
