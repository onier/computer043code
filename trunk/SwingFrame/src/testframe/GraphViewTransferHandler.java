/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testframe;

import java.awt.Component;
import java.awt.Container;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import shape.NodeElement;

/**
 *
 * @author Administrator
 */
public class GraphViewTransferHandler implements DropTargetListener, Transferable, DragSourceListener {

    private String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=shape.ShapeElement";
    private DataFlavor shapeFlavor;
    private NodeElement element;
    private Object object;

    public GraphViewTransferHandler() {
        //Try to create a DataFlavor for color.
        try {
            shapeFlavor = new DataFlavor(mimeType);
        } catch (ClassNotFoundException e) {
        }
    }

    /**
     * Does the flavor list have a Color flavor?
     */
    protected boolean hasShapeFlavor(DataFlavor[] flavors) {
        if (shapeFlavor == null) {
            return false;
        }

        for (int i = 0; i < flavors.length; i++) {
            if (shapeFlavor.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }

    public void dragEnter(DropTargetDragEvent dtde) {
    }

    void dropTargetDrag(DropTargetDragEvent ev) {
        ev.acceptDrag(ev.getDropAction());
    }

    public void dragOver(DropTargetDragEvent dtde) {
        dropTargetDrag(dtde);
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
        dropTargetDrag(dtde);
    }

    public void dragExit(DropTargetEvent dte) {
    }

    public void drop(DropTargetDropEvent ev) {
        ev.acceptDrop(ev.getDropAction());
        try {
            Object target = ev.getSource();
            Object source = ev.getTransferable().getTransferData(shapeFlavor);
            Component component = ((DragSourceContext) source).getComponent();
            Container oldContainer = component.getParent();
            Container container = (Container) ((DropTarget) target).getComponent();
            container.add(component);
            oldContainer.validate();
            oldContainer.repaint();
            container.validate();
            container.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ev.dropComplete(true);
    }

    public void dragGestureRecognized(DragGestureEvent dge) {
        dge.startDrag(null, this, this);
    }

    public void dragEnter(DragSourceDragEvent dsde) {
    }

    public void dragOver(DragSourceDragEvent dsde) {
        object = dsde.getSource();
    }

    public void dropActionChanged(DragSourceDragEvent dsde) {
    }

    public void dragExit(DragSourceEvent dse) {
    }

    public void dragDropEnd(DragSourceDropEvent dsde) {
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{shapeFlavor};
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.isMimeTypeEqual(shapeFlavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType)) {
            return object;
        } else {
            return null;
        }
    }
}
