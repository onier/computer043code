/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import layout.action.SelectChangeEvent;
import layout.action.SelectChangeListener;
import layout.action.SelectChangeSupport;
import tablerenderer.CellType;
import tablerenderer.TableCellEditorFactory;
import tablerenderer.TableCellRendererFactory;

/**
 *
 * @author Administrator
 */
public class TableComponentPanelRenderer extends JTable implements ComponentPanelRenderer {

    private Dimension animationSize = new Dimension();
    protected SelectChangeSupport support = new SelectChangeSupport();
    private boolean firing = false;

    public TableComponentPanelRenderer(TableModel tableModel) {
        super(tableModel);
//        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.setCellSelectionEnabled(true);
        this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                firing = true;
                support.fireSelectChangePerformed(new SelectChangeEvent(TableComponentPanelRenderer.this));
                firing = false;
            }
        });
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
    }

    public TableComponentPanelRenderer() {
        super(new AbstractTableModel() {

            Object[][] values = new Object[][]{
                {"String", "Color", "ComboBox", "Boolean", "Font"},
                {"text", Color.RED, CellType.BUTTON, true, new Font("宋体", 1, 12)}
            };

            public int getRowCount() {
                return values[0].length;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return true;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                values[columnIndex][rowIndex] = aValue;
            }

            public int getColumnCount() {
                return values.length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return values[columnIndex][rowIndex];
            }
        });
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.setCellSelectionEnabled(true);
        this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                firing = true;
                support.fireSelectChangePerformed(new SelectChangeEvent(TableComponentPanelRenderer.this));
                firing = false;
            }
        });
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        Object value = this.getValueAt(row, column);

        if (column != 0) {
            TableCellRenderer renderer = null;
            try {
                renderer = TableCellRendererFactory.createTableCellRenderer(value.getClass());
            } catch (Exception e) {
                System.out.println("dsfdf");
            }

            if (renderer != null) {
                return renderer;
            }
        }
        return super.getCellRenderer(row, column);
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        Object value = this.getValueAt(row, column);
        if (column != 0) {
            TableCellEditor editor = TableCellEditorFactory.createEditor(value.getClass());
            if (editor != null) {
                return editor;
            }
        }
        return super.getCellEditor(row, column);
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
            if (getCellEditor() != null) {
                firing = true;
                getCellEditor().stopCellEditing();
                firing = false;
            }
            clearSelection();
        }
    }

    public Dimension getAnimationSize() {
        return this.animationSize;
    }

    public void setAnimationSize(Dimension size) {
        this.animationSize = size;
    }
}
