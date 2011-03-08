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
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public class BlockBeanNodeElement extends AbstractBeanNodeElement {

    public BlockBeanNodeElement(BeanNodeElement parent, List<BeanNodeElement> children, TreeMap<String, Class> beanInfo, TreeMap<String, Object> beanValue, ImageIcon icon, String disctription) {
        super(parent, children, beanInfo, beanValue, icon, disctription);
    }

    public BlockBeanNodeElement() {
        this.beanInfo.put("code", String.class);
        this.beanValue.put("code", "");
        this.disctription = "Block";
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
        return this.getBeanValue().get("code").toString() + "\n";
    }

    public BeanNodeElement getEditNode() {
        return new BlockBeanNodeElement(this);
    }
}
