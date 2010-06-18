package zhidao;

import java.awt.event.*;
import java.awt.*;
import javax.swing.JOptionPane;

class Duihuahuang extends Frame implements ActionListener {

    TextField shuru;
    TextArea xianshi;

    Duihuahuang(String s) {
        super(s);
        shuru = new TextField(22);
        xianshi = new TextArea(10, 10);
        add(shuru, BorderLayout.NORTH);
        shuru.addActionListener(this);
        add(xianshi, BorderLayout.CENTER);
        setBounds(60, 60, 300, 300);
        setVisible(true);
        validate();
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        String s = shuru.getText();
        int n = Integer.parseInt(s);
        if (n > 1000) {
            int h = JOptionPane.showConfirmDialog(this, "是否输去", "确认对话框", JOptionPane.YES_NO_OPTION);
            if (h == JOptionPane.YES_OPTION) {
                xianshi.append("\n" + s);
            } else if (h == JOptionPane.NO_OPTION) {
                xianshi.setText(null);
            }
        } else {
            xianshi.append("\n" + s);
        }
    }
}

public class Rundechen3 {

    public static void main(String args[]) {
        Duihuahuang win = new Duihuahuang("对话框");
    }
}
