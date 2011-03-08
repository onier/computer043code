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
import node.CheckPointBeanNodeElement;
import testframe.BeanNodeGraphView;
import testframe.Properties;

/**
 *
 * @author admin
 */
public class CheckPointWidget extends ProgramNodeWidget implements WidgetInfo {

    private CheckPointBeanNodeElement parameterNode;

    public CheckPointWidget(BeanNodeGraphView scene, CheckPointBeanNodeElement node) {
        super(scene, node);
        this.parameterNode = node;
        node.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                CheckPointWidget.this.setToolTipText(WidgetUtils.getToolTipString(parameterNode.toString()));
            }
        });
        node.addPropertyChangeListener("properties", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                CheckPointWidget.this.properties();
            }
        });
    }

    public String getWidgetName() {
        return "Parameter";
    }

    public Icon getWidgetIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void properties() {
        Properties.getProperties().setProperties(getBeanNode().getPropertiesModel());
    }

    public CheckPointBeanNodeElement getBeanNodeElement() {
        return parameterNode;
    }

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(CheckPointBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                CheckPointBeanNodeElement test = (CheckPointBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }
}
