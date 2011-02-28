/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

/**
 *
 * @author Administrator
 */
public class CodeHelpComponent extends JPopupMenu {

    protected ClassPropertyList classPropertyList;

    public CodeHelpComponent() {
    }

    public CodeHelpComponent(Class cl) {
        classPropertyList = new ClassPropertyList(java.awt.geom.Rectangle2D.class);
        setLayout(new BorderLayout());
        add(new JScrollPane(getClassPropertyList()), BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(600, 300));
        setFocusable(false);
    }

    @Override
    public void show(Component invoker, int x, int y) {
        classPropertyList.setSelectedIndex(0);
        super.show(invoker, x, y);
    }

    /**
     * @return the classPropertyList
     */
    public ClassPropertyList getClassPropertyList() {
        return classPropertyList;
    }

    public boolean scrollHelpView(String key) {
        if (key == null) {
            setVisible(false);
            return false;
        }
        ClassListModel model = classPropertyList.getClassListModel();
        if (key.trim().length() <= 0) {
            int index = classPropertyList.getSelectedIndex();
            classPropertyList.setModel(model);
            classPropertyList.setSelectedIndex(index);
            return true;
        }
        int index = classPropertyList.getSelectedIndex();
        index = Math.max(0, index);
        ClassListModel temp = new ClassListModel();
        for (int i = index; i < model.getSize(); i++) {
            if (model.getElementAt(i).getName().toLowerCase().startsWith(key.trim().toLowerCase())) {
                temp.addListElement(model.getElementAt(i));
            }
        }
        if (temp.getSize() > 0) {
            classPropertyList.setModel(temp);
            classPropertyList.setSelectedIndex(0);
            classPropertyList.ensureIndexIsVisible(0);
            return true;
        } else {
            setVisible(false);
            return false;
        }
    }
}
