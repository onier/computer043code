/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animatino;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AnimatedButton extends JPanel {

    Font font = new Font("Arial", Font.PLAIN, 10);
    Color darkSlateGray = new Color(49, 79, 79);
    Color dimGray = new Color(105, 105, 105);
    Color slateGray = new Color(112, 138, 144);
    Color lightSlateGray = new Color(119, 136, 153);
    Color gray = new Color(190, 190, 190);
    Color lightGray = new Color(211, 211, 211);
    Color[] colors = {Color.black, darkSlateGray, dimGray, slateGray,
        lightSlateGray, gray, lightGray, Color.white};
    private JButton button = new JButton();
    private int fadeSpeed = 50;
    private boolean animate = false;
    private boolean allowAnimate = true; // we will animate
    private Color foreground = Color.black;

    public void setDefaultForeground(Color foreground) {
        this.foreground = foreground;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new AnimatedButton("test", true, Color.RED, true));
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }

    public AnimatedButton(String buttonText, boolean useBgImage, Color defaultForeground, boolean allowAnimate) {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        button.setText(buttonText);
        this.foreground = defaultForeground;
        this.allowAnimate = allowAnimate;
        setOpaque(false);
        add(button);
        if (allowAnimate) {
            runThread.start();
        }
    }
    private Thread runThread = new Thread(
            new Runnable() {

                public void run() {
                    while (true) {
                        try {
                            runThread.sleep(100);
                        } catch (Exception e) {
                        }
                        if (animate) {
                            for (int i = 0; i < colors.length; i++) {
                                button.setForeground(colors[i]);
                                try {
                                    runThread.sleep(fadeSpeed);
                                } catch (Exception e) {
                                }
                            }
                            try {
                                runThread.sleep(100);
                            } catch (Exception e) {
                            }
                            for (int i = colors.length - 1; i >= 0; i--) {
                                button.setForeground(colors[i]);
                                try {
                                    runThread.sleep(fadeSpeed);
                                } catch (Exception le) {
                                }
                            }
                        } // end if animate
                    } //end while
                }
            });

    /**
     *
     * Sets the button's and panel's minimum size
     *
     * @param width width, in pixels
     * @param height height, in pixels
     *
     */
    public void setMaximumSize(int width, int height) {
        setMaximumSize(new Dimension(width, height));
        button.setMaximumSize(new Dimension(width, height));
    }

    /**
     *
     * Sets the button's and panel's minimum size
     *
     * @param width width, in pixels
     * @param height height, in pixels
     *
     */
    public void setMinimumSize(int width, int height) {
        setMinimumSize(new Dimension(width, height));
        button.setMinimumSize(new Dimension(width, height));
    }

    /**
     *
     * Sets the button's and panel's preferred size
     *
     * @param width width, in pixels
     * @param height height, in pixels
     *
     */
    public void setPreferredSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        button.setPreferredSize(new Dimension(width, height));
    }

    /**
     *
     * gets the button instance
     *
     * @return JButton component of this class
     *
     */
    public JButton getButton() {
        return button;
    }

    public void setText(String text) {
        button.setText(text);
    }

    public void setEnable(boolean animate) {
        if (allowAnimate) {
            this.animate = animate;
            if (!animate) {
                button.setForeground(Color.black);
            }
            button.setEnabled(animate);
        } else {
            button.setEnabled(animate);
        }
    }
}

