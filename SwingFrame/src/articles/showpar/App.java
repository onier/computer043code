package articles.showpar;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class App extends JEditorPane {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Show paragraphs example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final App app = new App();
        JScrollPane scroll = new JScrollPane(app);
        frame.getContentPane().add(scroll);
        final JCheckBox chbShow=new JCheckBox("Show paragraphs", true);
        chbShow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (chbShow.isSelected()) {
                    Document doc=app.getDocument();
                    doc.putProperty("show paragraphs","");
                }
                else {
                    Document doc=app.getDocument();
                    doc.putProperty("show paragraphs", null);
                }
                app.repaint();
            }
        });
        frame.getContentPane().add(chbShow, BorderLayout.NORTH);

        frame.setSize(750, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public App() {
        super();
        final SimpleAttributeSet attrs=new SimpleAttributeSet();

        setEditorKit(new ShowParEditorKit());
        Document doc=App.this.getDocument();
        doc.putProperty("show paragraphs","");
        try {
            doc.insertString(doc.getLength(), "Space characters text in paragraph.\nNew paragrah with tab characters\t\t\t", attrs);
        }
        catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
}
