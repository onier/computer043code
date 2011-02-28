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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxUI;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

import sun.swing.SwingUtilities2;

public class CheckBoxUI extends BasicCheckBoxUI {
	// boolean mouseOver = false;
	// boolean onFocus = false;

	Image PCheckBox_box_normal = new ImageIcon(CheckBoxUI.class.getResource("images/PCheckBox/normal/PCheckBox_box_normal.png")).getImage();
	Image PCheckBox_box_checked = new ImageIcon(CheckBoxUI.class.getResource("images/PCheckBox/checked/PCheckBox_box_checked.png")).getImage();
	Image PCheckBox_box_mouseOver = new ImageIcon(CheckBoxUI.class.getResource("images/PCheckBox/mouseOver/PCheckBox_box_mouseOver.png")).getImage();
	Image PCheckBox_box_mouseOver_checked = new ImageIcon(CheckBoxUI.class.getResource("images/PCheckBox/mouseOver/PCheckBox_box_mouseOver_checked.png")).getImage();
	Image PCheckBox_box_disableNormal = new ImageIcon(CheckBoxUI.class.getResource("images/PCheckBox/disable_normal/PCheckBox_box_disableNormal.png")).getImage();
	Image PCheckBox_box_disableChecked = new ImageIcon(CheckBoxUI.class.getResource("images/PCheckBox/disable_checked/PCheckBox_box_disableChecked.png")).getImage();

	public static ComponentUI createUI(final JComponent c) {
		return new CheckBoxUI();
	}

	public void installDefaults(AbstractButton button) {
		super.installDefaults(button);
		// button.addMouseListener(this);
		button.setRolloverEnabled(true);
		button.setBorder(new EmptyBorder(2, 4, 2, 4));
		button.setOpaque(false);
	}

	public synchronized void paint(Graphics g, JComponent c) {
		AbstractButton b = (AbstractButton) c;
		ButtonModel model = b.getModel();

		Dimension size = c.getSize();

		Rectangle iconRect = new Rectangle();
		Rectangle textRect = new Rectangle();
		Rectangle viewRect = new Rectangle(size);

		Insets i = c.getInsets();
		viewRect.x += i.left;
		viewRect.y += i.top;
		viewRect.width -= (i.right + viewRect.x);
		viewRect.height -= (i.bottom + viewRect.y);

		Icon altIcon = b.getIcon();

		Font f = c.getFont();
		g.setFont(f);
		FontMetrics fm = SwingUtilities2.getFontMetrics(c, g, f);

		String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), altIcon != null ? altIcon : getDefaultIcon(), b.getVerticalAlignment(), b.getHorizontalAlignment(),
				b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, b.getIconTextGap());

		if (c.isOpaque()) {
			g.setColor(Color.white);
			g.fillRect(0, 0, size.width, size.height);
		}

		if (text != null) {
			View v = (View) c.getClientProperty(BasicHTML.propertyKey);
			if (v != null) {
				v.paint(g, textRect);
			} else {
				int mnemIndex = b.getDisplayedMnemonicIndex();
				if (model.isEnabled()) {
					g.setColor(b.getForeground());
				} else {
					// g.setColor(getDisabledTextColor());
				}
				SwingUtilities2.drawStringUnderlineCharAt(c, g, text, mnemIndex, textRect.x, textRect.y + fm.getAscent());
			}
			if (b.hasFocus() && b.isFocusPainted() && textRect.width > 0 && textRect.height > 0) {
				paintFocus(g, textRect, size);
			}
		}

		if (altIcon != null) {
			if (!model.isEnabled()) {
				if (model.isSelected()) {
					altIcon = b.getDisabledSelectedIcon();
				} else {
					altIcon = b.getDisabledIcon();
				}
			} else if (model.isPressed() && model.isArmed()) {
				altIcon = b.getPressedIcon();
				if (altIcon == null) {
					// Use selected icon
					altIcon = b.getSelectedIcon();
				}
			} else if (model.isSelected()) {
				if (b.isRolloverEnabled() && model.isRollover()) {
					altIcon = (Icon) b.getRolloverSelectedIcon();
					if (altIcon == null) {
						altIcon = (Icon) b.getSelectedIcon();
					}
				} else {
					altIcon = (Icon) b.getSelectedIcon();
				}
			} else if (b.isRolloverEnabled() && model.isRollover()) {
				altIcon = (Icon) b.getRolloverIcon();
			}

			if (altIcon == null) {
				altIcon = b.getIcon();
			}
			altIcon.paintIcon(c, g, iconRect.x, iconRect.y);
		} else {
			if (b.isEnabled()) {
				if (b.isSelected()) {
					if (b.getModel().isRollover() || c.hasFocus()) {
						g.drawImage(PCheckBox_box_mouseOver_checked, iconRect.x, (b.getHeight() - 13) / 2, null);
					} else {
						g.drawImage(PCheckBox_box_checked, iconRect.x, (b.getHeight() - 13) / 2, null);
					}
				} else {
					if (b.getModel().isRollover() || c.hasFocus()) {
						g.drawImage(PCheckBox_box_mouseOver, iconRect.x, (b.getHeight() - 13) / 2, null);
					} else {
						g.drawImage(PCheckBox_box_normal, iconRect.x, (b.getHeight() - 13) / 2, null);
					}
				}
			} else {
				if (b.isSelected()) {
					g.drawImage(PCheckBox_box_disableChecked, iconRect.x, (b.getHeight() - 13) / 2, null);
				} else {
					g.drawImage(PCheckBox_box_disableNormal, iconRect.x, (b.getHeight() - 13) / 2, null);

				}
			}
		}

	}
}
