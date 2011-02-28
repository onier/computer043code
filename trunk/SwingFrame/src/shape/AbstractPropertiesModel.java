/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author admin
 */
public abstract class AbstractPropertiesModel extends AbstractTableModel {

    public abstract String getPropertyName();

    public int getColumnCount() {
        return 2;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }
}
