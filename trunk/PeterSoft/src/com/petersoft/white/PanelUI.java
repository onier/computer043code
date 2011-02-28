package com.petersoft.white;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;

public class PanelUI extends BasicPanelUI {
	// Shared UI object
	private static PanelUI panelUI;

	public static ComponentUI createUI(JComponent c) {
		if (panelUI == null) {
			panelUI = new PanelUI();
		}
		return panelUI;
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		JPanel p = (JPanel) c;
		// p.setBorder(null);
		// p.setOpaque(true);
		p.setBackground(Color.white);
	}

	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
	}

	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
	}
}
