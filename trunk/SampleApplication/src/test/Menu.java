/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * @author ges
 * @author kwalrath
 */
/* MenuGlueDemo.java requires no other files. */
public class Menu {

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createMenu("Menu 1"));
        return menuBar;
    }

    public JMenu createMenu(String title) {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(new URL("http://img.baidu.com/img/iknow/logo-iknowxjd.gif"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        JMenu m = new JMenu(title);
        JMenuItem menuItem = new JMenuItem("ICON", icon);
        menuItem.setHorizontalAlignment(SwingConstants.LEFT);
        menuItem.setBorder(javax.swing.BorderFactory.createTitledBorder("123123"));
        m.add(menuItem);
        return m;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("MenuGlueDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        Menu demo = new Menu();
        frame.setContentPane(demo.createMenuBar());

        //Display the window.
        frame.setSize(300, 100);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });
    }
}
