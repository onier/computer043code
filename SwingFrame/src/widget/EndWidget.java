/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
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

    public EndBeanNodeElement getBeanNodeElement() {
        return startNode;
    }

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(EndBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                EndBeanNodeElement test = (EndBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }
}
