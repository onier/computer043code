/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.sample.xmlt.editor;

import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.text.Document;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.text.CloneableEditor;
import org.openide.text.NbDocument;

/**
 *
 * @author Zhenhai.Xu
 */
public class XMLTCloneableEditor extends CloneableEditor implements MultiViewElement {

    private JToolBar toolBar;

    public XMLTCloneableEditor(XMLTEditorSupport obj) {
        super(obj);
    }

    public XMLTCloneableEditor() {
    }

    @Override
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public void componentDeactivated() {
        super.componentDeactivated();
    }

    @Override
    public void componentActivated() {
        super.componentActivated();
    }

    @Override
    public void componentOpened() {
        super.componentOpened();
    }

    @Override
    public void componentClosed() {
        super.componentClosed();
    }

    @Override
    public void componentShowing() {
        super.componentShowing();
    }

    @Override
    public void componentHidden() {
        super.componentHidden();
    }

    @Override
    public JComponent getToolbarRepresentation() {
        if (toolBar == null) {
            if (this.pane != null) {
                Document document = pane.getDocument();
                if (document instanceof NbDocument.CustomToolbar) {
                    toolBar = ((NbDocument.CustomToolbar) document).createToolbar(pane);
                }
                if (toolBar == null) {
                    toolBar = new JToolBar();
                }
            }
        }
        return toolBar;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.updateName();
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }
}
