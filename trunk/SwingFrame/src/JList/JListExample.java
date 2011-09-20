/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JList;

import java.awt.event.*;
import java.awt.*;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JListExample extends JPanel {

    static MyJList mj;

    public static void main(String s[]) {
        JListExample ex = new JListExample();
        JFrame frame = new JFrame("JList Scrolling Display");
        JButton button = new JButton("Insert");
        ex.mj = new MyJList();
        ex.mj.list.setModel(new DefaultListModel());

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(ex.mj);
        frame.getContentPane().add(button);
        button.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {
                        DefaultListModel dlm =
                                (DefaultListModel) JListExample.mj.list.getModel();
                        dlm.addElement((Object) new Long(System.currentTimeMillis()));
                        JListExample.mj.list.ensureIndexIsVisible(JListExample.mj.list.getModel().getSize() - 1);
                    }
                });

        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}

class MyJList extends JPanel {

    JList list;

    public MyJList() {
        setLayout(new BorderLayout());
        list = new JList();
        add(new JScrollPane(list));
    }

    public Dimension getPreferredSize() {
        return new Dimension(150, 250);
    }
}
