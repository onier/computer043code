/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import component.JTabbedPaneHeader;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author admin
 */
public class ColorTabbedPaneHeaderUI extends ComponentUI {

    private JTabbedPaneHeader header;

    public ColorTabbedPaneHeaderUI() {
        super();
    }

    public static ComponentUI createUI(JComponent c) {
        return new ColorTabbedPaneHeaderUI();
    }

    @Override
    public void installUI(JComponent c) {
        header = (JTabbedPaneHeader) c;
        installDefaults((JTabbedPaneHeader) c);
        installListeners((JTabbedPaneHeader) c);
        installKeyboardActions((JTabbedPaneHeader) c);
    }

    @Override
    public void uninstallUI(JComponent c) {
        uninstallDefaults((JTabbedPaneHeader) c);
        uninstallListeners((JTabbedPaneHeader) c);
        uninstallKeyboardActions((JTabbedPaneHeader) c);
    }

    private void installDefaults(JTabbedPaneHeader jTabbedPaneHeader) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void installListeners(JTabbedPaneHeader jTabbedPaneHeader) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void installKeyboardActions(JTabbedPaneHeader jTabbedPaneHeader) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void uninstallDefaults(JTabbedPaneHeader jTabbedPaneHeader) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void uninstallListeners(JTabbedPaneHeader jTabbedPaneHeader) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void update(Graphics g, JComponent c) {
        this.paint(g, c);
    }

    private void uninstallKeyboardActions(JTabbedPaneHeader jTabbedPaneHeader) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    protected class Handler extends MouseAdapter implements PropertyChangeListener {

        boolean rollover = false;
        boolean armed = false;

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            super.mouseWheelMoved(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            armed = true;
            header.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            super.mouseMoved(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            rollover = false;
            header.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            rollover = true;
            header.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
        }

        public void propertyChange(PropertyChangeEvent evt) {
            header.repaint();
        }
    }
}
