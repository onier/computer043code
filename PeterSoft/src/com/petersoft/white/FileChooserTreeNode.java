package com.petersoft.white;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.tree.TreeNode;

public class FileChooserTreeNode implements TreeNode {
	private Icon icon;
	File file;
	private String text;
	boolean isDirectory;
	boolean noIcon;
	private Vector<FileChooserTreeNode> childNodes = new Vector<FileChooserTreeNode>();
	FileChooserTreeNode parent;

	public FileChooserTreeNode(String text, File file, boolean isDirectory) {
		this.text = text;
		this.file = file;
		this.isDirectory = isDirectory;
	}

	public FileChooserTreeNode(String text, File file, boolean isDirectory, boolean noIcon) {
		this(text, file, isDirectory);
		this.noIcon = noIcon;
	}

	public FileChooserTreeNode(String text, File file, boolean isDirectory, boolean noIcon, FileChooserTreeNode parent) {
		this(text, file, isDirectory);
		this.noIcon = noIcon;
		this.parent = parent;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		if (isDirectory && file.listFiles(new DirectoryFilter()) != null) {
			File child = file.listFiles(new DirectoryFilter())[childIndex];
			return new FileChooserTreeNode(child.getName(), child, true, true, this);
		} else {
			return childNodes.get(childIndex);
		}
	}

	@Override
	public int getChildCount() {
		if (isDirectory && file.listFiles(new DirectoryFilter()) != null) {
			return file.listFiles(new DirectoryFilter()).length;
		} else {
			return childNodes.size();
		}
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public int getIndex(TreeNode node) {
		FileChooserTreeNode n = (FileChooserTreeNode) node;
		File files[] = file.listFiles(new DirectoryFilter());
		for (int x = 0; x < files.length; x++) {
			if (files[x].getName().equals(n.file.getName())) {
				return x;
			}
		}
		return -1;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		if (isDirectory && file.listFiles(new DirectoryFilter()) != null) {
			return file.listFiles(new DirectoryFilter()).length == 0;
		} else {
			return childNodes.size() == 0;
		}
	}

	@Override
	public Enumeration children() {
		return new Vector(Arrays.asList(file.listFiles(new DirectoryFilter()))).elements();
	}

	public void add(FileChooserTreeNode node) {
		childNodes.add(node);
	}

	public String toString() {
		return (text == null) ? file.getName() : text;
	}

	class DirectoryFilter implements FileFilter {
		public boolean accept(File file) {
			return file.isDirectory();
		}
	}
}
