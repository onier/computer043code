/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import javax.swing.ImageIcon;
import shape.AbstractBeanNodeElement;
import shape.NodeElement;
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public class BlockBeanNodeElement extends AbstractBeanNodeElement {

    public BlockBeanNodeElement() {
        this.beanInfo.put("code", String.class);
        this.beanValue.put("code", "");
    }

    public BlockBeanNodeElement(BlockBeanNodeElement e) {
        super(e);
    }

    @Override
    public ImageIcon getIcon() {
        return WidgetUtils.MESSAGE_IMAGE;
    }

    @Override
    public String toString() {
        return this.beanValue.get("code").toString() + "\n";
    }

    @Override
    public String getDisctription() {
        return "Block";
    }

    public NodeElement getEditNode() {
        return new BlockBeanNodeElement(this);
    }
}
