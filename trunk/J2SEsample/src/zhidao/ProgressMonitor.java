/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Locale;
import javax.accessibility.*;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.*;
import javax.swing.text.*;

public class ProgressMonitor extends Object implements Accessible {

    private ProgressMonitor root;
    private JDialog dialog;
    private JOptionPane pane;
    private JProgressBar myBar;
    private JLabel noteLabel;
    private Component parentComponent;
    private String note;
    private Object[] cancelOption = null;
    private Object message;
    private long T0;
    private int millisToDecideToPopup = 500;
    private int millisToPopup = 2000;
    private int min;
    private int max;

    public ProgressMonitor(Component parentComponent,
            Object message,
            String note,
            int min,
            int max) {
        this(parentComponent, message, note, min, max, null);
    }

    private ProgressMonitor(Component parentComponent,
            Object message,
            String note,
            int min,
            int max,
            ProgressMonitor group) {
        this.min = min;
        this.max = max;
        this.parentComponent = parentComponent;

        cancelOption = new Object[1];
        cancelOption[0] = UIManager.getString("OptionPane.cancelButtonText");

        this.message = message;
        this.note = note;
        if (group != null) {
            root = (group.root != null) ? group.root : group;
            T0 = root.T0;
            dialog = root.dialog;
        } else {
            T0 = System.currentTimeMillis();
        }
    }

    private class ProgressOptionPane extends JOptionPane {

        ProgressOptionPane(Object messageList) {
            super(messageList,
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    ProgressMonitor.this.cancelOption,
                    null);
        }

        public int getMaxCharactersPerLineCount() {
            return 60;
        }


