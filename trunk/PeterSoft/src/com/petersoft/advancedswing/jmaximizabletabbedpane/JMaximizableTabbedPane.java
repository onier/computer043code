package com.petersoft.advancedswing.jmaximizabletabbedpane;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.TabbedPaneUI;

public class JMaximizableTabbedPane extends JTabbedPane implements MouseListener, MouseMotionListener {
	double scaleRatio = 0.7;

	private HashMap<String, Component> maps = new HashMap<String, Component>();

	private boolean isMaximized;

	LayoutManager layout;
	Component[] components;
	JMaximizableTabbedPane originalClosableTabbedPane;
	int originalSelectedIndex;

	final String ENDING_STRING = "    ";
	HashSet<Integer> closableTabIndex = new HashSet<Integer>();

	public JMaximizableTabbedPane() {
		super();
		init(false);
	}

	public JMaximizableTabbedPane(boolean isMaximized) {
		super();
		init(isMaximized);
	}

	public JMaximizableTabbedPane(int tabPlacement) {
		super(tabPlacement);
		init(false);
	}

	public JMaximizableTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
		init(false);
	}

	private void init(boolean isMaximized) {
		this.isMaximized = isMaximized;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void setUI(TabbedPaneUI ui) {
		super.setUI(new JMaximizableTabbedPaneUI());
	}

	public HashSet<Integer> getClosableTabIndex() {
		return closableTabIndex;
	}

	public void setClosableTabIndex(HashSet<Integer> closableTabIndex) {
		this.closableTabIndex = closableTabIndex;
	}

	public int getOriginalSelectedIndex() {
		return originalSelectedIndex;
	}

	public void setOriginalSelectedIndex(int originalSlectedIndex) {
		this.originalSelectedIndex = originalSlectedIndex;
	}

	public JMaximizableTabbedPane getOriginalClosableTabbedPane() {
		return originalClosableTabbedPane;
	}

	public void setOriginalClosableTabbedPane(JMaximizableTabbedPane originalClosableTabbedPane) {
		this.originalClosableTabbedPane = originalClosableTabbedPane;
	}

	public boolean isMaximized() {
		return isMaximized;
	}

	public void setMaximized(boolean isMaximized) {
		this.isMaximized = isMaximized;
	}

	public void addTabWithCloseButton(String title, Icon icon, Component component, String tip) {
		super.addTab(title, icon, component, tip);
		this.closableTabIndex.add(this.getTabCount() - 1);
	}

	public void insertTab(String title, Icon icon, Component component, String tooltip, int index) {
		try {
			tooltip = "tab" + component.hashCode();
			maps.put(tooltip, component);
			if (!title.endsWith(ENDING_STRING)) {
				title += ENDING_STRING;
			}
		} catch (Exception ex) {
			// without this try-catch, jigloo cannot display the correct UI
		}
		super.insertTab(title, icon, component, tooltip, index);
	}

	public void removeTabAt(int index) {
		Component component = getComponentAt(index);
		maps.remove("tab" + component.hashCode());
		closableTabIndex.remove(index);

		Integer[] tempArray = closableTabIndex.toArray(new Integer[0]);
		for (int x = index; x < tempArray.length; x++) {
			tempArray[x] -= 1;
		}
		closableTabIndex.clear();
		for (int x = 0; x < tempArray.length; x++) {
			if (tempArray[x] >= 0) {
				closableTabIndex.add(tempArray[x]);
			}
		}
		super.removeTabAt(index);
	}

	public JToolTip createToolTip() {
		ImageToolTip tooltip = new ImageToolTip();
		tooltip.setComponent(this);
		return tooltip;
	}

	class ImageToolTip extends JToolTip {
		public Dimension getPreferredSize() {
			String tip = getTipText();
			Component component = maps.get(tip);
			if (component != null) {
				return new Dimension((int) (getScaleRatio() * component.getWidth()), (int) (getScaleRatio() * component.getHeight()));
			} else {
				return super.getPreferredSize();
			}
		}

		public void paintComponent(Graphics g) {
			String tip = getTipText();
			Component component = maps.get(tip);
			if (component instanceof JComponent) {
				JComponent jcomponent = (JComponent) component;
				Graphics2D g2d = (Graphics2D) g;
				AffineTransform at = g2d.getTransform();
				g2d.transform(AffineTransform.getScaleInstance(getScaleRatio(), getScaleRatio()));
				ArrayList<JComponent> dbcomponents = new ArrayList<JComponent>();
				updateDoubleBuffered(jcomponent, dbcomponents);
				jcomponent.paint(g);
				resetDoubleBuffered(dbcomponents);
				g2d.setTransform(at);
			}
		}

		private void updateDoubleBuffered(JComponent component, ArrayList<JComponent> dbcomponents) {
			if (component.isDoubleBuffered()) {
				dbcomponents.add(component);
				component.setDoubleBuffered(false);
			}
			for (int i = 0; i < component.getComponentCount(); i++) {
				Component c = component.getComponent(i);
				if (c instanceof JComponent) {
					updateDoubleBuffered((JComponent) c, dbcomponents);
				}
			}
		}

		private void resetDoubleBuffered(ArrayList<JComponent> dbcomponents) {
			for (JComponent component : dbcomponents) {
				component.setDoubleBuffered(true);
			}
		}
	}

	public double getScaleRatio() {
		return scaleRatio;
	}

	public void setScaleRatio(double scaleRatio) {
		this.scaleRatio = scaleRatio;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			Component obj = this.getParent();
			while (obj != null) {
				if (obj instanceof JMaximizableTabbedPane_BasePanel) {
					JMaximizableTabbedPane_BasePanel basePanel = (JMaximizableTabbedPane_BasePanel) obj;
					JMaximizableTabbedPane tabbedPane = (JMaximizableTabbedPane) e.getSource();
					// tabbedPane.setUI(new JMaximizableTabbedPaneUI());
//					System.out.println(tabbedPane.getUI());
					int selectedIndex = tabbedPane.getSelectedIndex();
					if (!tabbedPane.isMaximized) {
						JMaximizableTabbedPane newTabbedPane = new JMaximizableTabbedPane(true);
						newTabbedPane.setOriginalClosableTabbedPane(this);
						newTabbedPane.setOriginalSelectedIndex(this.getSelectedIndex());
						// newTabbedPane.setOriginalIcon(this.getIconAt(this.getSelectedIndex()));

						int tempSelectedIndex = this.getSelectedIndex();
						// add all components to newTabbedPane
						Component components[] = new Component[this.getTabCount()];
						String componentsName[] = new String[this.getTabCount()];
						Icon componentsIcon[] = new Icon[this.getTabCount()];
						HashSet<Integer> closableTabIndex = (HashSet<Integer>) this.getClosableTabIndex().clone();
						for (int x = 0; x < this.getTabCount(); x++) {
							components[x] = this.getComponentAt(x);
							componentsName[x] = this.getTitleAt(x);
							componentsIcon[x] = this.getIconAt(x);
						}
						for (int x = 0; x < components.length; x++) {
							newTabbedPane.addTab(componentsName[x], componentsIcon[x], components[x]);
						}
						// end add all components to newTabbedPane

						newTabbedPane.setClosableTabIndex(closableTabIndex);
						newTabbedPane.setSelectedIndex(tempSelectedIndex);

						JPanel panel1 = new JPanel();
						panel1.setLayout(new BorderLayout());
						panel1.add(newTabbedPane, BorderLayout.CENTER);
						JToolBar eastPanel = new JToolBar();
						JToolBar southPanel = new JToolBar();
						JToolBar westPanel = new JToolBar();
						JToolBar northPanel = new JToolBar();
						eastPanel.setLayout(new BoxLayout(eastPanel, javax.swing.BoxLayout.Y_AXIS));
						southPanel.setLayout(new BoxLayout(southPanel, javax.swing.BoxLayout.X_AXIS));
						westPanel.setLayout(new BoxLayout(westPanel, javax.swing.BoxLayout.Y_AXIS));
						northPanel.setLayout(new BoxLayout(northPanel, javax.swing.BoxLayout.X_AXIS));

						panel1.add(eastPanel, BorderLayout.EAST);
						panel1.add(southPanel, BorderLayout.SOUTH);
						panel1.add(westPanel, BorderLayout.WEST);
						panel1.add(northPanel, BorderLayout.NORTH);

						// get all the tabs
						/*
						 * at this monent, all the tabs in current tabbedpane is moved to the newtab, so getAllTabs will not able to get them
						 */
						HashSet<Component> allComponents = new HashSet<Component>();
						getAllTabs(this, allComponents);
						deleteExceptTabbedPane(allComponents);
						for (Component c : allComponents) {
							if (c instanceof JTabbedPane) {
								final JTabbedPane jTabbedPane = (JTabbedPane) c;

								Point p = new Point();
								SwingUtilities.convertPointToScreen(p, jTabbedPane);

								Point currentPoint = new Point();
								SwingUtilities.convertPointToScreen(currentPoint, this);

								for (int x = 0; x < jTabbedPane.getTabCount(); x++) {
									if (jTabbedPane.getIconAt(x) != null) {
										JButton jButton = new JButton(jTabbedPane.getIconAt(x));
										jButton.setToolTipText(jTabbedPane.getTitleAt(x));

										class MyActionListener implements ActionListener {
											JTabbedPane jMaximizedTabbedPane;
											int selectedIndex;

											public MyActionListener(JTabbedPane jTabbedPane, int selectedIndex) {
												this.jMaximizedTabbedPane = jTabbedPane;
												this.selectedIndex = selectedIndex;
											}

											public void actionPerformed(ActionEvent e) {
												// MouseEvent mouseEvent = new
												// MouseEvent(jMaximizedTabbedPane,
												// 0, 0, 0, 0, 0, 2, false);
												// mouseClicked(mouseEvent);
												detachTab(jTabbedPane, selectedIndex);
											}
										}

										jButton.addActionListener(new MyActionListener(newTabbedPane, x));
										if (p.x < currentPoint.x) {
											westPanel.add(jButton);
										} else if (p.x > currentPoint.x) {
											eastPanel.add(jButton);
										} else if (p.y < currentPoint.y) {
											northPanel.add(jButton);
										} else {
											southPanel.add(jButton);
										}
									}
								}
							}
						}
						// end get all the tabs

						if (eastPanel.getComponentCount() == 0) {
							eastPanel.setVisible(false);
						} else {
							eastPanel.setVisible(true);
						}
						if (southPanel.getComponentCount() == 0) {
							southPanel.setVisible(false);
						} else {
							southPanel.setVisible(true);
						}
						if (westPanel.getComponentCount() == 0) {
							westPanel.setVisible(false);
						} else {
							westPanel.setVisible(true);
						}
						if (northPanel.getComponentCount() == 0) {
							northPanel.setVisible(false);
						} else {
							northPanel.setVisible(true);
						}

						basePanel.add(panel1, "newtab");
						CardLayout cardLayout = (CardLayout) basePanel.getLayout();
						cardLayout.show(basePanel, "newtab");
						return;
					} else {

						Component components[] = new Component[tabbedPane.getTabCount()];
						String componentsName[] = new String[tabbedPane.getTabCount()];
						Icon componentsIcon[] = new Icon[tabbedPane.getTabCount()];
						HashSet<Integer> closableTabIndex = (HashSet<Integer>) tabbedPane.getClosableTabIndex().clone();
						for (int x = 0; x < tabbedPane.getTabCount(); x++) {
							components[x] = tabbedPane.getComponentAt(x);
							componentsName[x] = tabbedPane.getTitleAt(x);
							componentsIcon[x] = tabbedPane.getIconAt(x);
						}
						for (int x = 0; x < components.length; x++) {
							tabbedPane.getOriginalClosableTabbedPane().addTab(componentsName[x], componentsIcon[x], components[x]);
						}
//						System.out.println("aa=" + closableTabIndex);
						tabbedPane.getOriginalClosableTabbedPane().setClosableTabIndex(closableTabIndex);
						tabbedPane.getOriginalClosableTabbedPane().setSelectedIndex(selectedIndex);
						CardLayout cardLayout = (CardLayout) basePanel.getLayout();
						cardLayout.show(basePanel, "MAIN");
						return;
					}
				}
				obj = obj.getParent();
			}
		} else {
			int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
			if (this.closableTabIndex.contains(tabNumber) && tabNumber >= 0) {
				Rectangle tabRect = getUI().getTabBounds(this, tabNumber);

				if (e.getX() > tabRect.x + tabRect.width - 20) {
					this.removeTabAt(tabNumber);
				}
			}
		}
	}

	public void detachTab(final JTabbedPane jTabbedPane, int index) {
		if (index < 0 || index >= jTabbedPane.getTabCount())
			return;

		final JFrame frame = new JFrame();

		Window parentWindow = SwingUtilities.windowForComponent(this);

		final int tabIndex = index;

		final JComponent c = (JComponent) jTabbedPane.getComponentAt(tabIndex);

		final Icon icon = jTabbedPane.getIconAt(tabIndex);
		final String title = jTabbedPane.getTitleAt(tabIndex);
		final String toolTip = jTabbedPane.getToolTipTextAt(tabIndex);
		final Border border = c.getBorder();

		jTabbedPane.removeTabAt(index);

		c.setPreferredSize(c.getSize());

		frame.setTitle(title);
		frame.getContentPane().add(c);
		frame.setLocation(parentWindow.getLocation());
		frame.pack();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				frame.dispose();
				jTabbedPane.insertTab(title, icon, c, toolTip, Math.min(tabIndex, jTabbedPane.getTabCount()));

				c.setBorder(border);
				jTabbedPane.setSelectedComponent(c);
			}

		});

		WindowFocusListener windowFocusListener = new WindowFocusListener() {
			long start;

			long end;

			public void windowGainedFocus(WindowEvent e) {
				start = System.currentTimeMillis();
			}

			public void windowLostFocus(WindowEvent e) {
				end = System.currentTimeMillis();
				long elapsed = end - start;
				// System.out.println(elapsed);
				if (elapsed < 100)
					frame.toFront();

				frame.removeWindowFocusListener(this);
			}
		};

		/*
		 * This is a small hack to avoid Windows GUI bug, that prevent a new window from stealing focus (without this windowFocusListener, most of the time the new frame would just
		 * blink from foreground to background). A windowFocusListener is added to the frame, and if the time between the frame beeing in foreground and the frame beeing in
		 * background is less that 100ms, it just brings the windows to the front once again. Then it removes the windowFocusListener. Note that this hack would not be required on
		 * Linux or UNIX based systems.
		 */

		frame.addWindowFocusListener(windowFocusListener);

		frame.show();
		frame.toFront();

	}

	private void deleteExceptTabbedPane(HashSet<Component> components) {
		Iterator<Component> it = components.iterator();
		while (it.hasNext()) {
			Component component = it.next();
			if (!(component instanceof JTabbedPane)) {
				it.remove();
			}
		}
	}

	private void getAllTabs(Component component, HashSet<Component> list) {
		if (list.contains(component)) {
			return;
		}
		if (component instanceof JMaximizableTabbedPane_BasePanel) {
			return;
		}
		list.add(component);

		if (component instanceof JPanel) {
			Component[] components = ((JPanel) component).getComponents();
			for (Component c : components) {
				getAllTabs(c, list);
			}
		}
		if (component instanceof JSplitPane) {
			JSplitPane jSplitPane = (JSplitPane) component;
			Component[] components = jSplitPane.getComponents();
			for (Component c : components) {
				getAllTabs(c, list);
			}
		}
		if (component.getParent() != null) {
			getAllTabs(component.getParent(), list);
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
		if (this.closableTabIndex.contains(tabNumber) && tabNumber >= 0) {
			Rectangle tabRect = getUI().getTabBounds(this, tabNumber);

			if (e.getX() > tabRect.x + tabRect.width - 20) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else {
				setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			}
		}
	}
}
