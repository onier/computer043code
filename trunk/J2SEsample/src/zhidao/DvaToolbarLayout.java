/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class DvaToolbarLayout implements java.awt.LayoutManager {

    private JPopupMenu popupMenu = new JPopupMenu();
    private JButton popupButton = new JButton(new PopupAction());

    public DvaToolbarLayout() {
        popupButton.setName("BTN_POPUP");
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void layoutContainer(Container parent) {

        if (parent.isVisible()) {

            //  Position all buttons in the container

            Insets insets = parent.getInsets();
            int x = insets.left;
            int y = insets.top;
            int spaceUsed = insets.right + insets.left;

            for (int i = 0; i < parent.getComponentCount(); i++) {
                Component aComponent = parent.getComponent(i);
                aComponent.setSize(aComponent.getPreferredSize());
                aComponent.setLocation(x, y);
                int componentWidth = aComponent.getPreferredSize().width;
                x += componentWidth;
                spaceUsed += componentWidth;
            }

            //  All the buttons won't fit, add extender button
            //  Note: the size of the extender button changes once it is added
            //  to the container. Add it here so correct width is used.

            int parentWidth = parent.getSize().width;

            if (spaceUsed > parentWidth) {
                popupMenu.removeAll();
                parent.add(popupButton);
                popupButton.setSize(popupButton.getPreferredSize());
                int popupX = parentWidth - insets.right - popupButton.getSize().width;
                popupButton.setLocation(popupX, y);
                spaceUsed += popupButton.getSize().width;
            }

            //  Remove buttons that don't fit and add to the popup menu

            int lastVisibleButtonIndex = 1;

            while (spaceUsed > parentWidth) {
                lastVisibleButtonIndex++;
                int last = parent.getComponentCount() - lastVisibleButtonIndex;

                Component component = parent.getComponent(last);
                component.setVisible(false);
                spaceUsed -= component.getSize().width;

                addComponentToPopup(component);

            }

        }

    }

    private void addComponentToPopup(Component component) {
        if (component instanceof JButton) {
            JButton button = (JButton) component;

            JMenuItem menuItem = new JMenuItem(button.getText());
            menuItem.setIcon(button.getIcon());
            menuItem.setToolTipText(button.getToolTipText());

            ActionListener[] listeners = button.getActionListeners();

            for (int i = 0; i < listeners.length; i++) {
                menuItem.addActionListener(listeners[i]);
            }

            popupMenu.insert(menuItem, 0);
        }

        if (component instanceof JToggleButton) {
            JToggleButton button = (JToggleButton) component;

            JMenuItem menuItem = new JMenuItem(button.getText());
            menuItem.setIcon(button.getIcon());
            menuItem.setToolTipText(button.getToolTipText());

            ActionListener[] listeners = button.getActionListeners();

            for (int i = 0; i < listeners.length; i++) {
                menuItem.addActionListener(listeners[i]);
            }

            popupMenu.insert(menuItem, 0);
        }

        if (component instanceof JToolBar.Separator) {
            popupMenu.insert(new JSeparator(), 0);
        }
    }

    /**
     * Calculates the minimum size dimensions for the specified
     * container, given the components it contains.
     * @param parent the component to be laid out
     * @see #preferredLayoutSize
     */
    public Dimension minimumLayoutSize(Container parent) {
        return popupButton.getMinimumSize();
    }

    /** Calculates the preferred size dimensions for the specified
     * container, given the components it contains.
     * @param parent the container to be laid out
     *
     * @see #minimumLayoutSize
     */
    public Dimension preferredLayoutSize(Container parent) {
        //  Move all components to the container and remove the extender button
        parent.remove(popupButton);

        //  Calculate the width of all components in the container

        Dimension d = new Dimension();
        d.width += parent.getInsets().right + parent.getInsets().left;

        for (int i = 0; i < parent.getComponents().length; i++) {
            Component component = parent.getComponent(i);
            component.setVisible(true);
            d.width += component.getPreferredSize().width;
            d.height = Math.max(d.height, component.getPreferredSize().height);
        }

        d.height += parent.getInsets().top + parent.getInsets().bottom + 5;
        return d;
    }

    /** Removes the specified component from the layout.
     * @param comp the component to be removed
     */
    public void removeLayoutComponent(Component comp) {
    }

    protected class PopupAction extends AbstractAction {

        public PopupAction() {
            super(">>");
        }

        public void actionPerformed(ActionEvent e) {
            JComponent component = (JComponent) e.getSource();
            popupMenu.show(component, 0, component.getHeight());
        }
    }

    public static void main(String[] argv) {
        ActionListener simpleAction = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            }
        };

        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new DvaToolbarLayout());
        toolBar.add(createButton("one", simpleAction));
        toolBar.add(createButton("two", simpleAction));
        toolBar.add(createButton("three", simpleAction));
        toolBar.add(createButton("four", simpleAction));
        toolBar.add(createButton("five", simpleAction));
        toolBar.add(createButton("six", simpleAction));
        toolBar.addSeparator();
        toolBar.add(createButton("seven", simpleAction));
        toolBar.add(createButton("eight", simpleAction));
        toolBar.addSeparator();
        toolBar.add(createButton("nine", simpleAction));
        toolBar.add(createButton("ten", simpleAction));

        JFrame f = new JFrame();
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(toolBar, BorderLayout.NORTH);
        f.setBounds(300, 200, 400, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private static JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }
}

