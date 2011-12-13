/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Zhenhai.Xu
 */
public class ColorTextFieldUI extends BasicTextFieldUI {
    
    protected JTextComponent componenet;
    protected boolean mouseIn = false;
    protected MouseHandler handler = new MouseHandler();
    
    public ColorTextFieldUI(JComponent c) {
        componenet = (JTextComponent) c;
        componenet.setBorder(BorderFactory.createLineBorder(UIManager.getColor(ColorLookAndFeelProperties.TEXT_BORDER_COLOR_KEY)));
        componenet.setBackground(UIManager.getColor(ColorLookAndFeelProperties.TEXT_BACKGROUND_COLOR_KEY));
        componenet.setSelectionColor(UIManager.getColor(ColorLookAndFeelProperties.TEXT_SLECT_COLOR_KEY));
        componenet.setSelectedTextColor(Color.BLACK);
    }
    
    public static ComponentUI createUI(JComponent c) {
        return new ColorTextFieldUI(c);
    }
    
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.addMouseListener(handler);
        c.addFocusListener(handler);
    }

    @Override
    protected void paintBackground(Graphics g) {
        super.paintBackground(g);
    }
    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        c.removeMouseListener(handler);
        c.removeFocusListener(handler);
    }
    
    protected class MouseHandler extends MouseAdapter implements FocusListener {
        
        @Override
        public void mouseEntered(MouseEvent e) {
            componenet.setBorder(BorderFactory.createLineBorder(UIManager.getColor(ColorLookAndFeelProperties.TEXT_FOCUS_BORDER_COLOR_KEY)));
            componenet.setBackground(UIManager.getColor(ColorLookAndFeelProperties.TEXT_FOCUS_BACKGROUND_COLOR_KEY));
            componenet.repaint();
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            componenet.setBorder(BorderFactory.createLineBorder(UIManager.getColor(ColorLookAndFeelProperties.TEXT_BORDER_COLOR_KEY)));
            componenet.setBackground(UIManager.getColor(ColorLookAndFeelProperties.TEXT_BACKGROUND_COLOR_KEY));
            componenet.repaint();
        }
        
        public void focusGained(FocusEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                
                public void run() {
                    componenet.selectAll();
                }
            });
        }
        
        public void focusLost(FocusEvent e) {
        }
    }
}
