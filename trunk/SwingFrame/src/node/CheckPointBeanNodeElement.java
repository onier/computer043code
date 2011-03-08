/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import shape.AbstractBeanNodeElement;
import shape.BeanNodeElement;

/**
 *
 * @author admin
 */
public class CheckPointBeanNodeElement extends AbstractBeanNodeElement {

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
