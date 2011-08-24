/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;

public class ElementSample {

    public static void main(String args[]) {
        JFrame frame = new JFrame("Element Example");
        Container content = frame.getContentPane();

        final JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton button = new JButton("Show Elements");
        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                Document document = textArea.getDocument();
                ElementIterator iterator = new ElementIterator(document);
                Element element = iterator.first();
                while (element != null) {
                    System.out.println(element.getStartOffset());
                    element = iterator.next();
                }
            }
        };
        button.addActionListener(actionListener);

        content.add(scrollPane, BorderLayout.CENTER);
        content.add(button, BorderLayout.SOUTH);

        frame.setSize(250, 250);
        frame.setVisible(true);
    }
}
