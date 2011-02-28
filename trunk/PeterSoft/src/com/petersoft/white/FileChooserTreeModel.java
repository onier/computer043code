package com.petersoft.white;

import java.io.File;

import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class FileChooserTreeModel extends DefaultTreeModel {
	FileChooserTreeNode root;

	public FileChooserTreeModel(TreeNode root) {
		super(root);
		this.root = (FileChooserTreeNode) root;
		if (System.getProperty("os.name").equals("Linux")) {
			FileSystemView view = FileSystemView.getFileSystemView();
			FileChooserTreeNode node;

			node = new FileChooserTreeNode("/", new File("/"), true);
			node.setIcon(view.getSystemIcon(new File("/")));
			this.root.add(node);

			node = new FileChooserTreeNode("Home", new File(System.getProperty("user.home")), true);
			node.setIcon(view.getSystemIcon(new File(System.getProperty("user.home"))));
			this.root.add(node);

			node = new FileChooserTreeNode("Desktop", new File(System.getProperty("user.home") + "/Desktop"), true);
			node.setIcon(view.getSystemIcon(new File(System.getProperty("user.home") + "/Desktop")));
			this.root.add(node);
		} else if (System.getProperty("os.name").contains("Windows")) {
			FileSystemView view = FileSystemView.getFileSystemView();
			FileChooserTreeNode node;

			node = new FileChooserTreeNode("Home", new File(System.getProperty("user.home")), false);
			node.setIcon(view.getSystemIcon(new File(System.getProperty("user.home"))));
			this.root.add(node);

			node = new FileChooserTreeNode("Desktop", new File(System.getProperty("user.home") + "/Desktop"), false);
			node.setIcon(view.getSystemIcon(new File(System.getProperty("user.home"))));
			this.root.add(node);

			File[] roots = File.listRoots();
			for (int i = 0; i < roots.length; i++) {
				node = new FileChooserTreeNode(roots[i].getPath(), roots[i], true);
				node.setIcon(view.getSystemIcon(roots[i]));
				this.root.add(node);
			}
		}
	}

	public Object getRoot() {
		return root;
	}

	public Object getChild(Object parent, int index) {
		FileChooserTreeNode node = (FileChooserTreeNode) parent;
		return node.getChildAt(index);
	}

	public int getChildCount(Object parent) {
		FileChooserTreeNode node = (FileChooserTreeNode) parent;
		return node.getChildCount();
	}

	public boolean isLeaf(Object node) {
		FileChooserTreeNode node2 = (FileChooserTreeNode) node;
		return node2.isLeaf();
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	public int getIndexOfChild(Object parent, Object child) {
		FileChooserTreeNode node = (FileChooserTreeNode) parent;
		return ((FileChooserTreeNode) parent).getIndex((FileChooserTreeNode) child);
	}

}
