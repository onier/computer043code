/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ComboBox;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

public class XMLEditor {

    /** Creates a new instance of XMLEditor */
    public XMLEditor() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new JEditorPane("text/xml", "text"));
        frame.setVisible(true);
    }
}
