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
public class CaseBeanNodeElement extends AbstractBeanNodeElement {

    protected BeanNodeElement trueNode;
    protected BeanNodeElement falseNode;

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
}
