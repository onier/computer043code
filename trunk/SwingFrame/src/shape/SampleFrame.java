/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.jvnet.lafwidget.LafWidget;
import org.jvnet.lafwidget.layout.TransitionLayoutManager;
import org.jvnet.lafwidget.utils.LafConstants.AnimationKind;

public class SampleFrame extends JFrame {

    JButton b1, b2, b3;

    public SampleFrame() {
        super("Transition test");

        this.getContentPane().setLayout(new BorderLayout());

        JPanel buttons = new JPanel(new FlowLayout());
        this.getContentPane().add(buttons, BorderLayout.SOUTH);

        // for the first movie, change the following line to
        // use the BorderLayout
        final JPanel mainPanel = new JPanel(new FlowLayout());
        // bring the magic in one single line
        TransitionLayoutManager.getInstance().track(mainPanel,false,true);
        this.b1 = new JButton("1");
        this.b2 = new JButton("2");
        this.b3 = new JButton("3");

        final JButton add = new JButton("add");
        add.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        mainPanel.add(b1, BorderLayout.WEST);
                        mainPanel.add(b2, BorderLayout.CENTER);
                        mainPanel.add(b3, BorderLayout.EAST);
                        mainPanel.revalidate();
                        add.setEnabled(false);
                    }
                });
            }
        });
        buttons.add(add);

        this.getContentPane().add(mainPanel, BorderLayout.CENTER);

        final JCheckBox cb1 = new JCheckBox("1");
        cb1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                b1.setVisible(!b1.isVisible());
                mainPanel.revalidate();
            }
        });
        buttons.add(cb1);

        final JCheckBox cb2 = new JCheckBox("2");
        cb2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                b2.setVisible(!b2.isVisible());
                mainPanel.revalidate();
            }
        });
        buttons.add(cb2);

        final JCheckBox cb3 = new JCheckBox("3");
        cb3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                b3.setVisible(!b3.isVisible());
                mainPanel.revalidate();
            }
        });
        buttons.add(cb3);

        final JCheckBox cb13 = new JCheckBox("13");
        cb13.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                b1.setVisible(!b1.isVisible());
                b3.setVisible(!b3.isVisible());
                mainPanel.revalidate();
            }
        });
        buttons.add(cb13);

        this.setSize(300, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        mainPanel.putClientProperty(LafWidget.TREE_AUTO_DND_SUPPORT,
                AnimationKind.FAST);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new SampleFrame().setVisible(true);
            }
        });
    }
}
