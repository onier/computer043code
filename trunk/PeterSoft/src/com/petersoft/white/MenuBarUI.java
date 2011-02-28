package com.petersoft.white;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicMenuBarUI;

public class MenuBarUI extends BasicMenuBarUI {
	Image menubarBG = new ImageIcon(ScrollBarUI.class.getResource("images/PMenu/menubarBG.png")).getImage();

	public static ComponentUI createUI(JComponent x) {
		if (x == null) {
			throw new NullPointerException("Must pass in a non-null component");
		}
		return new MenuBarUI();
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

	public void paint(Graphics g, JComponent c) {
		g.drawImage(menubarBG, 0, 0, c.getWidth(), c.getHeight(), null);
		// super.paint(g, c);
	}

	public void update(Graphics g, JComponent c) {
		boolean isOpaque = c.isOpaque();
		if (g == null) {
			throw new NullPointerException("Graphics must be non-null");
		}
		if (isOpaque && (c.getBackground() instanceof UIResource) && UIManager.get("MenuBar.gradient") != null) {
			paint(g, c);
		} else {
			super.update(g, c);
		}
	}
}
