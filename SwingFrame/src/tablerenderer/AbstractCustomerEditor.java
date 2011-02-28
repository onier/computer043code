/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablerenderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import shape.JFontChooser;

public abstract class AbstractCustomerEditor extends AbstractCellEditor implements
        TableCellEditor {

    public static final String TEXT = "...";
    public static final int WIDTH = 20;
    protected JButton button;
    protected JPanel editorComponent = new JPanel();
    protected EditorDelegate delegate;
    protected JTextField textField = new JTextField();
    protected int clickCountToStart = 2;
    protected Object value;

    protected AbstractCustomerEditor() {
        button = new JButton(TEXT);
        button.setPreferredSize(new Dimension(AbstractCustomerEditor.WIDTH, button.getPreferredSize().height));
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                value = getSelectObject(value);
                textField.setText(getText(value));
                AbstractCustomerEditor.this.stopCellEditing();
            }
        });
        delegate = new EditorDelegate();
        editorComponent.setLayout(new BorderLayout());
        editorComponent.add(textField, BorderLayout.CENTER);
        editorComponent.add(button, BorderLayout.EAST);
    }

    /**
     * 自定用选择数据对象,该方法会在单元格的右侧按钮的action中调用,用来实现用户选择自定义对象数据.
     * @param obj 
     * @return
     */
    public abstract Object getSelectObject(Object obj);

    /**
     * 用户自定义数据对象映射到字符串的显示方式.
     * @param obj
     */
    public abstract String getText(Object obj);

    /**
     * 方法继承自AbstractCellEditor,在该方法里实现由字符串构造成用户数据对象的功能.
     * @return
     */
    public abstract Object getCellEditorValue();

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        textField.setText(getText(value));
        textField.requestFocusInWindow();
        this.value = value;
        return editorComponent;
    }

    @Override
    public void cancelCellEditing() {
        delegate.cancelCellEditing();
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

    @Override
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        super.addCellEditorListener(l);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        super.removeCellEditorListener(l);
    }

    protected class EditorDelegate implements ActionListener, ItemListener,
            Serializable {

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

        @Override
        public void actionPerformed(ActionEvent e) {
            stopCellEditing();
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            stopCellEditing();
        }
    }

    public static class ArrayCustomerEditor extends AbstractCustomerEditor {

        private boolean bFlag = false;

        @Override
        public Object getSelectObject(Object obj) {
            ArrayList list = new ArrayList();
            if (obj instanceof ArrayList) {
                list = (ArrayList) obj;
            }
            TextEditDialog dialog = new TextEditDialog(TableCellEditorFactory.toShowString(list, "\n"));
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            bFlag = true;
            value = TableCellEditorFactory.toList(dialog.getValue(), "\n");
            return value;
        }

        @Override
        public String getText(Object obj) {
            if (obj == null || (!(obj instanceof ArrayList))) {
                obj = "";
            }
            return TableCellEditorFactory.toShowString((ArrayList) obj, ",");
        }

        @Override
        public Object getCellEditorValue() {
            if (bFlag) {
                bFlag = false;
                return value;
            } else {
                return TableCellEditorFactory.toList(this.textField.getText(), ",");
            }
        }
    }

    public static class TextCustomerEditor extends AbstractCustomerEditor {

        private boolean bFlag = false;

        @Override
        public Object getSelectObject(Object obj) {
            TextEditDialog dialog = new TextEditDialog(obj.toString());
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            value = dialog.getValue();
            bFlag = true;
            return dialog.getValue();
        }

        @Override
        public String getText(Object obj) {
            if (obj == null) {
                obj = "";
            }
            return obj.toString();
        }

        @Override
        public Object getCellEditorValue() {
            if (bFlag) {
                bFlag = false;
                return value;
            } else {
                return this.textField.getText();
            }
        }
    }

    public static class FontCustomerEditor extends AbstractCustomerEditor {

        public FontCustomerEditor() {
            textField.setEditable(false);
            textField.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() >= 2) {
                        Object temp = getSelectObject(value);
                        if (value != null) {
                            value = temp;
                        }
                    }
                    textField.setText(getText(value));
                }
            });
        }

        @Override
        public Object getSelectObject(Object obj) {
            Font font = null;
            if (obj != null) {
                font = JFontChooser.showFontDialog((Font) obj);
            }
            if (font != null) {
                return font;
            }
            return obj;
        }

        @Override
        public String getText(Object obj) {
            if (obj != null) {
                Font font = (Font) obj;
                return font.getName();
            }
            return "";
        }

        @Override
        public Object getCellEditorValue() {
            return value;
        }
    }

    /**
     * 用户颜色选择自定义editor
     */
    public static class ColorCustomerEditor extends AbstractCustomerEditor {

        @Override
        public Object getSelectObject(Object obj) {
            Color color = Color.RED;
            if (obj != null && obj instanceof Color) {
                color = (Color) obj;
            }
            return JColorChooser.showDialog(null, "Select Color", color);
        }

        @Override
        public String getText(Object obj) {
            Color color = Color.RED;
            if (obj != null && obj instanceof Color) {
                color = (Color) obj;
            }
            return "[" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "]";
        }

        @Override
        public Object getCellEditorValue() {
            String text = textField.getText();
            text = text.substring(1, text.length() - 1);
            String[] values = text.split(",");
            try {
                return new Color(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
            } catch (Exception e) {
                return value;
            }
        }
    }
}
