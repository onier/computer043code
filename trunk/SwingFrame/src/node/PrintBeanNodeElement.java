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
public class PrintBeanNodeElement extends AbstractBeanNodeElement {

    public PrintBeanNodeElement() {
        this.beanInfo.put("value", String.class);
        this.beanValue.put("value", "");
        this.disctription = "Print";
    }

    public PrintBeanNodeElement(PrintBeanNodeElement e) {
        super(e);
    }

    @Override
    public String toString() {
        String str = "System.out.println(" + beanValue.get("value").toString() + ");" + "\n";
        return str;
    }

    @Override
    public String getDisctription() {
        return disctription;
    }

    @Override
    public NodeElement getEditNode() {
        return new PrintBeanNodeElement(this);
    }
}
