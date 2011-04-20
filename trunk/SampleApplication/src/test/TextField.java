/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TextField.java
 *
 * Created on 2009-9-2, 11:02:52
 */
package test;

/**
 *
 * @author Administrator
 */
public class TextField extends javax.swing.JFrame {

    boolean lock = true;

    /** Creates new form TextField */
    public TextField() {
        initComponents();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jFormattedTextField1.setBorder(null);
        jFormattedTextField1.setText("jFormattedTextField1");
        jFormattedTextField1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jFormattedTextField1CaretUpdate(evt);
            }
        });

        jFormattedTextField2.setText("jFormattedTextField2");
        jFormattedTextField2.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jFormattedTextField2CaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(197, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFormattedTextField1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jFormattedTextField1CaretUpdate
//        jFormattedTextField2.setValue(jFormattedTextField1.getValue());
        if (lock) {
            lock = false;
            jFormattedTextField2.setText(jFormattedTextField1.getText());
            lock = true;
        }
    }//GEN-LAST:event_jFormattedTextField1CaretUpdate

    private void jFormattedTextField2CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jFormattedTextField2CaretUpdate
//        jFormattedTextField2.setValue(jFormattedTextField1.getValue());
        if (lock) {
            lock = false;
            jFormattedTextField1.setText(jFormattedTextField2.getText());
            lock = true;
        }
    }//GEN-LAST:event_jFormattedTextField2CaretUpdate

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new TextField().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    // End of variables declaration//GEN-END:variables
}
