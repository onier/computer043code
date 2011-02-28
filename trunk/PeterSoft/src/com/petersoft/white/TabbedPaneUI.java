package com.petersoft.white;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class TabbedPaneUI extends BasicTabbedPaneUI {
	// north
	protected Image top_tabLeft = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_top_leftTab.png")).getImage();
	protected Image top_tabMiddle = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_top_middleTab.png")).getImage();
	protected Image top_tabRight = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_top_rightTab.png")).getImage();
	protected Image top_tabRight_repeat = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_top_rightTab.png")).getImage();
	protected Image top_tabSelectedLeft = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_top_leftSelectedTab.png")).getImage();
	protected Image top_tabSelectedLeft_repeat = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_top_leftSelectedTab.png")).getImage();
	protected Image top_tabSelectedMiddle = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_top_middleSelectedTab.png")).getImage();
	protected Image top_tabSelectedRight = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_top_rightSelectedTab.png")).getImage();
	protected Image top_tabSelectedRight_repeat = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_top_rightSelectedTab.png")).getImage();
	// end north

	// left
	protected Image left_tabBottom = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_left_bottomTab.png")).getImage();
	protected Image left_tabBottom_repeat = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_left_bottomTab_repeat.png")).getImage();
	protected Image left_tabMiddle = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_left_middleTab.png")).getImage();
	protected Image left_tabTop = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_left_topTab.png")).getImage();
	protected Image left_tabTop_repeat = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_left_topTab_repeat.png")).getImage();
	protected Image left_tabSelectedBottom = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_left_bottomSelectedTab.png")).getImage();
	protected Image left_tabSelectedBottom_repeat = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_left_bottomSelectedTab_repeat.png")).getImage();
	protected Image left_tabSelectedMiddle = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_left_middleSelectedTab.png")).getImage();
	protected Image left_tabSelectedTop = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_left_topSelectedTab.png")).getImage();
	protected Image left_tabSelectedTop_repeat = new ImageIcon(TabbedPaneUI.class.getResource("images/PTabbedPane/ptabbedpane_left_topSelectedTab_repeat.png")).getImage();
	protected Color selectedBackground = new Color(188, 218, 244);

	// end left

	public static ComponentUI createUI(JComponent c) {
		return new TabbedPaneUI();
	}

	protected void installListeners() {

		super.installListeners();
		// tabPane.addMouseMotionListener((MouseMotionListener) mouseListener);
	}

	boolean isLeftToRight(Component c) {
		return c.getComponentOrientation().isLeftToRight();
	}

	// protected void paintTopTabBorder(int tabIndex, Graphics g, int x, int y,
	// int w, int h, int btm, int rght, boolean isSelected) {
	// System.out.println("paintTopTabBorder");
	// if (isSelected) {
	// g.drawImage(tabSelectedLeft, x, y, null);
	// g.drawImage(tabSelectedMiddle, x + 4, y, w - 8, h, null);
	// g.drawImage(tabSelectedRight, x + 4 + (w - 8), y, null);
	// } else {
	// g.drawImage(tabLeft, x, y, null);
	// g.drawImage(tabMiddle, x + 4, y, w - 8, h, null);
	// g.drawImage(tabRight, x + 4 + (w - 8), y, null);
	// }
	// }

	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		Insets contentBorderInsets = getContentBorderInsets(tabPlacement);

		if ((tabPane.getTabPlacement() == BOTTOM) && (contentBorderInsets.top == 5)) {
			contentBorderInsets.top = 0;
			contentBorderInsets.bottom = 5;
			tabPane.revalidate();
		} else if ((tabPane.getTabPlacement() == TOP) && (contentBorderInsets.top == 0)) {
			contentBorderInsets.top = 5;
			contentBorderInsets.bottom = 0;
			tabPane.revalidate();
		}

		if (isSelected) {
			if (tabPane.getTabPlacement() == TOP) {
				g.drawImage(top_tabSelectedLeft, x, y, 4, h, null);
				g.drawImage(top_tabSelectedMiddle, x + 4, y, w - 8, h, null);
				g.drawImage(top_tabSelectedRight, x + 4 + (w - 8), y, 4, h, null);
			} else if (tabPane.getTabPlacement() == LEFT) {
				g.setColor(selectedBackground);
				g.fillRect(x + 4, y + 4, w, h - 8);
				g.drawImage(left_tabSelectedTop, x, y, null);
				g.drawImage(left_tabSelectedTop_repeat, x + 4, y, w - 4, 4, null);
				g.drawImage(left_tabSelectedMiddle, x, y + 4, 4, h - 8, null);

				g.drawImage(top_tabSelectedMiddle, x + 4, y, w - 4, h, null);

				g.drawImage(left_tabSelectedBottom, x, y + h - 4, null);
				g.drawImage(left_tabSelectedBottom_repeat, x + 4, y + h - 4, w - 4, 4, null);
			}
		} else {
			if (tabPane.getTabPlacement() == TOP) {
				g.drawImage(top_tabLeft, x, y, 4, h, null);
				g.drawImage(top_tabMiddle, x + 4, y, w - 8, h, null);
				g.drawImage(top_tabRight, x + 4 + (w - 8), y, 4, h, null);
			} else if (tabPane.getTabPlacement() == LEFT) {
				g.setColor(Color.white);
				g.fillRect(x + 4, y + 4, w, h - 8);
				g.drawImage(left_tabTop, x, y, null);
				g.drawImage(left_tabTop_repeat, x + 4, y, w - 4, 4, null);
				g.drawImage(left_tabMiddle, x, y + 4, 4, h - 8, null);

				g.drawImage(top_tabMiddle, x + 4, y, w - 4, h, null);

				g.drawImage(left_tabBottom, x, y + h - 4, null);
				g.drawImage(left_tabBottom_repeat, x + 4, y + h - 4, w - 4, 4, null);
			}
		}
	}

	protected void paintIcon(Graphics g, int tabPlacement, int tabIndex, Icon icon, Rectangle iconRect, boolean isSelected) {
		if (icon != null) {
			icon.paintIcon(tabPane, g, iconRect.x, iconRect.y + 1);
		}
	}

	protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
		super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
	}

	// protected void layoutLabel(int tabPlacement, FontMetrics metrics, int
	// tabIndex, String title, Icon icon, Rectangle tabRect, Rectangle iconRect,
	// Rectangle textRect, boolean isSelected) {
	// // this function layoutLabel put the close icon to the right hand side
	// textRect.x = 0;
	// textRect.y = 0;
	// iconRect.x = 0;
	// iconRect.y = 0;
	// SwingUtilities.layoutCompoundLabel((JComponent) tabPane, metrics, title,
	// icon, SwingUtilities.CENTER, SwingUtilities.CENTER,
	// SwingUtilities.CENTER, SwingUtilities.LEFT, tabRect, iconRect,
	// textRect, textIconGap + 2);
	// }

	public void paint(Graphics g, JComponent c) {
		 g.setColor(Color.white);
		 g.fillRect(0, 0, c.getWidth(), c.getHeight());
		super.paint(g, c);
	}

	public void update(Graphics g, JComponent c) {
		paint(g, c);
	}
}
