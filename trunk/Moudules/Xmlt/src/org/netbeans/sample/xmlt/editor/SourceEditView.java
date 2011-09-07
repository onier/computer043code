/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.sample.xmlt.editor;

import java.awt.Image;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.openide.util.HelpCtx;
import org.openide.windows.TopComponent;

/**
 *
 * @author Zhenhai.Xu
 */
public class SourceEditView implements MultiViewDescription {

    private XMLTEditorSupport support;
    private XMLTCloneableEditor editor;

    public SourceEditView(XMLTEditorSupport support) {
        this.support = support;
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ONLY_OPENED;
    }

    @Override
    public String getDisplayName() {
        return "XMLT Source Editor";
    }

    @Override
    public Image getIcon() {
        return null;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public String preferredID() {
        return "XMLT Source";
    }

    @Override
    public MultiViewElement createElement() {
        if (editor == null) {
            editor = new XMLTCloneableEditor(support);
        }
        return editor;
    }
}
