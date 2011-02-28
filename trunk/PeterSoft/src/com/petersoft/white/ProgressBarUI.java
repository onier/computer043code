package com.petersoft.white;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class ProgressBarUI extends BasicProgressBarUI {
	Image bg = new ImageIcon(ButtonUI.class
			.getResource("images/PProgressBar/bg.png")).getImage();

	public static ComponentUI createUI(JComponent c) {
		return new ProgressBarUI();
	}

	public void paintDeterminate(Graphics g, JComponent c) {
		try {
			JProgressBar jProgressBar = (JProgressBar) c;
			g.drawImage(bg, 0, 0, c.getWidth()
					* (jProgressBar.getValue() - jProgressBar.getMinimum())
					/ (jProgressBar.getMaximum() - jProgressBar.getMinimum()),
					c.getHeight(), null, null);

			Insets b = progressBar.getInsets(); // area for border
			int barRectWidth = progressBar.getWidth() - (b.right + b.left);
			int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);
			int amountFull = getAmountFull(b, barRectWidth * 2, barRectHeight);

			g.setColor(Color.black);
			paintString(g, b.left, b.top, barRectWidth, barRectHeight,
					amountFull, b);
		} catch (Exception ex) {

		}
	}
	// public void paintIndeterminate(Graphics g, JComponent c) {
	// super.paintIndeterminate(g, c);
	//
	// if (!progressBar.isBorderPainted() || (!(g instanceof Graphics2D))) {
	// return;
	// }
	//
	// Insets b = progressBar.getInsets(); // area for border
	// int barRectWidth = progressBar.getWidth() - (b.left + b.right);
	// int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);
	// int amountFull = getAmountFull(b, barRectWidth, barRectHeight);
	// boolean isLeftToRight = true; // peter
	// int startX, startY, endX, endY;
	// Rectangle box = null;
	// box = getBox(box);
	//
	// // The progress bar border is painted according to a light source.
	// // This light source is stationary and does not change when the
	// // component orientation changes.
	// startX = b.left;
	// startY = b.top;
	// endX = b.left + barRectWidth - 1;
	// endY = b.top + barRectHeight - 1;
	//
	// Graphics2D g2 = (Graphics2D) g;
	// g2.setStroke(new BasicStroke(1.f));
	//
	// if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
	// // Draw light line lengthwise across the progress bar.
	// g2.setColor(MetalLookAndFeel.getControlShadow());
	// g2.drawLine(startX, startY, endX, startY);
	// g2.drawLine(startX, startY, startX, endY);
	//
	// // Draw darker lengthwise line over filled area.
	// g2.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
	// g2.drawLine(box.x, startY, box.x + box.width - 1, startY);
	//
	// } else { // VERTICAL
	// // Draw light line lengthwise across the progress bar.
	// g2.setColor(MetalLookAndFeel.getControlShadow());
	// g2.drawLine(startX, startY, startX, endY);
	// g2.drawLine(startX, startY, endX, startY);
	//
	// // Draw darker lengthwise line over filled area.
	// g2.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
	// g2.drawLine(startX, box.y, startX, box.y + box.height - 1);
	// }
	// }

}
