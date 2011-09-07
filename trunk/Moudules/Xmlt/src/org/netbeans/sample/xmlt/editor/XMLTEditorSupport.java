/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.sample.xmlt.editor;

import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewFactory;
import org.netbeans.sample.xmlt.XMLTDataObject;
import org.openide.cookies.EditCookie;
import org.openide.cookies.EditorCookie;
import org.openide.cookies.OpenCookie;
import org.openide.text.DataEditorSupport;
import org.openide.windows.CloneableTopComponent;

/**
 *
 * @author Zhenhai.Xu
 */
public class XMLTEditorSupport extends DataEditorSupport implements EditorCookie, EditCookie, OpenCookie {

    MultiViewDescription[] view = new MultiViewDescription[]{new SourceEditView(this), new XMLTVisualView(this)};

    public XMLTEditorSupport(XMLTDataObject dataObject) {
        super(dataObject, new XMLTEnv(dataObject));
    }

    @Override
    protected boolean asynchronousOpen() {
        return true;
    }

    @Override
    protected boolean notifyModified() {
        boolean retvalue;
        retvalue = super.notifyModified();
        if (retvalue) {
            XMLTDataObject obj = (XMLTDataObject) getDataObject();
            obj.getInstanceContent().add(env);
        }
        return retvalue;
    }

    @Override
    protected void notifyUnmodified() {
        super.notifyUnmodified();
        XMLTDataObject obj = (XMLTDataObject) getDataObject();
        obj.getInstanceContent().remove(env);
    }

    @Override
    protected Pane createPane() {
        CloneableTopComponent panel = MultiViewFactory.createCloneableMultiView(view, view[1]);
        panel.setDisplayName(this.getDataObject().getNodeDelegate().getDisplayName());
        return (Pane) panel;
    }
}
