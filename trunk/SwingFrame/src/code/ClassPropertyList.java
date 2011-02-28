/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import code.ClassListModel.FieldElement;
import code.ClassListModel.ListElement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Administrator
 */
public class ClassPropertyList extends JList {

    private ClassListModel model;

    public ClassPropertyList(Class cl) {
        model = new ClassListModel(cl);
        setModel(model);
        setCellRenderer(new CustomerRenderer());
    }

    public ClassListModel getClassListModel() {
        return model;
    }

    @Override
    public void clearSelection() {
        super.clearSelection();
    }

    @Override
    public ListElement getSelectedValue() {
        return (ListElement) super.getSelectedValue();
    }

    @Override
    public void setSelectedIndex(int index) {
        super.setSelectedIndex(index);
    }

    class CustomerRenderer implements ListCellRenderer {

        private JPanel panel = new JPanel();
        private JLabel leftLabel = new JLabel();
        private JLabel rightLabel = new JLabel();

        CustomerRenderer() {
            panel.setLayout(new BorderLayout());
            panel.add(leftLabel, BorderLayout.CENTER);
            panel.add(rightLabel, BorderLayout.EAST);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            ListElement element = (ListElement) value;
            leftLabel.setText(element.getName());
            rightLabel.setText(element.getType());
            if (element instanceof FieldElement) {
                leftLabel.setForeground(Color.GREEN);
                rightLabel.setForeground(Color.GREEN);
            } else {
                leftLabel.setForeground(Color.BLACK);
                rightLabel.setForeground(Color.BLACK);
            }
            if (isSelected) {
                panel.setBackground(Color.BLUE);
            } else {
                panel.setBackground(Color.WHITE);
            }
            panel.setSize(500, 300);
            return panel;
        }
    }
}
