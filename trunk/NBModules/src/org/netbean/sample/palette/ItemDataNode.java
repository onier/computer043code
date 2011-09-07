/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbean.sample.palette;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.BeanInfo;
import java.io.IOException;
import org.netbean.sample.ItemDataObject;
import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.util.datatransfer.ExTransferable;

/**
 *
 * @author Zhenhai.Xu
 */
public class ItemDataNode extends DataNode {

    public static final DataFlavor DATA_FLAVOR = new DataFlavor(ItemData.class, "ItemData");
    private ItemData data;

    public ItemDataNode(ItemDataObject obj, ItemData data) {
        super(obj, Children.LEAF);
        this.data = data;
        this.setName(data.getId());
        this.setShortDescription(data.getComment());
    }

    @Override
    public Transferable drag() throws IOException {
        ExTransferable retValue = ExTransferable.create(super.drag());
        retValue.put(new ExTransferable.Single((DATA_FLAVOR)) {

            @Override
            protected Object getData() throws IOException, UnsupportedFlavorException {
                return data;
            }
        });
        return retValue;
    }

    @Override
    public Image getIcon(int type) {
        if (type == BeanInfo.ICON_COLOR_16x16 || type == BeanInfo.ICON_MONO_16x16) {
            return data.getIcon16();
        }
        return data.getIcon32();
    }

    @Override
    public String getDisplayName() {
        return data.getDisplayName();
    }
}
