/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import javax.swing.ImageIcon;
import shape.AbstractBeanNodeElement;
import shape.BeanNodeElement;
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public class WhileBeanNodeElement extends AbstractBeanNodeElement {

    public WhileBeanNodeElement(WhileBeanNodeElement e) {
        super(e);
    }

    public WhileBeanNodeElement() {
        this.beanInfo.put("case", String.class);
        this.beanValue.put("case", "");
        this.beanInfo.put("block", String.class);
        this.beanValue.put("block", "");
        this.disctription = "While";
    }

    public void addBeanNode(BeanNodeElement e) {
        this.getChildren().add(e);
    }

    @Override
    public String toString() {
        String str = "while(" + getBeanValue().get("case").toString() + "){" + "\n";
        str = str + getBeanValue().get("block") + "\n" + "}" + "\n";
        return str;
    }

    public String toTipString() {
        String str = "while(" + WidgetUtils.getCaseString(getBeanValue().get("case").toString()) + "){" + "\n";
        str = str + getBeanValue().get("block") + "\n" + "}" + "\n";
        return str;
    }

    @Override
    public ImageIcon getIcon() {
        return WidgetUtils.LOOP_IMAGE;
    }

    public WhileBeanNodeElement getEditNode() {
        return new WhileBeanNodeElement(this);
    }
}
