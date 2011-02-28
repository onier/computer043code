/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import node.BlockBeanNodeElement;
import node.DoWhileBeanNodeElement;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.Widget;
import testframe.BeanNodeGraphView;
import testframe.Properties;

/**
 *
 * @author admin
 */
public class DoWhileWidget extends NodeContainerWidget implements WidgetInfo {

    private DoWhileBeanNodeElement doWhileNode;
    private ProgramNodeWidget buttom;

    public DoWhileWidget(final BeanNodeGraphView scene, DoWhileBeanNodeElement node) {
        super(scene, node);
        this.doWhileNode = node;
        doWhileNode.addPropertyChangeListener("case", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                buttom.setLabel("     while" + "(" + evt.getNewValue().toString() + ")");
                DoWhileWidget.this.setToolTipText(WidgetUtils.getToolTipString(doWhileNode.toString()));
                DoWhileWidget.this.setVisible(false);
                DoWhileWidget.this.setVisible(true);
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        scene.getView().repaint();
                    }
                });

            }
        });
        doWhileNode.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                DoWhileWidget.this.setToolTipText(WidgetUtils.getToolTipString(doWhileNode.toString()));
            }
        });
        BlockWidget widget = new BlockWidget(scene, new BlockBeanNodeElement(), 1, 1);
        this.adddNodeWidget(widget);
    }

    @Override
    protected ProgramNodeWidget createHeaderWidget() {
        ProgramNodeWidget widget = new ProgramNodeWidget(getScene(), node, 1, 1);
        widget.setLabel("do");
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
        buttom = widget;
        widget.setLabel("     while" + "(" + node.getBeanValue().get("case") + ")");
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
     * @return the doWhileNode
     */
    public DoWhileBeanNodeElement getDoWhileNode() {
        return doWhileNode;
    }

    public String getWidgetName() {
        return doWhileNode.getDisctription();
    }

    public Icon getWidgetIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void properties() {
        Properties.getProperties().setProperties(doWhileNode.getPropertiesModel());
    }
}
