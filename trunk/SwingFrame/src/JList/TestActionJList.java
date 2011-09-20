/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JList;

//import javax.swing.*;
import java.awt.GridLayout;
//import javax.swing.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class ActionJList extends JList {
    /*
     ** sends ACTION_PERFORMED event for double-click
     ** and ENTER key
     */

    ActionListener al;

    public ActionJList(String[] it) {
        super(it);

        addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent me) {
                if (al == null) {
                    return;
                }
                Object ob[] = getSelectedValues();
                if (ob.length > 1) {
                    return;
                }
                if (me.getClickCount() == 2) {
                    System.out.println("Sending ACTION_PERFORMED to ActionListener");
                    al.actionPerformed(new ActionEvent(this,
                            ActionEvent.ACTION_PERFORMED,
                            ob[0].toString()));
                    me.consume();
                }
            }
        });

        addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent ke) {
                if (al == null) {
                    return;
                }
                Object ob[] = getSelectedValues();
                if (ob.length > 1) {
                    return;
                }
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("Sending ACTION_PERFORMED to ActionListener");
                    al.actionPerformed(new ActionEvent(this,
                            ActionEvent.ACTION_PERFORMED,
                            ob[0].toString()));
                    ke.consume();
                }
            }
        });
        this.setSelectedIndex(0);
    }

    public void addActionListener(ActionListener al) {
        this.al = al;
    }
}

public class TestActionJList {

    public static void main(String args[]) {
        JFrame jf = new JFrame();
        jf.getContentPane().add(new PanelWithActionJList());
        jf.pack();
        jf.setVisible(true);
    }
}

class PanelWithActionJList extends JPanel {

    public PanelWithActionJList() {
        setLayout(new GridLayout(1, 1));

        String[] items = {"item 0", "item 1", "item 2", "item 3", "item 4",
            "item 5", "item 6", "item 7", "item 8", "item 9"};

        final ActionJList ajl = new ActionJList(items);

        ajl.setVisibleRowCount(5);
        ajl.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {
                        System.out.println("action in Panel " + ajl.getSelectedValue());
                    }
                });

        JScrollPane jsp = new JScrollPane();
        jsp.getViewport().add(ajl);
        add(jsp);
    }
}
