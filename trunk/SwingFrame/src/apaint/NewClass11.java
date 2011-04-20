/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author admin
 */
public class NewClass11 {

    public static void main(String[] args) {
        final JFrame f = new JFrame();
        f.setVisible(true);
        final Timer timer = new Timer(100, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Thread.sleep(100);
                    f.setExtendedState(JFrame.ICONIFIED);
                    Thread.sleep(100);
                    f.setVisible(true);
                } catch (InterruptedException ex) {
                }
            }
        });
        timer.setRepeats(true);
        f.addWindowStateListener(new WindowStateListener() {

            public void windowStateChanged(WindowEvent e) {
                if (f.getExtendedState() == JFrame.ICONIFIED) {
                    timer.start();
                } else {
                    f.setExtendedState(JFrame.NORMAL);
                    f.transferFocus();
                    timer.stop();
                }
            }
        });
        timer.start();
    }
}
