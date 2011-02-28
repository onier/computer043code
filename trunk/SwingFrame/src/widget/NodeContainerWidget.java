/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction.State;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseEvent;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;
import shape.BeanNodeElement;
import testframe.BeanNodeGraphView;
import testframe.LocaleConnectionWidget;
import testframe.WidgetAdapter;

/**
 *
 * @author admin
 */
public class NodeContainerWidget extends Widget {

    protected BeanNodeElement node;
    protected List<Widget> groupWidget = new ArrayList<Widget>();
    private Widget middle;
    protected ProgramNodeWidget header;
    protected BeanNodeGraphView scene;

    public NodeContainerWidget(final BeanNodeGraphView scene, BeanNodeElement node) {
        super(scene);
        this.node = node;
        this.scene = scene;
        setLayout(LayoutFactory.createVerticalFlowLayout());
//        setBorder(new RoundRectBorder(WidgetUtils.GRID_SPACE, new Color(52, 124, 150)));
        this.setBorder(BorderFactory.createRoundedBorder(5, 5, Color.yellow, Color.gray));
        header = createHeaderWidget();
        addChild(header);

        Widget con = new Widget(scene);
        con.setPreferredSize(new Dimension(WidgetUtils.getSize(1, 1).width, 2 * WidgetUtils.SPACE));
        this.addChild(con);

        groupWidget.add(con);

        middle = createMiddleWidget();
        addChild(middle);

        final Widget switchButton = createButtomWidget();
        addChild(switchButton);

        middle.setVisible(true);
        switchButton.getActions().addAction(new WidgetAdapter() {

            @Override
            public State mouseClicked(Widget widget, WidgetMouseEvent wme) {
                shwoWidgetList(middle.isVisible());
                middle.setVisible(!middle.isVisible());
                revalidate();
                scene.invokeLayout();
                return super.mouseClicked(widget, wme);
            }
        });
        header.getActions().addAction(new WidgetAdapter() {

            @Override
            public State mouseClicked(Widget widget, WidgetMouseEvent wme) {
                scene.revalidate();
                scene.repaint();
                return super.mouseClicked(widget, wme);
            }
        });
        getActions().addAction(ActionFactory.createMoveAction());
        setOpaque(true);
        shwoWidgetList(false);
    }

    private void shwoWidgetList(boolean b) {
        for (int i = 0; i < groupWidget.size(); i++) {
            groupWidget.get(i).setVisible(b);
        }
    }

    protected ProgramNodeWidget createHeaderWidget() {
        ProgramNodeWidget widget = new ProgramNodeWidget(getScene(), node, 1, 1);
        widget.setLabel("Header");
//        setBorder(new RoundRectBorder(WidgetUtils.GRID_SPACE, new Color(52, 124, 150)));
        widget.setBorder(BorderFactory.createRoundedBorder(5, 5, null, new Color(52, 124, 150)));
        widget.setOpaque(true);
        widget.setPreferredSize(WidgetUtils.getSize(1, 1));
        widget.getActions().addAction(scene.getConnectAction());
        return widget;
    }

    private Widget createMiddleWidget() {
        Widget card1 = new LabelWidget(this.getScene(), null);
        card1.setOpaque(true);
        card1.setBorder(BorderFactory.createBevelBorder(false, new Color(52, 124, 150)));
        card1.setPreferredSize(new Dimension(WidgetUtils.getSize(1, 1).width, 2 * WidgetUtils.SPACE));
        return card1;
    }

    protected Widget createButtomWidget() {
        ProgramNodeWidget widget = new ProgramNodeWidget(getScene(), node, 1, 1);
        widget.setLabel("Buttom");
        widget.setBorder(BorderFactory.createRoundedBorder(5, 5, null, new Color(52, 124, 150)));
//        setBorder(new RoundRectBorder(WidgetUtils.GRID_SPACE, new Color(52, 124, 150)));
//        widget.setBorder(BorderFactory.createBevelBorder(true, new Color(52, 124, 150)));
        widget.setOpaque(true);
        widget.setPreferredSize(WidgetUtils.getSize(1, 1));
        widget.getActions().addAction(scene.getConnectAction());
        return widget;
    }

    public void adddNodeWidget(ProgramNodeWidget widget) {
        widget.setVisible(!middle.isVisible());
        this.groupWidget.add(widget);
        LocaleConnectionWidget con = new LocaleConnectionWidget(scene);
        con.setPreferredSize(new Dimension(WidgetUtils.getSize(1, 1).width, 2 * WidgetUtils.SPACE));
        node.children().add(widget.getBeanNode());
        con.setVisible(!middle.isVisible());
        groupWidget.add(con);
        this.addChild(getChildren().size() - 3, con);
        this.addChild(getChildren().size() - 3, widget);
    }
}
