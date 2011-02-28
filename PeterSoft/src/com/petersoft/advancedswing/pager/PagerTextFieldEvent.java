package com.petersoft.advancedswing.pager;

import java.util.EventObject;

public class PagerTextFieldEvent extends EventObject {
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public PagerTextFieldEvent(Object source) {
		super(source);
	}
}
