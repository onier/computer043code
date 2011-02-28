package com.petersoft.advancedswing.pager;

import java.util.EventObject;

public class PagerEvent extends EventObject {
	public static int FIRST_PAGE_BUTTON = 0;
	public static int PREVIOUS_PAGE_BUTTON = 1;
	public static int NEXT_PAGE_BUTTON = 2;
	public static int LAST_PAGE_BUTTON = 3;
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public PagerEvent(Object source) {
		super(source);
	}
}
