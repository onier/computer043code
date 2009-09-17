/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


//给JPanel加入图片
public class BackgroundImage extends JFrame {

    public BackgroundImage() {
//        Animater animator = new Animater();
//        animator.setOpaque(true);
//        animator.setBackground(Color.white);
//        setContentPane(animator);
        JButton button1 = new JButton("确定");
        JButton button2 = new JButton("取消");
        JLabel lab = new JLabel(new ImageIcon("C:\\Documents and Settings\\All Users\\Documents\\My Pictures\\示例图片\\Blue hills.jpg"));
        JLabel lab1 = new JLabel("增添歌曲   请选择");
        JLabel label1 = new JLabel("歌曲名:");
        JLabel label2 = new JLabel("歌手:");
        JLabel label3 = new JLabel("类型:");
        JTextField text1 = new JTextField();
        JTextField text2 = new JTextField();
        JTextField text3 = new JTextField();
        label1.setBounds(30, 5, 120, 120);
        label2.setBounds(30, 80, 120, 120);
        label3.setBounds(30, 150, 120, 120);
        text1.setBounds(220, 50, 320, 30);
        text2.setBounds(220, 120, 320, 30);
        text3.setBounds(220, 190, 320, 30);
        button1.setBounds(320, 300, 60, 50);
        button2.setBounds(400, 300, 60, 50);
        lab.add(button1);
        lab.add(button2);
        lab.add(label1);
        lab.add(text1);
        lab.add(label2);
        lab.add(text2);
        lab.add(label3);
        lab.add(text3);
//        animator.add(lab);
//        animator.add(lab1, BorderLayout.NORTH);
        setContentPane(lab);
        this.setSize(700, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) {
        new BackgroundImage();
    }
}

class Animater extends JPanel {

    Animater() {
        super(new BorderLayout());
        JLabel lable = new JLabel(new ImageIcon("D:\\Check\\PLANS-Check\\resources\\cursors\\penToolCursors.png"));
        this.add(lable);
    }
}

