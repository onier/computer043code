/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JList;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CheckList extends JFrame {

    public CheckList() {
        super("AKCheckList");
        String[] listData = {"Apple", "Orange", "Cherry", "Blue Berry", "Banana", "Red Plum", "Watermelon"};
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Unable to find System Look and Feel");
        }
        final JList listCheckBox = new JList(buildCheckBoxItems(listData.length));
        final JList listDescription = new JList(listData);
        listDescription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listDescription.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() != 2) {
                    return;
                }
                int selectedIndex = listDescription.locationToIndex(me.getPoint());
                if (selectedIndex < 0) {
                    return;
                }
                CheckBoxItem item = (CheckBoxItem) listCheckBox.getModel().getElementAt(selectedIndex);
                item.setChecked(!item.isChecked());
                listCheckBox.repaint();

            }
        });
        listCheckBox.setCellRenderer(new CheckBoxRenderer());
        listCheckBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listCheckBox.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent me) {
                int selectedIndex = listCheckBox.locationToIndex(me.getPoint());
                if (selectedIndex < 0) {
                    return;
                }
                CheckBoxItem item = (CheckBoxItem) listCheckBox.getModel().getElementAt(selectedIndex);
                item.setChecked(!item.isChecked());
                listDescription.setSelectedIndex(selectedIndex);
                listCheckBox.repaint();
            }
        });
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setRowHeaderView(listCheckBox);
        scrollPane.setViewportView(listDescription);
        listDescription.setFixedCellHeight(20);
        listCheckBox.setFixedCellHeight(listDescription.getFixedCellHeight());
        listCheckBox.setFixedCellWidth(20);
        getContentPane().add(scrollPane); //, BorderLayout.CENTER);
        setSize(350, 200);
        setVisible(true);
    }

    private CheckBoxItem[] buildCheckBoxItems(int totalItems) {
        CheckBoxItem[] checkboxItems = new CheckBoxItem[totalItems];
        for (int counter = 0; counter < totalItems; counter++) {
            checkboxItems[counter] = new CheckBoxItem();
        }
        return checkboxItems;
    }

    public static void main(String args[]) {
        CheckList checkList = new CheckList();
        checkList.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    class CheckBoxItem {

        private boolean isChecked;

        public CheckBoxItem() {
            isChecked = false;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean value) {
            isChecked = value;
        }
    }

    /* Inner class that renders JCheckBox to JList*/
    class CheckBoxRenderer extends JCheckBox implements ListCellRenderer {

        public CheckBoxRenderer() {
            setBackground(UIManager.getColor("List.textBackground"));
            setForeground(UIManager.getColor("List.textForeground"));
        }

        public Component getListCellRendererComponent(JList listBox, Object obj, int currentindex,
                boolean isChecked, boolean hasFocus) {
            setSelected(((CheckBoxItem) obj).isChecked());
            return this;
        }
    }
}
