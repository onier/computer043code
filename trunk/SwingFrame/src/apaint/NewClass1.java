/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.*;
import javax.swing.*;

class MessagePanel extends JPanel {

    GridBagLayout gridbag = new GridBagLayout();

    public MessagePanel() {
        super();
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(gridbag);

        JLabel toLabel = new JLabel("To: ");
        JTextField to = new JTextField();
        JLabel subjectlJLabel = new JLabel("Subject: ");
        JTextField subject = new JTextField();
        JLabel ccJLabel = new JLabel("CC: ");
        JTextField cc = new JTextField();
        JLabel bcclJLabel = new JLabel("BCC:");
        JTextField bcc = new JTextField();

        addComponent(toLabel, 0, 0, 0, 1, 1, 10, 100, GridBagConstraints.NONE, GridBagConstraints.EAST);
        addComponent(to, 1, 1, 0, 9, 1, 90, 100, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        addComponent(subjectlJLabel, 0, 0, 1, 1, 1, 10, 100, GridBagConstraints.NONE, GridBagConstraints.EAST);
        addComponent(subject, 1, 1, 1, 9, 1, 90, 100, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        addComponent(ccJLabel, 0, 0, 2, 1, 1, 10, 100, GridBagConstraints.NONE, GridBagConstraints.EAST);
        addComponent(cc, 1, 1, 2, 1, 1, 40, 100, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
        addComponent(bcclJLabel, 5, 5, 2, 2, 1, 10, 100, GridBagConstraints.NONE, GridBagConstraints.EAST);
        addComponent(bcc, 6, 6, 2, 4, 1, 40, 100, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);

    }

    private void addComponent(Component compoment, int gridx, int gridy,
            int gridwidth, int l,
            int gridheight, int weightx, int weighty, int fill, int anchor) {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.fill = fill;
        gridbag.setConstraints(compoment, constraints);
        add(compoment);
    }
}

class MessagePanelRun extends JFrame {

    public MessagePanelRun() {
        super("Survey");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MessagePanel wiz = new MessagePanel();
        add(wiz);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        MessagePanelRun surv = new MessagePanelRun();
    }
}

