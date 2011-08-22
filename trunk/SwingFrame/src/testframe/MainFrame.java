/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testframe;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;
import org.openide.util.Exceptions;
import tablerenderer.TableCellEditorFactory;
import tablerenderer.TableCellRendererFactory;

/**
 *
 * @author Administrator
 */
public class MainFrame extends JFrame {

    public static void main(String[] args) {
//        ToolTipManager.sharedInstance().setDismissDelay(250);
//        ToolTipManager.sharedInstance().setInitialDelay(250);
//        ToolTipManager.sharedInstance().setReshowDelay(250);
        TableCellEditorFactory.createEditor(String.class);
        TableCellRendererFactory.createTableCellRenderer(String.class);
        final JPanel palatte = createVSplitPane(Palette.getPalette(), Properties.getProperties());
        JPanel p1 = createHSplitPane(new EditPanel(), palatte);
        JTextArea text = new JTextArea("BUILD SUCCESSFUL (total time: 8 seconds)");
        JPanel p2 = createVSplitPane(p1, new JScrollPane(text));
        JPanel p3 = createVSplitPane(new Project(), Navigator.getNavigator());
        JPanel panel = createHSplitPane(p3, p2);
        JFrame frame = new JFrame();
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add("New");
        menu.add("Open");
        menu.add("Save");
        menu.add("Save As");
        menu.add("Quit");
        bar.add(menu);
        menu = new JMenu("Edit");
        menu.add("Copy");
        menu.add("Cut");
        menu.add("Paste");
        menu.add("Delete");
        menu.add("Select");
        bar.add(menu);
        menu = new JMenu("about");
        menu.add("Help");
        bar.add(menu);
        JToolBar toolbar = new JToolBar();
        JButton b = new JButton("Open");
        toolbar.add(b);
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                palatte.setVisible(!palatte.isVisible());
            }
        });
        toolbar.add(new JButton("Save"));
        toolbar.add(new JButton("Copy"));
        toolbar.add(new JButton("Paste"));
        toolbar.add(new JButton("Cut"));
        frame.setJMenuBar(bar);
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(3);
        frame.getContentPane().add(toolbar, BorderLayout.NORTH);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static JInternalFrame createInternalFrame() {
        JInternalFrame iframe1 = new JInternalFrame();
        iframe1.setVisible(true);
        iframe1.setIconifiable(true);
        iframe1.setMaximizable(true);
        iframe1.setResizable(true);
        iframe1.setClosable(true);
        return iframe1;
    }

    public static void main1(String[] args) {
        final JPanel palatte = createVSplitPane(createInternalFrame(), createInternalFrame());
        JPanel p1 = createHSplitPane(createInternalFrame(), palatte);
        JTextArea text = new JTextArea("BUILD SUCCESSFUL (total time: 8 seconds)");
        JPanel p2 = createVSplitPane(p1, createInternalFrame());
        JPanel p3 = createVSplitPane(createInternalFrame(), createInternalFrame());
        JPanel panel = createHSplitPane(p3, p2);
        JFrame frame = new JFrame();
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(3);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static JPanel createVSplitPane(JComponent com1, JComponent com2) {
        JXMultiSplitPane palette = new JXMultiSplitPane();
        String paletteLayout = "(COLUMN(LEAF name=top weight=0.2)(LEAF name=buttom weight=0.2))";
        MultiSplitLayout.Node node = MultiSplitLayout.parseModel(paletteLayout);
        palette.getMultiSplitLayout().setModel(node);
        palette.add(com1, "top");
        palette.add(com2, "buttom");
        return palette;
    }

    public static JPanel createHSplitPane(JComponent com1, JComponent com2) {
        JXMultiSplitPane palette = new JXMultiSplitPane();
        String paletteLayout = "(Row(LEAF name=left weight=0.2)(LEAF name=right weight=0.2))";
        MultiSplitLayout.Node node = MultiSplitLayout.parseModel(paletteLayout);
        palette.getMultiSplitLayout().setModel(node);
        palette.add(com1, "left");
        palette.add(com2, "right");
        return palette;
    }
}
