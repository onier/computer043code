/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;

public class ColorCellRendererPane extends CellRendererPane implements Accessible {

    public ColorCellRendererPane() {
        super();
        setLayout(null);
        setVisible(false);
    }

    @Override
    public void invalidate() {
    }

    @Override
    public void paint(Graphics g) {
    }

    @Override
    public void update(Graphics g) {
    }

    @Override
    protected void addImpl(Component x, Object constraints, int index) {
        if (x.getParent() == this) {
            return;
        } else {
            super.addImpl(x, constraints, index);
        }
    }

    public void paintComponent(Graphics g, Component c, Container p, int x, int y, int w, int h, boolean shouldValidate) {
        if (c == null) {
            if (p != null) {
                Color oldColor = g.getColor();
                g.setColor(Color.RED);
                g.fillRect(x, y, w, h);
                g.setColor(oldColor);
            }
            return;
        }

        if (c.getParent() != this) {
            this.add(c);
        }

        c.setBounds(x, y, w, h);

        if (shouldValidate) {
            c.validate();
        }

        boolean wasDoubleBuffered = false;
        if ((c instanceof JComponent) && ((JComponent) c).isDoubleBuffered()) {
            wasDoubleBuffered = true;
            ((JComponent) c).setDoubleBuffered(false);
        }

        Graphics cg = g.create(x, y, w, h);
        try {
            c.paint(cg);
        } finally {
            cg.dispose();
        }

        if (wasDoubleBuffered && (c instanceof JComponent)) {
            ((JComponent) c).setDoubleBuffered(true);
        }

        c.setBounds(-w, -h, 0, 0);
    }

    public void paintComponent(Graphics g, Component c, Container p, int x, int y, int w, int h) {
        paintComponent(g, c, p, x, y, w, h, false);
    }

    public void paintComponent(Graphics g, Component c, Container p, Rectangle r) {
        paintComponent(g, c, p, r.x, r.y, r.width, r.height);
    }
    protected AccessibleContext accessibleContext = null;

    @Override
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleCellRendererPane();
        }
        return accessibleContext;
    }

    protected class AccessibleCellRendererPane extends AccessibleAWTContainer {

        @Override
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.PANEL;
        }
    }
}
