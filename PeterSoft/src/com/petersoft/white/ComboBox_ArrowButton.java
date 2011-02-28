package com.petersoft.white;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class ComboBox_ArrowButton extends JButton {
	// ////////////////////////////////////////////////////////////////
	Image normalUpperLeft = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_LeftTop.png")).getImage();
	Image normalMiddleLeft = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_LeftMiddle.png")).getImage();
	Image normalLowerLeft = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_LeftBottom.png")).getImage();
	Image normalMiddleUpper = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_MiddleTop.png")).getImage();
	Image normalMiddleLower = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_MiddleBottom.png")).getImage();
	Image normalUpperRight = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_RightTop.png")).getImage();
	Image normalMiddleRight = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_RightMiddle.png")).getImage();
	Image normalLowerRight = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_RightBottom.png")).getImage();
	Image arrow = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_Arrow.png")).getImage();

	// ////////////////////////////////////////////////////////////////
	Image mouseOverUpperLeft = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/mouseOver/PComboBox_ArrowButton_LeftTop_Over.png")).getImage();
	Image mouseOverMiddleLeft = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/mouseOver/PComboBox_ArrowButton_LeftMiddle_Over.png")).getImage();
	Image mouseOverLowerLeft = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/mouseOver/PComboBox_ArrowButton_LeftBottom_Over.png")).getImage();
	Image mouseOverMiddleUpper = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/mouseOver/PComboBox_ArrowButton_MiddleTop_Over.png")).getImage();
	Image mouseOverMiddleLower = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/mouseOver/PComboBox_ArrowButton_MiddleBottom_Over.png")).getImage();
	Image mouseOverUpperRight = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/mouseOver/PComboBox_ArrowButton_RightTop_Over.png")).getImage();
	Image mouseOverMiddleRight = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/mouseOver/PComboBox_ArrowButton_RightMiddle_Over.png")).getImage();
	Image mouseOverLowerRight = new ImageIcon(ComboBox_ArrowButton.class.getResource("images/PComboBox/PComboBox_ArrowButton/mouseOver/PComboBox_ArrowButton_RightBottom_Over.png")).getImage();

	JComboBox comboBox;

	public ComboBox_ArrowButton(JComboBox comboBox) {
		super();
		this.comboBox = comboBox;
	}

	public void paintComponent(Graphics g) {
		if (this.getParent().isOpaque()) {
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		} else {
			g.setColor(this.getParent().getParent().getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

		// if (comboBox.isEnabled()) {
		// if (getModel().isPressed()) {
		// g.setColor(new Color(225, 237, 255));
		// g.fillRect(0, 2, this.getWidth() - 1, this.getHeight() - 4);
		// } else {
		// g.setColor(new Color(248, 248, 248));
		// g.fillRect(0, 2, this.getWidth() - 1, this.getHeight() - 4);
		// }
		// } else {
		// g.setColor(new Color(230, 230, 230));
		// g.fillRect(0, 2, this.getWidth() - 1, this.getHeight() - 4);
		// }
		g.drawImage(arrow, (this.getWidth() - 7) / 2, (this.getHeight() - 4) / 2, 7, 4, null);

		if (getModel().isRollover() && comboBox.isEnabled()) {
			g.drawImage(mouseOverUpperLeft, 0, 0, 2, 2, null);
			g.drawImage(mouseOverMiddleLeft, 0, 2, 1, this.getHeight() - 4, null);
			g.drawImage(mouseOverLowerLeft, 0, this.getHeight() - 2, 2, 2, null);

			g.drawImage(mouseOverMiddleUpper, 2, 0, this.getWidth() - 7, 2, null);
			g.drawImage(mouseOverMiddleLower, 2, this.getHeight() - 2, this.getWidth() - 7, 2, null);

			g.drawImage(mouseOverUpperRight, this.getWidth() - 5, 0, 5, 4, null);
			g.drawImage(mouseOverMiddleRight, this.getWidth() - 2, 4, 2, this.getHeight() - 8, null);
			g.drawImage(mouseOverLowerRight, this.getWidth() - 5, this.getHeight() - 4, 5, 4, null);
		} else {
			g.drawImage(normalUpperLeft, 0, 0, 2, 2, null);
			g.drawImage(normalMiddleLeft, 0, 2, 1, this.getHeight() - 4, null);
			g.drawImage(normalLowerLeft, 0, this.getHeight() - 2, 2, 2, null);

			g.drawImage(normalMiddleUpper, 2, 0, this.getWidth() - 7, 2, null);
			g.drawImage(normalMiddleLower, 2, this.getHeight() - 2, this.getWidth() - 7, 2, null);

			g.drawImage(normalUpperRight, this.getWidth() - 5, 0, 5, 4, null);
			g.drawImage(normalMiddleRight, this.getWidth() - 2, 4, 2, this.getHeight() - 8, null);

			g.drawImage(normalLowerRight, this.getWidth() - 5, this.getHeight() - 4, 5, 4, null);
		}
	}

	public Icon getComboIcon() {
		return new ImageIcon(arrow);
	}

}
