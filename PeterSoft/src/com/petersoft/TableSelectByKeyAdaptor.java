package com.petersoft;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JTable;

public class TableSelectByKeyAdaptor extends KeyAdapter {
	JTable table;
	boolean searchFirstColumnOnly;

	public TableSelectByKeyAdaptor(JTable table, boolean searchFirstColumnOnly) {
		this.table = table;
		this.searchFirstColumnOnly = searchFirstColumnOnly;
	}

	public void keyTyped(KeyEvent evt) {
		if (table.getColumnCount() > 0) {
			for (int row = table.getSelectedRow(); row < table.getRowCount(); row++) {
				String value;
				if (table.getValueAt(row, 0) instanceof File) {
					value = ((File) table.getValueAt(row, 0)).getName().toLowerCase();
				} else {
					value = table.getValueAt(row, 0).toString().toLowerCase();
				}
				if (searchFirstColumnOnly) {
					if (value.charAt(0) == evt.getKeyChar()) {
						table.setRowSelectionInterval(row, row);
						return;
					}
				}
			}
			for (int row = 0; row < table.getSelectedRow(); row++) {
				String value;
				if (table.getValueAt(row, 0) instanceof File) {
					value = ((File) table.getValueAt(row, 0)).getName().toLowerCase();
				} else {
					value = table.getValueAt(row, 0).toString().toLowerCase();
				}
				if (searchFirstColumnOnly) {
					if (value.charAt(0) == evt.getKeyChar()) {
						table.setRowSelectionInterval(row, row);
						return;
					}
				}
			}
		}
	}
}