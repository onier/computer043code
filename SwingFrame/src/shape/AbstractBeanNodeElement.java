/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import org.jdesktop.application.AbstractBean;
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public abstract class AbstractBeanNodeElement extends AbstractBean implements BeanNodeElement {

    protected BeanNodeElement parent;
    protected List<BeanNodeElement> children = new ArrayList<BeanNodeElement>();
    protected HashMap<String, Class> beanInfo = new HashMap<String, Class>();
    protected HashMap<String, Object> beanValue = new HashMap<String, Object>();
    protected ImageIcon icon = WidgetUtils.MESSAGE_IMAGE;
    protected String disctription = "";

    public AbstractBeanNodeElement() {
    }

    public void adddBeanNodeElement(BeanNodeElement e) {
        children.add(e);
    }

    public AbstractBeanNodeElement(AbstractBeanNodeElement e) {
        copyMap(beanInfo, e.beanInfo);
        copyMap(beanValue, e.beanValue);
        icon = e.icon;
        this.disctription = e.disctription;
    }

    protected <K, V> Map<K, V> copyMap(Map<K, V> dmap, Map<K, V> smap) {
        Set<K> set = smap.keySet();
        Iterator<K> iterator = set.iterator();
        while (iterator.hasNext()) {
            K next = iterator.next();
            dmap.put(next, smap.get(next));
        }
        return dmap;
    }

    public AbstractPropertiesModel[] getPropertiesModel() {
        AbstractPropertiesModel tableMode = new AbstractPropertiesModel() {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex != 0;
            }

            public int getRowCount() {
                return beanInfo.keySet().size();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                Object[] objs = beanInfo.keySet().toArray();
                AbstractBeanNodeElement.this.firePropertyChange(objs[rowIndex].toString(), beanValue.get(objs[rowIndex].toString()), aValue);
                beanValue.put(objs[rowIndex].toString(), aValue);
                AbstractBeanNodeElement.this.firePropertyChange("change", true, false);
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                Object[] objs = beanInfo.keySet().toArray();
                if (columnIndex == 0) {
                    return objs[rowIndex].toString();
                }
                return beanValue.get(objs[rowIndex].toString());
            }

            @Override
            public String getPropertyName() {
                return "property";
            }
        };
        return new AbstractPropertiesModel[]{tableMode};
    }

    public AbstractBeanNodeElement(NodeElement node) {
        icon = node.getIcon();
        this.disctription = node.getDisctription();
    }

    public BeanNodeElement getParent() {
        return parent;
    }

    public boolean getAllowsChildren() {
        return true;
    }

    public BeanNodeElement getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    public List<BeanNodeElement> children() {
        return children;
    }

    public int getChildCount() {
        return children.size();
    }

    public HashMap<String, Class> getBeanInfo() {
        return beanInfo;
    }

    public String getDisctription() {
        return disctription;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public HashMap<String, Object> getBeanValue() {
        return beanValue;
    }
}
