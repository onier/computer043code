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
public class CaseBeanNodeElement extends AbstractBeanNodeElement {

    protected BeanNodeElement trueNode;
    protected BeanNodeElement falseNode;
    public static final String CASE = "case";
    public static final String TRUE_BLOCK = "trueBlock";
    public static final String FALSE_BLOCK = "falseBlock";

    public CaseBeanNodeElement(BeanNodeElement parent, List<BeanNodeElement> children, TreeMap<String, Class> beanInfo, TreeMap<String, Object> beanValue, ImageIcon icon, String disctription) {
        super(parent, children, beanInfo, beanValue, icon, disctription);
    }

    public CaseBeanNodeElement() {
        super();
        this.beanInfo.put("case", String.class);
        this.beanValue.put("case", "true");
        this.beanInfo.put("trueBlock", String.class);
        this.beanValue.put("trueBlock", " ");
        this.beanInfo.put("falseBlock", String.class);
        this.beanValue.put("falseBlock", " ");
        this.disctription = "Case";
    }

    public CaseBeanNodeElement(CaseBeanNodeElement node) {
        super(node);
    }

    @Override
    public ImageIcon getIcon() {
        return IconUtils.getLoopIcon();
    }

    @Override
    public String toString() {
        String str = "if(" + getBeanValue().get("case") + ")" + "{" + "\n";
        str = str + getBeanValue().get("trueBlock") + "\n";
        str = str + "}";
        if (getBeanValue().get("falseBlock") != null && getBeanValue().get("falseBlock").toString().trim().length() > 0) {
            str = str + "else{" + "\n";
            str = str + getBeanValue().get("falseBlock") + "\n";
            str = str + "}";
        }
        return str + "\n";
    }

    public void addFalseNode(BeanNodeElement node) {
        this.getChildren().add(node);
        falseNode = node;
    }

    public CaseBeanNodeElement getEditNode() {
        return new CaseBeanNodeElement(this);
    }

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(CaseBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                CaseBeanNodeElement test = (CaseBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }

    private static void parseTrueBlock(String str, CaseBeanNodeElement e) {
        int a = str.indexOf("{");
        if (a >= 0) {
            String c = str.substring(0, a);
            int n = c.indexOf("(");
            if (n >= 0) {
                int m = c.lastIndexOf(")");
                if (m >= 0) {
                    e.beanValue.put("case", c.substring(n + 1, m).trim());
                }
            }
            int b = str.lastIndexOf("}");
            if (b >= 0) {
                e.beanValue.put("trueBlock", str.substring(a + 1, b).trim());
            }
        }
    }

    private static void parseFalseBlock(String str, CaseBeanNodeElement e) {
        int a = str.indexOf("{");
        if (a >= 0) {
            int b = str.lastIndexOf("}");
            if (b >= 0) {
                e.beanValue.put("falseBlock", str.substring(a + 1, b).trim());
            }
        }
    }

    public static CaseBeanNodeElement parseElement(String str) {
        CaseBeanNodeElement e = new CaseBeanNodeElement();
        int n = str.indexOf("_e_l_s_e");
        if (n >= 0) {
            parseTrueBlock(str.substring(0, n), e);
            parseFalseBlock(str.substring(n + 8, str.length()), e);
        } else {
            parseTrueBlock(str.trim(), e);
        }
        return e;
    }

    public static void main(String[] args) {
        String str = "if (true) {" + "\n"
                + "if (false) {" + "\n"
                + "System.out.println(a);" + "\n"
                + "}" + "\n"
                + "System.out.println(b);" + "\n"
                + "} _e_l_s_e {" + "\n"
                + "System.out.println(c);" + "\n";
        CaseBeanNodeElement e = new CaseBeanNodeElement();
        System.out.println(e.parseElement(str));
    }
}
