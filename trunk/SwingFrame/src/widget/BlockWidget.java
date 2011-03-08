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
import node.BlockBeanNodeElement;
import org.netbeans.api.visual.widget.Scene;
import testframe.Properties;

/**
 *
 * @author admin
 */
public class BlockWidget extends ProgramNodeWidget implements WidgetInfo {

    private BlockBeanNodeElement block;

    public BlockWidget(Scene scene, BlockBeanNodeElement node) {
        super(scene, node, 1, 1);
        this.block = node;
        block.addPropertyChangeListener("code", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                BlockWidget.this.setToolTipText(WidgetUtils.getToolTipString(evt.getNewValue().toString()));
            }
        });
        node.addPropertyChangeListener("properties", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                properties();
            }
        });
    }

    public BlockBeanNodeElement getBeanNodeElement() {
        return block;
    }

    public BlockWidget(Scene scene, BlockBeanNodeElement node, int row, int column) {
        super(scene, node, row, column);
        this.block = node;
    }

    public String getWidgetName() {
        return block.getDisctription();
    }

    public Icon getWidgetIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void properties() {
        Properties.getProperties().setProperties(getBeanNode().getPropertiesModel());
    }

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(BlockBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                BlockBeanNodeElement test = (BlockBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }
}
