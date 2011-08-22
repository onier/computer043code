/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import shape.AbstractBeanNodeElement;
import shape.BeanNodeElement;
import testframe.IconUtils;

/**
 *
 * @author admin
 */
public class BlockBeanNodeElement extends AbstractBeanNodeElement {

    public static final String BLOCK_CODE = "code";

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
        return IconUtils.getMessageIcon();
    }

    @Override
    public String toString() {
        return this.getBeanValue().get("code").toString() + "\n";
    }

    public BeanNodeElement getEditNode() {
        return new BlockBeanNodeElement(this);
    }

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(BlockBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                BlockBeanNodeElement test = (BlockBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }

    public static BlockBeanNodeElement parseElement(String str) {
        BlockBeanNodeElement e = new BlockBeanNodeElement();
        e.beanValue.put("code", str);
        return e;
    }
}
