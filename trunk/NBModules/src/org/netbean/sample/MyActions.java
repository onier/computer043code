/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbean.sample;

import javax.swing.Action;
import org.netbeans.spi.palette.PaletteActions;

/**
 *
 * @author Zhenhai.Xu
 */
public class MyActions extends PaletteActions {

    @Override
    public Action[] getImportActions() {
        return null;
    }

    @Override
    public Action[] getCustomPaletteActions() {
        return null;
    }

    @Override
    public Action[] getCustomCategoryActions(org.openide.util.Lookup category) {
        return null;
    }

    @Override
    public Action[] getCustomItemActions(org.openide.util.Lookup item) {
        return null;
    }

    @Override
    public Action getPreferredAction(org.openide.util.Lookup item) {
        return null;
    }
}
