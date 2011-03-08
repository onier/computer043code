/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author admin
 */
public class TitlePane extends JPanel {

    private int index;

    TitlePane() {
        this.setSize(400, 100);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        frame.setContentPane(new TitlePane());
        frame.setVisible(true);
    }

    private void setSelect(Point point) {
        Rectangle rect = new Rectangle(0, 0, 30, 20);
        if (rect.contains(point)) {
            index = 1;
            return;
        }
        rect = new Rectangle(30, 0, 30, 20);
        if (rect.contains(point)) {
            index = 2;
            return;
        }
        rect = new Rectangle(60, 0, 30, 20);
        if (rect.contains(point)) {
            index = 3;
            return;
        }
        rect = new Rectangle(90, 0, 40, 20);
        if (rect.contains(point)) {
            index = 3;
            return;
        }
        index = -1;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(map);
        g2d.setColor(new Color(159, 193, 217));
        int h = 20;
        int w = 130;
        g2d.draw(new RoundRectangle2D.Float(1, -20, w, h + 20, 10, 10));
        g2d.draw(new Line2D.Float(30, 0, 30, h));
        g2d.draw(new Line2D.Float(60, 0, 60, h));
        g2d.draw(new Line2D.Float(90, 0, 90, h));
        paintShaowBorder(g2d);
    }

    private void paintShaowBorder(Graphics2D g2d) {

    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            setSelect(e.getPoint());
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            setSelect(e.getPoint());
        }
    }
}
