/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import javax.swing.ImageIcon;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import shape.BeanNodeElement;

/**
 *
 * @author admin
 */
public class IconMessageWidget extends IconNodeWidget {

    protected BeanNodeElement node;

    public IconMessageWidget(Scene scene, ImageIcon icon, BeanNodeElement node) {
        super(scene, TextOrientation.RIGHT_CENTER);
        this.node = node;
        setImage(icon.getImage());
        setLabel(node.getDisctription());
        setBorder(WidgetUtils.ROUNDED_BORDER);
        setPreferredSize(WidgetUtils.getSize(1, 1));
        setOpaque(true);
    }
}
