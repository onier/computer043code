package com.petersoft.advancedswing.combo_color_renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ComboBoxRenderer extends JLabel implements ListCellRenderer {
	Color color;

	public ComboBoxRenderer() {
		setOpaque(true);
	}

	public void paint(Graphics g) {
		setBackground(color);
		super.paint(g);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		setText("         ");
		setForeground(Color.PINK);
		setBackground((Color) value);
		color = (Color) value;
		return this;
	}
}
