/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.sample.xmlt;

import java.io.IOException;
import org.netbeans.sample.xmlt.editor.XMLTEditorSupport;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.Node;
import org.openide.nodes.Children;
import org.openide.nodes.Node.Cookie;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class XMLTDataObject extends MultiDataObject {
    
    private final AbstractLookup lookup;
    final InstanceContent ic;
    
    public XMLTDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        ic = new InstanceContent();
        lookup = new AbstractLookup(ic);
        ic.add(new XMLTEditorSupport(this));
        ic.add(this);
    }
    
    public InstanceContent getInstanceContent() {
        return ic;
    }
    
    @Override
    protected Node createNodeDelegate() {
        return new DataNode(this, Children.LEAF, getLookup());
    }
    
    @Override
    public Lookup getLookup() {
        return lookup;
    }

    @Override
    public <T extends Cookie> T getCookie(Class<T> type) {
        T o = lookup.lookup(type);
        return (T) (o instanceof Node.Cookie ? (Node.Cookie) o : null);
    }
    
}
