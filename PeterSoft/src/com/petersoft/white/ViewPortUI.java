package com.petersoft.white;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.ViewportUI;
import javax.swing.plaf.basic.BasicViewportUI;

public class ViewPortUI extends BasicViewportUI {

	// Shared UI object
	private static ViewportUI viewportUI;

	public static ComponentUI createUI(JComponent c) {
		if (viewportUI == null) {
			viewportUI = new ViewPortUI();
		}
		return viewportUI;
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		installDefaults(c);
	}

	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);

	}

	protected void installDefaults(JComponent c) {
		LookAndFeel.installColorsAndFont(c, "Viewport.background", "Viewport.foreground", "Viewport.font");
		LookAndFeel.installProperty(c, "opaque", Boolean.FALSE);
	}

	protected void uninstallDefaults(JComponent c) {
	}
}
