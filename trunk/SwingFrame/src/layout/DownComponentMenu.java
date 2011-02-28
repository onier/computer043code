/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import layout.action.SelectChangeEvent;
import layout.action.SelectChangeListener;
import layout.action.SelectChangeSupport;

/**
 *
 * @author Administrator
 */
public class DownComponentMenu extends JPanel implements ComponentPanelRenderer, PropertyChangeListener {

    private Dimension animationSize = new Dimension();
    protected ArrayList<DownComponentMenuItem> itemList = new ArrayList<DownComponentMenuItem>();
    protected SelectChangeSupport support = new SelectChangeSupport();

    public DownComponentMenu(Action... actions) {
        this.setLayout(new DownComponentLayout());
        for (int i = 0; i < actions.length; i++) {
            Action action = actions[i];
            DownComponentMenuItem item = new DownComponentMenuItem(action);
            this.add(item);
            item.addPropertyChangeListener("selectChange", DownComponentMenu.this);
            itemList.add(item);
        }
    }

    public JComponent getPopupPanelRenderer() {
        return this;
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
        for (DownComponentMenuItem item : itemList) {
            item.setBColor(color);
        }
    }

    public DownComponentRenderer[] getDownComponents() {
        return new DownComponentRenderer[]{this};
    }

    public void propertyChange(PropertyChangeEvent evt) {
        support.fireSelectChangePerformed(new SelectChangeEvent(this));
    }

    public void clearSelection(DownComponentRenderer renderer) {
    }

    public Dimension getAnimationSize() {
        return this.animationSize;
    }

    public void setAnimationSize(Dimension size) {
        this.animationSize = size;
    }
}
