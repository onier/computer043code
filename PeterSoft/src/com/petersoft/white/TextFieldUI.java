/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *        Liquid Look and Feel                                                   *
 *                                                                              *
 *  Author, Miroslav Lazarevic                                                  *
 *                                                                              *
 *   For licensing information and credits, please refer to the                 *
 *   comment in file com.birosoft.liquid.LiquidLookAndFeel                      *
 *                                                                              *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.petersoft.white;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

public class TextFieldUI extends BasicTextFieldUI implements FocusListener {
	// static JTextComponent _editor;
	// boolean onFocus;

	// ///////////////////////////////////////////////////////////////////////
	Image onFocus_upperLeft = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/onFocus/PTextField_onFocus_upperLeft.png")).getImage();
	Image onFocus_middleLeft = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/onFocus/PTextField_onFocus_middleLeft.png")).getImage();
	Image onFocus_lowerLeft = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/onFocus/PTextField_onFocus_lowerLeft.png")).getImage();
	Image onFocus_middleUpper = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/onFocus/PTextField_onFocus_middleUpper.png")).getImage();
	Image onFocus_middleLower = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/onFocus/PTextField_onFocus_middleLower.png")).getImage();
	Image onFocus_upperRight = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/onFocus/PTextField_onFocus_upperRight.png")).getImage();
	Image onFocus_middleRight = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/onFocus/PTextField_onFocus_middleRight.png")).getImage();
	Image onFocus_lowerRight = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/onFocus/PTextField_onFocus_lowerRight.png")).getImage();

	// ///////////////////////////////////////////////////////////////////////
	Image lostFocus_upperLeft = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/lostFocus/PTextField_lostFocus_upperLeft.png")).getImage();
	Image lostFocus_middleLeft = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/lostFocus/PTextField_lostFocus_middleLeft.png")).getImage();
	Image lostFocus_lowerLeft = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/lostFocus/PTextField_lostFocus_lowerLeft.png")).getImage();
	Image lostFocus_middleUpper = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/lostFocus/PTextField_lostFocus_middleUpper.png")).getImage();
	Image lostFocus_middleLower = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/lostFocus/PTextField_lostFocus_middleLower.png")).getImage();
	Image lostFocus_upperRight = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/lostFocus/PTextField_lostFocus_upperRight.png")).getImage();
	Image lostFocus_middleRight = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/lostFocus/PTextField_lostFocus_middleRight.png")).getImage();
	Image lostFocus_lowerRight = new ImageIcon(TextFieldUI.class.getResource("images/PTextField/lostFocus/PTextField_lostFocus_lowerRight.png")).getImage();

	public static ComponentUI createUI(JComponent c) {
		return new TextFieldUI();
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		// c.addMouseListener(this);
		c.setBorder(new EmptyBorder(2, 4, 2, 4));
	}

	protected void installDefaults() {
		super.installDefaults();

		getComponent().addFocusListener(this);
	}

	protected void uninstallDefaults() {
		getComponent().removeFocusListener(this);

		super.uninstallDefaults();
	}

	protected void paintBackground(Graphics g) {
		JTextComponent editor = getComponent();
		if (editor.isEnabled()) {
			editor.setBackground(Color.white);
		} else {
			editor.setBackground(new Color(230, 230, 230));
		}

		// super.paint(g, c);
		g.setColor(Color.white);
		g.fillRect(0, 0, editor.getWidth(), editor.getHeight());

		// Border border = this.getBorder();
		// int borderLeft = border.getBorderInsets(this).left;
		// int borderRight = border.getBorderInsets(this).right;
		// if (this.isEnabled()) {

		if (this.getComponent().hasFocus()) {
			g.drawImage(onFocus_upperLeft, 0, 0, 2, 2, null);
			g.drawImage(onFocus_middleLeft, 0, 2, 2, editor.getHeight() - 4, null);
			g.drawImage(onFocus_lowerLeft, 0, editor.getHeight() - 2, 2, 2, null);

			g.drawImage(onFocus_middleUpper, 2, 0, editor.getWidth() - 4, 2, null);
			g.drawImage(onFocus_middleLower, 2, editor.getHeight() - 2, editor.getWidth() - 4, 2, null);

			g.drawImage(onFocus_upperRight, editor.getWidth() - 2, 0, 2, 2, null);
			g.drawImage(onFocus_middleRight, editor.getWidth() - 2, 2, 2, editor.getHeight() - 4, null);
			g.drawImage(onFocus_lowerRight, editor.getWidth() - 2, editor.getHeight() - 2, 2, 2, null);

		} else {
			g.drawImage(lostFocus_upperLeft, 0, 0, 2, 2, null);
			g.drawImage(lostFocus_middleLeft, 0, 2, 2, editor.getHeight() - 4, null);
			g.drawImage(lostFocus_lowerLeft, 0, editor.getHeight() - 2, 2, 2, null);

			g.drawImage(lostFocus_middleUpper, 2, 0, editor.getWidth() - 4, 2, null);
			g.drawImage(lostFocus_middleLower, 2, editor.getHeight() - 2, editor.getWidth() - 4, 2, null);

			g.drawImage(lostFocus_upperRight, editor.getWidth() - 2, 0, 2, 2, null);
			g.drawImage(lostFocus_middleRight, editor.getWidth() - 2, 2, 2, editor.getHeight() - 4, null);
			g.drawImage(lostFocus_lowerRight, editor.getWidth() - 2, editor.getHeight() - 2, 2, 2, null);
		}
		// }else{
		// g.setColor(Color.red);
		// g.fillRect(0,0,getWidth(),getHeight());
		// }
	}

	public void focusGained(FocusEvent e) {
		getComponent().repaint();
	}

	public void focusLost(FocusEvent e) {
		getComponent().repaint();
	}

}
