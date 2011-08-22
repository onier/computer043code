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
        return IconUtils.getLoopIcon();
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

    public static DoWhileBeanNodeElement parseElement(String str) {
        DoWhileBeanNodeElement e = new DoWhileBeanNodeElement();
        int n = str.indexOf("{");
        if (n >= 0) {
            int m = str.lastIndexOf("}");
            e.beanValue.put("block", str.substring(n + 1, m).trim());
            n = str.lastIndexOf("while");
            if (n >= 0) {
                str = str.substring(n + 5, str.length());
                if (str.length() > 2) {
                    str = str.trim();
                    if (str.endsWith(";")) {
                        e.beanValue.put("case", str.substring(1, str.length() - 2).trim());
                    } else {
                        e.beanValue.put("case", str.substring(1, str.length() - 1).trim());
                    }
                }
            }
        }
        return e;
    }

    public static void main(String[] args) {
        String str = "do {" + "\n"
                + "if (true) {" + "\n"
                + "System.out.println(" + "sdfasdf" + ");" + "\n"
                + "}else{" + "\n"
                + "System.out.println(" + "dfsadfasdf" + ");" + "\n"
                + "}" + "\n"
                + "} while (true);";
        DoWhileBeanNodeElement e = new DoWhileBeanNodeElement();
        System.out.println(e.parseElement(str));
    }
}
