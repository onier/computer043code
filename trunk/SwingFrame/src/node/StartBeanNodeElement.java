/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import shape.AbstractBeanNodeElement;
import shape.AbstractPropertiesModel;
import shape.BeanNodeElement;
import tablerenderer.ParameterType;
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public class StartBeanNodeElement extends AbstractBeanNodeElement {

    public StartBeanNodeElement(BeanNodeElement parent, List<BeanNodeElement> children, TreeMap<String, Class> beanInfo, TreeMap<String, Object> beanValue, ImageIcon icon, String disctription) {
        super(parent, children, beanInfo, beanValue, icon, disctription);
        initListener();
    }
    private TreeMap<String, ParameterType> parameters = new TreeMap<String, ParameterType>();
    private TreeMap<String, String> parameterValues = new TreeMap<String, String>();

    public StartBeanNodeElement(StartBeanNodeElement e) {
        super(e);
        this.copyMap(parameters, e.parameters);
        this.copyMap(parameterValues, e.parameterValues);
        initListener();
    }

    public StartBeanNodeElement() {
        this.disctription = "Start";
        initListener();
        this.beanInfo.put("Parameters", ArrayList.class);
        this.beanValue.put("Parameters", new ArrayList<String>());
    }

    public void firePropertyChange() {
        this.firePropertyChange("setProperties", true, false);
    }

    private void initListener() {
        this.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                ArrayList<String> labels = (ArrayList<String>) getBeanValue().get("Parameters");
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
                        if (!parameterValues.containsKey(labels.get(i))) {
                            parameterValues.put(labels.get(i), "");
                        }
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
                    parameterValues.put(objs[rowIndex].toString(), ((ParameterType) aValue).getInitValue().toString());
                }
                if (columnIndex == 2) {
                    Object[] objs = getParameters().keySet().toArray();
                    parameterValues.put(objs[rowIndex].toString(), aValue.toString());
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
                    return parameterValues.get(objs[rowIndex].toString());
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
                str = str + parameters.get(str1) + " " + str1 + " = " + parameterValues.get(str1).toString() + ";\n";
            }
            str = str + "// End of variables declaration " + "\n" + "\n";
        }
        return str;
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

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(StartBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                StartBeanNodeElement test = (StartBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }

    /**
     * @return the values
     */
    public TreeMap<String, String> getValues() {
        return parameterValues;
    }

    /**
     * @param values the values to set
     */
    public void setValues(TreeMap<String, String> values) {
        this.parameterValues = values;
    }

    private static void parseParameters(String str, StartBeanNodeElement e) {
        str = str.replace(";", " ");
        String[] value = str.split(" ");
        ArrayList<String> l = new ArrayList<String>();
        for (int i = 0; i < value.length; i++) {
            String string = value[i];
            if (string.trim().length() > 0) {
                l.add(string);
            }
        }
        if (l.size() < 2) {
            return;
        }
        for (int i = 0; i < l.size(); i++) {
            e.parameters.put(l.get(1), ParameterType.parseType(l.get(0).trim()));
            if (l.size() > 2) {
                if (l.size() > 3) {
                    e.parameterValues.put(l.get(1).trim(), l.get(3).trim());
                } else {
                    e.parameterValues.put(l.get(1).trim(), "");
                }
            }
        }
        Object obj = e.beanValue.get("Parameters");
        if (obj != null && obj instanceof ArrayList) {
            ((ArrayList) obj).add(l.get(1).trim());
        } else {
            ArrayList<Object> list = new ArrayList<Object>();
            list.add(l.get(1).trim());
            e.beanValue.put("Parameters", list);
        }
    }

    public static void main(String[] args) {
        StartBeanNodeElement e = new StartBeanNodeElement();
        String str = "int a = 1;"
                + "double b = 1.0;"
                + "Object obj = null";
        System.out.println(e.parseElement(str));
    }

    public static StartBeanNodeElement parseElement(String str) {
        StartBeanNodeElement e = new StartBeanNodeElement();
        if (str != null && str.trim().length() > 0) {
            String[] values = str.split(";");
            for (int i = 0; i < values.length; i++) {
                String string = values[i];
                if (string != null && string.trim().length() > 0) {
                    parseParameters(string, e);
                }
            }
        }
        return e;
    }
}
