/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * A class representing a graphical view of a binary tree.
 */
public class BinaryTreeView extends JPanel implements ActionListener {

    // the binary tree
    private BinaryTree tree = null;
    // the node location of the tree
    private HashMap nodeLocations = null;
    // the sizes of the subtrees
    private HashMap subtreeSizes = null;
    // do we need to calculate locations
    private boolean dirty = true;
    // space between nodes
    private int parent2child = 20, child2child = 30;
    // helpers
    private Dimension empty = new Dimension(0, 0);
    private FontMetrics fm = null;

    public BinaryTreeView(BinaryTree tree) {
        this.tree = tree;
        nodeLocations = new HashMap();
        subtreeSizes = new HashMap();
        registerKeyboardAction(this, "add", KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), WHEN_IN_FOCUSED_WINDOW);
    }

    // event handler for pressing "A"
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            String c = JOptionPane.showInputDialog("Add a new node:");
            if (c != null && !"".equals(c)) {
                tree.addNode(c);
                dirty = true;
                repaint();
            }
        }
    }

    // calculate node locations
    private void calculateLocations() {
        nodeLocations.clear();
        subtreeSizes.clear();
        BinaryTree.Node root = tree.getRoot();
        if (root != null) {
            calculateSubtreeSize(root);
            calculateLocation(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
        }
    }

    // calculate the size of a subtree rooted at n
    private Dimension calculateSubtreeSize(BinaryTree.Node n) {
        if (n == null) {
            return new Dimension(0, 0);
        }
        String s = n.getContent().toString();
        Dimension ld = calculateSubtreeSize(n.getLeft());
        Dimension rd = calculateSubtreeSize(n.getRight());
        int h = fm.getHeight() + parent2child + Math.max(ld.height, rd.height);
        int w = ld.width + child2child + rd.width;
        Dimension d = new Dimension(w, h);
        subtreeSizes.put(n, d);
        return d;
    }

    // calculate the location of the nodes in the subtree rooted at n
    private void calculateLocation(BinaryTree.Node n, int left, int right, int top) {
        if (n == null) {
            return;
        }
        Dimension ld = (Dimension) subtreeSizes.get(n.getLeft());
        if (ld == null) {
            ld = empty;
        }
        Dimension rd = (Dimension) subtreeSizes.get(n.getRight());
        if (rd == null) {
            rd = empty;
        }
        int center = 0;
        if (right != Integer.MAX_VALUE) {
            center = right - rd.width - child2child / 2;
        } else if (left != Integer.MAX_VALUE) {
            center = left + ld.width + child2child / 2;
        }
        int width = fm.stringWidth(n.getContent().toString());
        Rectangle r = new Rectangle(center - width / 2 - 3, top, width + 6, fm.getHeight());
        nodeLocations.put(n, r);
        calculateLocation(n.getLeft(), Integer.MAX_VALUE, center - child2child / 2, top + fm.getHeight() + parent2child);
        calculateLocation(n.getRight(), center + child2child / 2, Integer.MAX_VALUE, top + fm.getHeight() + parent2child);
    }

    // draw the tree using the pre-calculated locations
    private void drawTree(Graphics2D g, BinaryTree.Node n, int px, int py, int yoffs) {
        if (n == null) {
            return;
        }
        Rectangle r = (Rectangle) nodeLocations.get(n);
        g.draw(r);
        g.drawString(n.getContent().toString(), r.x + 3, r.y + yoffs);
        if (px != Integer.MAX_VALUE) {
            g.drawLine(px, py, r.x + r.width / 2, r.y);
        }
        drawTree(g, n.getLeft(), r.x + r.width / 2, r.y + r.height, yoffs);
        drawTree(g, n.getRight(), r.x + r.width / 2, r.y + r.height, yoffs);
    }

    public void paint(Graphics g) {
        super.paint(g);
        fm = g.getFontMetrics();
        // if node locations not calculated
        if (dirty) {
            calculateLocations();
            dirty = false;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, parent2child);
        drawTree(g2d, tree.getRoot(), Integer.MAX_VALUE, Integer.MAX_VALUE, fm.getLeading() + fm.getAscent());
        fm = null;
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        JFrame f = new JFrame("BinaryTree");
        f.getContentPane().add(new BinaryTreeView(tree));
        // create and add an event handler for window closing event
        f.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setBounds(50, 50, 300, 300);
        f.show();
    }
}

