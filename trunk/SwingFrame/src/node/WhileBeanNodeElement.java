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
public class WhileBeanNodeElement extends AbstractBeanNodeElement {

    public WhileBeanNodeElement(WhileBeanNodeElement e) {
        super(e);
    }

    public WhileBeanNodeElement(BeanNodeElement parent, List<BeanNodeElement> children, TreeMap<String, Class> beanInfo, TreeMap<String, Object> beanValue, ImageIcon icon, String disctription) {
        super(parent, children, beanInfo, beanValue, icon, disctription);
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

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(WhileBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                WhileBeanNodeElement test = (WhileBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }

    public static WhileBeanNodeElement parseElement(String str) {
        WhileBeanNodeElement e = new WhileBeanNodeElement();
        int n = str.indexOf("{");
        if (n >= 0) {
            String c = str.substring(0, n);
            int a = c.indexOf("(");
            if (a >= 0) {
                int b = c.lastIndexOf(")");
                if (b >= 0) {
                    e.beanValue.put("case", c.substring(a + 1, b).trim());
                }
            }
            int m = str.lastIndexOf("}");
            e.beanValue.put("block", str.substring(n + 1, m).trim());
        }
        return e;
    }

    public static void main(String[] args) {
        String str = "while (true) {" + "\n"
                + "if (true) {" + "\n"
                + "System.out.println(1);" + "\n"
                + "} else {" + "\n"
                + "System.out.println(2);" + "\n"
                + "}" + "\n"
                + "}";
        WhileBeanNodeElement e = new WhileBeanNodeElement();
        System.out.println(e.parseElement(str));
    }
}

