/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.JScrollPane;

/**
 *
 * @author Administrator
 */
public class DownComponentLayout implements LayoutManager {

    protected Dimension minSize;
    protected Dimension preferredSize;

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension getSize(Container parent) {
        Dimension size = new Dimension();
        int n = parent.getComponentCount();
        for (int i = 0; i < n; i++) {
            Dimension d = parent.getComponent(i).getPreferredSize();
            if (parent.getComponent(i).isVisible()) {
//                size.width = Math.max(size.width, d.width);
                size.height = size.height + d.height;
            }
        }
        if (size.width == 0) {
            size.width = parent.getParent().getSize().width;
        }
        return size;
    }

    private Dimension getMinSize(Container parent) {
        Dimension size = new Dimension();
        int n = parent.getComponentCount();
        for (int i = 0; i < n; i++) {
            Dimension d = parent.getComponent(i).getPreferredSize();
            if (parent.getComponent(i).isVisible()) {
                size.width = Math.max(size.width, d.width);
                size.height = size.height + d.height;
            }
        }
        return size;
    }

    public Dimension preferredLayoutSize(Container parent) {
        return this.getMinSize(parent);
    }

    public Dimension minimumLayoutSize(Container parent) {
        if (minSize == null) {
            minSize = this.getMinSize(parent);
        }
        return minSize;
    }

    private JScrollPane getViewport(Container parent) {
        Container p = parent;
        do {
            p = p.getParent();
        } while (!(p instanceof JScrollPane) && p != null);
        return (JScrollPane) p;
    }

    public void layoutContainer(Container parent) {
        JScrollPane p = this.getViewport(parent);
        Dimension invaidSize = this.getSize(parent);
        if (p != null) {
            invaidSize.width = p.getViewportBorderBounds().width;
//            System.out.println(invaidSize.width);
        }
        int n = parent.getComponentCount();
        int y = 0;
        for (int i = 0; i < n; i++) {
            Component com = parent.getComponent(i);
            com.setBounds(0, y, invaidSize.width, com.getPreferredSize().height);
            y = y + com.getPreferredSize().height;
        }
    }
}
