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

    public ScriptBeanNodeElement getBeanNodeElement() {
        return scriptNode;
    }

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(ScriptBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                ScriptBeanNodeElement test = (ScriptBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }
}
