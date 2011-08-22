/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import testframe.IconUtils;

/**
 *
 * @author admin
 */
public abstract class AbstractBeanNodeElement extends AbstractBean implements BeanNodeElement {

    protected BeanNodeElement parent;
    protected List<BeanNodeElement> children = new ArrayList<BeanNodeElement>();
    protected TreeMap<String, Class> beanInfo = new TreeMap<String, Class>();
    protected TreeMap<String, Object> beanValue = new TreeMap<String, Object>();
    protected ImageIcon icon = IconUtils.getMessageIcon();
    protected String disctription = "";

    public AbstractBeanNodeElement(BeanNodeElement parent, List<BeanNodeElement> children, TreeMap<String, Class> beanInfo, TreeMap<String, Object> beanValue, ImageIcon icon, String disctription) {
        this.parent = parent;
        this.children = children;
        this.beanInfo = beanInfo;
        this.beanValue = beanValue;
        this.disctription = disctription;
//        this.icon = icon;
    }

    public AbstractBeanNodeElement() {
    }

    public void adddBeanNodeElement(BeanNodeElement e) {
        getChildren().add(e);
    }

    public AbstractBeanNodeElement(AbstractBeanNodeElement e) {
        copyMap(beanInfo, e.beanInfo);
        copyMap(beanValue, e.beanValue);
        icon = e.icon;
        this.disctription = e.disctription;
    }

    public void fireNodeProperties() {
        firePropertyChange("properties", false, true);
    }

    public void fireNodeChange() {
        firePropertyChange("change", false, true);
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
                return getBeanInfo().keySet().size();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                Object[] objs = getBeanInfo().keySet().toArray();
                AbstractBeanNodeElement.this.firePropertyChange(objs[rowIndex].toString(), getBeanValue().get(objs[rowIndex].toString()), aValue);
                getBeanValue().put(objs[rowIndex].toString(), aValue);
                AbstractBeanNodeElement.this.firePropertyChange("change", true, false);
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                Object[] objs = getBeanInfo().keySet().toArray();
                if (columnIndex == 0) {
                    return objs[rowIndex].toString();
                }
                return getBeanValue().get(objs[rowIndex].toString());
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
        return getChildren().get(childIndex);
    }

    public List<BeanNodeElement> children() {
        return getChildren();
    }

    public int getChildCount() {
        return getChildren().size();
    }

    public TreeMap<String, Class> getBeanInfo() {
        return beanInfo;
    }

    public String getDisctription() {
        return disctription;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public Map<String, Object> getBeanValue() {
        return beanValue;
    }

    /**
     * @param disctription the disctription to set
     */
    public void setDisctription(String disctription) {
        this.disctription = disctription;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(BeanNodeElement parent) {
        this.parent = parent;
    }

    /**
     * @return the children
     */
    public List<BeanNodeElement> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<BeanNodeElement> children) {
        this.children = children;
    }

    /**
     * @param beanInfo the beanInfo to set
     */
    public void setBeanInfo(TreeMap<String, Class> beanInfo) {
        this.beanInfo = beanInfo;
    }

    /**
     * @param beanValue the beanValue to set
     */
    public void setBeanValue(TreeMap<String, Object> beanValue) {
        this.beanValue = beanValue;
    }
}
