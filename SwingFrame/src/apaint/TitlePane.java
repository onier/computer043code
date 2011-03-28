/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

/**
 *
 * @author admin
 */
public class TitlePane extends JPanel {

    private int index;
    public static final Icon icon1;
    public static final Icon icon2;
    public static final Icon icon3;
    public static final Icon icon4;

    static {
        icon1 = new ImageIcon(ColorCheckBoxUI.class.getResource("/apaint/1.png"));
        icon2 = new ImageIcon(ColorCheckBoxUI.class.getResource("/apaint/2.png"));
        icon3 = new ImageIcon(ColorCheckBoxUI.class.getResource("/apaint/3.png"));
        icon4 = new ImageIcon(ColorCheckBoxUI.class.getResource("/apaint/4.png"));
    }

    TitlePane() {
        setSize(400, 100);
        MouseHandler handler = new MouseHandler();
        addMouseListener(handler);
        addMouseMotionListener(handler);
        setOpaque(true);
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
            index = 4;
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
        paintIcon(g2d);
    }

    private void paintIcon(Graphics2D g2d) {
        icon1.paintIcon(this, g2d, 1, 0);
        icon2.paintIcon(this, g2d, 30, 0);
        icon3.paintIcon(this, g2d, 60, 0);
        icon4.paintIcon(this, g2d, 100, 0);
    }

    private void paintShaowBorder(Graphics2D g2d) {
        Shape clip = null;
        Color color = new Color(177, 202, 235);
        switch (index) {
            case 1:
                clip = new Rectangle(0, 0, 30, 20);
                break;
            case 2:
                clip = new Rectangle(30, 0, 30, 20);
                break;
            case 3:
                clip = new Rectangle(60, 0, 30, 20);
                break;
            case 4:
                clip = new Rectangle(90, 0, 41, 20);
                color = Color.RED.brighter();
                break;
        }
        if (clip != null) {
            Shape c = g2d.getClip();
            g2d.setClip(clip);
            g2d.setColor(color);
            g2d.fill(new RoundRectangle2D.Float(1, -20, 130, 40, 10, 10));
            g2d.setClip(c);
        }
    }

    protected JPopupMenu createPoupMenu() {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem("file");
        menu.add(item);
        item.setPreferredSize(new Dimension(100, 20));
        item = new JMenuItem("eidt");
        item.setPreferredSize(new Dimension(100, 20));
        menu.add(item);
        item = new JMenuItem("about");
        item.setPreferredSize(new Dimension(100, 20));
        menu.add(item);
        item.setPreferredSize(new Dimension(100, 20));
        return menu;
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            setSelect(e.getPoint());
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            index = -1;
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            setSelect(e.getPoint());
            Window window = SwingUtilities.getWindowAncestor(TitlePane.this);
            if (index == 1) {
                createPoupMenu().show(TitlePane.this, 0, TitlePane.this.getHeight());
            }
            if (index == 2) {
                ((Frame) window).setExtendedState(Frame.ICONIFIED);
            }
            if (index == 3) {
                if (window instanceof Frame) {
                    if (((Frame) window).getExtendedState() == Frame.NORMAL) {
                        ((Frame) window).setExtendedState(Frame.MAXIMIZED_BOTH);
                    } else {
                        ((Frame) window).setExtendedState(Frame.NORMAL);
                    }
                }
            }
            if (index == 4) {
                System.exit(1);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            setSelect(e.getPoint());
            repaint();
        }
    }
}
