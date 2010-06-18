/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MaskImage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class ColorConvertDemo extends JFrame {

    ColorPanel displayPanel;
    JButton grayButton, resetButton;

    public ColorConvertDemo() {
        super();
        Container container = getContentPane();

        displayPanel = new ColorPanel();
        container.add(displayPanel);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(new TitledBorder(
                "Click the Gray Scale Button to Create Gray Scale Image..."));

        grayButton = new JButton("Gray Scale");
        grayButton.addActionListener(new ButtonListener());
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ButtonListener());

        panel.add(grayButton);
        panel.add(resetButton);

        container.add(BorderLayout.SOUTH, panel);

        addWindowListener(new WindowEventHandler());

        setSize(displayPanel.getWidth(), displayPanel.getHeight() + 15);
        setVisible(true);
    }

    class WindowEventHandler extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new ColorConvertDemo();
    }

    class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();

            if (button.equals(grayButton)) {
                displayPanel.grayOut();
                displayPanel.repaint();
            } else if (button.equals(resetButton)) {
                displayPanel.reset();
                displayPanel.repaint();
            }
        }
    }
}

class ColorPanel extends JLabel {

    Image displayImage;
    BufferedImage bi;
    Graphics2D big;

    ColorPanel() {
        setBackground(Color.black);
        loadImage();
        setSize(displayImage.getWidth(this), displayImage.getWidth(this));

        createBufferedImage();
    }

    public void loadImage() {
        displayImage = Toolkit.getDefaultToolkit().getImage(
                "c:\\view.jpg");
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(displayImage, 1);
        try {
            mt.waitForAll();
        } catch (Exception e) {
            System.out.println("Exception while loading.");
        }
        if (displayImage.getWidth(this) == -1) {
            System.out.println("No jpg ");
            System.exit(0);
        }
    }

    public void createBufferedImage() {
        bi = new BufferedImage(displayImage.getWidth(this), displayImage.getHeight(this), BufferedImage.TYPE_INT_RGB);

        big = bi.createGraphics();
        big.drawImage(displayImage, 0, 0, this);
    }

    public void grayOut() {
        ColorConvertOp
                colorConvert = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        colorConvert.filter(bi, bi);
    }

    public void reset() {
        big.setColor(Color.black);
        big.clearRect(0, 0, bi.getWidth(this), bi.getHeight(this));
        big.drawImage(displayImage, 0, 0, this);
    }

    public void update(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        paintComponent(g);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(bi, 0, 0, this);
    }
}


