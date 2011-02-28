package com.petersoft.advancedswing.diskpanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.petersoft.CommonLib;
import com.petersoft.advancedswing.pager.Pager;
import com.petersoft.advancedswing.pager.PagerEvent;
import com.petersoft.advancedswing.pager.PagerEventListener;
import com.petersoft.advancedswing.pager.PagerTextFieldEvent;
import com.petersoft.advancedswing.pager.PagerTextFieldEventListener;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class DiskPanel extends JPanel {
	private JScrollPane jScrollPane1;
	private JComboBox jColumnCountComboBox;
	private JLabel jLabel2;
	private JComboBox jRowCountComboBox;
	private JLabel jLabel1;
	private JComboBox jRadixComboBox;
	private Pager pager1;
	private JPanel jPanel1;
	private JTable jTable1;
	DiskTableModel model = new DiskTableModel();
	private File file;

	public DiskPanel() {
		initGUI();
	}

	public DiskPanel(File file) {
		this();
		this.file = file;
		model.setFile(file);
	}

	public File getFile() {
		return file;
	}

	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			{
				jScrollPane1 = new JScrollPane();
				this.add(jScrollPane1, BorderLayout.CENTER);
				{
					jTable1 = new JTable();
					jTable1.setRowSelectionAllowed(false);
					jTable1.getTableHeader().setReorderingAllowed(false);
					jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					jScrollPane1.setViewportView(jTable1);
					jTable1.setModel(model);
					for (int x = 1; x <= 9; x++) {
						jTable1.getColumnModel().getColumn(x).setPreferredWidth(40);
					}
				}
			}
			{
				jPanel1 = new JPanel();
				FlowLayout jPanel1Layout = new FlowLayout();
				jPanel1Layout.setAlignment(FlowLayout.LEFT);
				jPanel1.setLayout(jPanel1Layout);
				this.add(jPanel1, BorderLayout.NORTH);
				{
					pager1 = new Pager();
					pager1.setPageNo(0);
					jPanel1.add(pager1);
					pager1.addPagerTextFieldEventListener(new PagerTextFieldEventListener() {
						public void KeyReleased(PagerTextFieldEvent evt) {
							pager1KeyReleased(evt);
						}
					});
					pager1.addPagerEventListener(new PagerEventListener() {
						public void clicked(PagerEvent evt) {
							pager1Clicked(evt);
						}
					});
				}
				{
					ComboBoxModel jRadixComboBoxModel = new DefaultComboBoxModel(new String[] { "2", "8", "10", "16" });
					jRadixComboBox = new JComboBox();
					jPanel1.add(jRadixComboBox);
					jRadixComboBox.setModel(jRadixComboBoxModel);
					jRadixComboBox.setSelectedItem("16");
					jRadixComboBox.setPreferredSize(new java.awt.Dimension(49, 27));
					jRadixComboBox.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jRadixComboBoxActionPerformed(evt);
						}
					});
				}
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setText("Row count");
				}
				{
					ComboBoxModel jRowCountComboBoxModel = new DefaultComboBoxModel(new String[] { "10", "20", "50", "100" });
					jRowCountComboBox = new JComboBox();
					jPanel1.add(jRowCountComboBox);
					jRowCountComboBox.setModel(jRowCountComboBoxModel);
					jRowCountComboBox.setEditable(true);
					jRowCountComboBox.setPreferredSize(new java.awt.Dimension(61, 27));
					jRowCountComboBox.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jRowCountComboBoxActionPerformed(evt);
						}
					});
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2);
					jLabel2.setText("Col count");
				}
				{
					ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(new String[] { "8", "10", "16", "20", "24", "30" });
					jColumnCountComboBox = new JComboBox();
					jPanel1.add(jColumnCountComboBox);
					jColumnCountComboBox.setModel(jComboBox1Model);
					jColumnCountComboBox.setEditable(true);
					jColumnCountComboBox.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jColumnCountComboBoxActionPerformed(evt);
						}
					});
				}
			}
			model.setRadix(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFile(File file) {
		this.file = file;
		model.setFile(file);
	}

	private void pager1Clicked(PagerEvent evt) {
		int pageSize = (model.getByteColumnCount()) * model.getRowCount();
		if (evt.getType() == PagerEvent.FIRST_PAGE_BUTTON) {
			model.setOffset(0);
		} else if (evt.getType() == PagerEvent.PREVIOUS_PAGE_BUTTON) {
			model.setOffset(model.getOffset() - pageSize);
		} else if (evt.getType() == PagerEvent.NEXT_PAGE_BUTTON) {
			model.setOffset(model.getOffset() + pageSize);
		} else if (evt.getType() == PagerEvent.LAST_PAGE_BUTTON) {
			model.setOffset(file.length() - pageSize);
		}
		pager1.setPageNo((int) (model.getOffset()));
	}

	private void jRadixComboBoxActionPerformed(ActionEvent evt) {
		model.setRadix(Integer.parseInt(jRadixComboBox.getSelectedItem().toString()));
	}

	private void pager1KeyReleased(PagerTextFieldEvent evt) {
		long offset = 0;
		try {
			offset = CommonLib.string2decimal(evt.getValue());
		} catch (Exception ex) {
			offset = CommonLib.convertFilesize(evt.getValue());
		}
		model.setOffset(offset);
	}

	private void jRowCountComboBoxActionPerformed(ActionEvent evt) {
		model.setRowCount(Integer.parseInt(jRowCountComboBox.getSelectedItem().toString()));
		model.fireTableStructureChanged();
		model.fireTableDataChanged();
	}

	private void jColumnCountComboBoxActionPerformed(ActionEvent evt) {
		model.setColumnCount(Integer.parseInt(jColumnCountComboBox.getSelectedItem().toString()));
		model.fireTableStructureChanged();
		model.fireTableDataChanged();
	}

}
