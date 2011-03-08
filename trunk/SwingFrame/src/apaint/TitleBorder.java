/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TitleBorder.java
 *
 * Created on 2011-3-2, 9:14:57
 */
package apaint;

import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author admin
 */
public class TitleBorder extends javax.swing.JPanel {

    private int x, y;
    private MouseHandler handler = new MouseHandler();

    /** Creates new form TitleBorder */
    public TitleBorder() {
        initComponents();
        this.addMouseMotionListener(handler);
        this.addMouseListener(handler);
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() > 1) {
                Window window = SwingUtilities.getWindowAncestor(TitleBorder.this);
                if (window instanceof Frame) {
                    if (((Frame) window).getExtendedState() == Frame.NORMAL) {
                        ((Frame) window).setExtendedState(Frame.MAXIMIZED_BOTH);
                    } else {
                        ((Frame) window).setExtendedState(Frame.NORMAL);
                    }
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Window window = SwingUtilities.getWindowAncestor(TitleBorder.this);
            Point p = window.getLocation();
            p.translate(e.getX() - x, e.getY() - y);
            window.setLocation(p);
        }
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new  TitlePane();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("宋体", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 102));
        jLabel1.setText(org.openide.util.NbBundle.getMessage(TitleBorder.class, "TitleBorder.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        add(jLabel1, java.awt.BorderLayout.WEST);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 137, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        add(jPanel1, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
