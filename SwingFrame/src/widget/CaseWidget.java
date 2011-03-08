/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.awt.Color;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import node.CaseBeanNodeElement;
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
public class CaseWidget extends NodeContainerGroupWidget implements WidgetInfo {

    private CaseBeanNodeElement caseNode;

    public CaseWidget(final BeanNodeGraphView scene, CaseBeanNodeElement node) {
        super(scene, node, 0);
        this.caseNode = node;
        caseNode.addPropertyChangeListener("change", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                scene.createView().repaint();
                headerWidget.setLabel("if(" + caseNode.getBeanValue().get("case") + ")");
                Properties.getProperties().setProperties(caseNode.getPropertiesModel());
                CaseWidget.this.setToolTipText(WidgetUtils.getToolTipString(caseNode.toString()));
            }
        });
        node.addPropertyChangeListener("properties", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                properties();
            }
        });
        this.addGroup("true", "true");
        this.addGroup("false", "false");
        headerButtonChage();
    }

    @Override
    protected ProgramNodeWidget createHeaderWidget() {
        ProgramNodeWidget widget = new ProgramNodeWidget(getScene(), node, 1, Math.max(1, labels.size()));
        headerWidget = widget;
        widget.setLabel("if(" + node.getBeanValue().get("case") + ")");
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

    public String getWidgetName() {
        return getBeanNodeElement().getDisctription();
    }

    public Icon getWidgetIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void properties() {
        Properties.getProperties().setProperties(getBeanNodeElement().getPropertiesModel());
    }

    public CaseBeanNodeElement getBeanNodeElement() {
        return caseNode;
    }

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(CaseBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                CaseBeanNodeElement test = (CaseBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }
}
