/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.sample.xmlt.editor;

import java.io.IOException;
import org.netbeans.sample.xmlt.XMLTDataObject;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.loaders.MultiDataObject;
import org.openide.text.DataEditorSupport;

/**
 *
 * @author Zhenhai.Xu
 */
public class XMLTEnv extends DataEditorSupport.Env implements SaveCookie {

    public XMLTEnv(XMLTDataObject dataObject) {
        super(dataObject);
    }

    @Override
    protected FileObject getFile() {
        return super.getDataObject().getPrimaryFile();
    }

    @Override
    protected FileLock takeLock() throws IOException {
        return ((MultiDataObject) super.getDataObject()).getPrimaryEntry().takeLock();
    }

    @Override
    public void save() throws IOException {
        ((XMLTEditorSupport) super.findCloneableOpenSupport()).saveDocument();
    }
}
