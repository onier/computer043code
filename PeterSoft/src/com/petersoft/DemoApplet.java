package com.petersoft;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class DemoApplet extends JApplet {
	boolean isStandalone = false;
	BorderLayout borderLayout1 = new BorderLayout();
	BorderLayout borderLayout2 = new BorderLayout();
	JScrollPane pScrollPane1 = new JScrollPane();
	JLabel pLabel1 = new JLabel();

	// Get a parameter value
	// public String getParameter(String key, String def) {
	// return isStandalone ? System.getProperty(key, def) :
	// (getParameter(key) != null ? getParameter(key) : def);
	// }

	// Construct the applet
	public DemoApplet() {

	}

	// Initialize the applet
	public void init() {
		try {
			jbInit();
			JMainFrame frame = new JMainFrame();
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			pScrollPane1.setVisible(true);

			StackTraceElement stackTraceElement[] = ex.getStackTrace();
			for (StackTraceElement element : stackTraceElement) {
				pLabel1.setText(pLabel1.getText() + element.toString() + "\n");
			}

		}
	}

	// Component initializationla
	private void jbInit() throws Exception {
		this.setLayout(borderLayout2);
		this.add(pScrollPane1, java.awt.BorderLayout.CENTER);
		pScrollPane1.getViewport().add(pLabel1);
		pScrollPane1.setVisible(false);
		// new JDialog(this).setVisible(true);
		
		try {
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			 UIManager.setLookAndFeel("com.petersoft.white.PetersoftWhiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get Applet information
	public String getAppletInfo() {
		return "Applet Information";
	}

	// Get parameter info
	public String[][] getParameterInfo() {
		return null;
	}
}
