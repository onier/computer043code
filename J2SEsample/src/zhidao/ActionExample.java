/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;

import javax.swing.*;

/**
 * An example of using a common set of AbstractActions for handling
 * JToolBar, JMenuBar and JPopup menu events.
 *
 * @author R. Kevin Cole, kcole@users.sourceforge.net
 */
public class ActionExample {

    public static final Integer ACTION1_KEY = new Integer(1);
    public static final Integer ACTION2_KEY = new Integer(2);
    public static final Integer ABOUT_KEY = new Integer(3);
    public static final Integer EXIT_KEY = new Integer(4);
    /** Map of AbstractActions to Action Keywords */
    static final Hashtable actionTable = new Hashtable(89);
    /** Our popup menu */
    protected JPopupMenu popup;
    /** The frame in which we are embedded */
    JFrame frame;

    public ActionExample(final JFrame frame) {
        this.frame = frame;

        buildActionTable();

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setText("\n\tUse the MenuBar, ToolBar or right-click for a popup menu.\n\n" + "\tAction1 disables itself and enables Action2.\n" + "\tAction2 disables itself and enables Action1.\n" + "\tThe About action enables both.\n\n" + "\tAll components use a common set of actions.\n" + "\tThe actions and corresponding components\n" + "\tare enabled and disabled in concert.\n\n\n" + "\tFor larger swing applications, I place all actions\n" + "\tin a separate package which maps to a directory structure\n" + "\tsomething like this...\n\n" + "\tmypackage \n" + "\t\tActionExample.java\n" + "\tmypackage/action\n" + "\t\tExitAction.java\n" + "\t\tAction1.java\n" + "\t\tAction2.java\n" + "\t\tAboutAction.java\n");
        frame.getContentPane().add(new MyToolBar(actionTable), BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(textPane), BorderLayout.CENTER);
        textPane.addMouseListener(new MousePopupListener());
        frame.setJMenuBar(new MyMenuBar(actionTable));
        frame.setSize(640, 480);

// send window closing events to our "Exit" action handler.
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                getAction(ActionExample.EXIT_KEY).actionPerformed(null);
            }
        });
    }

    /** Enable/disable an AbstractAction
     * @param actionName the key that maps this action in actionTable
     * @param b true to enable or false to disable the action
     */
    public static synchronized void setActionEnabled(final Integer actionKey, final boolean b) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                AbstractAction aa = (AbstractAction) actionTable.get(actionKey);

                if (aa != null) {
                    aa.setEnabled(b);
                }
            }
        });
    }

    /** @return the action from the action-map that is associated with
     * this action key
     */
    public AbstractAction getAction(final Integer actionKey) {
        return (AbstractAction) actionTable.get(actionKey);
    }

    /** Populate a map of actions.
     */
    private void buildActionTable() {
        actionTable.put(ActionExample.EXIT_KEY, new ExitAction());
        actionTable.put(ActionExample.ACTION1_KEY, new Action1());
        actionTable.put(ActionExample.ACTION2_KEY, new Action2());
        actionTable.put(ActionExample.ABOUT_KEY, new AboutAction());

        ActionExample.setActionEnabled(ActionExample.ACTION2_KEY, false);
    }

    /** Lazy instantiation of a JPopupMenu.
     */
    public JPopupMenu getPopup() {
        if (popup == null) {
            popup = new JPopupMenu();
            popup.add(new JMenuItem((Action) actionTable.get(ActionExample.ACTION1_KEY)));
            popup.add(new JMenuItem((Action) actionTable.get(ActionExample.ACTION2_KEY)));
            popup.add(new JSeparator());
            popup.add(new JMenuItem((Action) actionTable.get(ActionExample.EXIT_KEY)));
        }
        return popup;
    }

// --------------- a test routine --------------------
    public static void main(String[] argv) throws Exception {
        JFrame frame = new JFrame("Action Example");

        final ActionExample instance = new ActionExample(frame);
        frame.setVisible(true);
    }

    /** Handle mouse clicks
     */
    class MousePopupListener extends MouseAdapter {

        public void mouseReleased(final MouseEvent e) {
            if (e.isPopupTrigger()) {
                Point pt = SwingUtilities.convertPoint((Component) e.getSource(),
                        e.getX(),
                        e.getY(), frame);
                getPopup().show(frame, pt.x, pt.y);

                return;
            }
        }
    }


