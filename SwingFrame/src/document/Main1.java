/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class Main1 {

    public static void main(String[] argv) {
        JTextField component = new JTextField(10);


        component.getInputMap(JComponent.WHEN_FOCUSED).put(
                KeyStroke.getKeyStroke("typed $"), "actionName");

        JFrame f = new JFrame();
        f.add(component);

        f.setSize(300, 300);

        f.setVisible(true);

    }
}
