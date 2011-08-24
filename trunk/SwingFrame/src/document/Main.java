/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

class JTextFieldFilter extends PlainDocument {

    public static final String FLOAT = "0123456789.";
    protected String acceptedChars = null;
    protected boolean negativeAccepted = false;

    public JTextFieldFilter() {
        this(FLOAT);
    }

    public JTextFieldFilter(String acceptedchars) {
        acceptedChars = acceptedchars;
    }

    public void setNegativeAccepted(boolean negativeaccepted) {
        if (acceptedChars.equals(FLOAT)) {
            negativeAccepted = negativeaccepted;
            acceptedChars += "-";
        }
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            if (acceptedChars.indexOf(str.valueOf(str.charAt(i))) == -1) {
                return;
            }
        }

        if (acceptedChars.equals(FLOAT) || (acceptedChars.equals(FLOAT + "-") && negativeAccepted)) {
            if (str.indexOf(".") != -1) {
                if (getText(0, getLength()).indexOf(".") != -1) {
                    return;
                }
            }
        }

        if (negativeAccepted && str.indexOf("-") != -1) {
            if (str.indexOf("-") != 0 || offset != 0) {
                return;
            }
        }

        super.insertString(offset, str, attr);
    }
}

public class Main extends JFrame {

    public static void main(String[] argv) throws Exception {
        new Main();
    }

    public Main() {
        JTextField tf1b;
        JLabel l1b;

        setLayout(new FlowLayout());

        l1b = new JLabel("only float");
        tf1b = new JTextField(10);
        getContentPane().add(l1b);
        getContentPane().add(tf1b);
        tf1b.setDocument(new JTextFieldFilter(JTextFieldFilter.FLOAT));
        setSize(300, 300);
        setVisible(true);
    }
}
