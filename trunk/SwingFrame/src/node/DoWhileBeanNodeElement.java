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
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public class DoWhileBeanNodeElement extends AbstractBeanNodeElement {

    public DoWhileBeanNodeElement(BeanNodeElement parent, List<BeanNodeElement> children, TreeMap<String, Class> beanInfo, TreeMap<String, Object> beanValue, ImageIcon icon, String disctription) {
        super(parent, children, beanInfo, beanValue, icon, disctription);
    }

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

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(DoWhileBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                DoWhileBeanNodeElement test = (DoWhileBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }
}
