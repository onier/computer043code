package apaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;

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

public class ColorRadioButtonUI extends BasicRadioButtonUI {

    private static final ColorRadioButtonUI radioButtonUI = new ColorRadioButtonUI();
    private Image PRadioButton_box_normal;//= new ImageIcon(ColorRadioButtonUI.class.getResource("/apaint/PRadioButton_normal.png")).getImage();
    private Image PRadioButton_box_checked;// = new ImageIcon(ColorRadioButtonUI.class.getResource("/apaint/PRadioButton_checked.png")).getImage();
    private Image PRadioButton_box_mouseOver;//= new ImageIcon(ColorRadioButtonUI.class.getResource("/apaint/PRadioButton_mouseOver.png")).getImage();
    private Image PRadioButton_box_mouseOver_checked;// = new ImageIcon(ColorRadioButtonUI.class.getResource("/apaint/PRadioButton_mouseOver_checked.png")).getImage();
    private Image PRadioButton_box_disableChecked;// = new ImageIcon(ColorRadioButtonUI.class.getResource("/apaint/PRadioButton_checked.png")).getImage();
    private Image PRadioButton_disableNormal;// = new ImageIcon(ColorRadioButtonUI.class.getResource("/apaint/D_PRadioButton_normal.png.png")).getImage();

    public static ComponentUI createUI(JComponent b) {
        return radioButtonUI;
    }

    @Override
    public void installDefaults(AbstractButton button) {
        super.installDefaults(button);
        button.setOpaque(false);
        button.setRolloverEnabled(true);
    }

    @Override
    public void paint(Graphics gg, JComponent c) {
        Graphics2D g = (Graphics2D) gg;
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(map);
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
                        g.drawImage(getPRadioButton_box_mouseOver_checked(), 4, (b.getHeight() - 13) / 2, null);
                    } else {
                        g.drawImage(getPRadioButton_box_checked(), 4, (b.getHeight() - 13) / 2, null);
                    }
                } else {
                    if (b.getModel().isRollover() || c.hasFocus()) {
                        g.drawImage(getPRadioButton_box_mouseOver(), 4, (b.getHeight() - 13) / 2, null);
                    } else {
                        g.drawImage(getPRadioButton_box_normal(), 4, (b.getHeight() - 13) / 2, null);
                    }
                }
            } else {
                if (b.isSelected()) {
                    g.drawImage(getPRadioButton_box_disableChecked(), 4, (b.getHeight() - 13) / 2, null);
                } else {
                    g.drawImage(getPRadioButton_disableNormal(), 4, (b.getHeight() - 13) / 2, null);

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

    /**
     * @return the PRadioButton_box_normal
     */
    public Image getPRadioButton_box_normal() {
        if (PRadioButton_box_normal == null) {
            PRadioButton_box_normal = new ImageIcon(ColorRadioButtonUI.class.getResource("/apaint/PRadioButton_normal.png")).getImage();
        }
        return PRadioButton_box_normal;
    }

    /**
     * @return the PRadioButton_box_checked
     */
    public Image getPRadioButton_box_checked() {
        if (PRadioButton_box_checked == null) {
            PRadioButton_box_checked = new ImageIcon(ColorRadioButtonUI.class.getResource("/apaint/PRadioButton_checked.png")).getImage();
        }
        return PRadioButton_box_checked;
    }

    /**
     * @return the PRadioButton_box_mouseOver
     */
    public Image getPRadioButton_box_mouseOver() {
        if (PRadioButton_box_mouseOver == null) {
            PRadioButton_box_mouseOver = new ImageIcon(ColorRadioButtonUI.class.getResource("/apaint/PRadioButton_mouseOver.png")).getImage();
        }
        return PRadioButton_box_mouseOver;
    }

    /**
     * @return the PRadioButton_box_mouseOver_checked
     */
    public Image getPRadioButton_box_mouseOver_checked() {
        if (PRadioButton_box_mouseOver_checked == null) {
            PRadioButton_box_mouseOver_checked = new ImageIcon(ColorRadioButtonUI.class.getResource("/apaint/PRadioButton_mouseOver_checked.png")).getImage();
        }
        return PRadioButton_box_mouseOver_checked;
    }

    /**
     * @return the PRadioButton_box_disableChecked
     */
    public Image getPRadioButton_box_disableChecked() {
        if (PRadioButton_box_disableChecked == null) {
            PRadioButton_box_disableChecked = new ImageIcon(ColorRadioButtonUI.class.getResource("/apaint/PRadioButton_checked.png")).getImage();
        }
        return PRadioButton_box_disableChecked;
    }

    /**
     * @return the PRadioButton_disableNormal
     */
    public Image getPRadioButton_disableNormal() {
        if (PRadioButton_disableNormal == null) {
            PRadioButton_disableNormal = new ImageIcon(ColorRadioButtonUI.class.getResource("/apaint/D_PRadioButton_normal.png.png")).getImage();
        }
        return PRadioButton_disableNormal;
    }
}
