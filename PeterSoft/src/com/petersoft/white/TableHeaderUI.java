package com.petersoft.white;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.CellRendererPane;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.LookAndFeel;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TableHeaderUI extends BasicTableHeaderUI {
	private static Cursor resizeCursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
	protected final static int HEADER_HEIGHT = 22;
	private int columnSelected = -1;

	Image normalBG = new ImageIcon(TableHeaderUI.class.getResource("images/PTable/PTable_Header.png")).getImage();
	Image mouseOverBG = new ImageIcon(TableHeaderUI.class.getResource("images/PTable/PTable_Header_mouseOver.png")).getImage();

	boolean mouseOver;
	int mouseOverColumn = -1;

	protected JTableHeader header;
	protected CellRendererPane rendererPane;

	protected MouseInputListener mouseInputListener;

	protected MouseInputListener createMouseInputListener() {
		return new MouseInputHandler();
	}

	public static ComponentUI createUI(JComponent h) {
		return new TableHeaderUI();
	}

	public void installUI(JComponent c) {
		header = (JTableHeader) c;

		rendererPane = new CellRendererPane();
		header.add(rendererPane);

		installDefaults();
		installListeners();
		installKeyboardActions();
	}

	protected void installDefaults() {
		LookAndFeel.installColorsAndFont(header, "TableHeader.background", "TableHeader.foreground", "TableHeader.font");
	}

	protected void installListeners() {
		mouseInputListener = createMouseInputListener();

		header.addMouseListener(mouseInputListener);
		header.addMouseMotionListener(mouseInputListener);
	}

	protected void installKeyboardActions() {
	}

	public void uninstallUI(JComponent c) {
		uninstallDefaults();
		uninstallListeners();
		uninstallKeyboardActions();

		header.remove(rendererPane);
		rendererPane = null;
		header = null;
	}

	protected void uninstallDefaults() {
	}

	protected void uninstallListeners() {
		header.removeMouseListener(mouseInputListener);
		header.removeMouseMotionListener(mouseInputListener);

		mouseInputListener = null;
	}

	protected void uninstallKeyboardActions() {
	}

	public void paint(Graphics g, JComponent c) {
		if (header.getColumnModel().getColumnCount() <= 0) {
			return;
		}

		boolean ltr = header.getComponentOrientation().isLeftToRight();

		Rectangle clip = g.getClipBounds();
		Point left = clip.getLocation();
		Point right = new Point((clip.x + clip.width) - 1, clip.y);
		TableColumnModel cm = header.getColumnModel();
		int cMin = header.columnAtPoint(ltr ? left : right);
		int cMax = header.columnAtPoint(ltr ? right : left);

		// This should never happen.
		if (cMin == -1) {
			cMin = 0;
		}

		// If the table does not have enough columns to fill the view we'll get
		// -1.
		// Replace this with the index of the last column.
		if (cMax == -1) {
			cMax = cm.getColumnCount() - 1;
		}

		TableColumn draggedColumn = header.getDraggedColumn();
		int columnWidth;
		int columnMargin = cm.getColumnMargin();
		Rectangle cellRect = header.getHeaderRect(cMin);
		TableColumn aColumn;

		if (ltr) {
			for (int column = cMin; column <= cMax; column++) {
				aColumn = cm.getColumn(column);
				columnWidth = aColumn.getWidth();
				cellRect.width = columnWidth - columnMargin;

				if (aColumn != draggedColumn) {
					paintCell(g, cellRect, column);
				}

				cellRect.x += columnWidth;
			}
		} else {
			aColumn = cm.getColumn(cMin);

			if (aColumn != draggedColumn) {
				columnWidth = aColumn.getWidth();
				cellRect.width = columnWidth - columnMargin;
				cellRect.x += columnMargin;
				paintCell(g, cellRect, cMin);
			}

			for (int column = cMin + 1; column <= cMax; column++) {
				aColumn = cm.getColumn(column);
				columnWidth = aColumn.getWidth();
				cellRect.width = columnWidth - columnMargin;
				cellRect.x -= columnWidth;

				if (aColumn != draggedColumn) {
					paintCell(g, cellRect, column);
				}
			}
		}

		// Paint the dragged column if we are dragging.
		if (draggedColumn != null) {
			int draggedColumnIndex = viewIndexForColumn(draggedColumn);
			Rectangle draggedCellRect = header.getHeaderRect(draggedColumnIndex);

			// Draw a gray well in place of the moving column.
			g.setColor(header.getParent().getBackground());
			g.fillRect(draggedCellRect.x, draggedCellRect.y, draggedCellRect.width, draggedCellRect.height);

			draggedCellRect.x += header.getDraggedDistance();

			// Fill the background.
			g.setColor(header.getBackground());
			g.fillRect(draggedCellRect.x, draggedCellRect.y, draggedCellRect.width, draggedCellRect.height);

			paintCell(g, draggedCellRect, draggedColumnIndex);
		}

		// Remove all components in the rendererPane.
		rendererPane.removeAll();
	}

	private Component getHeaderRenderer(int columnIndex) {
		TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
		TableCellRenderer renderer = aColumn.getHeaderRenderer();

		if (renderer == null) {
			renderer = header.getDefaultRenderer();
		}

		return renderer.getTableCellRendererComponent(header.getTable(), aColumn.getHeaderValue(), false, false, -1, columnIndex);
	}

	private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
		if (mouseOver && mouseOverColumn == columnIndex) {
			g.drawImage(mouseOverBG, cellRect.x, cellRect.y, cellRect.width, cellRect.height, null, null);
		} else {
			g.drawImage(normalBG, cellRect.x, cellRect.y, cellRect.width, cellRect.height, null, null);
		}
		g.setColor(Color.gray);
		g.drawLine(cellRect.x, cellRect.height - 1, cellRect.x + cellRect.width, cellRect.height - 1);

		Component component = getHeaderRenderer(columnIndex);
		rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
	}

	private int viewIndexForColumn(TableColumn aColumn) {
		TableColumnModel cm = header.getColumnModel();

		for (int column = 0; column < cm.getColumnCount(); column++) {
			if (cm.getColumn(column) == aColumn) {
				return column;
			}
		}

		return -1;
	}

	private int getHeaderHeight() {
		return HEADER_HEIGHT;
	}

	private Dimension createHeaderSize(long width) {
		TableColumnModel columnModel = header.getColumnModel();

		// None of the callers include the intercell spacing, do it here.
		if (width > Integer.MAX_VALUE) {
			width = Integer.MAX_VALUE;
		}

		return new Dimension((int) width, getHeaderHeight());
	}

	public Dimension getMinimumSize(JComponent c) {
		long width = 0;
		Enumeration enumeration = header.getColumnModel().getColumns();

		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getMinWidth();
		}

		return createHeaderSize(width);
	}

	public Dimension getPreferredSize(JComponent c) {
		long width = 0;
		Enumeration enumeration = header.getColumnModel().getColumns();

		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getPreferredWidth();
		}

		return createHeaderSize(width);
	}

	public Dimension getMaximumSize(JComponent c) {
		long width = 0;
		Enumeration enumeration = header.getColumnModel().getColumns();

		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getMaxWidth();
		}

		return createHeaderSize(width);
	}

	public class MouseInputHandler implements MouseInputListener {
		private int mouseXOffset;
		private Cursor otherCursor = resizeCursor;

		public void mouseClicked(MouseEvent e) {
		}

		private boolean canResize(TableColumn column) {
			return (column != null) && header.getResizingAllowed() && column.getResizable();
		}

		private TableColumn getResizingColumn(Point p) {
			return getResizingColumn(p, header.columnAtPoint(p));
		}

		private TableColumn getResizingColumn(Point p, int column) {
			if (column == -1) {
				return null;
			}

			Rectangle r = header.getHeaderRect(column);
			r.grow(-3, 0);

			if (r.contains(p)) {
				return null;
			}

			int midPoint = r.x + (r.width / 2);
			int columnIndex;

			if (header.getComponentOrientation().isLeftToRight()) {
				columnIndex = (p.x < midPoint) ? (column - 1) : column;
			} else {
				columnIndex = (p.x < midPoint) ? column : (column - 1);
			}

			if (columnIndex == -1) {
				return null;
			}

			return header.getColumnModel().getColumn(columnIndex);
		}

		public void mousePressed(MouseEvent e) {
			header.setDraggedColumn(null);
			header.setResizingColumn(null);
			header.setDraggedDistance(0);

			Point p = e.getPoint();

			// First find which header cell was hit
			TableColumnModel columnModel = header.getColumnModel();
			int index = header.columnAtPoint(p);

			if (index != -1) {
				// The last 3 pixels + 3 pixels of next column are for resizing
				TableColumn resizingColumn = getResizingColumn(p, index);

				if (canResize(resizingColumn)) {
					header.setResizingColumn(resizingColumn);

					if (header.getComponentOrientation().isLeftToRight()) {
						mouseXOffset = p.x - resizingColumn.getWidth();
					} else {
						mouseXOffset = p.x + resizingColumn.getWidth();
					}
				} else if (header.getReorderingAllowed()) {
					TableColumn hitColumn = columnModel.getColumn(index);
					header.setDraggedColumn(hitColumn);
					mouseXOffset = p.x;
				}
			}
		}

		private void swapCursor() {
			Cursor tmp = header.getCursor();
			header.setCursor(otherCursor);
			otherCursor = tmp;
		}

		public void mouseMoved(MouseEvent e) {
			if (canResize(getResizingColumn(e.getPoint())) != (header.getCursor() == resizeCursor)) {
				swapCursor();
			}

			Point p = e.getPoint();
			TableColumnModel columnModel = header.getColumnModel();
			int index = header.columnAtPoint(p);

			if (index != columnSelected) {
				columnSelected = index;
				header.repaint();
			}
			
			// for mouse over header
			mouseOverColumn = columnModel.getColumnIndexAtX(e.getPoint().x);
			header.repaint();
			// end for mouse over header
		}

		public void mouseDragged(MouseEvent e) {
			int mouseX = e.getX();

			TableColumn resizingColumn = header.getResizingColumn();
			TableColumn draggedColumn = header.getDraggedColumn();

			boolean headerLeftToRight = header.getComponentOrientation().isLeftToRight();

			if (resizingColumn != null) {
				int oldWidth = resizingColumn.getWidth();
				int newWidth;

				if (headerLeftToRight) {
					newWidth = mouseX - mouseXOffset;
				} else {
					newWidth = mouseXOffset - mouseX;
				}

				resizingColumn.setWidth(newWidth);

				Container container;

				if ((header.getParent() == null) || ((container = header.getParent().getParent()) == null) || !(container instanceof JScrollPane)) {
					return;
				}

				if (!container.getComponentOrientation().isLeftToRight() && !headerLeftToRight) {
					JTable table = header.getTable();

					if (table != null) {
						JViewport viewport = ((JScrollPane) container).getViewport();
						int viewportWidth = viewport.getWidth();
						int diff = newWidth - oldWidth;
						int newHeaderWidth = table.getWidth() + diff;

						/* Resize a table */
						Dimension tableSize = table.getSize();
						tableSize.width += diff;
						table.setSize(tableSize);

						/*
						 * If this table is in AUTO_RESIZE_OFF mode and has a
						 * horizontal scrollbar, we need to update a view's
						 * position.
						 */
						if ((newHeaderWidth >= viewportWidth) && (table.getAutoResizeMode() == JTable.AUTO_RESIZE_OFF)) {
							Point p = viewport.getViewPosition();
							p.x = Math.max(0, Math.min(newHeaderWidth - viewportWidth, p.x + diff));
							viewport.setViewPosition(p);

							/* Update the original X offset value. */
							mouseXOffset += diff;
						}
					}
				}
			} else if (draggedColumn != null) {
				TableColumnModel cm = header.getColumnModel();
				int draggedDistance = mouseX - mouseXOffset;
				int direction = (draggedDistance < 0) ? (-1) : 1;
				int columnIndex = viewIndexForColumn(draggedColumn);
				int newColumnIndex = columnIndex + (headerLeftToRight ? direction : (-direction));

				if ((0 <= newColumnIndex) && (newColumnIndex < cm.getColumnCount())) {
					int width = cm.getColumn(newColumnIndex).getWidth();

					if (Math.abs(draggedDistance) > (width / 2)) {
						mouseXOffset = mouseXOffset + (direction * width);
						header.setDraggedDistance(draggedDistance - (direction * width));
						cm.moveColumn(columnIndex, newColumnIndex);

						return;
					}
				}

				setDraggedDistance(draggedDistance, columnIndex);
			}
		}

		public void mouseReleased(MouseEvent e) {
			setDraggedDistance(0, viewIndexForColumn(header.getDraggedColumn()));

			header.setResizingColumn(null);
			header.setDraggedColumn(null);
		}

		public void mouseEntered(MouseEvent e) {
			mouseOver = true;
			TableColumnModel columnModel = header.getColumnModel();
			mouseOverColumn = columnModel.getColumnIndexAtX(e.getPoint().x);
			header.repaint();
		}

		public void mouseExited(MouseEvent e) {
			columnSelected = -1;
			mouseOver = false;
			header.repaint();
		}

		private void setDraggedDistance(int draggedDistance, int column) {
			header.setDraggedDistance(draggedDistance);

			if (column != -1) {
				header.getColumnModel().moveColumn(column, column);
			}
		}
	}
}
