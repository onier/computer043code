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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class CombineApp extends JFrame {

    CombinePanel displayPanel;
    JButton redBandButton, greenBandButton, blueBandButton, inverseBandButton,
            middleBandButton, resetButton;

    public CombineApp() {
        super("TBandCombineOp");
        Container container = getContentPane();

        displayPanel = new CombinePanel();
        container.add(displayPanel);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.setBorder(new TitledBorder(
                "Click a Button to Perform the Associated Operation and Reset..."));

        redBandButton = new JButton("Show Red Band");
        redBandButton.addActionListener(new ButtonListener());
        greenBandButton = new JButton("Show Green Band");
        greenBandButton.addActionListener(new ButtonListener());
        blueBandButton = new JButton("Show Blue Band");
        blueBandButton.addActionListener(new ButtonListener());
        inverseBandButton = new JButton("Invert All Bands");
        inverseBandButton.addActionListener(new ButtonListener());
        middleBandButton = new JButton("Average Each Band");
        middleBandButton.addActionListener(new ButtonListener());

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ButtonListener());

        panel.add(redBandButton);
        panel.add(blueBandButton);
        panel.add(greenBandButton);
        panel.add(inverseBandButton);
        panel.add(middleBandButton);
        panel.add(resetButton);

        container.add(BorderLayout.SOUTH, panel);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setSize(displayPanel.getWidth(), displayPanel.getHeight());
        setVisible(true);
    }

    public static void main(String[] args) {
        new CombineApp();
    }

    class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();

            if (button.equals(redBandButton)) {
                displayPanel.bandCombine(CombinePanel.RED_BAND_MATRIX);
                displayPanel.repaint();
            } else if (button.equals(greenBandButton)) {
                displayPanel.bandCombine(CombinePanel.GREEN_BAND_MATRIX);
                displayPanel.repaint();
            } else if (button.equals(blueBandButton)) {
                displayPanel.bandCombine(CombinePanel.BLUE_BAND_MATRIX);
                displayPanel.repaint();
            } else if (button.equals(inverseBandButton)) {
                displayPanel.bandCombine(CombinePanel.INVERSE_BAND_MATRIX);
                displayPanel.repaint();
            } else if (button.equals(middleBandButton)) {
                displayPanel.bandCombine(CombinePanel.AVERAGE_BAND_MATRIX);
                displayPanel.repaint();
            } else if (button.equals(resetButton)) {
                displayPanel.reset();
                displayPanel.repaint();
            }
        }
    }
}

class CombinePanel extends JLabel {
    // red band Matrix

    static final float RED_BAND_MATRIX[][] = {{1.0f, 0.0f, 0.0f},
        {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}};
    // green band Matrix
    static final float GREEN_BAND_MATRIX[][] = {{0.0f, 0.0f, 0.0f},
        {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f, 0.0f}};
    // blue band Matrix
    static final float BLUE_BAND_MATRIX[][] = {{0.0f, 0.0f, 0.0f},
        {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 1.0f}};
    // Matrix that inverts all the bands
    // the nagative of the image.
    static final float INVERSE_BAND_MATRIX[][] = {{-1.0f, 0.0f, 0.0f},
        {0.0f, -1.0f, 0.0f}, {0.0f, 0.0f, -1.0f}};
    // Matrix that reduces the intensities of all bands
    static final float AVERAGE_BAND_MATRIX[][] = {{0.5f, 0.0f, 0.0f},
        {0.0f, 0.5f, 0.0f}, {0.0f, 0.0f, 0.5f}};
    Image displayImage;
    // The source and destination images
    BufferedImage biSrc;
    BufferedImage biDest;
    // The source and destination rasters
    Raster srcRaster;
    WritableRaster dstRaster;
    BufferedImage bi;
    Graphics2D big;

    CombinePanel() {
        setBackground(Color.black);
        loadImage();
        setSize(displayImage.getWidth(this), displayImage.getWidth(this));

        createBufferedImages();
        bi = biSrc;
    }

    public void loadImage() {
        try {
            displayImage = new javax.swing.ImageIcon(new URL("http://news.xinhuanet.com/mil/2010-11/18/12790465_31n.jpg")).getImage();
            MediaTracker mt = new MediaTracker(this);
            mt.addImage(displayImage, 1);
            try {
                mt.waitForAll();
            } catch (Exception e) {
                System.out.println("Exception while loading.");
            }
            if (displayImage.getWidth(this) == -1) {
                System.out.println("No jpg) file");
                System.exit(0);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(CombinePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createBufferedImages() {
        biSrc = new BufferedImage(displayImage.getWidth(this), displayImage.getHeight(this), BufferedImage.TYPE_INT_RGB);

        big = biSrc.createGraphics();
        big.drawImage(displayImage, 0, 0, this);

        srcRaster = biSrc.getRaster();

        biDest = new BufferedImage(displayImage.getWidth(this), displayImage.getHeight(this), BufferedImage.TYPE_INT_RGB);

        dstRaster = (WritableRaster) biDest.getRaster();
    }

    public void bandCombine(float[][] bandCombineMatrix) {
        BandCombineOp bandCombineOp = new BandCombineOp(bandCombineMatrix, null);
        bandCombineOp.filter(srcRaster, dstRaster);
        bi = biDest;
    }

    public void reset() {
        big.setColor(Color.black);
        big.clearRect(0, 0, bi.getWidth(this), bi.getHeight(this));
        big.drawImage(displayImage, 0, 0, this);
        bi = biSrc;
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
