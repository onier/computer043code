/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.beans.Encoder;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface BeanNodeElement extends NodeElement {

    public BeanNodeElement getParent();

    public void loadEncoderDelegate(Encoder encoder);

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
    public Map<String, Class> getBeanInfo();

    /**
     * 包含bean信息的节点,<属性名,数据类型>  <Visible,Boolean>
     * @return
     */
    public Map<String, Object> getBeanValue();
}
