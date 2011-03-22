/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package testframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import node.BeanNodeElementUtils;
import org.openide.util.Exceptions;
import shape.BeanNodeElement;

/**
 *
 * @author  alex
 */
public class EditPanel extends javax.swing.JPanel {

    private BeanNodeGraphView scene;
    protected JComponent view;
    private JTextArea area = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane();
    private EidtFrame frame = new EidtFrame("test");
    private Action EidtFrameAction;
    private KeyStroke keyStroke = KeyStroke.getKeyStroke("alt shift D");

    /** Creates new form NewJPanel */
    public EditPanel() {
        initComponents();
        scene = new BeanNodeGraphView();
        view = scene.createView();
        scrollPane.setViewportView(view);
        jPanel1.add(scrollPane, java.awt.BorderLayout.CENTER);
        EidtFrameAction = new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                frame.setVisible(!frame.isVisible());
            }
        };
        init();
        this.jToggleButton2.setSelected(true);
        this.jToolBar2.setVisible(false);
    }

    private void init() {
        scene.createView().getActionMap().put("edit", EidtFrameAction);
        scene.createView().getInputMap().put(keyStroke, "edit");
        area.getActionMap().put("edit", EidtFrameAction);
        area.getInputMap().put(keyStroke, "edit");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        chartToolBar = new javax.swing.JToolBar();
        GridsToggleButton = new javax.swing.JToggleButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setFocusable(false);

        jToggleButton1.setText("source");
        jToggleButton1.setFocusable(false);
        jToggleButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jToggleButton1);

        jToggleButton2.setText("design");
        jToggleButton2.setFocusable(false);
        jToggleButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jToggleButton2);

        chartToolBar.setBorder(null);
        chartToolBar.setFloatable(false);
        chartToolBar.setRollover(true);

        GridsToggleButton.setIcon(new javax.swing.ImageIcon("E:\\TDDOWNLOAD\\NetBeans\\src\\test\\resources\\class.gif")); // NOI18N
        GridsToggleButton.setSelected(true);
        GridsToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GridsToggleButtonActionPerformed(evt);
            }
        });
        chartToolBar.add(GridsToggleButton);

        jButton2.setText("保存");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        chartToolBar.add(jButton2);

        jButton3.setText("打开");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        chartToolBar.add(jButton3);

        jToolBar1.add(chartToolBar);

        jToolBar2.setBorder(null);
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        jButton1.setText("源编辑");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton1);

        jToolBar1.add(jToolBar2);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.NORTH);

        jTabbedPane1.addTab("tab1", jPanel1);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void GridsToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GridsToggleButtonActionPerformed
        if (GridsToggleButton.isSelected()) {
            scene.initGrids();
        } else {
            scene.setBackground(Color.WHITE);
            scene.validate();
        }
    }//GEN-LAST:event_GridsToggleButtonActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        area.setText(scene.toCode());
        this.jToolBar2.setVisible(true);
        this.chartToolBar.setVisible(false);
        this.jToggleButton2.setSelected(false);
        scrollPane.setViewportView(area);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        scene.removeAll();
        ArrayList<BeanNodeElement> list = BeanNodeElementUtils.parse(area.getText());
        if (list != null) {
            scene.addElements(list);
            scene.invokeLayout();
        }
        this.jToolBar2.setVisible(false);
        this.chartToolBar.setVisible(true);
        jToggleButton1.setSelected(false);
        scrollPane.setViewportView(scene.createView());
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            scene.save("c:/test.xml");
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            scene.read("c:/test.xml");
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton GridsToggleButton;
    private javax.swing.JToolBar chartToolBar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables

    private class EidtFrame extends JFrame {

        public EidtFrame(String text) {
            super(text);
        }

        @Override
        public void setVisible(boolean b) {
            if (b) {
                jTabbedPane1.remove(jPanel1);
                getContentPane().add(jPanel1, BorderLayout.CENTER);
            } else {
                getContentPane().remove(jPanel1);
                jTabbedPane1.add(jPanel1);
            }
            super.setVisible(b);
        }
    }
}
