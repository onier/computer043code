/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class MaxLengthDocument extends PlainDocument {

    private int max;

    public MaxLengthDocument(int maxLength) {
        max = maxLength;
    }

    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        if (getLength() + str.length() > max) {
            java.awt.Toolkit.getDefaultToolkit().beep();
        } else {
            super.insertString(offset, str, a);
        }
    }
}

class NewClass {

    public static void main(String[] a) {
        Document doc = new MaxLengthDocument(5); // set maximum length to 5
        JTextField field = new JTextField(doc, "", 8);

        JPanel flowPanel = new JPanel();
        flowPanel.add(field);
        JFrame frame = new JFrame("MaxLengthDocument demo");
        frame.setContentPane(flowPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(160, 80);
        frame.setVisible(true);
    }
}
