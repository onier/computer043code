/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.applet.Applet;

/**
 *
 * @author Administrator
 */
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class AppletApp extends Applet {

    /**
     *
     */
// private static final long serialVersionUID = -5234734486830015017L;
    public static void main(String args[]) {
        new AppletApp().AppletAppinit();
    }

    void AppletAppinit() {
        Frame frame = new Frame("Application");
        AppletApp app = new AppletApp();

        frame.add("Center", app);
        frame.setSize(200, 200);
        frame.validate();
        frame.setVisible(true);

        frame.addWindowListener(new WindowControl(app));

        app.init();
        app.start();
    }

    public void paint(Graphics g) {
        g.drawString("Hello", 25, 25);
    }

    public void destroy() {
        System.exit(0);
    }

    class WindowControl extends WindowAdapter {

        Applet c;

        public WindowControl(Applet c) {
            this.c = c;
        }

        public void windowClosing(WindowEvent e) {
            c.destroy();
        }
    }
}
