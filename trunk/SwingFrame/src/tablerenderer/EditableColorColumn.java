/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablerenderer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;

import java.awt.Color;

import java.awt.Component;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.FocusAdapter;

import java.awt.event.FocusEvent;

import java.awt.event.ItemEvent;

import java.awt.event.ItemListener;

import java.awt.event.KeyEvent;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.io.Serializable;

import java.util.EventObject;

import javax.swing.AbstractAction;

import javax.swing.JComponent;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JPopupMenu;

import javax.swing.JScrollPane;

import javax.swing.JTable;

import javax.swing.JTextArea;

import javax.swing.KeyStroke;

import javax.swing.SwingUtilities;

import javax.swing.event.CellEditorListener;

import javax.swing.event.ChangeEvent;

import javax.swing.event.EventListenerList;

import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableCellEditor;

import javax.swing.table.TableColumn;

import javax.swing.table.TableColumnModel;

public class EditableColorColumn {

    public static void main(String args[]) {
        final JFrame frame = new JFrame("Editable Color Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JTable table = getTable();
        TableColumnModel colModel = table.getColumnModel();
        TableColumn col = colModel.getColumn(1);
//        col.setCellRenderer(new ToolTipTableRenderer());
        col.setCellEditor(new TextAreaEditor());
        table.setCellEditor(new TextAreaEditor());
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(400, 150);
        frame.setVisible(true);
        table.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private static JTable getTable() {
        String[] tournies = {
            "tournament 1tournament 2", "tournament 1",
            "tournament 1tounrament 2tournament 3tournament 4",
            "tournament 1tounrament 2tournament 3tournament 4"
            + "tournament 5ntounrament ntournament ntournament 8"
            + "tournament 9ntounrament ntournament ntournament n"
        };
        String[] colNames = {"city", "tournaments", "sea"};
        Object[][] data = {
            {"San Diego", tournies[0], "Pacific"},
            {"Cape Cod", tournies[1], "Atlantic"},
            {"Sarasota", tournies[2], "Gulf"},
            {"Seattle", tournies[3], "Pacific"}
        };
        DefaultTableModel model = new DefaultTableModel(data, colNames);
        return new JTable(model);
    }

    static class TextAreaEditor extends JPopupMenu implements TableCellEditor {

        protected EventListenerList listenerList = new EventListenerList();
        transient protected ChangeEvent changeEvent = null;
        protected JComponent editorComponent;
        protected EditorDelegate delegate;
        protected int clickCountToStart = 2;
        protected JTextArea textArea;
        protected JScrollPane scrollPane;
        protected JTable table;
        protected int row, column;

        public TextAreaEditor() {
            textArea = new JTextArea(4, 2);
            textArea.addFocusListener(new FocusAdapter() {

                @Override
                public void focusGained(FocusEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            textArea.selectAll();
                        }
                    });
                }
            });
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            scrollPane = new JScrollPane(textArea);
            editorComponent = scrollPane;
            clickCountToStart = 2;
            this.add(scrollPane, BorderLayout.CENTER);
            delegate = new EditorDelegate() {

                @Override
                public void setValue(Object value) {
                    textArea.setText((value != null) ? value.toString() : "");
                }

                @Override
                public Object getCellEditorValue() {
                    return textArea.getText();
                }
            };
            AbstractAction tabAction = new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    stopCellEditing();
                    if (TextAreaEditor.this.isVisible()) {
                        TextAreaEditor.this.setVisible(false);
                    }
                }
            };
            KeyStroke tabKey = KeyStroke.getKeyStroke("TAB");
            textArea.getInputMap().put(tabKey, tabAction);
            textArea.getActionMap().put("Enter", new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    delegate.actionPerformed(e);
                    table.setRowHeight(row, table.getRowHeight());
                }
            });
            textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
            textArea.setBackground(Color.WHITE);
        }

        @Override
        public Component getTableCellEditorComponent(final JTable table,
                Object value,
                boolean isSelected,
                final int row, final int column) {
            this.table = table;
            this.row = row;
            this.column = column;
//            table.setRowHeight(row, table.getRowHeight() * 2);
            textArea.setText((String) value);
//            textArea.setToolTipText((String) value);
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    Rectangle rect = table.getCellRect(row, column, true);
                    setPopupSize(rect.width, rect.height * 3);
                    show(table, rect.x, rect.y + rect.height);
                    textArea.selectAll();
                }
            });
            textArea.selectAll();
            return new JLabel(((String) value));
        }

        @Override
        public void cancelCellEditing() {
            delegate.cancelCellEditing();
            table.setRowHeight(row, table.getRowHeight());
        }

        @Override
        public Object getCellEditorValue() {
            return textArea.getText();
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            if (anEvent instanceof MouseEvent) {
                return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
            }
            return true;
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
            return true;
        }

        protected void fireEditingStopped() {
            // Guaranteed to return a non-null array
            Object[] listeners = listenerList.getListenerList();
            // Process the listeners last to first, notifying
            // those that are interested in this event
            for (int i = listeners.length - 2; i >= 0; i -= 2) {
                if (listeners[i] == CellEditorListener.class) {
                    // Lazily create the event:
                    if (changeEvent == null) {
                        changeEvent = new ChangeEvent(this);
                    }
                    ((CellEditorListener) listeners[i + 1]).editingStopped(changeEvent);
                }
            }
        }

        @Override
        public boolean stopCellEditing() {
            fireEditingStopped();
            if (table != null) {
                table.setRowHeight(row, table.getRowHeight());
            }
            return true;
        }

        public void addCellEditorListener(CellEditorListener l) {
            listenerList.add(CellEditorListener.class, l);
        }

        public void removeCellEditorListener(CellEditorListener l) {
            listenerList.remove(CellEditorListener.class, l);
        }

        protected class EditorDelegate implements ActionListener, ItemListener, Serializable {

            protected Object value;

            public Object getCellEditorValue() {
                return value;
            }

            public void setValue(Object value) {
                this.value = value;
            }

            public boolean isCellEditable(EventObject anEvent) {
                if (anEvent instanceof MouseEvent) {
                    return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
                }
                return true;
            }

            public boolean shouldSelectCell(EventObject anEvent) {
                return true;
            }

            public boolean startCellEditing(EventObject anEvent) {
                return true;
            }

            public boolean stopCellEditing() {
                fireEditingStopped();
                return true;
            }

            public void cancelCellEditing() {
                fireEditingCanceled();
            }

            protected void fireEditingCanceled() {
                // Guaranteed to return a non-null array
                Object[] listeners = listenerList.getListenerList();
                // Process the listeners last to first, notifying
                // those that are interested in this event
                for (int i = listeners.length - 2; i >= 0; i -= 2) {
                    if (listeners[i] == CellEditorListener.class) {
                        // Lazily create the event:
                        if (changeEvent == null) {
                            changeEvent = new ChangeEvent(this);
                        }
                        ((CellEditorListener) listeners[i + 1]).editingCanceled(changeEvent);
                    }
                }
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                stopCellEditing();
            }

            @Override
            public void itemStateChanged(ItemEvent e) {
                stopCellEditing();
            }
        }
    }
}


