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
import node.GeneralBeanNodeElement;
import testframe.BeanNodeGraphView;
import testframe.Properties;

/**
 *
 * @author admin
 */
public class GeneralWidget extends ProgramNodeWidget implements WidgetInfo {

    private GeneralBeanNodeElement parameterNode;

    public GeneralWidget(BeanNodeGraphView scene, GeneralBeanNodeElement node) {
        super(scene, node);
        this.parameterNode = node;
        node.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                GeneralWidget.this.setToolTipText(WidgetUtils.getToolTipString(parameterNode.toString()));
            }
        });
        node.addPropertyChangeListener("properties", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                properties();
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

    public GeneralBeanNodeElement getBeanNodeElement() {
        return parameterNode;
    }

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(GeneralBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                GeneralBeanNodeElement test = (GeneralBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }
}
