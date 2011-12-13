/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import code.ClassListModel.ListElement;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import sun.swing.SwingUtilities2;

/**
 *
 * @author Administrator
 */
public class CodeHelpAction1 extends AbstractAction implements KeyListener {

    public static final String ACTION_START = ".";
    public static final String ACTION_COMMAD = "helpCode";
    public static final KeyStroke ACTION_STROKE = KeyStroke.getKeyStroke(46, 0);
    private CodeHelpComponent componenet = new CodeHelpComponent(ClassPropertyList.class);
    private DefaultStyledDocument document = new DefaultStyledDocument();
    private ArrayList<Character> buffer = new ArrayList<Character>();
    private JTextComponent text;
    private boolean firing = false;
    private Action upAction, downAction;
    private Object upName, downName;

    public CodeHelpAction1(JTextComponent textCom) {
        this.text = textCom;
        textCom.setDocument(document);
        componenet.getClassPropertyList().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    addEdit();
                }
            }
        });
        componenet.addPopupMenuListener(new PopupMenuListener() {

            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                uninstallDefaultAction();
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                installDefaultAction();
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
                installDefaultAction();
            }
        });
    }

    private void uninstallDefaultAction() {
        upName = text.getInputMap().get(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
        upAction = text.getActionMap().get(upName);
        downName = text.getInputMap().get(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
        downAction = text.getActionMap().get(downName);
        AbstractAction action = new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
            }
        };
        text.getActionMap().put(upName, action);
        text.getActionMap().put(downName, action);
    }

    private void installDefaultAction() {
        if (upName != null && downName != null) {
            text.getActionMap().put(upName, upAction);
            text.getActionMap().put(downName, downAction);
        }
    }

    public void installCodeHelpAction() {
        ActionMap am = text.getActionMap();
        InputMap im = text.getInputMap();
        am.put(ACTION_COMMAD, this);
        im.put(ACTION_STROKE, ACTION_COMMAD);
        text.addKeyListener(this);
    }

    public void uninstallCodeHelpAction() {
        ActionMap am = text.getActionMap();
        InputMap im = text.getInputMap();
        am.remove(ACTION_COMMAD);
        im.remove(ACTION_STROKE);
        text.removeKeyListener(this);
    }

    private void getClassForModel() {
        int n = text.getCaretPosition();
        String str = text.getText();
        str = text.getText().substring(0, n);
        int m = str.lastIndexOf(" ");
    }

    public void actionPerformed(ActionEvent e) {
        try {
            FontMetrics metrics = SwingUtilities2.getFontMetrics(text, text.getFont());
            int rowHeight = metrics.getHeight();
            Rectangle rect = text.modelToView(text.getCaretPosition());
            System.out.println(rect.y / rowHeight);
            componenet.getClassPropertyList().setSelectedIndex(0);
            componenet.setSize(800, 600);
            buffer.clear();
            componenet.show(text, rect.x, rect.y + rowHeight);
            text.requestFocusInWindow();
        } catch (BadLocationException ex) {
            Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    private void addEdit() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                int n = text.getCaretPosition();
                String str = text.getText();
                String str1 = text.getText().substring(n, text.getText().length());
                str = str.substring(0, n);
                int index = str.lastIndexOf(ACTION_START);
                str = str.substring(0, index + 1);
                ListElement element = componenet.getClassPropertyList().getSelectedValue();
                str = str + element.getInputValue();
                if (element.getType().equalsIgnoreCase("void") || element.getType().equalsIgnoreCase("boolean") || element.getType().equalsIgnoreCase("int") || element.getType().equalsIgnoreCase("float") || element.getType().equalsIgnoreCase("double")) {
                    str = str + ";";
                }
                final int m = str.lastIndexOf("para0");
                text.setText(str + str1);
                final int p = str.length();
                componenet.setVisible(false);
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        if (m != -1) {
                            text.setSelectionStart(m);
                            text.setSelectionEnd(m + 5);
                        } else {
                            text.setCaretPosition(p);
                        }
                    }
                });
            }
        });
    }

    public void keyPressed(KeyEvent e) {
        int index, size;
        if (componenet.isVisible()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_BACK_SPACE:
                    if (buffer.size() == 0) {
                        componenet.setVisible(false);
                    } else {
                        buffer.remove(buffer.size() - 1);
                        componenet.scrollHelpView(getKeyValue());
                    }
                    return;
                case KeyEvent.VK_ENTER:
                    firing = true;
                    addEdit();
                    return;
                case KeyEvent.VK_UP:
                    firing = true;
                    index = componenet.getClassPropertyList().getSelectedIndex();
                    index--;
                    size = componenet.getClassPropertyList().getModel().getSize();
                    index = index + size;
                    componenet.getClassPropertyList().setSelectedIndex(index % size);
                    componenet.getClassPropertyList().ensureIndexIsVisible(index % size);
                    e.setKeyChar('\0');
                    return;
                case KeyEvent.VK_DOWN:
                    firing = true;
                    index = componenet.getClassPropertyList().getSelectedIndex();
                    index++;
                    size = componenet.getClassPropertyList().getModel().getSize();
                    componenet.getClassPropertyList().setSelectedIndex(index % size);
                    componenet.getClassPropertyList().ensureIndexIsVisible(index % size);
                    e.setKeyChar('\0');
                    return;
            }
            buffer.add(e.getKeyChar());
        }
    }

    private String getKeyValue() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < buffer.size(); i++) {
            str.append(buffer.get(i));
        }
        return str.toString();
    }

    public void keyReleased(KeyEvent e) {
        Style errorStyle;
        String key = getKeyValue();
        if (componenet.isVisible() && !firing) {
            if (!componenet.scrollHelpView(key) && key != null) {
                final int index = text.getText().lastIndexOf(ACTION_START);
                if (document.getStyle("error") == null) {
                    errorStyle = document.addStyle("error", null);
                } else {
                    errorStyle = document.getStyle("error");
                }
                errorStyle.addAttribute(StyleConstants.Foreground, Color.RED);
                document.setCharacterAttributes(index + 1, key.length(), errorStyle, false);
            } else {
                document.removeStyle("error");
            }
        }
        firing = false;
    }
}
