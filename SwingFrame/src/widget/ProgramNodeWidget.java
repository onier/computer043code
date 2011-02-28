/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.LayoutFactory.SerialAlignment;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import shape.BeanNodeElement;
import testframe.BeanNodeGraphView;

/**
 *
 * @author admin
 */
public class ProgramNodeWidget extends IconNodeWidget {

    protected List<ProgramNodeWidget> nodeWidget = new ArrayList<ProgramNodeWidget>();
    protected int rowCount = 1, columnCount = 1;
    protected BeanNodeElement node;
    private BeanNodeGraphView scene;

    public ProgramNodeWidget(BeanNodeGraphView scene, int row, int column) {
        super(scene);
        this.scene = scene;
        rowCount = row;
        columnCount = column;
        setPreferredSize(WidgetUtils.getSize(this));
//        setBorder(new RoundRectBorder(WidgetUtils.GRID_SPACE, new Color(52, 124, 150)));
        setLayout(LayoutFactory.createVerticalFlowLayout(SerialAlignment.RIGHT_BOTTOM, 2 * WidgetUtils.SPACE));
    }

    public void adddNodeWidget(ProgramNodeWidget widget) {
        this.addChild(widget);
        nodeWidget.add(widget);
        this.revalidate();
//        this.getScene().addChild(widget);
    }

    public ProgramNodeWidget(Scene scene, BeanNodeElement node) {
        this(scene, node, 1, 1);
    }

    public ProgramNodeWidget(Scene scene, BeanNodeElement node, int row, int column) {
        super(scene, IconNodeWidget.TextOrientation.RIGHT_CENTER);
        this.setLabel(node.getDisctription());
        this.setImage(node.getIcon().getImage());
        rowCount = row;
        columnCount = column;
        this.node = node;
        this.setPreferredSize(WidgetUtils.getSize(this));
        this.setBorder(BorderFactory.createRoundedBorder(5, 5, Color.yellow, Color.gray));
//        setLayout(LayoutFactory.createVerticalFlowLayout(SerialAlignment.LEFT_TOP, 2 * WidgetUtils.SPACE));
    }

    public BeanNodeElement getBeanNode() {
        return node;
    }

    /**
     * @return the rowCount
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * @param rowCount the rowCount to set
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        if (rowCount < 1) {
            this.setPreferredSize(new Dimension());
        } else {
            this.setPreferredSize(WidgetUtils.getSize(this));
        }
    }

    /**
     * @return the columnCount
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * @param columnCount the columnCount to set
     */
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        if (rowCount < 1) {
            this.setPreferredSize(new Dimension());
        } else {
            this.setPreferredSize(WidgetUtils.getSize(this));
        }
    }
}
