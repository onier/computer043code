/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import javax.swing.Icon;
import shape.AbstractBeanNodeElement;

/**
 *
 * @author admin
 */
public interface WidgetInfo {

    public String getWidgetName();

    public Icon getWidgetIcon();

    public String getToolTipText();

    public AbstractBeanNodeElement getBeanNodeElement();

    public void properties();
}
