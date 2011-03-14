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
import node.StartBeanNodeElement;
import org.netbeans.api.visual.widget.Scene;
import testframe.Properties;

/**
 *
 * @author admin
 */
public class StartWidget extends ProgramNodeWidget implements WidgetInfo {

    private StartBeanNodeElement startNode;

    public StartWidget(final Scene scene, StartBeanNodeElement node) {
        super(scene, node, 1, 1);
        this.startNode = node;
        startNode.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                scene.createView().repaint();
                Properties.getProperties().setProperties(startNode.getPropertiesModel());
            }
        });
        node.addPropertyChangeListener("properties", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                properties();
            }
        });
        startNode.addPropertyChangeListener("setProperties", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                scene.createView().repaint();
                Properties.getProperties().setProperties(startNode.getPropertiesModel());
            }
        });
    }

    public StartBeanNodeElement getStartBeanNode() {
        return startNode;
    }

    public StartWidget(Scene scene, StartBeanNodeElement node, int row, int column) {
        super(scene, node, row, column);
        this.startNode = node;
    }

    public String getWidgetName() {
        return startNode.getDisctription();
    }

    public Icon getWidgetIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void properties() {
        Properties.getProperties().setProperties(getBeanNode().getPropertiesModel());
    }

    public StartBeanNodeElement getBeanNodeElement() {
        return startNode;
    }
}
