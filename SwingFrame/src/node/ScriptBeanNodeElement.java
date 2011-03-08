/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import shape.AbstractBeanNodeElement;
import shape.BeanNodeElement;

/**
 *
 * @author admin
 */
public class ScriptBeanNodeElement extends AbstractBeanNodeElement {

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
