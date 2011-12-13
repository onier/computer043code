/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package font;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Zhenhai.Xu
 */
public class Frame {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        class TableModel extends AbstractTableModel {

            private int row = 5, column = 6;

            public void addRow() {
                row++;
                this.fireTableStructureChanged();
            }

            public void addColumn() {
                column++;
                this.fireTableStructureChanged();
            }

            public int getRowCount() {
                return row;
            }

            public int getColumnCount() {
                return column;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return columnIndex + rowIndex;
            }
        }
        final TableModel model = new TableModel();
        final JTable table = new JTable(model);
        final JPopupMenu menu = new JPopupMenu();
        menu.add(new AbstractAction("add row") {

            public void actionPerformed(ActionEvent e) {
                model.addRow();
            }
        });
        menu.add(new AbstractAction("add column") {

            public void actionPerformed(ActionEvent e) {
                model.addColumn();
            }
        });
        frame.setContentPane(table);
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu.show(table, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu.show(table, e.getX(), e.getY());
                }
            }
        });
        frame.setDefaultCloseOperation(3);
        frame.setSize(400, 500);
        frame.setVisible(true);
    }
}