// -------------- A MenuBar and ToolBar using our common actions ------------------
    /** Our implementation of a JMenuBar.
     */
    public class MyMenuBar extends JMenuBar {

        public MyMenuBar(final Hashtable actions) {
            JMenu menu = new JMenu("File");
            menu.setMnemonic('F');
            menu.add(new JMenuItem((Action) actions.get(ActionExample.ACTION1_KEY)));
            menu.add(new JMenuItem((Action) actions.get(ActionExample.ACTION2_KEY)));
            menu.add(new JSeparator());
            menu.add(new JMenuItem((Action) actions.get(ActionExample.EXIT_KEY)));
            add(menu);

            menu = new JMenu("Help");
            menu.setMnemonic('H');
            menu.add(new JMenuItem((Action) actions.get(ActionExample.ABOUT_KEY)));
            add(menu);
        }
    }

    /** Our implementation of a JToolBar
     */
    public class MyToolBar extends JToolBar {

        public MyToolBar(final Hashtable actions) {
            super();

            JButton btn = new JButton((Action) actions.get(ActionExample.ACTION1_KEY));
            add(btn);
            btn = new JButton((Action) actions.get(ActionExample.ACTION2_KEY));
            add(btn);
        }
    }


// -------------- Our common actions ------------------
    /** Close the application.
     */
    public class ExitAction extends AbstractAction {

        public ExitAction() {
            putValue(Action.NAME, "exit");
            putValue(Action.SHORT_DESCRIPTION, "Exit this application");
            putValue(Action.ACTION_COMMAND_KEY, "exit");
            putValue(Action.MNEMONIC_KEY, new Integer(java.awt.event.KeyEvent.VK_X));
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F10"));
// putValue(Action.SMALL_ICON, Icons.getInstance().get(Icons.SMALL_EXIT));
        }

        public void actionPerformed(final ActionEvent ae) {
            System.out.println("Exit action performed.");
            System.exit(0);
        }
    }

    /** Test action1
     */
    public class Action1 extends AbstractAction {

        public Action1() {
            putValue(Action.NAME, "action1");
            putValue(Action.SHORT_DESCRIPTION, "Action 1");
            putValue(Action.ACTION_COMMAND_KEY, "action1");
            putValue(Action.MNEMONIC_KEY, new Integer(java.awt.event.KeyEvent.VK_1));
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F1"));
// putValue(Action.SMALL_ICON, Icons.getInstance().get(Icons.SMALL_ACTION1));
        }

        public void actionPerformed(final ActionEvent ae) {
            System.out.println("Action 1 performed.");
            ActionExample.setActionEnabled(ActionExample.ACTION1_KEY, false);
            ActionExample.setActionEnabled(ActionExample.ACTION2_KEY, true);
        }
    }

    /** Test action2
     */
    public class Action2 extends AbstractAction {

        public Action2() {
            putValue(Action.NAME, "action2");
            putValue(Action.SHORT_DESCRIPTION, "Action 2");
            putValue(Action.ACTION_COMMAND_KEY, "action2");
            putValue(Action.MNEMONIC_KEY, new Integer(java.awt.event.KeyEvent.VK_2));
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
// putValue(Action.SMALL_ICON, Icons.getInstance().get(Icons.SMALL_ACTION2));
        }

        public void actionPerformed(final ActionEvent ae) {
            System.out.println("Action 2 performed.");
            ActionExample.setActionEnabled(ActionExample.ACTION1_KEY, true);
            ActionExample.setActionEnabled(ActionExample.ACTION2_KEY, false);
        }
    }

    /** The "About" action
     */
    public class AboutAction extends AbstractAction {

        public AboutAction() {
            putValue(Action.NAME, "about");
            putValue(Action.SHORT_DESCRIPTION, "About the author");
            putValue(Action.ACTION_COMMAND_KEY, "about");
            putValue(Action.MNEMONIC_KEY, new Integer(java.awt.event.KeyEvent.VK_A));
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F3"));
// putValue(Action.SMALL_ICON, Icons.getInstance().get(Icons.SMALL_ABOUT));
        }

        public void actionPerformed(final ActionEvent ae) {
            System.out.println("About performed.");
            JOptionPane.showMessageDialog(frame, "The author, Kevin Cole, is seeking a position " + "\nas a Java/Swing/J2EE programmer.\n " + "Please contact me at kcole@users.sourceforge.net\n" + "if you have opportunities available.", "About ActionExample", JOptionPane.INFORMATION_MESSAGE);
            ActionExample.setActionEnabled(ActionExample.ACTION2_KEY, true);
            ActionExample.setActionEnabled(ActionExample.ACTION2_KEY, true);
        }
    }
}

