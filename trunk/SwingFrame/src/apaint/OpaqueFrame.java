/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import com.sun.awt.AWTUtilities;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import sun.awt.AWTAccessor;

/**
 *
 * @author admin
 */
public class OpaqueFrame extends JFrame {

    OpaqueFrame() {
        setDefaultCloseOperation(3);
        setUndecorated(true);
        final ImageIcon image = new javax.swing.ImageIcon("C:\\Documents and Settings\\admin\\桌面\\c2cec3fd57d8b810d7887d54.jpg");
        JLabel label = new JLabel() {

            @Override
            public JToolTip createToolTip() {
                return super.createToolTip();
            }

            @Override
            public void paint(Graphics g) {
//                image.paintIcon(this, g, 0, 0);
                super.paint(g);
            }
        };
        this.init(label);
        label.setSize(image.getIconWidth(), image.getIconHeight());
        label.setOpaque(false);
        getContentPane().add(label, BorderLayout.CENTER);
        setSize(image.getIconWidth(), image.getIconHeight());
        AWTUtilities.setWindowOpaque(this, false);
        setLocationRelativeTo(null);
    }

    private void init(JComponent com) {
        java.awt.GridBagConstraints gridBagConstraints;

        JLabel jLabel1 = new javax.swing.JLabel();
        JTextField jTextField1 = new javax.swing.JTextField();
        JLabel jLabel2 = new javax.swing.JLabel();
        JPasswordField jPasswordField1 = new javax.swing.JPasswordField();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();

        com.setName("Form"); // NOI18N
        com.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("用户名");
        jLabel1.setName("jLabel1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        com.add(jLabel1, gridBagConstraints);

        jTextField1.setText(""); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        com.add(jTextField1, gridBagConstraints);

        jLabel2.setText("密码");
        jLabel2.setName("jLabel2"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        com.add(jLabel2, gridBagConstraints);

        jPasswordField1.setText(""); // NOI18N
        jPasswordField1.setName("jPasswordField1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        com.add(jPasswordField1, gridBagConstraints);

        jButton1.setText("确定"); // NOI18N
        jButton1.setOpaque(false);
        jButton1.setName("jButton1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        com.add(jButton1, gridBagConstraints);

        jButton2.setText("取消"); // NOI18N
        jButton2.setOpaque(false);
        jButton2.setName("jButton2"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        com.add(jButton2, gridBagConstraints);
    }

    public static void main(String[] args) {
        new OpaqueFrame().setVisible(true);
    }
}
