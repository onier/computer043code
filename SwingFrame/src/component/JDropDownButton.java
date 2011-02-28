/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package component;

import apaint.ColorLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.plaf.ButtonUI;

/**
 *
 * @author admin
 */
public class JDropDownButton extends JButton {

    private JButton downButton;
    private String text;
    private JButton mainButton;
    private Icon icon;
    private Action action;

    /**
     * separator true????????????action
     * @param separator
     */
    public JDropDownButton(boolean separator) {
        mainButton = new JButton(text);
        initComponent(separator);
    }

    public void setMainButtonBorderColor(Color color) {
        mainButton.putClientProperty(ColorLookAndFeel.BUTTON_BORDER, color);
    }

    public void setDownButtonBorderColor(Color color) {
        downButton.putClientProperty(ColorLookAndFeel.BUTTON_BORDER, color);
    }

    public JDropDownButton(String text, boolean separator) {
        this.text = text;
        mainButton = new JButton(text);
        initComponent(separator);
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        mainButton.setEnabled(b);
        this.downButton.setEnabled(b);
    }

    @Override
    public void updateUI() {
        setUI((ButtonUI) UIManager.getUI(this));
    }

    public JDropDownButton(Action action) {
        this.action = action;
        this.text = action.getValue(Action.NAME).toString();
        this.icon = (Icon) action.getValue(Action.SMALL_ICON);
        mainButton = new JButton(action);
        mainButton.getModel().addActionListener(action);
        initComponent(false);
    }

    public void addMainButtonAction(ActionListener l) {
        if (this.getDownButton() != null) {
            mainButton.addActionListener(l);
        }
    }

    public void addDownButtonAction(ActionListener l) {
        if (this.getDownButton() != null) {
            getDownButton().addActionListener(l);
        }
    }

    public JDropDownButton(String text, Icon icon, boolean separator) {
        this.text = text;
        this.icon = icon;
        mainButton = new JButton(text, icon);
        initComponent(separator);
    }

    @Override
    public void setText(String text) {
        this.text = text;
        getMainButton().setText(text);
    }

    /**
     * ?????addDownButtonAction()\addMainButtonAction()??
     * @param l
     * @deprecated
     */
    @Override
    @Deprecated
    public void addActionListener(ActionListener l) {
    }

    @Override
    public void setIcon(Icon defaultIcon) {
        this.icon = defaultIcon;
        getMainButton().setIcon(icon);
    }

    protected void initComponent(boolean separator) {
        setBorder(null);
        setLayout(new BorderLayout());
        add(getMainButton(), BorderLayout.CENTER);
        downButton = new javax.swing.JButton();
        downButton.putClientProperty(ColorLookAndFeel.BUTTON_LAYOUT, ColorLookAndFeel.BUTTON_LAYOUT_LEFT);
        mainButton.putClientProperty(ColorLookAndFeel.BUTTON_LAYOUT, ColorLookAndFeel.BUTTON_LAYOUT_RIGHT);
        getDownButton().setIcon(new javax.swing.ImageIcon(getClass().getResource("/component/Down.gif")));
        getDownButton().setBorderPainted(false);
        add(getDownButton(), BorderLayout.EAST);
        downButton.putClientProperty(ColorLookAndFeel.BUTTON_SEPARATOR, separator);
        mainButton.putClientProperty(ColorLookAndFeel.BUTTON_SEPARATOR, separator);
        if (!separator) {
            downButton.setModel(mainButton.getModel());
        }
        downButton.addPropertyChangeListener("mouseIn", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                mainButton.putClientProperty("mouseIn", evt.getNewValue());
            }
        });
        mainButton.addPropertyChangeListener("mouseIn", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                downButton.putClientProperty("mouseIn", evt.getNewValue());
            }
        });
    }

//    @Override
//    public String getUIClassID() {
//        return uiClassID;
//    }
    /**
     * @return the downButton
     */
    public JButton getDownButton() {
        return downButton;
    }

    /**
     * @return the mainButton
     */
    public JButton getMainButton() {
        return mainButton;
    }
}
