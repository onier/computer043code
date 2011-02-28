/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import shape.AbstractBeanNodeElement;
import shape.AbstractPropertiesModel;
import tablerenderer.ParameterType;
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public class StartBeanNodeElement extends AbstractBeanNodeElement {

    private TreeMap<String, ParameterType> parameters = new TreeMap<String, ParameterType>();

    public StartBeanNodeElement(StartBeanNodeElement e) {
        super(e);
        this.beanInfo.put("Parameters", ArrayList.class);
        this.beanValue.put("Parameters", new ArrayList<String>());
        initListener();
    }

    public StartBeanNodeElement() {
        initListener();
    }

    public void firePropertyChange() {
        this.firePropertyChange("setProperties", true, false);
    }

    private void initListener() {
        this.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                ArrayList<String> labels = (ArrayList<String>) beanValue.get("Parameters");
                Set<String> set = parameters.keySet();
                String[] values = new String[set.size()];
                set.toArray(values);
                for (int i = 0; i < values.length; i++) {
                    if (!labels.contains(values[i])) {
                        parameters.remove(values[i]);
                    }
                }
                for (int i = 0; i < labels.size(); i++) {
                    if (!parameters.containsKey(labels.get(i))) {
                        parameters.put(labels.get(i), ParameterType.NULL);
                    }
                }
            }
        });
    }

    @Override
    public AbstractPropertiesModel[] getPropertiesModel() {
        ArrayList<AbstractPropertiesModel> list = new ArrayList<AbstractPropertiesModel>();
        list.addAll(Arrays.asList(super.getPropertiesModel()));
        list.add(new AbstractPropertiesModel() {

            @Override
            public String getPropertyName() {
                return "Properties";
            }

            public int getRowCount() {
                return getParameters().keySet().size();
            }

            @Override
            public int getColumnCount() {
                return 3;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex > 0;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                if (columnIndex == 1) {
                    Object[] objs = getParameters().keySet().toArray();
                    getParameters().put(objs[rowIndex].toString(), ((ParameterType) aValue));
                }
                if (columnIndex == 2) {
                    Object[] objs = getParameters().keySet().toArray();
                    getParameters().get(objs[rowIndex].toString()).setInitValue(aValue.toString());
                }
                firePropertyChange("change", true, false);
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                Object[] objs = getParameters().keySet().toArray();
                if (columnIndex == 0) {
                    return objs[rowIndex];
                }
                if (columnIndex == 1) {
                    return getParameters().get(objs[rowIndex].toString());
                }
                if (columnIndex == 2) {
                    return getParameters().get(objs[rowIndex].toString()).getInitValue().toString();
                }
                return "";
            }
        });
        return list.toArray(new AbstractPropertiesModel[list.size()]);
    }

    @Override
    public ImageIcon getIcon() {
        return WidgetUtils.START_IMAGE;
    }

    @Override
    public String toString() {
        String str = "//start" + "\n";
        if (parameters.size() > 0) {
            str = str + "// Variables declaration" + "\n";
            Iterator<String> iterator = parameters.keySet().iterator();
            while (iterator.hasNext()) {
                String str1 = iterator.next();
                str = str + parameters.get(str1) + " " + str1 + " = " + parameters.get(str1).getInitValue().toString() + ";\n";
            }
            str = str + "// End of variables declaration " + "\n" + "\n";
        }
        return str;
    }

    @Override
    public String getDisctription() {
        return "Start";
    }

    public StartBeanNodeElement getEditNode() {
        return new StartBeanNodeElement(this);
    }

    /**
     * @return the parameters
     */
    public TreeMap<String, ParameterType> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(TreeMap<String, ParameterType> parameters) {
        this.parameters = parameters;
    }
}
