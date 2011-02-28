/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author admin
 */
public interface BeanNodeElement extends NodeElement {

    public BeanNodeElement getParent();

    public void addPropertyChangeListener(PropertyChangeListener listener);

    public void removePropertyChangeListener(PropertyChangeListener listener);

    public PropertyChangeListener[] getPropertyChangeListeners();

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public boolean getAllowsChildren();

    public BeanNodeElement getChildAt(int childIndex);

    public AbstractPropertiesModel[] getPropertiesModel();

    public List<BeanNodeElement> children();

    public int getChildCount();

    /**
     * 包含bean信息的节点,<属性名,数据类型>  <Visible,Boolean>
     * @return
     */
    public HashMap<String, Class> getBeanInfo();

    /**
     * 包含bean信息的节点,<属性名,数据类型>  <Visible,Boolean>
     * @return
     */
    public HashMap<String, Object> getBeanValue();
}
