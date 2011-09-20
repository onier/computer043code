/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JTree;

import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import javax.swing.tree.*;
import org.openide.util.Exceptions;

public class Tree {

    static ImageIcon image;

    public static void main(String[] args) {
        try {
            image = new ImageIcon(new URL("http://www.google.com.hk/intl/zh-CN/images/logo_cn.png"));
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
        JFrame frame = new JFrame("Table");
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                Window win = e.getWindow();
                win.setVisible(false);
                win.dispose();
                System.exit(0);
            }
        });


        JTree tree = new JTree() {

            public void paint(Graphics g) {
                // First draw the background image - tiled
                Dimension d = getSize();
                for (int x = 0; x < d.width; x += image.getIconWidth()) {
                    for (int y = 0; y < d.height; y += image.getIconHeight()) {
                        g.drawImage(image.getImage(), x, y, null, null);
                    }
                }
                // Now let the regular paint code do it's work
                super.paint(g);
            }
        };

        // Set the tree transparent so we can see the background image
        tree.setOpaque(false);
        tree.setCellRenderer(new MyCellRenderer());
        JScrollPane sp = new JScrollPane(tree);

        frame.getContentPane().add(sp);
        frame.pack();
        frame.show();
    }
}

class MyCellRenderer extends JLabel implements TreeCellRenderer {

    public MyCellRenderer() {
        setOpaque(false);
        setBackground(null);
    }

    public Component getTreeCellRendererComponent(JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {
        setFont(tree.getFont());
        String stringValue = tree.convertValueToText(value, sel,
                expanded, leaf, row, hasFocus);

        setEnabled(tree.isEnabled());
        setText(stringValue);
        if (sel) {
            setForeground(Color.blue);
        } else {
            setForeground(Color.black);
        }
        if (leaf) {
            setIcon(UIManager.getIcon("Tree.leafIcon"));
        } else if (expanded) {
            setIcon(UIManager.getIcon("Tree.openIcon"));
        } else {
            setIcon(UIManager.getIcon("Tree.closedIcon"));
        }
        return this;
    }
}
