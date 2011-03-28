/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package component;

import java.awt.BorderLayout;
import javax.swing.ButtonModel;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 *
 * @author admin
 */
public class JSearchTextField extends JTextField {

    private ButtonModel model = new DefaultButtonModel();

    public JSearchTextField() {
        super();
        init();
    }

    public JSearchTextField(String text) {
        super(text);
        init();
    }

    public JSearchTextField(int column) {
        super(column);
        init();
    }

    public JSearchTextField(Document document, String text, int column) {
        super(document, text, column);
        init();
    }

    public void init() {
        this.setLayout(new BorderLayout());
        JButton b = new JButton("F");
        b.setModel(model);
        this.add(b, BorderLayout.EAST);
    }
}
