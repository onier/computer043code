/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;


import java.awt.FlowLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;

import javax.swing.JButton;

import javax.swing.JCheckBoxMenuItem;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JMenuItem;

import javax.swing.JPanel;

import javax.swing.JScrollPane;

import javax.swing.JTextArea;

import javax.swing.JTextField;

import javax.swing.SwingUtilities;

public class JFramePopupMenu extends JFrame {

    private static final long serialVersionUID = 1;
    private JPanel jContentPane = null;
    private JButton jbnPopup = null;
    private JTextField jtfNumOfMenus = null;
    private JLabel lblNumElem = null;
    JTextArea output;
    JScrollPane scrollPane;
    String newline = "\n";
    ScrollablePopupMenu scrollablePopupMenu = new ScrollablePopupMenu(JFramePopupMenu.this.getGraphicsConfiguration());

    private JButton getBtnPopup() {
        if (jbnPopup == null) {
            jbnPopup = new JButton();
            jbnPopup.setText("View Popup");
            jbnPopup.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    int n = Integer.parseInt(getTxtNumElem().getText());
                    JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
                    cbMenuItem.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            System.out.println(e);
                            scrollablePopupMenu.hidemenu();
                        }
                    });
                    cbMenuItem.setMnemonic(KeyEvent.VK_C);
                    scrollablePopupMenu.add(cbMenuItem);
                    for (int i = 0; i < n; i++) {
                        JMenuItem xx = new JMenuItem(" JMenuItem  " + (i + 1));
                        xx.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent e) {
                                System.out.println(e);
                                scrollablePopupMenu.hidemenu();
                            }
                        });
                        //	scrollablePopupMenu.add(new JButton(" JMenuItem  " + (i+1)));
                        scrollablePopupMenu.add(xx);
                    }
                    scrollablePopupMenu.show(jbnPopup, jbnPopup.getWidth() * 3, 0);
                }
            });
        }
        return jbnPopup;
    }

    private JTextField getTxtNumElem() {
        if (jtfNumOfMenus == null) {
            jtfNumOfMenus = new JTextField();
            jtfNumOfMenus.setColumns(3);
            jtfNumOfMenus.setText("60");
        }
        return jtfNumOfMenus;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                JFramePopupMenu thisClass = new JFramePopupMenu();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    public JFramePopupMenu() {
        super();
        initialize();
    }

    private void initialize() {
        this.setSize(274, 109);
        this.setContentPane(getJContentPane());
        this.setTitle("Scrollable JPopupMenu");
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            lblNumElem = new JLabel();
//			lblNumElem.setText("N?mero de elementos del Men?");
            FlowLayout flowLayout = new FlowLayout();
            flowLayout.setHgap(8);
            flowLayout.setVgap(8);
            jContentPane = new JPanel();
            jContentPane.setLayout(flowLayout);
            jContentPane.add(getBtnPopup(), null);
            jContentPane.add(lblNumElem, null);
            jContentPane.add(getTxtNumElem(), null);
        }
        return jContentPane;
    }
}

