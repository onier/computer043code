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

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(PrintBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                PrintBeanNodeElement test = (PrintBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }

    public static void main(String[] args) {
        String str = " System.out.println(aaa);";
        PrintBeanNodeElement e = new PrintBeanNodeElement();
        System.out.println(e.parseElement(str));
    }

    public static PrintBeanNodeElement parseElement(String str) {
        PrintBeanNodeElement e = new PrintBeanNodeElement();
        int s = str.indexOf("(");
        int n = str.lastIndexOf(")");
        str = str.substring(s + 1, n);
        e.beanValue.put("value", str);
        return e;
    }
}
