package com.petersoft.white;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class SplitPaneUI extends BasicSplitPaneUI {
	public static ComponentUI createUI(JComponent x) {
		return new SplitPaneUI();
	}

	public BasicSplitPaneDivider createDefaultDivider() {
		return new SplitPaneDivider(this);
	}

}
