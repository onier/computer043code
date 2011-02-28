/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction.State;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseEvent;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;
import shape.BeanNodeElement;
import testframe.BeanNodeGraphView;
import testframe.WidgetAdapter;
import widget.NodeContainerGroup.LocaleNodeContainerWidget;

/**
 *
 * @author admin
 */
public class NodeContainerGroupWidget extends Widget {

    protected ArrayList<String> labels = new ArrayList<String>();
    protected NodeContainerGroup group;
    protected BeanNodeElement node;
    protected BeanNodeGraphView scene;
    protected Widget middle;
    protected int column;
    protected ProgramNodeWidget headerWidget, buttomWidget;

    public NodeContainerGroupWidget(BeanNodeGraphView scene, BeanNodeElement node, int column) {
        super(scene);
        this.scene = scene;
        this.column = column;
        this.node = node;
        group = new NodeContainerGroup(scene, node, column);
        setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.LEFT_TOP, 3));
        middle = this.createMiddleWidget();
        addChild(createHeaderWidget());
        addChild(middle);
        addChild(group);
        group.setVisible(false);
        middle.setVisible(true);
        addChild(createButtomWidget());
//        this.setBorder(new RoundRectBorder(3, new Color(52, 124, 150)));
        setBorder(BorderFactory.createRoundedBorder(5, 5, Color.yellow, new Color(52, 124, 150)));
        getActions().addAction(ActionFactory.createMoveAction());
    }

    public LocaleNodeContainerWidget addGroup(String label, String value) {
        if (!labels.contains(label)) {
            labels.add(label);
            return group.addNodeContainerWidget(label, value);
        }
        return null;
    }

    public void removeGroup(String label) {
//        labels.remove(label);
        group.removeNodeContainerWidget(label);
        headerButtonChage();
    }

    protected void headerButtonChage() {
        Dimension size = WidgetUtils.getSize(1, group.getColumnCount());
        headerWidget.setPreferredSize(size);
        buttomWidget.setPreferredSize(size);
        middle.setPreferredSize(new Dimension(size.width, middle.getPreferredSize().height));
        this.revalidate();
//        this.setPreferredSize(new Dimension(size.width + 2, WidgetUtils.getSize(3, group.getColumnCount()).height));
    }

    protected ProgramNodeWidget createHeaderWidget() {
        ProgramNodeWidget widget = new ProgramNodeWidget(getScene(), node, 1, column);
        headerWidget = widget;
        widget.setLabel("Header");
//        setBorder(new RoundRectBorder(WidgetUtils.GRID_SPACE, new Color(52, 124, 150)));
        widget.setBorder(BorderFactory.createRoundedBorder(5, 5, null, new Color(52, 124, 150)));
        widget.setOpaque(true);
        widget.getActions().addAction(scene.getConnectAction());
        widget.getActions().addAction(new WidgetAdapter() {

            @Override
            public State mouseClicked(Widget widget, WidgetMouseEvent wme) {
                scene.revalidate();
                scene.repaint();
                return super.mouseClicked(widget, wme);
            }
        });
        return widget;
    }

    protected Widget createMiddleWidget() {
        Widget card1 = new LabelWidget(this.getScene(), null);
        card1.setOpaque(true);
        card1.setBorder(BorderFactory.createBevelBorder(false, new Color(52, 124, 150)));
        card1.setPreferredSize(new Dimension(WidgetUtils.getSize(1, Math.max(1, column)).width, 2 * WidgetUtils.SPACE));
        return card1;
    }

    protected Widget createButtomWidget() {
        ProgramNodeWidget widget = new ProgramNodeWidget(getScene(), node, 1, column);
        widget.setLabel("Buttom");
        buttomWidget = widget;
        widget.setBorder(BorderFactory.createRoundedBorder(5, 5, null, new Color(52, 124, 150)));
//        setBorder(new RoundRectBorder(WidgetUtils.GRID_SPACE, new Color(52, 124, 150)));
//        widget.setBorder(BorderFactory.createBevelBorder(true, new Color(52, 124, 150)));
        widget.setOpaque(true);
        widget.getActions().addAction(scene.getConnectAction());
        widget.getActions().addAction(new WidgetAdapter() {

            @Override
            public State mouseClicked(Widget widget, WidgetMouseEvent wme) {
                middle.setVisible(!middle.isVisible());
                group.setVisible(!group.isVisible());
                revalidate();
                scene.invokeLayout();
                return super.mouseClicked(widget, wme);
            }
        });
        return widget;
    }
}
