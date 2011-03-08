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

/**
 *
 * @author admin
 */
public class ScriptBeanNodeElement extends AbstractBeanNodeElement {

    public ScriptBeanNodeElement(BeanNodeElement parent, List<BeanNodeElement> children, TreeMap<String, Class> beanInfo, TreeMap<String, Object> beanValue, ImageIcon icon, String disctription) {
        super(parent, children, beanInfo, beanValue, icon, disctription);
    }

    /**
     * 脚本名称
     */
    public ScriptBeanNodeElement() {
        this.beanInfo.put("parameter", String.class);
        this.beanValue.put("parameter", "");
        disctription = "Script";
    }

    public ScriptBeanNodeElement(ScriptBeanNodeElement e) {
        super(e);
    }

    @Override
    public String toString() {
        return getBeanValue().get("parameter").toString() + "\n";
    }

    public BeanNodeElement getEditNode() {
        return new ScriptBeanNodeElement(this);
    }
}
