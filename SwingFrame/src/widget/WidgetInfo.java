/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import javax.swing.Icon;
import shape.BeanNodeElement;

/**
 *
 * @author admin
 */
public interface WidgetInfo {

    public String getWidgetName();

    public Icon getWidgetIcon();

    public String getToolTipText();

    public BeanNodeElement getBeanNodeElement();

    public void properties();
}
