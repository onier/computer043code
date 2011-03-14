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
import node.BeanNodeElementUtils.ParseElementException;
import shape.AbstractBeanNodeElement;
import shape.BeanNodeElement;
import widget.WidgetUtils;

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
        return WidgetUtils.LOOP_IMAGE;
    }

    @Override
    public String toString() {
        String str = "if(" + getBeanValue().get("case") + ")" + "{" + "\n";
        str = str + getBeanValue().get("trueBlock") + "\n";
        str = str + "}";
        if (getBeanValue().get("falseBlock") != null && getBeanValue().get("falseBlock").toString().length() > 0) {
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

    private void parseCase(String str, CaseBeanNodeElement e) {
    }

    private void parseTrueBlock(String str, CaseBeanNodeElement e) {
    }

    private void parseFalseBlock(String str, CaseBeanNodeElement e) {
    }

    public BeanNodeElement parseElement(String str) {
        CaseBeanNodeElement e = new CaseBeanNodeElement();
        int a = str.indexOf("if");
        int b = str.indexOf("else");
        if (a < 0 || a > str.length()) {
            throw new ParseElementException(e, "if is not find");
        }
        if(b>=0){

        }
//        int start = str.indexOf("if(");
//        String str1 = str.substring(start + 3, str.length());
//        int n = str1.indexOf(")");
//        str1 = str1.substring(0, n);
//        beanValue.put(CASE, str1);
//        str1 = str1.substring(n + 1, str1.length());
//        if (str1.startsWith("{")) {
//            str1 = str1.substring(1, str1.length());
//            n = str1.indexOf("}");
//        }
        return e;
    }

    public static void main(String[] args) {
        String str = "0if(true)";
        int sr = str.indexOf("if");
        String str1 = str.substring(sr + 3, str.length());
        int st = str1.indexOf(")");
        str1 = str1.substring(0, st);
        System.out.println(str1);
    }
}
