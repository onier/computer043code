/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuItemUI;

/**
 *
 * @author admin
 */
public class ColorMenuItemUI extends BasicMenuItemUI {

    public static ComponentUI createUI(JComponent c) {
        return new ColorMenuItemUI();
    }
}