        public JDialog createDialog(Component parentComponent, String title) {
            final JDialog dialog;

            dialog = new JDialog((Dialog) null, title, true);
            Container contentPane = dialog.getContentPane();

            contentPane.setLayout(new BorderLayout());
            contentPane.add(this, BorderLayout.CENTER);
            dialog.pack();
            dialog.setLocationRelativeTo(parentComponent);
            dialog.addWindowListener(new WindowAdapter() {

                boolean gotFocus = false;

                public void windowClosing(WindowEvent we) {
                    setValue(cancelOption[0]);
                }

                public void windowActivated(WindowEvent we) {
                    // Once window gets focus, set initial focus
                    if (!gotFocus) {
                        selectInitialValue();
                        gotFocus = true;
                    }
                }
            });

            addPropertyChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent event) {
                    if (dialog.isVisible() &&
                            event.getSource() == ProgressOptionPane.this &&
                            (event.getPropertyName().equals(VALUE_PROPERTY) ||
                            event.getPropertyName().equals(INPUT_VALUE_PROPERTY))) {
                        dialog.setVisible(false);
                        dialog.dispose();
                    }
                }
            });

            return dialog;
        }

        public AccessibleContext getAccessibleContext() {
            return ProgressMonitor.this.getAccessibleContext();
        }

        private AccessibleContext getAccessibleJOptionPane() {
            return super.getAccessibleContext();
        }
    }

    public void setProgress(int nv) {
        if (nv >= max) {
            close();
        } else {
            if (myBar != null) {
                myBar.setValue(nv);
            } else {
                long T = System.currentTimeMillis();
                long dT = (int) (T - T0);
                if (dT >= millisToDecideToPopup) {
                    int predictedCompletionTime;
                    if (nv > min) {
                        predictedCompletionTime = (int) ((long) dT *
                                (max - min) /
                                (nv - min));
                    } else {
                        predictedCompletionTime = millisToPopup;
                    }
                    if (predictedCompletionTime >= millisToPopup) {
                        myBar = new JProgressBar();
                        myBar.setMinimum(min);
                        myBar.setMaximum(max);
                        myBar.setValue(nv);
                        if (note != null) {
                            noteLabel = new JLabel(note);
                        }
                        pane = new ProgressOptionPane(new Object[]{message,
                                    noteLabel,
                                    myBar});
                        dialog = pane.createDialog(parentComponent,
                                UIManager.getString(
                                "ProgressMonitor.progressText"));
                        dialog.show();
                    }
                }
            }
        }
    }

    public void close() {
        if (dialog != null) {
            dialog.setVisible(false);
            dialog.dispose();
            dialog = null;
            pane = null;
            myBar = null;
        }
    }

    public int getMinimum() {
        return min;
    }

    public void setMinimum(int m) {
        if (myBar != null) {
            myBar.setMinimum(m);
        }
        min = m;
    }

    public int getMaximum() {
        return max;
    }

    public void setMaximum(int m) {
        if (myBar != null) {
            myBar.setMaximum(m);
        }
        max = m;
    }

    public boolean isCanceled() {
        if (pane == null) {
            return false;
        }
        Object v = pane.getValue();
        return ((v != null) &&
                (cancelOption.length == 1) &&
                (v.equals(cancelOption[0])));
    }

    public void setMillisToDecideToPopup(int millisToDecideToPopup) {
        this.millisToDecideToPopup = millisToDecideToPopup;
    }

    public int getMillisToDecideToPopup() {
        return millisToDecideToPopup;
    }

    public void setMillisToPopup(int millisToPopup) {
        this.millisToPopup = millisToPopup;
    }

    public int getMillisToPopup() {
        return millisToPopup;
    }

    public void setNote(String note) {
        this.note = note;
        if (noteLabel != null) {
            noteLabel.setText(note);
        }
    }

    public String getNote() {
        return note;
    }

    protected AccessibleContext accessibleContext = null;
    private AccessibleContext accessibleJOptionPane = null;

    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleProgressMonitor();
        }
        if (pane != null && accessibleJOptionPane == null) {
            if (accessibleContext instanceof AccessibleProgressMonitor) {
                ((AccessibleProgressMonitor) accessibleContext).optionPaneCreated();
            }
        }
        return accessibleContext;
    }

    protected class AccessibleProgressMonitor extends AccessibleContext
            implements AccessibleText, ChangeListener, PropertyChangeListener {

        private Object oldModelValue;

        protected AccessibleProgressMonitor() {
        }

        private void optionPaneCreated() {
            accessibleJOptionPane =
                    ((ProgressOptionPane) pane).getAccessibleJOptionPane();

            if (myBar != null) {
                myBar.addChangeListener(this);
            }

            if (noteLabel != null) {
                noteLabel.addPropertyChangeListener(this);
            }
        }

        public void stateChanged(ChangeEvent e) {
            if (e == null) {
                return;
            }
            if (myBar != null) {
                // the progress bar value changed
                Object newModelValue = myBar.getValue();
                firePropertyChange(ACCESSIBLE_VALUE_PROPERTY,
                        oldModelValue,
                        newModelValue);
                oldModelValue = newModelValue;
            }
        }

        public void propertyChange(PropertyChangeEvent e) {
            if (e.getSource() == noteLabel && e.getPropertyName() == "text") {
                // the note label text changed
                firePropertyChange(ACCESSIBLE_TEXT_PROPERTY, null, 0);
            }
        }

        public String getAccessibleName() {
            if (accessibleName != null) { // defined in AccessibleContext
                return accessibleName;
            } else if (accessibleJOptionPane != null) {
                // delegate to the AccessibleJOptionPane
                return accessibleJOptionPane.getAccessibleName();
            }
            return null;
        }

        public String getAccessibleDescription() {
            if (accessibleDescription != null) { // defined in AccessibleContext
                return accessibleDescription;
            } else if (accessibleJOptionPane != null) {
                // delegate to the AccessibleJOptionPane
                return accessibleJOptionPane.getAccessibleDescription();
            }
            return null;
        }

        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.PROGRESS_MONITOR;
        }

        public AccessibleStateSet getAccessibleStateSet() {
            if (accessibleJOptionPane != null) {
                // delegate to the AccessibleJOptionPane
                return accessibleJOptionPane.getAccessibleStateSet();
            }
            return null;
        }

        public Accessible getAccessibleParent() {
            if (dialog != null) {
                return (Accessible) dialog;
            }
            return null;
        }

        private AccessibleContext getParentAccessibleContext() {
            if (dialog != null) {
                return dialog.getAccessibleContext();
            }
            return null;
        }

        public int getAccessibleIndexInParent() {
            if (accessibleJOptionPane != null) {
                // delegate to the AccessibleJOptionPane
                return accessibleJOptionPane.getAccessibleIndexInParent();
            }
            return -1;
        }

        public int getAccessibleChildrenCount() {
            // return the number of children in the JPanel containing
            // the message, note label and progress bar
            AccessibleContext ac = getPanelAccessibleContext();
            if (ac != null) {
                return ac.getAccessibleChildrenCount();
            }
            return 0;
        }

        public Accessible getAccessibleChild(int i) {
            // return a child in the JPanel containing the message, note label
            // and progress bar
            AccessibleContext ac = getPanelAccessibleContext();
            if (ac != null) {
                return ac.getAccessibleChild(i);
            }
            return null;
        }

        private AccessibleContext getPanelAccessibleContext() {
            if (myBar != null) {
                Component c = myBar.getParent();
                if (c instanceof Accessible) {
                    return ((Accessible) c).getAccessibleContext();
                }
            }
            return null;
        }

        public Locale getLocale() throws IllegalComponentStateException {
            if (accessibleJOptionPane != null) {
                // delegate to the AccessibleJOptionPane
                return accessibleJOptionPane.getLocale();
            }
            return null;
        }

        public AccessibleComponent getAccessibleComponent() {
            if (accessibleJOptionPane != null) {
                // delegate to the AccessibleJOptionPane
                return accessibleJOptionPane.getAccessibleComponent();
            }
            return null;
        }

        public AccessibleValue getAccessibleValue() {
            if (myBar != null) {
                // delegate to the AccessibleJProgressBar
                return myBar.getAccessibleContext().getAccessibleValue();
            }
            return null;
        }

        public AccessibleText getAccessibleText() {
            if (getNoteLabelAccessibleText() != null) {
                return this;
            }
            return null;
        }

        private AccessibleText getNoteLabelAccessibleText() {
            if (noteLabel != null) {
                return noteLabel.getAccessibleContext().getAccessibleText();
            }
            return null;
        }

        public int getIndexAtPoint(Point p) {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null && sameWindowAncestor(pane, noteLabel)) {
                // convert point from the option pane bounds
                // to the note label bounds.
                Point noteLabelPoint = SwingUtilities.convertPoint(pane,
                        p,
                        noteLabel);
                if (noteLabelPoint != null) {
                    return at.getIndexAtPoint(noteLabelPoint);
                }
            }
            return -1;
        }

        public Rectangle getCharacterBounds(int i) {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null && sameWindowAncestor(pane, noteLabel)) {
                // return rectangle in the option pane bounds
                Rectangle noteLabelRect = at.getCharacterBounds(i);
                if (noteLabelRect != null) {
                    return SwingUtilities.convertRectangle(noteLabel,
                            noteLabelRect,
                            pane);
                }
            }
            return null;
        }

        private boolean sameWindowAncestor(Component src, Component dest) {
            if (src == null || dest == null) {
                return false;
            }
            return SwingUtilities.getWindowAncestor(src) ==
                    SwingUtilities.getWindowAncestor(dest);
        }

        public int getCharCount() {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {	// JLabel contains HTML text
                return at.getCharCount();
            }
            return -1;
        }

        public int getCaretPosition() {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {	// JLabel contains HTML text
                return at.getCaretPosition();
            }
            return -1;
        }

        public String getAtIndex(int part, int index) {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {	// JLabel contains HTML text
                return at.getAtIndex(part, index);
            }
            return null;
        }

        public String getAfterIndex(int part, int index) {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {	// JLabel contains HTML text
                return at.getAfterIndex(part, index);
            }
            return null;
        }

        public String getBeforeIndex(int part, int index) {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {	// JLabel contains HTML text
                return at.getBeforeIndex(part, index);
            }
            return null;
        }

        public AttributeSet getCharacterAttribute(int i) {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {	// JLabel contains HTML text
                return at.getCharacterAttribute(i);
            }
            return null;
        }

        public int getSelectionStart() {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {	// JLabel contains HTML text
                return at.getSelectionStart();
            }
            return -1;
        }

        public int getSelectionEnd() {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {	// JLabel contains HTML text
                return at.getSelectionEnd();
            }
            return -1;
        }

        public String getSelectedText() {
            AccessibleText at = getNoteLabelAccessibleText();
            if (at != null) {	// JLabel contains HTML text
                return at.getSelectedText();
            }
            return null;
        }
    }
}
