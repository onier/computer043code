/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.io.*;

public class FileTree extends JTree {

    public FileTree(String path)
            throws FileNotFoundException, SecurityException {
        super((TreeModel) null);// Create the JTree itself

        putClientProperty("JTree.lineStyle", "Angled");

        FileTreeNode rootNode = new FileTreeNode(null, path);

        boolean addedNodes = rootNode.populateDirectories(true);
        setModel(new DefaultTreeModel(rootNode));

        addTreeExpansionListener(new TreeExpansionHandler());
    }

    public String getPathName(TreePath path) {
        Object o = path.getLastPathComponent();
        if (o instanceof FileTreeNode) {
            return ((FileTreeNode) o).file.getAbsolutePath();
        }
        return null;
    }

    public File getFile(TreePath path) {
        Object o = path.getLastPathComponent();
        if (o instanceof FileTreeNode) {
            return ((FileTreeNode) o).file;
        }
        return null;
    }

    protected static class FileTreeNode
            extends DefaultMutableTreeNode {

        public FileTreeNode(File parent, String name)
                throws SecurityException, FileNotFoundException {
            this.name = name;

            file = new File(parent, name);
            if (!file.exists()) {
                throw new FileNotFoundException("File " + name + " does not exist");
            }

            isDir = file.isDirectory();

            setUserObject(file);

        }

        public boolean isLeaf() {
            return !isDir;
        }

        public boolean getAllowsChildren() {
            return isDir;
        }

        boolean populateDirectories(boolean descend) {
            boolean addedNodes = false;
            if (populated == false) {
                if (interim == true) {
                    removeAllChildren();
                    interim = false;
                }

                String[] names = file.list();// Get list of contents

                for (int i = 0; i < names.length; i++) {
                    String name = names[i];
                    File d = new File(file, name);
                    try {
                        if (d.isDirectory()) {
                            FileTreeNode node =
                                    new FileTreeNode(file, name);
                            this.add(node);
                            if (descend) {
                                node.populateDirectories(false);
                            }
                            addedNodes = true;
                            if (descend == false) {
                                break;
                            }
                        }
                    } catch (Throwable t) {
                    }
                }

                if (descend == true || addedNodes == false) {
                    populated = true;
                } else {
                    // Just set interim state
                    interim = true;
                }
            }
            return addedNodes;
        }
        protected File file;// File object for this node
        protected String name;// Name of this node
        protected boolean populated;
        // true if we have been populated
        protected boolean interim;
        // true if we are in interim state
        protected boolean isDir;// true if this is a directory
    }

    // Inner class that handles Tree Expansion Events
    protected class TreeExpansionHandler
            implements TreeExpansionListener {

        public void treeExpanded(TreeExpansionEvent evt) {
            TreePath path = evt.getPath();// The expanded path JTree tree =
            JTree tree = (JTree) evt.getSource();// The tree

            FileTreeNode node =
                    (FileTreeNode) path.getLastPathComponent();
            if (node.populateDirectories(true)) {
                ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(node);
            }
        }

        public void treeCollapsed(TreeExpansionEvent evt) {
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        try {
            FileTree tree = new FileTree("d:");
            tree.getClass().getAnnotations();
            tree.getClass().getMethods();
            frame.getContentPane().add(tree);
            frame.setDefaultCloseOperation(3);
            frame.setVisible(true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(FileTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
