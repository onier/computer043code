/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Testing {

    public void buildGUI() {

//        try {
//            UIManager.setLookAndFeel("apaint.ColorLookAndFeel");
//        } catch (ClassNotFoundException ex) {
//            Exceptions.printStackTrace(ex);
//        } catch (InstantiationException ex) {
//            Exceptions.printStackTrace(ex);
//        } catch (IllegalAccessException ex) {
//            Exceptions.printStackTrace(ex);
//        } catch (UnsupportedLookAndFeelException ex) {
//            Exceptions.printStackTrace(ex);
//        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame f = new JFrame() {

            @Override
            public void setBounds(int x, int y, int width, int height) {
                super.setBounds(x, y, width, height);
            }
        };
        f.setResizable(false);
        f.setUndecorated(true);
//        f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        removeMinMaxClose(f);
        JPanel p = new JPanel(new GridBagLayout());
        JButton btn = new JButton("Exit");
        p.add(btn, new GridBagConstraints());
        f.getContentPane().add(p);
        f.setSize(400, 300);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        btn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
    }

    public void removeMinMaxClose(Component comp) {
        if (comp instanceof AbstractButton) {
            System.out.println(((AbstractButton) comp).getAction());
//            comp.getParent().remove(comp);
        }
        if (comp instanceof Container) {
            Component[] comps = ((Container) comp).getComponents();
            for (int x = 0, y = comps.length; x < y; x++) {
                removeMinMaxClose(comps[x]);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new Testing().buildGUI();
            }
        });
    }
}
