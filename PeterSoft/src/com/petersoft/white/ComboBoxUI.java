package com.petersoft.white;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class ComboBoxUI extends BasicComboBoxUI {
	public static ComponentUI createUI(final JComponent c) {
		return new ComboBoxUI();
	}

	public void installUI(JComponent c) {
		super.installUI(c);
	}

	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
	}

	public JButton createArrowButton() {
		return new ComboBox_ArrowButton(comboBox);
	}

	public ComboBoxEditor createEditor() {
		return new ComboBox_ComboBoxEditor();
	}

	protected ComboPopup createPopup() {
		// ComboBox_ComboPopup pComboBox_ComboPopup = new
		// ComboBox_ComboPopup(comboBox) {
		// protected JScrollPane createScroller() {
		// return new JScrollPane(list,
		// ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
		// ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// }
		// };
		//
		// return pComboBox_ComboPopup;

		return super.createPopup();
	}

	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		// g.setColor(Color.RED);
		// g.fillRect(0,0,500,500);
	}

	public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
		// This is really only called if we're using ocean.
		//		bounds.x += 2;
		//		bounds.width -= 3;
		//		if (arrowButton != null) {
		//			Insets buttonInsets = arrowButton.getInsets();
		//			bounds.y += 1;
		//			bounds.y += buttonInsets.top;
		//			bounds.height -= (buttonInsets.top + buttonInsets.bottom);
		//		} else {
		//			bounds.y += 2;
		//			bounds.height -= 2;
		//		}
		bounds.x += 2;
		bounds.width -= 2;
		bounds.y += 2;
		bounds.height -= 4;
		super.paintCurrentValue(g, bounds, hasFocus);
	}

	public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
		super.paintCurrentValueBackground(g, bounds, hasFocus);
		g.setColor(MetalLookAndFeel.getControlDarkShadow());
		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height - 1);
		g.setColor(MetalLookAndFeel.getControlShadow());
		g.drawRect(bounds.x + 1, bounds.y + 1, bounds.width, bounds.height - 3);
		//		g.setColor(Color.red);
		//		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	public Dimension getMinimumSize(JComponent c) {
		if (!isMinimumSizeDirty) {
			return new Dimension(cachedMinimumSize);
		}

		Dimension size = null;

		if (!comboBox.isEditable() && arrowButton != null) {
			Insets buttonInsets = arrowButton.getInsets();
			Insets insets = comboBox.getInsets();

			size = getDisplaySize();
			size.width += insets.left + insets.right;
			size.width += buttonInsets.right;
			size.width += arrowButton.getMinimumSize().width;
			size.height += insets.top + insets.bottom;
			size.height += buttonInsets.top + buttonInsets.bottom;
		} else if (comboBox.isEditable() && arrowButton != null && editor != null) {
			size = super.getMinimumSize(c);
			Insets margin = arrowButton.getMargin();
			size.height += margin.top + margin.bottom;
			size.width += margin.left + margin.right;
		} else {
			size = super.getMinimumSize(c);
		}

		cachedMinimumSize.setSize(size.width, size.height);
		isMinimumSizeDirty = false;

		return new Dimension(cachedMinimumSize);
	}

}
