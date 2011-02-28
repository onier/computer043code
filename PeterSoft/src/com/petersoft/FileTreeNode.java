package com.petersoft;

import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

public class FileTreeNode implements TreeNode {
	File file;
	FileTreeNode parent;

	public FileTreeNode(File file, FileTreeNode parent) {
		this.file = file;
		this.parent = parent;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return new FileTreeNode(file.listFiles()[childIndex], this);
	}

	@Override
	public int getChildCount() {
		return file.listFiles().length;
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public int getIndex(TreeNode node) {
		FileTreeNode n = (FileTreeNode) node;
		File files[] = file.listFiles();
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
		if (file != null && file.listFiles() != null) {
			return file.listFiles().length == 0;
		} else {
			return true;
		}
	}

	@Override
	public Enumeration children() {
		return new Vector(Arrays.asList(file.listFiles())).elements();
	}

	public String toString() {
		return file.getName();
	}

}
