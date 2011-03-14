/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import node.BlockBeanNodeElement;
import node.ForBeanNodeElement;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.Widget;
import testframe.BeanNodeGraphView;
import testframe.Properties;

/**
 *
 * @author admin
 */
public class ForWidget extends NodeContainerWidget implements WidgetInfo {

    private ForBeanNodeElement forNode;

    public ForWidget(BeanNodeGraphView scene, ForBeanNodeElement node) {
        super(scene, node);
        this.forNode = node;
        forNode.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                ForWidget.this.setToolTipText(WidgetUtils.getToolTipString(forNode.toString()));
            }
        });
        node.addPropertyChangeListener("properties", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                properties();
            }
        });
        BlockBeanNodeElement e = new BlockBeanNodeElement();
        forNode.adddBeanNodeElement(e);
        this.adddNodeWidget(new BlockWidget(scene, e));
    }

    @Override
    protected ProgramNodeWidget createHeaderWidget() {
        ProgramNodeWidget widget = new ProgramNodeWidget(getScene(), node, 1, 1);
        widget.setLabel("For");
//        setBorder(new RoundRectBorder(WidgetUtils.GRID_SPACE, new Color(52, 124, 150)));
        widget.setBorder(BorderFactory.createRoundedBorder(5, 5, null, new Color(52, 124, 150)));
        widget.setOpaque(true);
        widget.setPreferredSize(WidgetUtils.getSize(1, 1));
        widget.getActions().addAction(scene.getConnectAction());
        return widget;
    }

    @Override
    protected Widget createButtomWidget() {
        ProgramNodeWidget widget = new ProgramNodeWidget(getScene(), node, 1, 1);
        widget.setLabel("   END");
        widget.setImage(null);
        widget.setBorder(BorderFactory.createRoundedBorder(5, 5, null, new Color(52, 124, 150)));
//        setBorder(new RoundRectBorder(WidgetUtils.GRID_SPACE, new Color(52, 124, 150)));
//        widget.setBorder(BorderFactory.createBevelBorder(true, new Color(52, 124, 150)));
        widget.setOpaque(true);
        widget.setPreferredSize(WidgetUtils.getSize(1, 1));
        widget.getActions().addAction(scene.getConnectAction());
        return widget;
    }

    /**
     * @return the forNode
     */
    public ForBeanNodeElement getForNode() {
        return forNode;
    }

    public String getWidgetName() {
        return node.getDisctription();
    }

    public Icon getWidgetIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void properties() {
        Properties.getProperties().setProperties(forNode.getPropertiesModel());
    }

    public ForBeanNodeElement getBeanNodeElement() {
        return forNode;
    }
}
