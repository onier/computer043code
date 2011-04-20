/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.beans.PropertyVetoException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.net.URL;

public class MyDesktopPane extends JFrame implements ActionListener {

    final static JDesktopPane desktopPane = new JDesktopPane();
    private ImageIcon imglcon;

    public MyDesktopPane() {
        super("MyDesktopPane.java:DesktopPane测试");
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);
        try {
            imglcon = new ImageIcon(new URL("http://img.baidu.com/img/logo-zhidao.gif"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(MyDesktopPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("新增窗口");
        JMenuItem menultem = new JMenuItem("内部框架窗口");
        menu.add(menultem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        setContentPane(desktopPane);
        menultem.addActionListener(this);
        setSize(350, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JInternalFrame inFrame = new JInternalFrame("内部框架（圆环）", true, true, true, true);
        Container c = inFrame.getContentPane();
        JLabel label = new JLabel(imglcon);
        c.add(label, BorderLayout.CENTER);
        label = new JLabel("圆环");
        c.add(label, BorderLayout.WEST);
        inFrame.setOpaque(true);
        inFrame.setBounds(0, 0, 50, 80);
        inFrame.setVisible(true);
        desktopPane.add(inFrame);
    }

    public static void main(String args[]) {
        MyDesktopPane app = new MyDesktopPane();
        app.addWindowListener(new MyWindowListener());
    }
}

//class CirclePanel extends JPanel {
//
//    private ImageIcon imglcon;
//
//    public CirclePanel() {
//        try {
//            imglcon = new ImageIcon(new URL("http://img.baidu.com/img/logo-zhidao.gif"));
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(CirclePanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        JLabel label = new JLabel(imglcon);
//        this.setLayout(new BorderLayout());
//        this.add(label, BorderLayout.CENTER);
//    }
////
////    public void paint(Graphics g) {
////       g.drawImage(imglcon.getImage(), 0, 0, this);
////    }
////
////    public Dimension getImageWidthHeight() {
////        return new Dimension(imglcon.getIconWidth(), imglcon.getIconHeight());
////    }
//}
class MyWindowListener extends WindowAdapter {

    public void windowClosing(WindowEvent e) {
        System.exit(1);
    }
}
