package com.petersoft.white;

import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollPaneUI;

public class ScrollPaneUI extends BasicScrollPaneUI implements PropertyChangeListener {
	public static ComponentUI createUI(JComponent c) {
		return new ScrollPaneUI();
	}

	protected MouseWheelListener createMouseWheelListener() {
		return new MouseWheelHandler();
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		// c.setBorder(new LineBorder(Color.black));
		c.setBorder(null);
		scrollpane.getHorizontalScrollBar().putClientProperty(ScrollBarUI.FREE_STANDING_PROP, Boolean.FALSE);
		scrollpane.getVerticalScrollBar().putClientProperty(ScrollBarUI.FREE_STANDING_PROP, Boolean.FALSE);
	}

	protected PropertyChangeListener createScrollBarSwapListener() {
		return this;
	}

	public void propertyChange(PropertyChangeEvent event) {
	}

	protected class MouseWheelHandler implements MouseWheelListener {
		public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
			if (scrollpane.isWheelScrollingEnabled() && (e.getScrollAmount() != 0)) {
				JScrollBar toScroll = scrollpane.getVerticalScrollBar();
				int direction = 0;
				int length = toScroll.getHeight();

				// find which scrollbar to scroll, or return if none
				if ((toScroll == null) || !toScroll.isVisible() || (e.getModifiers() == InputEvent.ALT_MASK)) {
					toScroll = scrollpane.getHorizontalScrollBar();

					if ((toScroll == null) || !toScroll.isVisible()) {
						return;
					}

					length = toScroll.getWidth();
				}

				direction = (e.getWheelRotation() < 0) ? (-1) : 1;

				if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
					int newValue = toScroll.getValue() + ((e.getWheelRotation() * length) / (toScroll.getUnitIncrement() * 2));
					toScroll.setValue(newValue);
				} else if (e.getScrollType() == MouseWheelEvent.WHEEL_BLOCK_SCROLL) {
					int newValue = toScroll.getValue() + ((e.getWheelRotation() * length) / (toScroll.getBlockIncrement() * 2));
					toScroll.setValue(newValue);
				}
			}
		}
	}
}
