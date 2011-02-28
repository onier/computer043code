package com.petersoft.white;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboPopup;

public class ComboBox_ComboPopup extends BasicComboPopup {
	Border border1;

	public ComboBox_ComboPopup(JComboBox comboBox) {
		super(comboBox);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setVisible(boolean b) {
		// public void show() {
		if (b) {
			int selectedIndex = comboBox.getSelectedIndex();
			if (selectedIndex == -1) {
				list.clearSelection();
			} else {
				list.setSelectedIndex(selectedIndex);
				list.ensureIndexIsVisible(selectedIndex);
			}

			Insets insets = getInsets();
			Dimension listDim = list.getPreferredSize();
			boolean hasScrollBar = scroller.getViewport().getViewSize().height != listDim.height;
			// if (hasScrollBar) {
			// JScrollBar scrollBar = scroller.getVerticalScrollBar();
			// listDim.width += scrollBar.getPreferredSize().getWidth() * 2;
			// } else {
			JScrollBar scrollBar = scroller.getVerticalScrollBar();
			listDim.width += scrollBar.getPreferredSize().getWidth() + 10;
			// }

			int width = Math.max(listDim.width, comboBox.getWidth() - (insets.right + insets.left));
			int height = getPopupHeightForRowCount(comboBox.getMaximumRowCount());
			Rectangle popupBounds = computePopupBounds(0, comboBox.getHeight(), width, height);
			Dimension scrollSize = popupBounds.getSize();
			scroller.setMaximumSize(scrollSize);
			scroller.setPreferredSize(scrollSize);
			scroller.setMinimumSize(scrollSize);
			list.revalidate();
			// super.show(comboBox, popupBounds.x, popupBounds.y);
			// comboBox.setVisible(true);
		}
		super.setVisible(b);
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(110, 162, 255));
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);

		// two left-most vertical lines
		g.setColor(new Color(110, 162, 255));
		g.fillRect(0, 1, 1, getHeight() - 1);

		g.setColor(new Color(207, 226, 254));
		g.fillRect(1, 1, 1, getHeight() - 2);
	}

	private void jbInit() throws Exception {
		border1 = BorderFactory.createEmptyBorder(1, 2, 1, 1);
		this.setBorder(border1);
	}

	// public void delegateFocus(MouseEvent e) {
	// super.delegateFocus(e);
	// }
}
