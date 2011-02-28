package com.petersoft.white;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuUI;

public class MenuUI extends BasicMenuUI {
	Image menubarBG = new ImageIcon(ScrollBarUI.class.getResource("images/PMenu/menubarBG.png")).getImage();

	public static ComponentUI createUI(JComponent x) {
		if (x == null) {
			throw new NullPointerException("Must pass in a non-null component");
		}
		return new MenuUI();
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		c.setBackground(Color.white);
		ToolBarUI.register(c);
	}

	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
		ToolBarUI.unregister(c);
	}

	// public void paint(Graphics g, JComponent c) {

	// super.paint(g, c);
	// }

	protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
		if (menuItem.getParent() instanceof JMenuBar) {
			g.drawImage(menubarBG, 0, 0, menuItem.getWidth(), menuItem.getHeight(), null);
		} else {
			super.paintBackground(g, menuItem, bgColor);
		}
	}

	// public void update(Graphics g, JComponent c) {
	// JMenu menu = (JMenu) c;
	// boolean isOpaque = c.isOpaque();
	// if (g == null) {
	// throw new NullPointerException("Graphics must be non-null");
	// }
	// if (isOpaque && (c.getBackground() instanceof UIResource) &&
	// UIManager.get("MenuBar.gradient") != null) {
	// paint(g, c);

	// System.out.println(menu.getText());
	// this.paintText(g, c, textRect, c.get)
	// } else {
	// super.update(g, c);
	// }
	// }
}
