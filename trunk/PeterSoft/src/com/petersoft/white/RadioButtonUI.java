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
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import javax.swing.text.View;

import sun.swing.SwingUtilities2;

public class RadioButtonUI extends BasicRadioButtonUI {

    Image PRadioButton_box_normal = new ImageIcon(RadioButtonUI.class.getResource("images/PRadioButton/normal/PRadioButton_normal.png")).getImage();
    Image PRadioButton_box_checked = new ImageIcon(RadioButtonUI.class.getResource("images/PRadioButton/checked/PRadioButton_checked.png")).getImage();
    Image PRadioButton_box_mouseOver = new ImageIcon(RadioButtonUI.class.getResource("images/PRadioButton/mouseOver/PRadioButton_mouseOver.png")).getImage();
    Image PRadioButton_box_mouseOver_checked = new ImageIcon(RadioButtonUI.class.getResource("images/PRadioButton/mouseOver/PRadioButton_mouseOver_checked.png")).getImage();
    Image PRadioButton_box_disableChecked = new ImageIcon(RadioButtonUI.class.getResource("images/PRadioButton/disable_checked/PRadioButton_checked.png")).getImage();
    Image PRadioButton_disableNormal = new ImageIcon(RadioButtonUI.class.getResource("images/PRadioButton/disable_normal/PRadioButton_normal.png")).getImage();

    public static ComponentUI createUI(JComponent c) {
        return new RadioButtonUI();
    }

    @Override
    public void installDefaults(AbstractButton button) {
        super.installDefaults(button);
        button.setOpaque(false);
        button.setRolloverEnabled(true);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
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

        String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), altIcon != null ? altIcon : getDefaultIcon(), b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, b.getIconTextGap());

        if (c.isOpaque()) {
            g.setColor(Color.white);
            g.fillRect(0, 0, size.width, size.height);
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
                        g.drawImage(PRadioButton_box_mouseOver_checked, 4, (b.getHeight() - 13) / 2, null);
                    } else {
                        g.drawImage(PRadioButton_box_checked, 4, (b.getHeight() - 13) / 2, null);
                    }
                } else {
                    if (b.getModel().isRollover() || c.hasFocus()) {
                        g.drawImage(PRadioButton_box_mouseOver, 4, (b.getHeight() - 13) / 2, null);
                    } else {
                        g.drawImage(PRadioButton_box_normal, 4, (b.getHeight() - 13) / 2, null);
                    }
                }
            } else {
                if (b.isSelected()) {
                    g.drawImage(PRadioButton_box_disableChecked, 4, (b.getHeight() - 13) / 2, null);
                } else {
                    g.drawImage(PRadioButton_disableNormal, 4, (b.getHeight() - 13) / 2, null);

                }
            }
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
    }
}
