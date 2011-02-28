package com.petersoft.advancedswing.diskpanel;

import java.io.File;

import javax.swing.table.DefaultTableModel;

import com.petersoft.CommonLib;

public class DiskTableModel extends DefaultTableModel {
	int radix = 16;
	int addressRadix = 16;
	int numberOfColumn = 8;
	int numberOfRow = 10;
	File file;
	long offset;
	byte tempBytes[] = new byte[numberOfRow * numberOfColumn];

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		try {
			if (offset <= 0) {
				this.offset = 0;
			} else if (offset >= file.length()) {
				this.offset = file.length() - (getByteColumnCount() * getRowCount());
			} else {
				this.offset = offset;
			}

			tempBytes = new byte[numberOfRow * numberOfColumn];
			if (offset + tempBytes.length > file.length()) {
				tempBytes = CommonLib.readFileByte(file, this.offset, (int) (file.length() - this.offset));
			} else {
				tempBytes = CommonLib.readFileByte(file, this.offset, tempBytes.length);
			}
			this.fireTableDataChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
		setOffset(0);
		this.fireTableDataChanged();
	}

	public int getRadix() {
		return radix;
	}

	public void setRadix(int radix) {
		this.radix = radix;
		this.fireTableDataChanged();
	}

	public Object getValueAt(int row, int column) {
		try {
			if (column == 0) {
				if (addressRadix == 16) {
					return "0x" + Long.toHexString(offset + row * getByteColumnCount());
				} else {
					return offset + row * (getByteColumnCount());
				}
			} else if (column == getColumnCount() - 1) {
				String str = "";
				for (int x = 0; x < getByteColumnCount(); x++) {
					str += (char) tempBytes[(int) (row * getByteColumnCount() + x)];
				}
				return str;
			} else {
				byte b = tempBytes[(int) (row * getByteColumnCount() + (column - 1))];
				if (radix == 16) {
					// return "0x" + Long.toHexString(b & 0xff);
					return String.format("%02x", b & 0xff);
				} else if (radix == 10) {
					return b;
				} else if (radix == 8) {
					return Long.toOctalString(b & 0xff);
				} else {
					return Long.toBinaryString(b & 0xff);
				}
			}
		} catch (Exception ex) {
			return "-";
		}
	}

	public void setColumnCount(int columnCount) {
		numberOfColumn = columnCount;
		setOffset(getOffset());
	}

	public int getColumnCount() {
		return numberOfColumn + 2;
	}

	public int getByteColumnCount() {
		return numberOfColumn;
	}

	public void setRowCount(int rowCount) {
		numberOfRow = rowCount;
		setOffset(getOffset());
	}

	public int getRowCount() {
		return numberOfRow;
	}

	public String getColumnName(int col) {
		if (col == 0) {
			return "File offset";
		} else if (col == getColumnCount() - 1) {
			return "Text";
		} else {
			return String.valueOf(col - 1);
		}
	}

	public boolean isCellEditable(int row, int col) {
		return true;
	}

	public Class getColumnClass(int columnIndex) {
		return String.class;
	}

	public void setValueAt(Object aValue, int row, int column) {
	}

}
