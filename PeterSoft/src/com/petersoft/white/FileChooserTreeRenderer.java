package com.petersoft.white;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public class FileChooserTreeRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer {
	Color selectionBackground = UIManager.getColor("Tree.selectionBackground");
	Color selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
	static JFileChooser filechooser = new JFileChooser();

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		FileChooserTreeNode node = (FileChooserTreeNode) value;
		if (node.isDirectory) {
			this.setIcon(filechooser.getIcon(node.file));
		} else {
			if (node.getIcon() != null) {
				this.setIcon(node.getIcon());
			}
		}
		this.setText(node.toString());
		if (sel) {
			this.setBackground(selectionBackground);
			this.setBorder(new LineBorder(selectionBorderColor));
		} else {
			this.setBackground(tree.getBackground());
			this.setBorder(null);
		}
		this.setOpaque(true);
		return this;
	}
}
