package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import shape.NodeElement;

/**
 *  @author Administrator
 */
public class ShapeComponentListPanelRenderer<T extends NodeElement> extends ListComponentPanelRenderer {

    public final static DataFlavor DEFAULT_ELEMENT_FLAVOR = new DataFlavor(NodeElement.class, "Default Mutable Tree Node");
    DataFlavor[] flavors = new DataFlavor[]{DEFAULT_ELEMENT_FLAVOR};

    public ShapeComponentListPanelRenderer(T... objs) {
        super(new ShapeListModel(objs));
        this.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        this.setVisibleRowCount(-1);
        this.setCellRenderer(new ShapeListRenderer());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setDragEnabled(true);
        this.setTransferHandler(new ListTransferHandler());
        this.setDropMode(DropMode.ON);
    }

    @Override
    public ShapeListModel getModel() {
        return (ShapeListModel) super.getModel();
    }

    public static class ShapeListModel<T extends NodeElement> extends AbstractListModel {

        protected Vector<T> data = new Vector<T>();

        public ShapeListModel(T... objs) {
            data.addAll(Arrays.asList(objs));
        }

        public int getSize() {
            return data.size();
        }

        public void setElement(T obj, int index) {
            data.setElementAt(obj, index);
        }

        public T getElementAt(int index) {
            return data.get(index);
        }
    }

    public static class ShapeListRenderer extends JPanel implements ListCellRenderer {

        private JLabel textLabel = new JLabel();
        private JLabel iconLabel = new JLabel();

        public ShapeListRenderer() {
            this.setPreferredSize(new Dimension(50, 50));
            textLabel.setBackground(Color.WHITE);
            iconLabel.setBackground(Color.WHITE);
            setBackground(Color.WHITE);
            this.setLayout(new BorderLayout());
//            iconLabel.setHorizontalAlignment(JLabel.CENTER);
//            textLabel.setHorizontalAlignment(JLabel.CENTER);
//            iconLabel.setVerticalAlignment(JLabel.CENTER);
            this.add(iconLabel, BorderLayout.CENTER);
            this.add(textLabel, BorderLayout.SOUTH);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof NodeElement) {
                final NodeElement element = (NodeElement) value;
                textLabel.setText(element.getDisctription());
                this.setToolTipText(element.getDisctription());
                iconLabel.setIcon(element.getIcon());
                Color bg = list.getSelectionBackground();
                Color fg = list.getSelectionForeground();
                if (isSelected) {
                    setBackground(bg == null ? list.getSelectionBackground() : bg);
                    setForeground(fg == null ? list.getSelectionForeground() : fg);
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
            }
            return this;
        }
    }

    class ListTransferHandler extends TransferHandler {

        public boolean canImport(TransferHandler.TransferSupport info) {
            // Check for String flavor
            if (!info.isDataFlavorSupported(flavors[0])) {
                return false;
            }
            return true;
        }

        protected Transferable createTransferable(JComponent c) {
            return new TransferableListElement();
        }

        public int getSourceActions(JComponent c) {
            return TransferHandler.COPY_OR_MOVE;
        }

        public boolean importData(TransferHandler.TransferSupport info) {
            if (!info.isDrop()) {
                return false;
            }
            ShapeComponentListPanelRenderer list = (ShapeComponentListPanelRenderer) info.getComponent();
            int oldIndex = list.getSelectedIndex();
            Point point = info.getDropLocation().getDropPoint();
            int newIndex = list.locationToIndex(point);
            ShapeListModel model = list.getModel();
            NodeElement oldValue, newValue;
            if (ShapeComponentListPanelRenderer.this == list) {
                if (oldIndex != newIndex) {
                    oldValue = model.getElementAt(oldIndex);
                    newValue = model.getElementAt(newIndex);
                    model.setElement(oldValue, newIndex);
                    model.setElement(newValue, oldIndex);
                    list.setSelectedIndex(newIndex);
                }
            }
            return true;
        }
    }

    public class TransferableListElement implements Transferable {

        public TransferableListElement() {
        }

        public DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.isMimeTypeEqual(DEFAULT_ELEMENT_FLAVOR);

        }

        public NodeElement getTransferData(DataFlavor flavor) throws java.awt.datatransfer.UnsupportedFlavorException, java.io.IOException {
            if (flavor.isMimeTypeEqual(DEFAULT_ELEMENT_FLAVOR)) {
                return ((NodeElement) ShapeComponentListPanelRenderer.this.getSelectedValue()).getEditNode();
            } else {
                return null;
            }
        }
    }
}

