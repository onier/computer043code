/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.Icon;
import node.SwitchBeanNodeElement;
import org.netbeans.api.visual.action.WidgetAction.State;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseEvent;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.Widget;
import testframe.BeanNodeGraphView;
import testframe.Properties;
import testframe.WidgetAdapter;

/**
 *
 * @author admin
 */
public class SwitchWidget extends NodeContainerGroupWidget implements WidgetInfo {

    private SwitchBeanNodeElement switchNode;

    public SwitchWidget(final BeanNodeGraphView scene, SwitchBeanNodeElement node, int column) {
        super(scene, node, column);
        switchNode = node;
//        switchNode.addPropertyChangeListener("switchLabel", new PropertyChangeListener() {
//
//            public void propertyChange(PropertyChangeEvent evt) {
//                addGroup(evt.getNewValue().toString());
//            }
//        });
        switchNode.addPropertyChangeListener("expression", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                headerWidget.setLabel("Switch(" + evt.getNewValue().toString() + ")");
            }
        });
        switchNode.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                scene.createView().repaint();
                Properties.getProperties().setProperties(switchNode.getPropertiesModel());
                SwitchWidget.this.addGroups(switchNode.getLabelsMap());
                SwitchWidget.this.setToolTipText(WidgetUtils.getToolTipString(switchNode.toString()));
            }
        });
        node.addPropertyChangeListener("properties", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                properties();
            }
        });
    }

    @Override
    protected ProgramNodeWidget createHeaderWidget() {
        ProgramNodeWidget widget = new ProgramNodeWidget(getScene(), node, 1, Math.max(1, labels.size()));
        headerWidget = widget;
        widget.setLabel("Switch(" + node.getBeanValue().get("expression") + ")");
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

    public SwitchBeanNodeElement getBeanNodeElement() {
        return switchNode;
    }

    public void addGroups(Map<String, String> beanValue) {
        middle.setVisible(true);
        group.setVisible(false);
        Set<String> set = beanValue.keySet();
        String[] values = new String[set.size()];
        ArrayList<String> Int = new ArrayList<String>();
        set.toArray(values);
        for (int i = 0; i < labels.size(); i++) {
            String string = labels.get(i);
            if (!set.contains(string)) {
                this.removeGroup(string);
                Int.add(string);
            }
        }
        for (int i = 0; i < Int.size(); i++) {
            labels.remove(Int.get(i));
        }
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (!labels.contains(str)) {
                addGroup(str, beanValue.get(str));
//                if (widget != null) {
//                    BlockBeanNodeElement node1 = new BlockBeanNodeElement();
//                    node1.getBeanValue().put("code", beanValue.get(str));
//                    widget.addWidget(node1);
//                    widget.revalidate();
//                }
            }
        }
        headerButtonChage();
//        this.repaint();
//        System.out.println("sdfsdfafsf");
    }

    @Override
    protected Widget createButtomWidget() {
        ProgramNodeWidget widget = new ProgramNodeWidget(getScene(), node, 1, Math.max(1, labels.size()));
        widget.setLabel("   END");
        buttomWidget = widget;
        widget.setImage(null);
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
                scene.invokeLayout();
                revalidate();
                return super.mouseClicked(widget, wme);
            }
        });
        return widget;
    }

    /**
     * @return the switchNode
     */
    public SwitchBeanNodeElement getSwitchNode() {
        return switchNode;
    }

    public String getWidgetName() {
        return node.getDisctription();
    }

    public Icon getWidgetIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void properties() {
        Properties.getProperties().setProperties(switchNode.getPropertiesModel());
    }
}
