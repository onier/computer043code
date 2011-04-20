/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Colors extends JFrame {

    private static final Random rand = new Random();

    public Colors() {
        super("Colors");
        final JPanel panel = new JPanel() {

            public void paint(Graphics draw) {

//               draw.setColor(Color.black);
//                draw.fillRect(0, 0, 640, 480);
                draw.drawString("Colors ", 50, 50);

                Color newColor = new Color(40, 60, 80);
                draw.setColor(newColor);
                draw.drawArc(100, 100, 50, 50, 0, -180);

                newColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                draw.setColor(newColor);
                draw.drawRect(250, 100, 50, 50);

                newColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                draw.setColor(newColor);
                draw.fillRect(450, 200, 50, 50);
            }
        };
        Action action = new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                panel.repaint();
            }
        };
        Timer timer = new Timer(250, action);
        timer.setRepeats(true);
        timer.start();
        this.setContentPane(panel);
        setSize(640, 480);
        setVisible(true);
        setBackground(Color.white);

    }

    public static void main(String args[]) {
        Colors test = new Colors();
    }
} 
