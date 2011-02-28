package com.petersoft.white;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class SplitPaneDivider extends BasicSplitPaneDivider {
	Color bgColor = new Color(235, 248, 255);
	Color lineColor = new Color(140, 150, 240);

	public SplitPaneDivider(BasicSplitPaneUI ui) {
		super(ui);
	}

	protected JButton createLeftOneTouchButton() {
		JButton b = new JButton("<");
		return b;
	}

	protected JButton createRightOneTouchButton() {
		JButton b = new JButton(">");
		return b;
	}

	public void paint(Graphics g) {
		g.setColor(bgColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(lineColor);
		if (super.orientation == JSplitPane.VERTICAL_SPLIT) {
			g.drawLine(0, 0, this.getWidth(), 0);
			g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
		} else {
			g.drawLine(0, 0, 0, this.getHeight());
			g.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight());
		}
	}

}
