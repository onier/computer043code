/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MaskImage;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;
import java.awt.image.ShortLookupTable;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.text.Utilities;

public class Sampler extends Frame {

    private Frame mImageFrame;
    private SplitImageComponent mSplitImageComponent;
    private Hashtable mOps;

    public static void main(String[] args) {
        String imageFile = "C:\\view.JPG";
        if (args.length > 0) {
            imageFile = args[0];
        }
        new Sampler(imageFile);
    }

    public Sampler(String imageFile) {
        super();
        createOps();
        createImageFrame(imageFile);
        createUI();
        setVisible(true);
    }

    private void createOps() {
        mOps = new Hashtable();
        createConvolutions();
        createTransformations();
        createLookups();
        createRescales();
        createColorOps();
    }

    private void createConvolutions() {
        float ninth = 1.0f / 9.0f;
        float[] blurKernel = {ninth, ninth, ninth, ninth, ninth, ninth, ninth,
            ninth, ninth};
        mOps.put("Blur", new ConvolveOp(new Kernel(3, 3, blurKernel),
                ConvolveOp.EDGE_NO_OP, null));

        float[] edge = {0f, -1f, 0f, -1f, 4f, -1f, 0f, -1f, 0f};
        mOps.put("Edge detector", new ConvolveOp(new Kernel(3, 3, edge),
                ConvolveOp.EDGE_NO_OP, null));

        float[] sharp = {0f, -1f, 0f, -1f, 5f, -1f, 0f, -1f, 0f};
        mOps.put("Sharpen", new ConvolveOp(new Kernel(3, 3, sharp)));
    }

    private void createTransformations() {
        AffineTransform at;
        at = AffineTransform.getRotateInstance(Math.PI / 6, 0, 285);
        mOps.put("Rotate nearest neighbor", new AffineTransformOp(at, null));

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        mOps.put("Rotate bilinear", new AffineTransformOp(at, rh));

        at = AffineTransform.getScaleInstance(.5, .5);
        mOps.put("Scale .5, .5", new AffineTransformOp(at, null));

        at = AffineTransform.getRotateInstance(Math.PI / 6);
        mOps.put("Rotate bilinear (origin)", new AffineTransformOp(at, rh));
    }

    private void createLookups() {
        short[] brighten = new short[256];
        short[] betterBrighten = new short[256];
        short[] posterize = new short[256];
        short[] invert = new short[256];
        short[] straight = new short[256];
        short[] zero = new short[256];
        for (int i = 0; i < 256; i++) {
            brighten[i] = (short) (128 + i / 2);
            betterBrighten[i] = (short) (Math.sqrt((double) i / 255.0) * 255.0);
            posterize[i] = (short) (i - (i % 32));
            invert[i] = (short) (255 - i);
            straight[i] = (short) i;
            zero[i] = (short) 0;
        }
        mOps.put("Brighten", new LookupOp(new ShortLookupTable(0, brighten),
                null));
        mOps.put("Better Brighten", new LookupOp(new ShortLookupTable(0,
                betterBrighten), null));
        mOps.put("Posterize", new LookupOp(new ShortLookupTable(0, posterize),
                null));
        mOps.put("Invert", new LookupOp(new ShortLookupTable(0, invert), null));

        short[][] redOnly = {invert, straight, straight};
        short[][] greenOnly = {straight, invert, straight};
        short[][] blueOnly = {straight, straight, invert};
        mOps.put("Red invert", new LookupOp(new ShortLookupTable(0, redOnly),
                null));
        mOps.put("Green invert", new LookupOp(
                new ShortLookupTable(0, greenOnly), null));
        mOps.put("Blue invert", new LookupOp(new ShortLookupTable(0, blueOnly),
                null));

        short[][] redRemove = {zero, straight, straight};
        short[][] greenRemove = {straight, zero, straight};
        short[][] blueRemove = {straight, straight, zero};
        mOps.put("Red remove", new LookupOp(new ShortLookupTable(0, redRemove),
                null));
        mOps.put("Green remove", new LookupOp(new ShortLookupTable(0,
                greenRemove), null));
        mOps.put("Blue remove", new LookupOp(
                new ShortLookupTable(0, blueRemove), null));
    }

    private void createRescales() {
        mOps.put("Rescale .5, 0", new RescaleOp(.5f, 0, null));
        mOps.put("Rescale .5, 64", new RescaleOp(.5f, 64, null));
        mOps.put("Rescale 1.2, 0", new RescaleOp(1.2f, 0, null));
        mOps.put("Rescale 1.5, 0", new RescaleOp(1.5f, 0, null));
    }

