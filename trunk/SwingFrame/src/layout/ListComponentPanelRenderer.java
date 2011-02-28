/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import layout.action.SelectChangeEvent;
import layout.action.SelectChangeListener;
import layout.action.SelectChangeSupport;

/**
 *
 * @author Administrator
 */
public class ListComponentPanelRenderer extends JList implements ComponentPanelRenderer {

    protected SelectChangeSupport support = new SelectChangeSupport();
    private boolean firing = false;
    private Dimension animationSize;

    public ListComponentPanelRenderer(ListModel model) {
        super(model);
        setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        initListener();
    }

    public ListComponentPanelRenderer(Object... objs) {
        super(objs);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initListener();
    }

    private void initListener() {
        this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                firing = true;
                support.fireSelectChangePerformed(new SelectChangeEvent(ListComponentPanelRenderer.this));
                firing = false;
            }
        });
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

    public DownComponentRenderer[] getDownComponents() {
        return new DownComponentRenderer[]{this};
    }

    public void clearSelection(DownComponentRenderer renderer) {
        if (renderer != this && !firing) {
            this.clearSelection();
        }
    }

    public Dimension getAnimationSize() {
        return this.animationSize;
    }

    public void setAnimationSize(Dimension size) {
        this.animationSize = size;
    }
}
