/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class ScrollablePopupMenu extends JPopupMenu {

    private static final long serialVersionUID = 1;
    private JPanel panelMenus = null;
    private JScrollPane scroll = null;

    public ScrollablePopupMenu(GraphicsConfiguration gc) {
        super();

        scroll = new JScrollPane();
        panelMenus = new JPanel();
        panelMenus.setLayout(new GridLayout(0, 1));
        scroll.setViewportView(panelMenus);
        scroll.setBorder(null);
        scroll.setMaximumSize(new Dimension(scroll.getMaximumSize().width,
                this.getToolkit().getScreenSize().height -
                this.getToolkit().getScreenInsets(gc).top -
                this.getToolkit().getScreenInsets(gc).bottom - 4));
        super.add(scroll);
    }

    public void show(Component invoker, int x, int y) {
        this.pack();
        panelMenus.validate();
        int maxsize = scroll.getMaximumSize().height;
        int realsize = panelMenus.getPreferredSize().height;

        int sizescroll = 0;

        if (maxsize < realsize) {
            sizescroll = scroll.getVerticalScrollBar().getPreferredSize().width;
        }
        scroll.setPreferredSize(new Dimension(
                scroll.getPreferredSize().width + sizescroll,
                scroll.getPreferredSize().height));
        this.setLocation(x, y);
        this.setVisible(true);
    }

    public void hidemenu() {
        if (this.isVisible()) {
            this.setVisible(false);
        }
    }

    @Override
    public JMenuItem add(JMenuItem menuItem) {
        panelMenus.add(menuItem);
        return menuItem;
    }

    public void addSeparator() {
        panelMenus.add(new JSeparator());
    }
}

