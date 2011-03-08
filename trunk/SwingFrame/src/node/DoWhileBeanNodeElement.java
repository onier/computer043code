/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import javax.swing.ImageIcon;
import shape.AbstractBeanNodeElement;
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public class DoWhileBeanNodeElement extends AbstractBeanNodeElement {

    public DoWhileBeanNodeElement(DoWhileBeanNodeElement e) {
        super(e);
    }

    public DoWhileBeanNodeElement() {
        this.beanInfo.put("case", String.class);
        this.beanValue.put("case", "");
        this.beanInfo.put("block", String.class);
        this.beanValue.put("block", "");
        this.disctription = "doWhile";
    }

    @Override
    public ImageIcon getIcon() {
        return WidgetUtils.LOOP_IMAGE;
    }

    @Override
    public String toString() {
        String str = "do {" + "\n";
        str = str + getBeanValue().get("block").toString() + "\n";
        str = str + "}" + "while(" + getBeanValue().get("case").toString() + ");" + "\n";
        return str;
    }

    public String toTipString() {
        String str = "do {" + "\n";
        str = str + getBeanValue().get("block").toString() + "\n";
        str = str + "}" + "while(" + WidgetUtils.getCaseString(getBeanValue().get("case").toString()) + ");" + "\n";
        return str;
    }

    public DoWhileBeanNodeElement getEditNode() {
        return new DoWhileBeanNodeElement(this);
    }
}