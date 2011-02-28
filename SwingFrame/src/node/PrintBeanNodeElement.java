/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import shape.AbstractBeanNodeElement;
import shape.NodeElement;

/**
 *
 * @author admin
 */
public class PrintBeanNodeElement extends AbstractBeanNodeElement {

    public PrintBeanNodeElement() {
        this.beanInfo.put(disctription, null);
    }

    @Override
    public NodeElement getEditNode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