    private void createColorOps() {
        mOps.put("Grayscale", new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null));
    }

    private void createImageFrame(String imageFile) {
        // Create the image frame.
        mSplitImageComponent = new SplitImageComponent(imageFile);
        mImageFrame = new Frame(imageFile);
        mImageFrame.setLayout(new BorderLayout());
        mImageFrame.add(mSplitImageComponent, BorderLayout.CENTER);
//    Utilities.sizeContainerToComponent(mImageFrame, mSplitImageComponent);
        //  Utilities.centerFrame(mImageFrame);
        mImageFrame.setVisible(true);
    }

    private void createUI() {
        setFont(new Font("Serif", Font.PLAIN, 12));
        setLayout(new BorderLayout());
        // Set our location to the left of the image frame.
        setSize(200, 350);
        Point pt = mImageFrame.getLocation();
        setLocation(pt.x - getSize().width, pt.y);

        final Checkbox accumulateCheckbox = new Checkbox("Accumulate", false);
        final Label statusLabel = new Label("");

        // Make a sorted list of the operators.
        Enumeration e = mOps.keys();
        Vector names = new Vector();
        while (e.hasMoreElements()) {
            names.addElement(e.nextElement());
        }
        Collections.sort(names);
        final java.awt.List list = new java.awt.List();
        for (int i = 0; i < names.size(); i++) {
            list.add((String) names.elementAt(i));
        }
        add(list, BorderLayout.CENTER);

        // When an item is selected, do the corresponding transformation.
        list.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() != ItemEvent.SELECTED) {
                    return;
                }
                String key = list.getSelectedItem();
                BufferedImageOp op = (BufferedImageOp) mOps.get(key);
                BufferedImage source = mSplitImageComponent.getSecondImage();
                boolean accumulate = accumulateCheckbox.getState();
                if (source == null || accumulate == false) {
                    source = mSplitImageComponent.getImage();
                }
                String previous = mImageFrame.getTitle() + " + ";
                if (accumulate == false) {
                    previous = "";
                }
                mImageFrame.setTitle(previous + key);
                statusLabel.setText("Performing " + key + "...");
                list.setEnabled(false);
                accumulateCheckbox.setEnabled(false);
                BufferedImage destination = op.filter(source, null);
                mSplitImageComponent.setSecondImage(destination);
                mSplitImageComponent.setSize(mSplitImageComponent.getPreferredSize());
                mImageFrame.setSize(mImageFrame.getPreferredSize());
                list.setEnabled(true);
                accumulateCheckbox.setEnabled(true);
                statusLabel.setText("Performing " + key + "...done.");
            }
        });

        Button loadButton = new Button("Load...");
        loadButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                FileDialog fd = new FileDialog(Sampler.this);
                fd.show();
                if (fd.getFile() == null) {
                    return;
                }
                String path = fd.getDirectory() + fd.getFile();
                mSplitImageComponent.setImage(path);
                mSplitImageComponent.setSecondImage(null);
//        Utilities.sizeContainerToComponent(mImageFrame,
                //          mSplitImageComponent);
                mImageFrame.validate();
                mImageFrame.repaint();
            }
        });

        Panel bottom = new Panel(new GridLayout(2, 1));
        Panel topBottom = new Panel();
        topBottom.add(accumulateCheckbox);
        topBottom.add(loadButton);
        bottom.add(topBottom);
        bottom.add(statusLabel);
        add(bottom, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                mImageFrame.dispose();
                dispose();
                System.exit(0);
            }
        });
    }

    class SplitImageComponent extends JPanel {

        private BufferedImage mImage;
        private BufferedImage mSecondImage;
        private int mSplitX;

        public SplitImageComponent(String path) {
            setImage(path);
            init();
        }

        public SplitImageComponent(BufferedImage image) {
            setImage(image);
            init();
        }

        public void setImage(String path) {
            Image image = blockingLoad(path);
            mImage = makeBufferedImage(image);
        }

        public void setImage(BufferedImage image) {
            mImage = image;
        }

        public void setSecondImage(BufferedImage image) {
            mSecondImage = image;
            repaint();
        }

        public BufferedImage getImage() {
            return mImage;
        }

        public BufferedImage getSecondImage() {
            return mSecondImage;
        }

        private void init() {
            setBackground(Color.white);
            addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent me) {
                    mSplitX = me.getX();
                    repaint();
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {

                public void mouseDragged(MouseEvent me) {
                    mSplitX = me.getX();
                    repaint();
                }
            });
        }

        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            int width = getSize().width;
            int height = getSize().height;

            // Explicitly clear the window.
            Rectangle clear = new Rectangle(0, 0, width, height);
            g2.setPaint(getBackground());
            g2.fill(clear);
            // Clip the first image, if appropriate,
            // to be on the right side of the split.
            if (mSplitX != 0 && mSecondImage != null) {
                Rectangle firstClip = new Rectangle(mSplitX, 0, width - mSplitX,
                        height);
                g2.setClip(firstClip);
            }
            g2.drawImage(getImage(), 0, 0, null);

            if (mSplitX == 0 || mSecondImage == null) {
                return;
            }

            Rectangle secondClip = new Rectangle(0, 0, mSplitX, height);
            g2.setClip(secondClip);
            g2.drawImage(mSecondImage, 0, 0, null);

            Line2D splitLine = new Line2D.Float(mSplitX, 0, mSplitX, height);
            g2.setClip(null);
            g2.setColor(Color.white);
            g2.draw(splitLine);
        }

        public Dimension getPreferredSize() {
            int width = getImage().getWidth();
            int height = getImage().getHeight();
            if (mSecondImage != null) {
                width = Math.max(width, mSecondImage.getWidth());
                height = Math.max(height, mSecondImage.getHeight());
            }
            return new Dimension(width, height);
        }
    }
    private Component sComponent = new Component() {
    };
    private final MediaTracker sTracker = new MediaTracker(sComponent);
    private int sID = 0;

    public boolean waitForImage(Image image) {
        int id;
        synchronized (sComponent) {
            id = sID++;
        }
        sTracker.addImage(image, id);
        try {
            sTracker.waitForID(id);
        } catch (InterruptedException ie) {
            return false;
        }
        if (sTracker.isErrorID(id)) {
            return false;
        }
        return true;
    }

    public Image blockingLoad(String path) {
        Image image = Toolkit.getDefaultToolkit().getImage(path);
        if (waitForImage(image) == false) {
            return null;
        }
        return image;
    }

    public Image blockingLoad(URL url) {
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        if (waitForImage(image) == false) {
            return null;
        }
        return image;
    }

    public BufferedImage makeBufferedImage(Image image) {
        return makeBufferedImage(image, BufferedImage.TYPE_INT_RGB);
    }

    public BufferedImage makeBufferedImage(Image image, int imageType) {
        if (waitForImage(image) == false) {
            return null;
        }

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), imageType);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, null, null);
        return bufferedImage;
    }
}

