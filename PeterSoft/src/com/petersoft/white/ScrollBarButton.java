package com.petersoft.white;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicArrowButton;

public class ScrollBarButton extends BasicArrowButton {
	Image image, imageOver;

	public ScrollBarButton(int direction) {
		super(direction);

		try {
			// this.addMouseListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {
		if (image == null && imageOver == null) {
			if (getDirection() == NORTH) {
				image = new ImageIcon(ScrollBarButton.class.getResource("images/PScrollBar/PScrollBar_up.png")).getImage();
				imageOver = new ImageIcon(ScrollBarButton.class.getResource("images/PScrollBar/PScrollBar_up_mouseOver.png")).getImage();
			} else if (getDirection() == SOUTH) {
				image = new ImageIcon(ScrollBarButton.class.getResource("images/PScrollBar/PScrollBar_down.png")).getImage();
				imageOver = new ImageIcon(ScrollBarButton.class.getResource("images/PScrollBar/PScrollBar_down_mouseOver.png")).getImage();
			} else if (getDirection() == WEST) {
				image = new ImageIcon(ScrollBarButton.class.getResource("images/PScrollBar/PScrollBar_left.png")).getImage();
				imageOver = new ImageIcon(ScrollBarButton.class.getResource("images/PScrollBar/PScrollBar_left_mouseOver.png")).getImage();
			} else {
				image = new ImageIcon(ScrollBarButton.class.getResource("images/PScrollBar/PScrollBar_right.png")).getImage();
				imageOver = new ImageIcon(ScrollBarButton.class.getResource("images/PScrollBar/PScrollBar_right_mouseOver.png")).getImage();
			}
		}

		if (getModel().isArmed()) {
			g.drawImage(imageOver, 0, 0, getWidth(), getHeight(), null);
		} else {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		}
	}

}
