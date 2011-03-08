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
public class PrintBeanNodeElement extends AbstractBeanNodeElement {

    public PrintBeanNodeElement(BeanNodeElement parent, List<BeanNodeElement> children, TreeMap<String, Class> beanInfo, TreeMap<String, Object> beanValue, ImageIcon icon, String disctription) {
        super(parent, children, beanInfo, beanValue, icon, disctription);
    }

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
        String str = "System.out.println(" + getBeanValue().get("value").toString() + ");" + "\n";
        return str;
    }

    @Override
    public BeanNodeElement getEditNode() {
        return new PrintBeanNodeElement(this);
    }
}
