/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import layout.action.SelectChangeEvent;
import layout.action.SelectChangeListener;
import layout.action.SelectChangeSupport;
import testframe.IconUtils;

/**
 *
 * @author Administrator
 */
public class ButtonHeaderRenderer extends JToggleButton implements ComponentHeaderRenderer {

    protected SelectChangeSupport support = new SelectChangeSupport();
    private Color background;
    private Color foreground;
    private int iconAlignment = SwingConstants.LEFT;

    public ButtonHeaderRenderer(String text) {
        super(text);
        setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        final ImageIcon ICON_UP = IconUtils.getUpIcon();
        setIcon(ICON_UP);
        background = this.getBackground(); //        this.setUI(new ButtonReadererUI());
        foreground = this.getForeground();
        this.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (ButtonHeaderRenderer.this.getIcon().equals(ICON_UP)) {
                    ButtonHeaderRenderer.this.setIcon(IconUtils.getDownIcon());
                } else {
                    ButtonHeaderRenderer.this.setIcon(ICON_UP);
                }
                support.fireSelectChangePerformed(new SelectChangeEvent(ButtonHeaderRenderer.this));
            }
        });
    }

    public JComponent getPopupHeaderRenderer() {
        return this;
    }

    /**
     * @return the inconAlignment
     */
    public int getIconAlignment() {
        return iconAlignment;
    }

    /**设置按钮图标的位置.目前只能为左右.
     * @param inconAlignment the inconAlignment to set
     */
    public void setInconAlignment(int inconAlignment) {
        this.iconAlignment = inconAlignment;
    }

    public void addSelectChangeListener(SelectChangeListener l) {
        support.addSelectChangeListener(l);
    }

    public SelectChangeListener[] getSelectChangeListeners() {
        return support.getSelectChangeListeners();
    }

    public void removeSelectChangeListener(SelectChangeListener l) {
        support.removeSelectChangeListener(l);
    }

    public void setSelectionBackground(Color color) {
    }

    public void clearSelection(DownComponentRenderer renderer) {
    }
}
