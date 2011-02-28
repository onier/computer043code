/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package component;

import javax.swing.JButton;
import javax.swing.border.Border;

/**
 *
 * @author admin
 */
public class EllipseButton extends JButton {

    @Override
    public void setBorder(Border border) {
        super.setBorder(null);
    }

    @Override
    public void setOpaque(boolean isOpaque) {
        super.setOpaque(false);
    }

    @Override
    public String getUIClassID() {
        return "EllipseButtonUI";
    }
}
