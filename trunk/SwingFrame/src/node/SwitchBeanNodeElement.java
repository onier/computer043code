/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import shape.AbstractBeanNodeElement;
import shape.AbstractPropertiesModel;
import shape.NodeElement;
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public class SwitchBeanNodeElement extends AbstractBeanNodeElement {

    private TreeMap<String, String> labelsMap = new TreeMap<String, String>();

    public SwitchBeanNodeElement(SwitchBeanNodeElement e) {
        super(e);
        copyMap(labelsMap, e.labelsMap);
        initListener();
    }

    public SwitchBeanNodeElement() {
        this.beanInfo.put("expression", String.class);
        this.beanValue.put("expression", "");
        this.beanInfo.put("switchLabels", ArrayList.class);
        this.beanValue.put("switchLabels", new ArrayList<String>());
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
        return (ArrayList<String>) beanValue.get("switchLabels");
    }

    @Override
    public ImageIcon getIcon() {
        return WidgetUtils.LOOP_IMAGE;
    }

    @Override
    public String toString() {
        String str = "switch(" + beanValue.get("expression") + ")" + "{" + "\n";
        Iterator<String> iterator = labelsMap.keySet().iterator();
        while (iterator.hasNext()) {
            String str1 = iterator.next();
            str = str + "case " + str1 + ":" + "\n";
            str = str + "       " + labelsMap.get(str1) + "\n";
        }
        str = str + "}" + "\n";
        return str.toString();
    }

    @Override
    public String getDisctription() {
        return "Switch";
    }

    /**
     * @return the labelsMap
     */
    public TreeMap<String, String> getLabelsMap() {
        return labelsMap;
    }

    public NodeElement getEditNode() {
        return new SwitchBeanNodeElement(this);
    }
}
