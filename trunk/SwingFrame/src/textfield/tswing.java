/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textfield;

import java.awt.FlowLayout;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

// or  import javax.swing.*;    if Java 2
 class JTextFieldFilter extends PlainDocument {

    public static final String LOWERCASE =
            "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPERCASE =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ALPHA =
            LOWERCASE + UPPERCASE;
    public static final String NUMERIC =
            "0123456789";
    public static final String FLOAT =
            NUMERIC + ".";
    public static final String ALPHA_NUMERIC =
            ALPHA + NUMERIC;
    protected String acceptedChars = null;
    protected boolean negativeAccepted = false;

    public JTextFieldFilter() {
        this(ALPHA_NUMERIC);
    }

    public JTextFieldFilter(String acceptedchars) {
        acceptedChars = acceptedchars;
    }

    public void setNegativeAccepted(boolean negativeaccepted) {
        if (acceptedChars.equals(NUMERIC)
                || acceptedChars.equals(FLOAT)
                || acceptedChars.equals(ALPHA_NUMERIC)) {
            negativeAccepted = negativeaccepted;
            acceptedChars += "-";
        }
    }

    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) {
            return;
        }

        if (acceptedChars.equals(UPPERCASE)) {
            str = str.toUpperCase();
        } else if (acceptedChars.equals(LOWERCASE)) {
            str = str.toLowerCase();
        }

        for (int i = 0; i < str.length(); i++) {
            if (acceptedChars.indexOf(str.valueOf(str.charAt(i))) == -1) {
                return;
            }
        }

        if (acceptedChars.equals(FLOAT)
                || (acceptedChars.equals(FLOAT + "-") && negativeAccepted)) {
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

public class tswing extends JApplet {

    JTextField tf1, tf1b, tf1c, tf2, tf3;
    JLabel l1, l1b, l1c, l2, l3;

    public void init() {
        getContentPane().setLayout(new FlowLayout());
        //
        l1 = new JLabel("only numerics");
        tf1 = new JTextField(10);
        getContentPane().add(l1);
        getContentPane().add(tf1);
        tf1.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));

        //
        l1b = new JLabel("only float");
        tf1b = new JTextField(10);
        getContentPane().add(l1b);
        getContentPane().add(tf1b);
        tf1b.setDocument(new JTextFieldFilter(JTextFieldFilter.FLOAT));

        //
        l1c = new JLabel("only float(can be negative)");
        tf1c = new JTextField(10);
        getContentPane().add(l1c);
        getContentPane().add(tf1c);
        JTextFieldFilter jtff = new JTextFieldFilter(JTextFieldFilter.FLOAT);
        jtff.setNegativeAccepted(true);
        tf1c.setDocument(jtff);

        //
        l2 = new JLabel("only uppercase");
        tf2 = new JTextField(10);
        getContentPane().add(l2);
        getContentPane().add(tf2);
        tf2.setDocument(new JTextFieldFilter(JTextFieldFilter.UPPERCASE));

        //
        l3 = new JLabel("only 'abc123%$'");
        tf3 = new JTextField(10);
        getContentPane().add(l3);
        getContentPane().add(tf3);
        tf3.setDocument(new JTextFieldFilter("abc123%$"));
    }
}
