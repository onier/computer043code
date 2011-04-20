/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFontChooser.java
 *
 * Created on 2009-9-14, 10:38:17
 */
package test;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Vector;
import javax.swing.DefaultListModel;

/**
 *
 * @author Administrator
 */
public class JFontChooser extends javax.swing.JDialog {

    Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    String formats[] = new String[]{"无格式", "粗体", "斜体", "粗斜体"};
    boolean initFlag = true;

    /** Creates new form JFontChooser */
    public JFontChooser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.jFormattedTextField1.setValue(fonts[0].getSize());
        fontNameList.setSelectedIndex(0);
        this.jList1.setSelectedIndex(0);
    }

    private void reSetPreviewValue() {
        Font fontTemp = fonts[fontNameList.getSelectedIndex()];
        Font font = new Font(fontTemp.getFontName(), this.jList1.getSelectedIndex(), 20);
        this.previewLabel.setText(this.fontNameLabel.getText() + " " + this.formatLabel.getText());
        previewLabel.setFont(font);
//    this.previewLabel.setText("abc");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        previewLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fontNameList = new javax.swing.JList();
        fontNameLabel = new javax.swing.JLabel();
        fontTitleLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        formatLabel = new javax.swing.JLabel();
        fontformatTitleLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList(formats);
        jPanel5 = new javax.swing.JPanel();
        sizeTitleLabel = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("FontChooser");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("预览"));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        previewLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        previewLabel.setText("abcdef");
        jPanel1.add(previewLabel);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 150, 50));

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(0, 0));

        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < fonts.length; i++) {
            model.addElement(fonts[i].getFontName());
        }
        fontNameList.setModel(model);
        fontNameList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                fontNameListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(fontNameList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        fontNameLabel.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(fontNameLabel, gridBagConstraints);

        fontTitleLabel.setText("字体");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(fontTitleLabel, gridBagConstraints);

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 3, 190, 185));

        jPanel6.setLayout(new java.awt.GridBagLayout());

        formatLabel.setText("     ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel6.add(formatLabel, gridBagConstraints);

        fontformatTitleLabel.setText("字体样式");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel6.add(fontformatTitleLabel, gridBagConstraints);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(24, 24));

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.ipady = 135;
        jPanel6.add(jScrollPane2, gridBagConstraints);

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 107, 190));

        jPanel5.setLayout(new java.awt.GridBagLayout());

        sizeTitleLabel.setText("大小");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(sizeTitleLabel, gridBagConstraints);

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 34;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(jFormattedTextField1, gridBagConstraints);

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.ipady = 115;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel5.add(jScrollPane3, gridBagConstraints);

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 44, 190));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jButton1.setText("确定");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(jButton1, gridBagConstraints);

        jButton2.setText("取消");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel4.add(jButton2, gridBagConstraints);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fontNameListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_fontNameListValueChanged
        int index = this.fontNameList.getSelectedIndex();
        if (index >= 0) {
            this.fontNameLabel.setText(fontNameList.getSelectedValue().toString());
            reSetPreviewValue();
        }
}//GEN-LAST:event_fontNameListValueChanged

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        int index = jList1.getSelectedIndex();
        if (index >= 0) {
            this.formatLabel.setText(jList1.getSelectedValue().toString());
            reSetPreviewValue();
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFontChooser dialog = new JFontChooser(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fontNameLabel;
    private javax.swing.JList fontNameList;
    private javax.swing.JLabel fontTitleLabel;
    private javax.swing.JLabel fontformatTitleLabel;
    private javax.swing.JLabel formatLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel previewLabel;
    private javax.swing.JLabel sizeTitleLabel;
    // End of variables declaration//GEN-END:variables
}
