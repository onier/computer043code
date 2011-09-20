/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JTree;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.plaf.basic.*;

/**
 * @version 1.0 04/16/99
 */
public class OnlyTextTreeExample extends JFrame {

    public OnlyTextTreeExample() {
        super("OnlyTextTreeExample");
        String[] strs = {"swing", // 0
            "platf", // 1
            "basic", // 2
            "metal", // 3
            "JTree"};    // 4

        DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[strs.length];
        for (int i = 0; i < strs.length; i++) {
            nodes[i] = new DefaultMutableTreeNode(strs[i]);
        }
        nodes[0].add(nodes[1]);
        nodes[1].add(nodes[2]);
        nodes[1].add(nodes[3]);
        nodes[0].add(nodes[4]);
        JTree tree = new JTree(nodes[0]);
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        renderer.setOpenIcon(null);
        renderer.setClosedIcon(null);
        renderer.setLeafIcon(null);
        BasicTreeUI ui = (BasicTreeUI) tree.getUI();
        ui.setExpandedIcon(null);
        ui.setCollapsedIcon(null);
        JScrollPane sp = new JScrollPane(tree);
        getContentPane().add(sp, BorderLayout.CENTER);
    }

    public static void main(String args[]) {
        OnlyTextTreeExample frame = new OnlyTextTreeExample();
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize(300, 150);
        frame.setVisible(true);
    }
}
