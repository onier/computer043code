/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbean.sample;

import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.netbean.sample.palette.ItemData;
import org.netbean.sample.palette.ItemDataNode;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.text.DataEditorSupport;

public class ItemDataObject extends MultiDataObject {

    private final ItemData data;

    public ItemDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        InputStream input = pf.getInputStream();
        Properties properties = new Properties();
        properties.load(input);
        input.close();
        data = new ItemData(properties);
        CookieSet cookies = getCookieSet();
        cookies.add((Node.Cookie) DataEditorSupport.create(this, getPrimaryEntry(), cookies));
    }

    public ItemData getData() {
        return data;
    }

    @Override
    protected Node createNodeDelegate() {
        return new ItemDataNode(this, data);
    }

    @Override
    public Lookup getLookup() {
        return getCookieSet().getLookup();
    }
}
