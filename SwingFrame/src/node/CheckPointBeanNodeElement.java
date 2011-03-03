/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import shape.AbstractBeanNodeElement;
import shape.NodeElement;

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
        return beanValue.get("checkPoint").toString() + "\n";
    }

    public NodeElement getEditNode() {
        return new CheckPointBeanNodeElement(this);
    }

    @Override
    public String getDisctription() {
        return this.disctription;
    }
}
