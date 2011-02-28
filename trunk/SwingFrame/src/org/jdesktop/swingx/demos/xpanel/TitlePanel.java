/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.swingx.demos.xpanel;

import java.awt.BorderLayout;
import javax.swing.JWindow;
import org.jdesktop.swingx.JXTitledPanel;

/**
 *
 * @author Administrator
 */
public class TitlePanel {

    public static void main(String[] args) {
        JWindow window = new JWindow();
        JXTitledPanel jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jXTitledPanel1.setTitle("录制中");
        window.setBounds(100, 100, 400, 300);
        window.setLayout(new BorderLayout());
        window.getContentPane().add(jXTitledPanel1, BorderLayout.NORTH);
        window.setVisible(true);
    }
}
