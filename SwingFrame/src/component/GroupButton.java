/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package component;

import apaint.ColorLookAndFeel;
import java.awt.Color;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 *
 * @author admin
 */
public class GroupButton extends JButton {

    private JComponent com;

    public GroupButton() {
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
        this.putClientProperty(ColorLookAndFeel.BUTTON_BORDER, Color.RED);
    }

    public void addButton(JButton b) {
        if (com == null) {
            b.putClientProperty(ColorLookAndFeel.BUTTON_LAYOUT, ColorLookAndFeel.BUTTON_LAYOUT_RIGHT);
        } else {
            if (this.getComponentCount() > 1) {
                com.putClientProperty(ColorLookAndFeel.BUTTON_LAYOUT, ColorLookAndFeel.BUTTON_LAYOUT_BOTH);
            } else {
                com.putClientProperty(ColorLookAndFeel.BUTTON_LAYOUT, ColorLookAndFeel.BUTTON_LAYOUT_LEFT);
            }
        }
        this.add(b);
        com = b;
        this.repaint();
        b.putClientProperty(ColorLookAndFeel.BUTTON_BORDER, Color.RED);
    }

    public void addAction(Action action) {
        JButton b = new JButton(action);
        add(b);
    }
}
