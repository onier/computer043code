/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

/*
Java Swing, 2nd Edition
By Marc Loy, Robert Eckstein, Dave Wood, James Elliott, Brian Cole
ISBN: 0-596-00408-7
Publisher: O'Reilly
 */
// UndoStyleFrame.java
//Add undo support to the StyleFrame example. This example only
//retains the most recent edit, to keep things simple.
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.undo.UndoableEdit;


//StyleFrame.java
//A JTextPane with a menu for Style manipulation.
public class UndoStyleFrame extends StyleFrame {

    protected UndoAct undoAction = new UndoAct(); // an Action for undo
    protected RedoAct redoAction = new RedoAct(); // an Action for redo

    public UndoStyleFrame() {
        super();
        setTitle("UndoStyleFrame");

        // register the Actions as undo listeners (we inherited textPane)
        textPane.getDocument().addUndoableEditListener(undoAction);
        textPane.getDocument().addUndoableEditListener(redoAction);

        // create menu for undo/redo
        JMenu editMenu = new JMenu("Edit");
        editMenu.add(new JMenuItem(undoAction));
        editMenu.add(new JMenuItem(redoAction));
        menuBar.add(editMenu); // we inherited menuBar from superclass

        // create buttons for undo/redo
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton(undoAction));
        buttonPanel.add(new JButton(redoAction));
        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);
    }

    // begin inner classes ------------
    public class UndoAct extends AbstractAction implements UndoableEditListener {

        private UndoableEdit edit;

        public UndoAct() {
            super("Undo");
            setEnabled(false);
        }

        public void updateEnabled() {
            setEnabled(edit.canUndo());
        }

        public void undoableEditHappened(UndoableEditEvent event) {
            edit = event.getEdit();
            putValue(NAME, edit.getUndoPresentationName());
            updateEnabled();
        }

        public void actionPerformed(ActionEvent ae) {
            edit.undo();
            updateEnabled(); // disable undo
            redoAction.updateEnabled(); // enable redo
        }
    }

    public class RedoAct extends AbstractAction implements UndoableEditListener {

        private UndoableEdit edit;

        public RedoAct() {
            super("Redo");
            setEnabled(false);
        }

        public void updateEnabled() {
            setEnabled(edit.canRedo());
        }

        public void undoableEditHappened(UndoableEditEvent event) {
            edit = event.getEdit();
            putValue(NAME, edit.getRedoPresentationName());
            updateEnabled();
        }

        public void actionPerformed(ActionEvent ae) {
            edit.redo();
            updateEnabled(); // disable redo
            undoAction.updateEnabled(); // enable undo
        }
    }

    // end inner classes ------------
    public static void main(String[] args) {
        JFrame frame = new UndoStyleFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
class StyleFrame extends JFrame implements ActionListener {

    protected StyleBox styleBox;
    protected JTextPane textPane;
    protected JMenuBar menuBar;
    protected JMenu applyStyleMenu,  modifyStyleMenu;
    protected JMenuItem createItem;

    public StyleFrame() {
        super("StyleFrame");

        styleBox = new StyleBox();
        textPane = new JTextPane();
        getContentPane().add(new JScrollPane(textPane), BorderLayout.CENTER);

        // set up menu
        menuBar = new JMenuBar();
        JMenu styleMenu = new JMenu("Style");
        menuBar.add(styleMenu);
        setJMenuBar(menuBar);

        applyStyleMenu = new JMenu("Set Logical Style");
        applyStyleMenu.setToolTipText("set the Logical Style for the paragraph at caret location");
        styleMenu.add(applyStyleMenu);

        modifyStyleMenu = new JMenu("Modify Style");
        modifyStyleMenu.setToolTipText("redefine a named Style (will affect paragraphs using that style)");
        styleMenu.add(modifyStyleMenu);

        createItem = new JMenuItem("Create New Style");
        createItem.setToolTipText("define a new Style (which can then be applied to paragraphs)");
        createItem.addActionListener(this);
        styleMenu.add(createItem);

        // add the default style to applyStyleMenu and modifyStyleMenu
        createMenuItems(StyleContext.DEFAULT_STYLE);
    }

    protected void createMenuItems(String styleName) {
        // add 'styleName' to applyStyleMenu and modifyStyleMenu
        JMenuItem applyItem = new JMenuItem(styleName);
        applyItem.addActionListener(this);
        applyStyleMenu.add(applyItem);

        JMenuItem modifyItem = new JMenuItem(styleName);
        modifyItem.addActionListener(this);
        modifyStyleMenu.add(modifyItem);
    }

    public void actionPerformed(ActionEvent e) {
        // determine which menuItem was invoked and process it
        JMenuItem source = (JMenuItem) e.getSource();

        if (applyStyleMenu.isMenuComponent(source)) {
            // apply an existing style to the paragraph at the caret position
            String styleName = source.getActionCommand();
            Style style = textPane.getStyle(styleName);
            textPane.setLogicalStyle(style);
        }

        if (source == createItem) {
            // define a new Style and add it to the menus
            styleBox.clear();
            int response = JOptionPane.showConfirmDialog(this, styleBox,
                    "Style Editor", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (response == JOptionPane.OK_OPTION && styleBox.getStyleName().length() > 0) {
                String styleName = styleBox.getStyleName();
                Style style = textPane.addStyle(styleName, null);
                styleBox.fillStyle(style);
                createMenuItems(styleName); // add new Style to the menus
            }
        }

        if (modifyStyleMenu.isMenuComponent(source)) {
            // redefine a Style (will automatically redraw paragraphs using
            // Style)
            String styleName = source.getActionCommand();
            Style style = textPane.getStyle(styleName);
            styleBox.loadFromStyle(style);
            int response = JOptionPane.showConfirmDialog(this, styleBox,
                    "Style Editor", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (response == JOptionPane.OK_OPTION) {
                styleBox.fillStyle(style);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new StyleFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}

//StyleBox.java
//A control panel that can be used to edit a style's paragraph attributes.
class StyleBox extends JPanel {

    private static final String[] fonts = {"Monospaced", "Serif", "SansSerif"};
    private static final String[] sizes = {"8", "10", "12", "18", "24", "36"};
    private JTextField nameField;
    private JComboBox fontCombo,  sizeCombo;
    private JTextField leftField,  rightField,  aboveField,  belowField;
    private JCheckBox boldCheck,  italicCheck;

    public StyleBox() {
        // create the fields and lay them out
        super(new BorderLayout(4, 4));
        JPanel labelPanel = new JPanel(new GridLayout(8, 1, 0, 2));
        JPanel valuePanel = new JPanel(new GridLayout(8, 1, 0, 2));
        add(labelPanel, BorderLayout.WEST);
        add(valuePanel, BorderLayout.CENTER);
        JLabel lab;
        JPanel sidePanel;

        lab = new JLabel("Style Name", SwingConstants.RIGHT);
        labelPanel.add(lab);
        nameField = new JTextField();
        lab.setLabelFor(nameField);
        valuePanel.add(nameField);

        lab = new JLabel("Font", SwingConstants.RIGHT);
        labelPanel.add(lab);
        fontCombo = new JComboBox(fonts);
        fontCombo.setEditable(true); // user may enter custom value
        lab.setLabelFor(fontCombo);
        valuePanel.add(fontCombo);

        lab = new JLabel("Size", SwingConstants.RIGHT);
        labelPanel.add(lab);
        sizeCombo = new JComboBox(sizes);
        sizeCombo.setEditable(true); // user may enter custom value
        lab.setLabelFor(sizeCombo);
        sidePanel = new JPanel(new BorderLayout(4, 0));
        sidePanel.add(sizeCombo, BorderLayout.CENTER);
        sidePanel.add(new JLabel("points"), BorderLayout.EAST);
        valuePanel.add(sidePanel);

        lab = new JLabel("Left Indent", SwingConstants.RIGHT);
        labelPanel.add(lab);
        leftField = new JTextField();
        lab.setLabelFor(leftField);
        sidePanel = new JPanel(new BorderLayout(4, 0));
        sidePanel.add(leftField, BorderLayout.CENTER);
        sidePanel.add(new JLabel("points"), BorderLayout.EAST);
        valuePanel.add(sidePanel);

        lab = new JLabel("Right Indent", SwingConstants.RIGHT);
        labelPanel.add(lab);
        rightField = new JTextField();
        lab.setLabelFor(rightField);
        sidePanel = new JPanel(new BorderLayout(4, 0));
        sidePanel.add(rightField, BorderLayout.CENTER);
        sidePanel.add(new JLabel("points"), BorderLayout.EAST);
        valuePanel.add(sidePanel);

        lab = new JLabel("Space Above", SwingConstants.RIGHT);
        labelPanel.add(lab);
        aboveField = new JTextField();
        lab.setLabelFor(aboveField);
        sidePanel = new JPanel(new BorderLayout(4, 0));
        sidePanel.add(aboveField, BorderLayout.CENTER);
        sidePanel.add(new JLabel("points"), BorderLayout.EAST);
        valuePanel.add(sidePanel);

        lab = new JLabel("Space Below", SwingConstants.RIGHT);
        labelPanel.add(lab);
        belowField = new JTextField();
        lab.setLabelFor(belowField);
        sidePanel = new JPanel(new BorderLayout(4, 0));
        sidePanel.add(belowField, BorderLayout.CENTER);
        sidePanel.add(new JLabel("points"), BorderLayout.EAST);
        valuePanel.add(sidePanel);

        boldCheck = new JCheckBox("Bold");
        italicCheck = new JCheckBox("Italic");
        sidePanel = new JPanel(new GridLayout(1, 2));
        sidePanel.add(boldCheck);
        sidePanel.add(italicCheck);
        valuePanel.add(sidePanel);

        clear(); // sets initial values, etc.
    }

    public void clear() {
        // reset all fields (also sets nameField to be editable)
        nameField.setText("");
        nameField.setEditable(true);
        fontCombo.setSelectedIndex(0);
        sizeCombo.setSelectedIndex(2);
        leftField.setText("0.0");
        rightField.setText("0.0");
        aboveField.setText("0.0");
        belowField.setText("0.0");
        boldCheck.setSelected(false);
        italicCheck.setSelected(false);
    }

    public String getStyleName() {
        // return the name of the style
        String name = nameField.getText();
        if (name.length() > 0) {
            return name;
        } else {
            return null;
        }
    }

    public void fillStyle(Style style) {
        // mutate 'style' with the values entered in the fields
        // (no value checking--could throw NumberFormatException)
        String font = (String) fontCombo.getSelectedItem();
        StyleConstants.setFontFamily(style, font);

        String size = (String) sizeCombo.getSelectedItem();
        StyleConstants.setFontSize(style, Integer.parseInt(size));

        String left = leftField.getText();
        StyleConstants.setLeftIndent(style, Float.valueOf(left).floatValue());

        String right = rightField.getText();
        StyleConstants.setRightIndent(style, Float.valueOf(right).floatValue());

        String above = aboveField.getText();
        StyleConstants.setSpaceAbove(style, Float.valueOf(above).floatValue());

        String below = belowField.getText();
        StyleConstants.setSpaceBelow(style, Float.valueOf(below).floatValue());

        boolean bold = boldCheck.isSelected();
        StyleConstants.setBold(style, bold);

        boolean italic = italicCheck.isSelected();
        StyleConstants.setItalic(style, italic);
    }

    // Load the form from an existing Style.
    public void loadFromStyle(Style style) {
        nameField.setText(style.getName());
        nameField.setEditable(false); // don't allow name change

        String fam = StyleConstants.getFontFamily(style);
        fontCombo.setSelectedItem(fam);

        int size = StyleConstants.getFontSize(style);
        sizeCombo.setSelectedItem(Integer.toString(size));

        float left = StyleConstants.getLeftIndent(style);
        leftField.setText(Float.toString(left));

        float right = StyleConstants.getRightIndent(style);
        rightField.setText(Float.toString(right));

        float above = StyleConstants.getSpaceAbove(style);
        aboveField.setText(Float.toString(above));

        float below = StyleConstants.getSpaceBelow(style);
        belowField.setText(Float.toString(below));

        boolean bold = StyleConstants.isBold(style);
        boldCheck.setSelected(bold);

        boolean italic = StyleConstants.isItalic(style);
        italicCheck.setSelected(italic);
    }
}

