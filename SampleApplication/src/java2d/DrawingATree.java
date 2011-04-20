/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2d;

import java.awt.*;
import java.awt.font.*;
import javax.swing.*;

public class DrawingATree extends JPanel {

    String[][] data = {
        {"Car"},
        {"Toyota", "Volkswagen"}
    };
    int[][] offsets = {{0}, {25, 25}};
    final int PAD = 20;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        float y = PAD;
        for (int j = 0; j < data.length; j++) {
            for (int k = 0; k < data[j].length; k++) {
                String s = data[j][k];
                LineMetrics lm = font.getLineMetrics(s, frc);
                float x = PAD + offsets[j][k];
                y += lm.getAscent();
                g2.drawString(s, x, y);
                y += lm.getDescent();
            }
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new DrawingATree());
        f.setSize(400, 400);
        f.setLocation(200, 200);
        f.setVisible(true);
    }
}
