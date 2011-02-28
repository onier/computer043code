/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablerenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import tablerenderer.AbstractCustomerRenderer.ArrayCustomerRenderer;
import tablerenderer.AbstractCustomerRenderer.FontCustomerRenderer;
import tablerenderer.AbstractCustomerRenderer.ProcessCustomerRenderer;
import tablerenderer.AbstractCustomerRenderer.TextCustomerRenderer;

/**
 *
 * @author Administrator
 */
public class TableCellRendererFactory {

    private static final HashMap<Class, TableCellRenderer> rendererMap = new HashMap<Class, TableCellRenderer>();

    static {
        rendererMap.put(String.class, new TextCustomerRenderer());
        rendererMap.put(Boolean.class,
                new TableCellRenderer() {

                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        JCheckBox checkBox = new JCheckBox();
                        if (value != null && value.toString().equalsIgnoreCase("true")) {
                            checkBox.setSelected(true);
                        } else {
                            checkBox.setSelected(false);
                        }
                        if (isSelected) {
                            checkBox.setBackground(table.getSelectionBackground());
                            checkBox.setForeground(table.getSelectionForeground());
                        } else {
                            checkBox.setBackground(table.getBackground());
                            checkBox.setForeground(table.getForeground());
                        }
                        return checkBox;
                    }
                });
        rendererMap.put(Enum.class, new TableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JComboBox comboxBox = new JComboBox(ParameterType.values());
                comboxBox.setSelectedItem(value);
                comboxBox.setEditable(false);
                if (isSelected) {
                    comboxBox.setBackground(table.getSelectionBackground());
                    comboxBox.setForeground(table.getSelectionForeground());
                } else {
                    comboxBox.setBackground(table.getBackground());
                    comboxBox.setForeground(table.getForeground());
                }
                return comboxBox;
            }
        });
        rendererMap.put(Color.class, new ProcessCustomerRenderer());
        rendererMap.put(Font.class, new FontCustomerRenderer());
        rendererMap.put(ArrayList.class, new ArrayCustomerRenderer());
    }

    public static TableCellRenderer createTableCellRenderer(Class cl) {
        return rendererMap.get(cl);
    }

    private void TableCellRendererFactory() {
    }

    public static void putCellRenderer(Class cl, TableCellRenderer renderer) {
        rendererMap.put(cl, renderer);
    }
}
