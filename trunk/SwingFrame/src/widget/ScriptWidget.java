/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import node.ScriptBeanNodeElement;
import testframe.BeanNodeGraphView;
import testframe.Properties;

/**
 *
 * @author admin
 */
public class ScriptWidget extends ProgramNodeWidget implements WidgetInfo {

    private ScriptBeanNodeElement scriptNode;

    public ScriptWidget(BeanNodeGraphView scene, ScriptBeanNodeElement node) {
        super(scene, node);
        scriptNode = node;
        scriptNode.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                ScriptWidget.this.setToolTipText(WidgetUtils.getToolTipString(scriptNode.toString()));
            }
        });
        node.addPropertyChangeListener("properties", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                properties();
            }
        });
    }

    public String getWidgetName() {
        return "Script";
    }

    public Icon getWidgetIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void properties() {
        Properties.getProperties().setProperties(getBeanNode().getPropertiesModel());
    }
}
