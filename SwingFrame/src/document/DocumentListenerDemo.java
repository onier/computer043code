/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocumentListenerDemo extends JFrame implements DocumentListener {

    private JTextField hourField = new JTextField("12", 3);
    private JTextField minuteField = new JTextField("00", 3);
    private JLabel label = new JLabel();

    public DocumentListenerDemo() {
        setTitle("TextTest");
        setSize(500, 200);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Container contentPane = getContentPane();

        JPanel p = new JPanel();

        p.add(hourField);
        hourField.getDocument().addDocumentListener(this);

        p.add(minuteField);
        minuteField.getDocument().addDocumentListener(this);

        contentPane.add(p, "Center");
        contentPane.add(label, "North");

    }

    public void insertUpdate(DocumentEvent e) {
        label.setText(e.toString());
    }

    public void removeUpdate(DocumentEvent e) {
        label.setText(e.toString());
    }

    public void changedUpdate(DocumentEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new DocumentListenerDemo();
        frame.show();
    }
}
