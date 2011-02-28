package com.petersoft.white;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ContainerListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicToolBarUI;

public class ToolBarUI extends BasicToolBarUI {

	Image toolbarBackgroundImage = new ImageIcon(ToolBarUI.class.getResource("images/PToolBar/toolbarBackground.png")).getImage();
	private static List components = new ArrayList();

	protected ContainerListener contListener;

	private static Border nonRolloverBorder;

	synchronized static void register(JComponent c) {
		if (c == null) {
			// Exception is thrown as convenience for callers that are
			// typed to throw an NPE.
			throw new NullPointerException("JComponent must be non-null");
		}
		components.add(new WeakReference(c));
	}

	synchronized static void unregister(JComponent c) {
		for (int counter = components.size() - 1; counter >= 0; counter--) {
			// Search for the component, removing any flushed references
			// along the way.
			WeakReference ref = (WeakReference) components.get(counter);
			Object target = ((WeakReference) components.get(counter)).get();

			if (target == c || target == null) {
				components.remove(counter);
			}
		}
	}

	synchronized static Object findRegisteredComponentOfType(JComponent from, Class target) {
		JRootPane rp = SwingUtilities.getRootPane(from);
		if (rp != null) {
			for (int counter = components.size() - 1; counter >= 0; counter--) {
				Object component = ((WeakReference) components.get(counter)).get();

				if (component == null) {
					// WeakReference has gone away, remove the WeakReference
					components.remove(counter);
				} else if (target.isInstance(component) && SwingUtilities.getRootPane((Component) component) == rp) {
					return component;
				}
			}
		}
		return null;
	}

	static boolean doesMenuBarBorderToolBar(JMenuBar c) {
		JToolBar tb = (JToolBar) ToolBarUI.findRegisteredComponentOfType(c, JToolBar.class);
		if (tb != null && tb.getOrientation() == JToolBar.HORIZONTAL) {
			JRootPane rp = SwingUtilities.getRootPane(c);
			Point point = new Point(0, 0);
			point = SwingUtilities.convertPoint(c, point, rp);
			int menuX = point.x;
			int menuY = point.y;
			point.x = point.y = 0;
			point = SwingUtilities.convertPoint(tb, point, rp);
			return (point.x == menuX && menuY + c.getHeight() == point.y && c.getWidth() == tb.getWidth());
		}
		return false;
	}

	public static ComponentUI createUI(JComponent c) {
		return new ToolBarUI();
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		setRolloverBorders(true);
		register(c);
	}

	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
		nonRolloverBorder = null;
		unregister(c);
	}

	protected void installListeners() {
		super.installListeners();

		contListener = createContainerListener();
		if (contListener != null) {
			toolBar.addContainerListener(contListener);
		}
	}

	protected void uninstallListeners() {
		super.uninstallListeners();

		if (contListener != null) {
			toolBar.removeContainerListener(contListener);
		}

	}

	protected Border createRolloverBorder() {
		return new LineBorder(Color.blue);
	}

	protected Border createNonRolloverBorder() {
		return super.createNonRolloverBorder();
	}

	private Border createNonRolloverToggleBorder() {
		return createNonRolloverBorder();
	}

	protected void setBorderToNonRollover(Component c) {
		if (c instanceof JToggleButton && !(c instanceof JCheckBox)) {
			JToggleButton b = (JToggleButton) c;
			Border border = b.getBorder();
			super.setBorderToNonRollover(c);
			if (border instanceof UIResource) {
				if (nonRolloverBorder == null) {
					nonRolloverBorder = createNonRolloverToggleBorder();
				}
				b.setBorder(nonRolloverBorder);
			}
		} else {
			super.setBorderToNonRollover(c);
		}
	}

	protected ContainerListener createContainerListener() {
		return null;
	}

	protected void setDragOffset(Point p) {
		if (!GraphicsEnvironment.isHeadless()) {
			if (dragWindow == null) {
				dragWindow = createDragWindow(toolBar);
			}
			dragWindow.setOffset(p);
		}
	}

	public void update(Graphics g, JComponent c) {
		super.update(g, c);
		// System.out.println(c.getClass().getName());
		// JOptionPane.showMessageDialog(null,
		// c.getParent().getClass().getName() + "\n" + //
		// c.getParent().getParent().getClass().getName());
		// JOptionPane.showMessageDialog(null,
		// c.getParent().getParent().getClass().getName()); //
		// System.out.println();

		g.drawImage(toolbarBackgroundImage, 0, 0, 99999, c.getHeight(), null);

		// g.setColor(new Color(170, 179, 179));
		// g.drawLine(0, 0, c.getWidth(), 0);
		// g.drawLine(0, 0, 0, c.getHeight());

		// g.setColor(new Color(145, 153, 154));
		// g.drawLine(c.getWidth() - 1, 0, c.getWidth() - 1, c.getHeight());
		// g.drawLine(0, c.getHeight() - 1, c.getWidth(), c.getHeight() - 1);
	}

}
