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
import node.WhileBeanNodeElement;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.Widget;
import testframe.BeanNodeGraphView;
import testframe.Properties;

/**
 *
 * @author admin
 */
public class WhileWidget extends NodeContainerWidget implements WidgetInfo {

    private WhileBeanNodeElement whileNode;

    public WhileWidget(BeanNodeGraphView scene, WhileBeanNodeElement node) {
        super(scene, node);
        this.whileNode = node;
        whileNode.addPropertyChangeListener("case", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                header.setLabel("while(" + evt.getNewValue().toString() + ")");
            }
        });
        whileNode.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                WhileWidget.this.setToolTipText(WidgetUtils.getToolTipString(whileNode.toString()));
            }
        });
        BlockBeanNodeElement e = new BlockBeanNodeElement();
        whileNode.addBeanNode(e);
        this.adddNodeWidget(new BlockWidget(scene, e));
//        ProgramNodeWidget widget = new ProgramNodeWidget(scene, node, 1, 1);
//        widget.setImage(null);
//        this.adddNodeWidget(widget);
    }

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

    protected ProgramNodeWidget createHeaderWidget() {
        ProgramNodeWidget widget = new ProgramNodeWidget(getScene(), node, 1, 1);
        widget.setLabel("while");
//        setBorder(new RoundRectBorder(WidgetUtils.GRID_SPACE, new Color(52, 124, 150)));
        widget.setBorder(BorderFactory.createRoundedBorder(5, 5, null, new Color(52, 124, 150)));
        widget.setOpaque(true);
        widget.setPreferredSize(WidgetUtils.getSize(1, 1));
        widget.getActions().addAction(scene.getConnectAction());
        return widget;
    }

    /**
     * @return the doWhileNode
     */
    public WhileBeanNodeElement getWhileNode() {
        return whileNode;
    }

    public String getWidgetName() {
        return node.getDisctription();
    }

    public Icon getWidgetIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void properties() {
        Properties.getProperties().setProperties(whileNode.getPropertiesModel());
    }
}
