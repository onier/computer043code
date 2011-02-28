/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drag;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;


/*
 * ColorAndTextTransferHandler.java is used by the 1.4 DragColorDemo.java
 * example.
 *//**
 * Example code that shows a text component that both accepts color (by changing
 * its foreground) and also exports simple text.
 */
public class DragColorTextFieldDemo extends JPanel {

    JCheckBox toggleForeground;
    ColorAndTextTransferHandler colorHandler;

    public DragColorTextFieldDemo() {
        super(new BorderLayout());
        JTextField textField;

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JColorChooser chooser = new JColorChooser();
        chooser.setDragEnabled(true);
        add(chooser, BorderLayout.PAGE_START);

        //Create the color transfer handler.
        colorHandler = new ColorAndTextTransferHandler();

        //Create some text fields.
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        textField = new JTextField("I can accept color/text and drag text.");
        textField.setTransferHandler(colorHandler);
        textField.setDragEnabled(true);
        buttonPanel.add(textField);
        textField = new JTextField("Me too!");
        textField.setTransferHandler(colorHandler);
        textField.setDragEnabled(true);
        buttonPanel.add(textField);
        textField = new JTextField("Me three!");
        textField.setTransferHandler(colorHandler);
        textField.setDragEnabled(true);
        buttonPanel.add(textField);
        add(buttonPanel, BorderLayout.CENTER);
    }

    //Create an Edit menu to support cut/copy/paste.
    public JMenuBar createMenuBar() {
        JMenuItem menuItem = null;
        JMenuBar menuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("Edit");
        mainMenu.setMnemonic(KeyEvent.VK_E);

        menuItem = new JMenuItem(new DefaultEditorKit.CutAction());
        menuItem.setText("Cut");
        menuItem.setMnemonic(KeyEvent.VK_T);
        mainMenu.add(menuItem);
        menuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
        menuItem.setText("Copy");
        menuItem.setMnemonic(KeyEvent.VK_C);
        mainMenu.add(menuItem);
        menuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
        menuItem.setText("Paste");
        menuItem.setMnemonic(KeyEvent.VK_P);
        mainMenu.add(menuItem);

        menuBar.add(mainMenu);
        return menuBar;
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("DragColorTextFieldDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the menu bar and content pane.
        DragColorTextFieldDemo demo = new DragColorTextFieldDemo();
        frame.setJMenuBar(demo.createMenuBar());
        demo.setOpaque(true); //content panes must be opaque
        frame.setContentPane(demo);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });
    }
}

/**
 * An implementation of TransferHandler that adds support for the import of
 * color and the import and export of text. Dropping a color on a component
 * having this TransferHandler changes the foreground of the component to the
 * imported color.
 */
class ColorAndTextTransferHandler extends ColorTransferHandler {

    private DataFlavor stringFlavor = DataFlavor.stringFlavor;
    private JTextComponent source;
    private boolean shouldRemove;
    //Start and end position in the source text.
    //We need this information when performing a MOVE
    //in order to remove the dragged text from the source.
    Position p0 = null, p1 = null;

    //Get the flavors from the Transferable.
    //Is there a color flavor? If so, set the foreground color.
    //Is there a string flavor? If so, set the text property.
    public boolean importData(JComponent c, Transferable t) {
        JTextComponent tc = (JTextComponent) c;

        if (!canImport(c, t.getTransferDataFlavors())) {
            return false;
        }

        if (tc.equals(source) && (tc.getCaretPosition() >= p0.getOffset())
                && (tc.getCaretPosition() <= p1.getOffset())) {
            shouldRemove = false;
            return true;
        }

        if (hasStringFlavor(t.getTransferDataFlavors())) {
            try {
                String str = (String) t.getTransferData(stringFlavor);
                tc.replaceSelection(str);
                return true;
            } catch (UnsupportedFlavorException ufe) {
                System.out.println("importData: unsupported data flavor");
            } catch (IOException ioe) {
                System.out.println("importData: I/O exception");
            }
        }
        //The ColorTransferHandler superclass handles color.
        return super.importData(c, t);
    }

