/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import javax.swing.ImageIcon;
import shape.AbstractBeanNodeElement;
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public class EndBeanNodeElement extends AbstractBeanNodeElement {

    public EndBeanNodeElement(EndBeanNodeElement e) {
        super(e);
    }

    public EndBeanNodeElement() {
    }

    @Override
    public ImageIcon getIcon() {
        return WidgetUtils.END_IMAGE;
    }

    @Override
    public String toString() {
        return "//End" + "\n";
    }

    @Override
    public String getDisctription() {
        return "End";
    }

    public EndBeanNodeElement getEditNode() {
        return new EndBeanNodeElement(this);
    }
}
