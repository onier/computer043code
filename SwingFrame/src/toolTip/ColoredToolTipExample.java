/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toolTip;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;

/**
 * @version 1.0 12/10/98
 */
public class ColoredToolTipExample extends JFrame {

    public ColoredToolTipExample() {
        super("Colored ToolTip Example");

        UIManager.put("ToolTip.foreground",
                new ColorUIResource(Color.red));
        UIManager.put("ToolTip.background",
                new ColorUIResource(Color.yellow));

        JButton button = new JButton("Hello, world");
        button.setToolTipText("Red / Yellow");
        getContentPane().add(button);
    }

    public static void main(String args[]) {
        ColoredToolTipExample f = new ColoredToolTipExample();
        f.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setSize(300, 100);
        f.show();
    }
}
