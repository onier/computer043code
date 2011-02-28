package com.petersoft.advancedswing.jmaximizabletabbedpane;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import com.petersoft.white.TabbedPaneUI;

public class JMaximizableTabbedPaneUI extends TabbedPaneUI {
	int tabWidth;;
	int tabHeight;
	int tabX;
	int tabY;
	ImageIcon closeIcon = new ImageIcon(this.getClass().getResource("/com/petersoft/white/images/JMaximizableTabbedPane/closeIcon.gif"));
	protected JMaximizableTabbedPane jMaximizableTabbedPane;

	public static ComponentUI createUI(JComponent c) {
		return new JMaximizableTabbedPaneUI();
	}

	public void installUI(JComponent c) {
		this.jMaximizableTabbedPane = (JMaximizableTabbedPane) c;
		super.installUI(c);
	}

	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		this.tabWidth = w;
		this.tabHeight = h;
		this.tabX = x;
		this.tabY = y;
		super.paintTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
	}

	protected void paintIcon(Graphics g, int tabPlacement, int tabIndex, Icon icon, Rectangle iconRect, boolean isSelected) {
		super.paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);
		if (jMaximizableTabbedPane.getClosableTabIndex().contains(tabIndex)) {
			closeIcon.paintIcon(tabPane, g, tabX + tabWidth - 18, this.tabY + ((tabHeight - closeIcon.getIconHeight()) / 2));
		}
	}

}
