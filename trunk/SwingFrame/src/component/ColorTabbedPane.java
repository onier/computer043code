/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package component;

import java.awt.Component;
import javax.swing.JTabbedPane;

/**sdfsdfsdfasdfasdfasdfasdfasdfasdfdfsdfsdfsdfasdfafasfdfsdfsdfsdfsdf
 *
 * @author adminsd
 */
public class ColorTabbedPane extends JTabbedPane {

    public ColorTabbedPane() {
        super();
    }

    public ColorTabbedPane(int tabPlacement) {
        super(tabPlacement, WRAP_TAB_LAYOUT);
    }

    public ColorTabbedPane(int tabPlacement, int tabLayoutPolicy) {
        super(tabPlacement, tabLayoutPolicy);
    }

    public void setTabHeaderAt(int index, JTabbedPaneHeader component) {
        component.setTabbedPane(this);
        component.setTabIndex(index);
        super.setTabComponentAt(index, component);
    }

    @Override
    public void addTab(String title, Component component) {
        super.addTab(null, component);
        if (title != null) {
            JTabbedPaneHeader header = new JTabbedPaneHeader(title);
            setTabHeaderAt(this.getTabCount() - 1, header);
        }
    }
}
