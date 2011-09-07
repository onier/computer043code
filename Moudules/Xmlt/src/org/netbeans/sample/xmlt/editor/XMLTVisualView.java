/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.sample.xmlt.editor;

import java.awt.Image;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.netbeans.sample.xmlt.XMLTDataObject;
import org.openide.awt.UndoRedo;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;

/**
 *
 * @author Zhenhai.Xu
 */
public class XMLTVisualView extends TopComponent implements MultiViewElement, MultiViewDescription {

    private XMLTEditorSupport support;

    XMLTVisualView(XMLTEditorSupport aThis) {
        support = aThis;
    }

    @Override
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        return new JToolBar();
    }

    @Override
    public Action[] getActions() {
        return new Action[]{};
    }

    @Override
    public Lookup getLookup() {
        return ((XMLTDataObject) support.getDataObject()).getLookup();
    }

    @Override
    public void componentOpened() {
    }

    @Override
    public void componentClosed() {
    }

    @Override
    public void componentShowing() {
    }

    @Override
    public void componentHidden() {
    }

    @Override
    public void componentActivated() {
    }

    @Override
    public void componentDeactivated() {
    }

    @Override
    public UndoRedo getUndoRedo() {
        return null;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }

    @Override
    public int getPersistenceType() {
        return 1;
    }

    @Override
    public String getDisplayName() {
        return "Visual Editor";
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
        return "Visual";
    }

    @Override
    public MultiViewElement createElement() {
        return this;
    }
}
