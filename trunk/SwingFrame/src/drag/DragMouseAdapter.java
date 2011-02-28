/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drag;

import java.awt.*;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.event.*;
import java.net.URL;

class DragMouseAdapter1 extends MouseAdapter {

    public void mousePressed(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        TransferHandler handler = c.getTransferHandler();
        handler.exportAsDrag(c, e, TransferHandler.COPY);
    }
}

public class DragMouseAdapter {

    public static void main(String[] args) {
        try {
            JFrame f = new JFrame("Drag & Drop");
            ImageIcon icon1 = new ImageIcon(new URL("http://www.google.com.hk/intl/zh-CN/images/logo_cn.png"));
            ImageIcon icon2 = new ImageIcon(new URL("http://www.google.com.hk/intl/zh-CN/images/logo_cn.png"));
            JPanel panel = new JPanel(new GridLayout(2, 1));
            JPanel panel1 = new JPanel(new BorderLayout());
            JPanel panel2 = new JPanel();
            panel1.setBackground(Color.red);
            panel2.setBackground(Color.white);
            JLabel label1 = new JLabel(icon1);
            JLabel label2 = new JLabel(icon2);
            MouseListener listener = new DragMouseAdapter1();
            label1.addMouseListener(listener);
            label2.addMouseListener(listener);
            label1.setTransferHandler(new TransferHandler("icon"));
            label2.setTransferHandler(new TransferHandler("icon"));
            panel1.add(label1);
            panel2.add(label2);
            panel.add(panel1);
            panel.add(panel2);
            f.setLayout(new GridLayout(1, 2));
            f.setSize(300, 400);
            f.add(panel);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        } catch (MalformedURLException ex) {
            Logger.getLogger(DragMouseAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
