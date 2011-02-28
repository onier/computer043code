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
import javax.swing.Action;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.JLabel;

public class DownComponentMenuItem extends JLabel {

    protected MenItemModel model = new MenItemModel();
    protected String text;
    private boolean flag = false;
    private Color bcolor;

    public DownComponentMenuItem() {
        setForeground(Color.GREEN);
    }

    public void addActionListener(ActionListener listener) {
        model.addActionListener(listener);
    }

    public ActionListener[] getActionListeners() {
        return model.getActionListeners();
    }

    @Override
    public void paint(Graphics g) {
        if (flag) {
            g.setColor(bcolor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        super.paint(g);
    }

    public void removeActionListener(ActionListener listener) {
        model.removeActionListener(listener);
    }

    private void initListener() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                flag = true;
                setText("<html><u>" + DownComponentMenuItem.this.text + "</u></html>");
                firePropertyChange("selectChange", -1, 1);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                flag = false;
                setText("<html>" + DownComponentMenuItem.this.text + "</html>");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                model.fireActionPerformed(new ActionEvent(DownComponentMenuItem.this, 1001, DownComponentMenuItem.this.getText()));
            }
        });
    }

    public void setBColor(Color c) {
        this.bcolor = c;
    }

    public DownComponentMenuItem(Action action) {
        this((String) action.getValue("Name"), (Icon) action.getValue("SmallIcon"));
        model.addActionListener(action);
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setForeground(Color.GREEN);
    }

    public DownComponentMenuItem(String text) {
        super.setForeground(Color.blue);
        this.text = text;
        super.setText("<html>" + this.text + "</html>");
        initListener();
        this.setForeground(Color.BLACK);
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
    }

    public DownComponentMenuItem(String text, Icon icon) {
        super(icon, 2);
        super.setForeground(Color.blue);
        this.text = text;
        super.setText("<html>" + this.text + "</html>");
        initListener();
        this.setForeground(Color.BLACK);
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
    }

    public static class MenItemModel extends DefaultButtonModel {

        @Override
        public void fireActionPerformed(ActionEvent e) {
            super.fireActionPerformed(e);
        }
    }
}