    //Create a Transferable implementation that contains the
    //selected text.
    protected Transferable createTransferable(JComponent c) {
        source = (JTextComponent) c;
        int start = source.getSelectionStart();
        int end = source.getSelectionEnd();
        Document doc = source.getDocument();
        if (start == end) {
            return null;
        }
        try {
            p0 = doc.createPosition(start);
            p1 = doc.createPosition(end);
        } catch (BadLocationException e) {
            System.out.println("Can't create position - unable to remove text from source.");
        }
        shouldRemove = true;
        String data = source.getSelectedText();
        return new StringSelection(data);
    }

    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    //Remove the old text if the action is a MOVE.
    //However, we do not allow dropping on top of the selected text,
    //so in that case do nothing.
    protected void exportDone(JComponent c, Transferable data, int action) {
        if (shouldRemove && (action == MOVE)) {
            if ((p0 != null) && (p1 != null)
                    && (p0.getOffset() != p1.getOffset())) {
                try {
                    JTextComponent tc = (JTextComponent) c;
                    tc.getDocument().remove(p0.getOffset(),
                            p1.getOffset() - p0.getOffset());
                } catch (BadLocationException e) {
                    System.out.println("Can't remove text from source.");
                }
            }
        }
        source = null;
    }

    /**
     * Does flavors contain a color or string Transferable?
     */
    public boolean canImport(JComponent c, DataFlavor[] flavors) {
        if (hasStringFlavor(flavors)) {
            return true;
        }
        return super.canImport(c, flavors);
    }

    /**
     * Does the flavor list have a string flavor?
     */
    protected boolean hasStringFlavor(DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (stringFlavor.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }
}

/*
 * ColorTransferHandler.java is used by the 1.4 DragColorDemo.java and
 * DragColorTextFieldDemo examples.
 */
/**
 * An implementation of TransferHandler that adds support for dropping colors.
 * Dropping a color on a component having this TransferHandler changes the
 * foreground or the background of the component to the dropped color, according
 * to the value of the changesForegroundColor property.
 */
class ColorTransferHandler extends TransferHandler {
    //The data type exported from JColorChooser.

    String mimeType = DataFlavor.javaJVMLocalObjectMimeType
            + ";class=java.awt.Color";
    DataFlavor colorFlavor;
    private boolean changesForegroundColor = true;

    ColorTransferHandler() {
        //Try to create a DataFlavor for color.
        try {
            colorFlavor = new DataFlavor(mimeType);
        } catch (ClassNotFoundException e) {
        }
    }

    /**
     * Overridden to import a Color if it is available.
     * getChangesForegroundColor is used to determine whether the foreground or
     * the background color is changed.
     */
    public boolean importData(JComponent c, Transferable t) {
        if (hasColorFlavor(t.getTransferDataFlavors())) {
            try {
                Color col = (Color) t.getTransferData(colorFlavor);
                if (getChangesForegroundColor()) {
                    c.setForeground(col);
                } else {
                    c.setBackground(col);
                }
                return true;
            } catch (UnsupportedFlavorException ufe) {
                System.out.println("importData: unsupported data flavor");
            } catch (IOException ioe) {
                System.out.println("importData: I/O exception");
            }
        }
        return false;
    }

    /**
     * Does the flavor list have a Color flavor?
     */
    protected boolean hasColorFlavor(DataFlavor[] flavors) {
        if (colorFlavor == null) {
            return false;
        }

        for (int i = 0; i < flavors.length; i++) {
            if (colorFlavor.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Overridden to include a check for a color flavor.
     */
    public boolean canImport(JComponent c, DataFlavor[] flavors) {
        return hasColorFlavor(flavors);
    }

    protected void setChangesForegroundColor(boolean flag) {
        changesForegroundColor = flag;
    }

    protected boolean getChangesForegroundColor() {
        return changesForegroundColor;
    }
}




