/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Administrator
 */
public class TableTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        String[][] data = new String[][]{{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}};
        String[] names = new String[]{"1", "2", "3"};
        final JTable table = new JTable(data, names);
        DefaultCellEditor cellEdit = new DefaultCellEditor(new JTextField());
        cellEdit.setClickCountToStart(100);//点击一百次进入编辑状态
        table.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());
                    if (table.getCellEditor() != null) {
                        table.getCellEditor().stopCellEditing();
                    }
                    table.clearSelection();
                    table.editCellAt(row, column);
                }
            }
        });
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumn(names[i]).setCellEditor(cellEdit);
        }
        frame.setContentPane(table);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}
