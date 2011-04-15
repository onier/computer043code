/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testframe;

import com.l2fprod.common.Version;
import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

 class Main extends JPanel {

    public Main() {
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        add("Center", tabs);

        addDemo(tabs, "JButtonBar", "ButtonBarMain");
        addDemo(tabs, "JDirectoryChooser", "ChooseDirectory");
        addDemo(tabs, "JFontChooser", "ChooseFont");
        addDemo(tabs, "JOutlookBar", "OutlookBarMain");
        addDemo(tabs, "JTaskPane", "TaskPaneMain");
        addDemo(tabs, "PropertySheet", "PropertySheetMain");
        addDemo(tabs, "JTipOfTheDay", "TOTDTest");
        try {
            JEditorPane pane = new JEditorPane("text/html", "<html>") {

                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                    super.paintComponent(g);
                }
            };
            pane.setPage(Main.class.getResource("demo.html"));
            pane.setBackground(Color.white);
            pane.setEditable(false);
            pane.setOpaque(true);
            add("South", pane);
        } catch (Exception e) {
        }
    }

    void addDemo(JTabbedPane tabs, String title, String demoClass) {
        String prefix = "com.l2fprod.common.demo.";
        LookAndFeelAddons addon = LookAndFeelAddons.getAddon();
        try {
            JComponent component = (JComponent) Class.forName(prefix + demoClass).newInstance();

            component.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            tabs.addTab(title, component);
        } catch (Exception e) {
        } finally {
            try {
                LookAndFeelAddons.setAddon(addon.getClass());
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JFrame frame = new JFrame("L2FProd.com Common Components " + Version.getVersion() + " (build " + Version.getBuildTimestamp() + ")");

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add("Center", new Main());
        frame.setDefaultCloseOperation(3);
        frame.setSize(450, 550);
        frame.setLocation(100, 100);
        frame.setVisible(true);
    }
}
