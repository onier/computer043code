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
import node.PrintBeanNodeElement;
import testframe.BeanNodeGraphView;
import testframe.Properties;

/**
 *
 * @author admin
 */
public class PrintWidget extends ProgramNodeWidget implements WidgetInfo {

    private PrintBeanNodeElement printNode;

    public PrintWidget(BeanNodeGraphView scene, PrintBeanNodeElement node) {
        super(scene, node);
        this.printNode = node;
        node.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                PrintWidget.this.setToolTipText(WidgetUtils.getToolTipString(printNode.toString()));
            }
        });
        node.addPropertyChangeListener("properties", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                properties();
            }
        });
    }

    public String getWidgetName() {
        return "Print";
    }

    public Icon getWidgetIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void properties() {
        Properties.getProperties().setProperties(getBeanNode().getPropertiesModel());
    }

    public PrintBeanNodeElement getBeanNodeElement() {
        return printNode;
    }

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(PrintBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                PrintBeanNodeElement test = (PrintBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }
}
