/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.util.List;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import shape.AbstractBeanNodeElement;
import shape.BeanNodeElement;

/**
 *
 * @author admin
 */
public class CheckPointBeanNodeElement extends AbstractBeanNodeElement {

    public CheckPointBeanNodeElement(BeanNodeElement parent, List<BeanNodeElement> children, TreeMap<String, Class> beanInfo, TreeMap<String, Object> beanValue, ImageIcon icon, String disctription) {
        super(parent, children, beanInfo, beanValue, icon, disctription);
    }

    public CheckPointBeanNodeElement() {
        this.beanInfo.put("checkPoint", String.class);
        this.beanValue.put("checkPoint", "");
        this.disctription = "checkPoint";
    }

    public CheckPointBeanNodeElement(CheckPointBeanNodeElement e) {
        super(e);
    }

    @Override
    public String toString() {
        return getBeanValue().get("checkPoint").toString() + "\n";
    }

    public BeanNodeElement getEditNode() {
        return new CheckPointBeanNodeElement(this);
    }
}
