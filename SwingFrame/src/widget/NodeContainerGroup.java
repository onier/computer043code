/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import node.BlockBeanNodeElement;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.LayoutFactory.SerialAlignment;
import org.netbeans.api.visual.widget.Widget;
import shape.BeanNodeElement;
import testframe.BeanNodeGraphView;
import testframe.LocaleConnectionWidget;

/**
 *
 * @author admin
 */
public class NodeContainerGroup extends Widget {

    protected HashMap<String, LocaleNodeContainerWidget> map = new HashMap<String, LocaleNodeContainerWidget>();
//    protected ArrayList<LocaleNodeContainerWidget> list = new ArrayList<LocaleNodeContainerWidget>();
    private int columnCount = 0;
    protected BeanNodeElement node;
    private BeanNodeGraphView scene;

    public NodeContainerGroup(BeanNodeGraphView scene, BeanNodeElement node, int column) {
        super(scene);
        this.scene = scene;
//        this.columnCount = column;
        this.node = node;
//        setBorder(new RoundRectBorder(WidgetUtils.GRID_SPACE, new Color(52, 124, 150)));
        setLayout(LayoutFactory.createHorizontalFlowLayout(SerialAlignment.LEFT_TOP, 2 * WidgetUtils.SPACE));
        for (int i = 0; i < column; i++) {
//            LocaleNodeContainerWidget w = new LocaleNodeContainerWidget(scene);
//            list.add(w);
//            this.addChild(w);
            addNodeContainerWidget(i + "", "");
        }
        if (getRowCount() > 0) {
            Dimension size = WidgetUtils.getSize(getRowCount(), Math.max(1, columnCount));
            size.height = size.height + 3 * WidgetUtils.SPACE;
            setPreferredSize(size);
        } else {
            setPreferredSize(new Dimension(WidgetUtils.getSize(1, Math.max(1, column)).width, 2 * WidgetUtils.SPACE));
        }
    }

    public void removeNodeContainerWidget(String text) {
        if (map.containsKey(text)) {
            this.removeChild(map.get(text));
            map.remove(text);
            columnCount--;
        }
        Dimension size = WidgetUtils.getSize(Math.max(getRowCount(), 1), columnCount);
        size.height = size.height + 3 * WidgetUtils.SPACE;
        setPreferredSize(size);
    }

    public LocaleNodeContainerWidget addNodeContainerWidget(String text, String value) {
        LocaleNodeContainerWidget w = new LocaleNodeContainerWidget(scene, text, value);
        map.put(text, w);
        this.addChild(w);
        columnCount++;
        Dimension size = WidgetUtils.getSize(Math.max(getRowCount(), 1), columnCount);
        size.height = size.height + 3 * WidgetUtils.SPACE;
        setPreferredSize(size);
//        this.revalidate();
        return w;
    }

    /**
     * @param rowCount the rowCount to set
     */
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
    }

    /**
     * @return the rowCount
     */
    public int getRowCount() {
        int row = 0;
        for (LocaleNodeContainerWidget widget : map.values()) {
            if (widget != null) {
                row = Math.max(row, widget.getList().size());
            }
        }
        return row;
    }

    protected class LocaleNodeContainerWidget
            extends Widget {

        protected String text;
        protected ArrayList<ProgramNodeWidget> list = new ArrayList<ProgramNodeWidget>();

        LocaleNodeContainerWidget(BeanNodeGraphView scene, String text, String value) {
            super(scene);
            this.text = text;
            BlockBeanNodeElement node1 = new BlockBeanNodeElement();
            node1.getBeanValue().put("code", value);
            setLayout(LayoutFactory.createVerticalFlowLayout());
            addWidget(node1);
//            this.addWidget(new AbstractBeanNodeElement() {
//
//                @Override
//                public String getDisctription() {
//                    return "A";
//                }
//            });
//            this.addWidget(new AbstractBeanNodeElement() {
//
//                @Override
//                public String getDisctription() {
//                    return "B";
//                }
//            });
//            this.addWidget(new AbstractBeanNodeElement() {
//
//                @Override
//                public String getDisctription() {
//                    return "C";
//                }
//            });
//            this.addWidget(new AbstractBeanNodeElement() {
//
//                @Override
//                public String getDisctription() {
//                    return "D";
//                }
//            });
            Widget con = new Widget(scene);
            con.setPreferredSize(new Dimension(WidgetUtils.getSize(1, 1).width, 2 * WidgetUtils.SPACE));
            this.addChild(con);
        }

        public void addWidget(BeanNodeElement e) {
            LocaleConnectionWidget con = new LocaleConnectionWidget(scene);
            con.setPreferredSize(new Dimension(WidgetUtils.getSize(1, 1).width - 2, 2 * WidgetUtils.SPACE));
            this.addChild(con);
            ProgramNodeWidget widget = new ProgramNodeWidget(scene, e);
            widget.setLabel(text);
            widget.setBorder(BorderFactory.createRoundedBorder(5, 5, Color.yellow, new Color(52, 124, 150)));
            getList().add(widget);
            this.addChild(widget);
        }

        /**
         * @return the list
         */
        public ArrayList<ProgramNodeWidget> getList() {
            return list;
        }

        /**
         * @param list the list to set
         */
        public void setList(ArrayList<ProgramNodeWidget> list) {
            this.list = list;
        }
    }
}
