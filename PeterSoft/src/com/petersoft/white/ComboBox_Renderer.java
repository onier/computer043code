package com.petersoft.white;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class ComboBox_Renderer extends JLabel implements ListCellRenderer {
	private Border border1;
	private boolean isMouseOver = false;
	private int index;
	private boolean cellHasFocus = false;

	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
	private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

	// ////////////////////////////////////////////////////////////////
//	Image normalUpperLeft = new ImageIcon(ComboBox_Renderer.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_LeftTop.png")).getImage();
//	Image normalMiddleLeft = new ImageIcon(ComboBox_Renderer.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_LeftMiddle.png")).getImage();
//	Image normalLowerLeft = new ImageIcon(ComboBox_Renderer.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_LeftBottom.png")).getImage();
//	Image normalMiddleUpper = new ImageIcon(ComboBox_Renderer.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_MiddleTop.png")).getImage();
//	Image normalMiddleLower = new ImageIcon(ComboBox_Renderer.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_MiddleBottom.png")).getImage();
//	Image normalUpperRight = new ImageIcon(ComboBox_Renderer.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_RightTop.png")).getImage();
//	Image normalMiddleRight = new ImageIcon(ComboBox_Renderer.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_RightMiddle.png")).getImage();
//	Image normalLowerRight = new ImageIcon(ComboBox_Renderer.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_RightBottom.png")).getImage();
//	Image arrow = new ImageIcon(ComboBox_Renderer.class.getResource("images/PComboBox/PComboBox_ArrowButton/normal/PComboBox_ArrowButton_Arrow.png")).getImage();

	public ComboBox_Renderer() {
		super();
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		border1 = BorderFactory.createEmptyBorder(4, 0, 4, 0);
		this.setBorder(border1);
		setOpaque(true);
	}

	public void paint(Graphics g) {
		if (index == -1) {
			// Color bgColor;
			//
			// // first item
			// if (isEnabled()) {
			// bgColor = new Color(248, 248, 248);
			// } else {
			// bgColor = new Color(230, 230, 230);
			// }
			// g.setColor(bgColor);
			// g.fillRect(0, 0, this.getWidth(), this.getHeight());

			// ///////////////////////////////////////////////////////////////
			// g.drawImage(normalUpperLeft, 0, 0, 2, 2, null);
			// g.setColor(new Color(205, 205, 205));
			// g.drawLine(0, 2, 0, this.getHeight() - 2);
			// g.drawImage(normalLowerLeft, 0, this.getHeight() - 2, 2, 2,
			// null);
			// //
			// ///////////////////////////////////////////////////////////////
			// g.drawImage(normalMiddleUpper, 2, 0, this.getWidth() - 2, 2,
			// null);
			// g.drawImage(normalMiddleLower, 2, this.getHeight() - 2,
			// this.getWidth() - 2, 2, null);

			if (this.getHorizontalAlignment() == JComboBox.CENTER_ALIGNMENT) {
				FontMetrics fontMetrics = this.getFontMetrics(this.getFont());

				int stringWidth = fontMetrics.stringWidth(getText());
				int stringHeight = getFont().getSize();
				g.setColor(Color.black);
				g.setFont(new java.awt.Font("Dialog", 0, 12));
				g.drawString(getText(), (getWidth() - stringWidth) / 2, getHeight() - ((getHeight() - stringHeight) / 2) - 1);
			} else {
				int stringHeight = getFont().getSize();
				g.setColor(Color.black);
				g.setFont(new java.awt.Font("Dialog", 0, 12));
				// System.out.println(comboBox.getSelectedItem().getClass());
				g.drawString(getText(), 5, getHeight() - ((getHeight() - stringHeight) / 2) - 1);
			}
		} else {
			// subitem
			if (isMouseOver) {
				g.setColor(new Color(196, 217, 255));
				g.fillRect(0, 0, getWidth(), getHeight());
			} else {
				g.setColor(Color.white);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
			if (this.isEnabled()) {
				g.setColor(Color.black);
			} else {
				g.setColor(Color.gray);
			}

			int stringHeight = getFont().getSize();
			g.setFont(this.getFont());
			g.drawString(this.getText(), 5, getHeight() - ((getHeight() - stringHeight) / 2) - 1);
		}
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		this.index = index;
		this.cellHasFocus = cellHasFocus;
		this.isMouseOver = isSelected;

		this.setText(String.valueOf(value));
		return this;
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		// Strings get interned...
		if (propertyName == "text") {
			super.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	public boolean isOpaque() {
		Color back = getBackground();
		Component p = getParent();
		if (p != null) {
			p = p.getParent();
		}
		// p should now be the JList.
		boolean colorMatch = (back != null) && (p != null) && back.equals(p.getBackground()) && p.isOpaque();
		return !colorMatch && super.isOpaque();
	}

	// public Dimension getPreferredSize() {
	// Dimension size;
	//
	// if ((this.getText() == null) || (this.getText().equals(""))) {
	// this.setText(" ");
	// size = super.getPreferredSize();
	// this.setText("");
	// } else {
	// size = super.getPreferredSize();
	// }

	// return super.getPreferredSize(););
	// }
}
