package com.petersoft.advancedswing.jmaximizabletabbedpane;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Icon;

public class CloseTabIcon implements Icon{
	private int x_pos;
	private int y_pos;
	private int width;
	private int height;
	private Icon icon;
	private Icon closeIcon;

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public Icon getCloseIcon() {
		return closeIcon;
	}

	public void setCloseIcon(Icon closeIcon) {
		this.closeIcon = closeIcon;
	}

	public CloseTabIcon(Icon closeIcon, Icon icon) {
		this.closeIcon = closeIcon;
		this.icon = icon;
		width = 0;
		height = 0;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		this.x_pos = x;
		this.y_pos = y;
		int y_p = y + 2;

		if (icon != null) {
			icon.paintIcon(c, g, x, y_p);
		}
	}

	public int getIconWidth() {
		if (icon != null) {
			return icon.getIconWidth();
		} else {
			return width;
		}
	}

	public int getIconHeight() {
		if (icon != null) {
			return icon.getIconHeight();
		} else {
			return width;
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x_pos, y_pos, width, height);
	}
}
