/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import node.EndBeanNodeElement;
import org.netbeans.api.visual.widget.Scene;
import testframe.Properties;

/**
 *
 * @author admin
 */
public class EndWidget extends ProgramNodeWidget implements WidgetInfo {

    private EndBeanNodeElement startNode;

    public EndWidget(Scene scene, final EndBeanNodeElement node) {
        super(scene, node, 1, 1);
        this.startNode = node;
        node.addPropertyChangeListener("properties", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                properties();
            }
        });
    }

    public EndBeanNodeElement getStartBeanNode() {
        return startNode;
    }

    public EndWidget(Scene scene, EndBeanNodeElement node, int row, int column) {
        super(scene, node, row, column);
        this.startNode = node;
    }

    public String getWidgetName() {
        return node.getDisctription();
    }

    public Icon getWidgetIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void properties() {
        Properties.getProperties().setProperties(getBeanNode().getPropertiesModel());
    }
}
