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
import testframe.IconUtils;

/**
 *
 * @author admin
 */
public class SwitchBeanNodeElement extends AbstractBeanNodeElement {

    private TreeMap<String, String> labelsMap = new TreeMap<String, String>();
    public static final String EXPRESSION = "expression";
    public static final String SWITCH_LABELS = "switchLabels";

    public SwitchBeanNodeElement(BeanNodeElement parent, List<BeanNodeElement> children, TreeMap<String, Class> beanInfo, TreeMap<String, Object> beanValue, ImageIcon icon, String disctription) {
        super(parent, children, beanInfo, beanValue, icon, disctription);
        initListener();
        this.firePropertyChange("change", false, true);
    }

    public SwitchBeanNodeElement(SwitchBeanNodeElement e) {
        super(e);
        copyMap(labelsMap, e.labelsMap);
        this.disctription = "Switch";
        initListener();
    }

    public SwitchBeanNodeElement() {
        this.beanInfo.put(EXPRESSION, String.class);
        this.beanValue.put(EXPRESSION, "");
        this.beanInfo.put(SWITCH_LABELS, ArrayList.class);
        this.beanValue.put(SWITCH_LABELS, new ArrayList<String>());
        this.disctription = "Switch";
        initListener();
    }

    @Override
    public AbstractPropertiesModel[] getPropertiesModel() {
        ArrayList<AbstractPropertiesModel> list = new ArrayList<AbstractPropertiesModel>();
        list.addAll(Arrays.asList(super.getPropertiesModel()));
        list.add(new AbstractPropertiesModel() {

            @Override
            public String getPropertyName() {
                return "SwitchLabels";
            }

            public int getRowCount() {
                return getLabelsMap().keySet().size();
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                if (columnIndex == 1) {
                    Object[] objs = getLabelsMap().keySet().toArray();
                    getLabelsMap().put(objs[rowIndex].toString(), aValue.toString());
                    firePropertyChange("change", true, false);
                }
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                Object[] objs = getLabelsMap().keySet().toArray();
                if (columnIndex == 0) {
                    return objs[rowIndex];
                }
                return getLabelsMap().get(objs[rowIndex].toString());
            }
        });
        return list.toArray(new AbstractPropertiesModel[list.size()]);
    }

    private void initListener() {
        this.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                ArrayList<String> labels = getSwitchLabel();
                Set<String> set = getLabelsMap().keySet();
                String[] values = new String[set.size()];
                set.toArray(values);
                for (int i = 0; i < values.length; i++) {
                    if (!labels.contains(values[i])) {
                        getLabelsMap().remove(values[i]);
                    }
                }
                for (int i = 0; i < labels.size(); i++) {
                    if (!labelsMap.containsKey(labels.get(i))) {
                        getLabelsMap().put(labels.get(i), "");
                    }
                }
            }
        });
    }

    public ArrayList<String> getSwitchLabel() {
        return (ArrayList<String>) getBeanValue().get("switchLabels");
    }

    @Override
    public ImageIcon getIcon() {
        return IconUtils.getLoopIcon();
    }

    @Override
    public String toString() {
        String str = "switch(" + getBeanValue().get("expression") + ")" + "{" + "\n";
        Iterator<String> iterator = labelsMap.keySet().iterator();
        while (iterator.hasNext()) {
            String str1 = iterator.next();
            str = str + "case " + str1 + ":" + "\n";
            str = str + "       " + labelsMap.get(str1) + "\n";
        }
        str = str + "}" + "\n";
        return str.toString();
    }

    /**
     * @return the labelsMap
     */
    public TreeMap<String, String> getLabelsMap() {
        return labelsMap;
    }

    public BeanNodeElement getEditNode() {
        return new SwitchBeanNodeElement(this);
    }

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(SwitchBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                SwitchBeanNodeElement test = (SwitchBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }

    private static void parseLabel(String str, SwitchBeanNodeElement e) {
        int n = str.indexOf("case");
        if (n >= 0) {
            str = str.substring(n + 4, str.length()).trim();
            n = str.indexOf(":");
            if (n >= 0) {
                String label = str.substring(0, n).trim();
                addLabel(e, label.trim());
                String content = str.substring(n + 1, str.length());
                e.labelsMap.put(label, content.trim());
            }
        }
    }

    public static void addLabel(SwitchBeanNodeElement e, String label) {
        Object ob = e.beanValue.get(SwitchBeanNodeElement.SWITCH_LABELS);
        if (ob != null) {
            ((ArrayList) ob).add(label);
        } else {
            ArrayList<Object> list = new ArrayList<Object>();
            list.add(label);
            e.beanValue.put(SwitchBeanNodeElement.SWITCH_LABELS, label);
        }
    }

    private static void parseExpression(String str, SwitchBeanNodeElement e) {
        int a = 1;
        int n = str.indexOf("switch");
        if (n >= 0) {
            str = str.substring(n + 6, str.length()).trim();
            n = str.indexOf("(");
            if (n >= 0) {
                str = str.substring(n + 1, str.length()).trim();
                n = str.lastIndexOf(")");
                if (n >= 0) {
                    str = str.substring(0, n).trim();
                    e.beanValue.put(SwitchBeanNodeElement.EXPRESSION, str);
                }
            }
        }
    }

    public static SwitchBeanNodeElement parseElement(String str) {
        SwitchBeanNodeElement e = new SwitchBeanNodeElement();
        int n = str.lastIndexOf("}");
        if (n > 0) {
            str = str.substring(0, n);
            String[] values = str.split("case");
            parseExpression(values[0], e);
            for (int i = 1; i < values.length; i++) {
                String string = values[i];
                if (string != null && string.trim().length() > 0) {
                    parseLabel("case " + string, e);
                }
            }
        }
        return e;
    }

    public static void main(String[] args) {
        SwitchBeanNodeElement e = new SwitchBeanNodeElement();
        String str = " switch(a){"
                + "case 1:" + "\n"
                + " if(true){aaa}else{bbb}" + "\n"
                + "case 2:" + "" + "int b;" + "\n" + "}";
        System.out.println(e.parseElement(str));
    }
}
