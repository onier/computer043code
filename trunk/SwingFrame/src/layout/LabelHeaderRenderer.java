/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;
import layout.DownComponentMenuItem.MenItemModel;
import layout.action.SelectChangeEvent;
import layout.action.SelectChangeListener;
import layout.action.SelectChangeSupport;

/**
 *
 * @author Administrator
 */
public class LabelHeaderRenderer extends JLabel implements ComponentHeaderRenderer {

    protected SelectChangeSupport support = new SelectChangeSupport();
    private Color background;
    private MenItemModel model = new MenItemModel();
    private boolean flag = false;

    public LabelHeaderRenderer(String text) {
        super(text);
        this.setIcon(UIManager.getIcon("Tree.closedIcon"));
        initListener();
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
    }

    public LabelHeaderRenderer(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
    }

    @Override
    public void paint(Graphics g) {
        if (flag) {
            g.setColor(background);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        super.paint(g);
    }

    private void initListener() {
        addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                model.fireActionPerformed(new ActionEvent(LabelHeaderRenderer.this, 1001, LabelHeaderRenderer.this.getText()));
                support.fireSelectChangePerformed(new SelectChangeEvent(LabelHeaderRenderer.this));
                flag = true;
                repaint();
            }
        });
    }

    public JComponent getPopupHeaderRenderer() {
        return this;
    }

    public void addActionListener(ActionListener action) {
        model.addActionListener(action);
    }

    public ActionListener[] getActionListeners() {
        return model.getActionListeners();
    }

    public void removeActionListener(ActionListener listener) {
        model.removeActionListener(listener);
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
        this.background = color;
    }

    public void clearSelection(DownComponentRenderer renderer) {
        if (renderer != this) {
            flag = false;
            repaint();
        }
    }
}
