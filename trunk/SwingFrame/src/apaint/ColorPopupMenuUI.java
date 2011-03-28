/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

/**
 *
 * @author admin
 */
public class ColorPopupMenuUI extends BasicPopupMenuUI {

    public static ComponentUI createUI(JComponent x) {
        return new ColorPopupMenuUI();
    }
}
