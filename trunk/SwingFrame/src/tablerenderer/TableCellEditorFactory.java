/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablerenderer;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.table.TableCellEditor;
import tablerenderer.AbstractCustomerEditor.ArrayCustomerEditor;
import tablerenderer.AbstractCustomerEditor.ColorCustomerEditor;
import tablerenderer.AbstractCustomerEditor.FontCustomerEditor;
import tablerenderer.AbstractCustomerEditor.TextCustomerEditor;

/**
 *
 * @author Administrator
 */
public class TableCellEditorFactory {

    private static final HashMap<Class, TableCellEditor> editorMap = new HashMap<Class, TableCellEditor>();

    static {
        editorMap.put(String.class, new TextCustomerEditor());
        editorMap.put(Boolean.class, new DefaultCellEditor(new JCheckBox()));
        editorMap.put(Enum.class, createComboBoxEditor(ParameterType.class));
        editorMap.put(Color.class, new ColorCustomerEditor());
        editorMap.put(Font.class, new FontCustomerEditor());
        editorMap.put(ArrayList.class, new ArrayCustomerEditor());
    }

    public static String toShowString(ArrayList list, String s) {
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null && list.get(i).toString().trim().length() > 0) {
                str = str + list.get(i).toString() + s;
            }
        }
        return str;
    }

    public static ArrayList toList(String str, String s) {
        String[] values = str.split(s);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; i++) {
            String string = values[i];
            if (string != null && string.trim().length() > 0) {
                list.add(string);
            }
        }
        return list;
    }

    private TableCellEditorFactory() {
    }

    public static TableCellEditor createEditor(Class cl) {
        if (cl.isEnum()) {
            JComboBox combox = new JComboBox(cl.getEnumConstants());
            combox.setEditable(false);
            DefaultCellEditor editor = new DefaultCellEditor(combox);
            editor.setClickCountToStart(2);
            return editor;
        }
        return editorMap.get(cl);
    }

    private static TableCellEditor createComboBoxEditor(Class type) {
        JComboBox combox = new JComboBox(ParameterType.values());
        combox.setEditable(false);
        DefaultCellEditor editor = new DefaultCellEditor(combox);
        editor.setClickCountToStart(2);
        return editor;
    }
}
