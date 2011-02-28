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
public class CaseBeanNodeElement extends AbstractBeanNodeElement {

    protected BeanNodeElement trueNode;
    protected BeanNodeElement falseNode;

    public CaseBeanNodeElement() {
        super();
        this.beanInfo.put("case", String.class);
        this.beanValue.put("case", "true");
        this.beanInfo.put("trueBlock", String.class);
        this.beanValue.put("trueBlock", " ");
        this.beanInfo.put("falseBlock", String.class);
        this.beanValue.put("falseBlock", " ");
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
        String str = "if(" + beanValue.get("case") + ")" + "{" + "\n";
        str = str + beanValue.get("trueBlock") + "\n";
        str = str + "}";
        if (beanValue.get("falseBlock") != null && beanValue.get("falseBlock").toString().length() > 0) {
            str = str + "else{" + "\n";
            str = str + beanValue.get("falseBlock") + "\n";
            str = str + "}";
        }
        return str;
    }

    public void addFalseNode(BeanNodeElement node) {
        this.children.add(node);
        falseNode = node;
    }

    @Override
    public String getDisctription() {
        return "Case";
    }

    public CaseBeanNodeElement getEditNode() {
        return new CaseBeanNodeElement(this);
    }
}
