package com.petersoft.white;

import java.beans.PropertyChangeEvent;

import javax.swing.AbstractButton;
import javax.swing.plaf.basic.BasicButtonListener;

public class ButtonListener extends BasicButtonListener {
	public ButtonListener(AbstractButton button) {
		super(button);
	}

	public void propertyChange(PropertyChangeEvent e) {
		super.propertyChange(e);
		if (e.getPropertyName().equals(AbstractButton.ROLLOVER_ENABLED_CHANGED_PROPERTY)) {
			AbstractButton b = (AbstractButton) e.getSource();
		}
	}
}
