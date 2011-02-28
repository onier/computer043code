package com.petersoft.white;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;

public class ComboBox_ComboBoxEditor implements ComboBoxEditor {
	JTextField pTextField = new JTextField();

	public ComboBox_ComboBoxEditor() {
		super();
	}

	public Component getEditorComponent() {
		return pTextField;
	}

	public void setItem(Object anObject) {
		if (anObject != null) {
			pTextField.setText(anObject.toString());
		}
	}

	public Object getItem() {
		return pTextField.getText();
	}

	public void selectAll() {
		pTextField.setSelectionStart(0);
		pTextField.setSelectionEnd(pTextField.getText().length());
	}

	public void addActionListener(ActionListener l) {
		pTextField.addActionListener(l);
	}

	public void removeActionListener(ActionListener l) {
		pTextField.removeActionListener(l);
	}

}
