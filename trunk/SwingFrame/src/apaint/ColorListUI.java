/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicListUI;

/**
 *
 * @author Zhenhai.Xu
 */
public class ColorListUI extends BasicListUI {

    private int index = -1;
    private Handler handler = new Handler();
    private Renderer renderer = new Renderer();

    public static ComponentUI createUI(JComponent list) {
        return new ColorListUI();
    }

    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        list.removeMouseMotionListener(handler);
        list.removeMouseListener(handler);
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
        list.setCellRenderer(renderer);
        list.addMouseListener(handler);
        list.addMouseMotionListener(handler);
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
    }

    @Override
    protected void paintCell(Graphics g, int row, Rectangle rowBounds, ListCellRenderer cellRenderer, ListModel dataModel, ListSelectionModel selModel, int leadIndex) {
        Graphics2D g2d = (Graphics2D) g;
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(map);
        Paint paint;
        int w = ((JComponent) cellRenderer).getWidth();
        int h = ((JComponent) cellRenderer).getHeight();
        Rectangle rect = list.getCellBounds(row, row);
        if (row == ColorListUI.this.index) {
            paint = new LinearGradientPaint(new Point2D.Double(rect.x, rect.y), new Point2D.Double(rect.x, rect.getMaxY()), new float[]{0f, 0.4f, 0.5f, 1f}, new Color[]{UIManager.getColor(ColorLookAndFeelProperties.RENDERER_COLOR1_KEY), UIManager.getColor(ColorLookAndFeelProperties.RENDERER_COLOR2_KEY), UIManager.getColor(ColorLookAndFeelProperties.RENDERER_COLOR3_KEY), UIManager.getColor(ColorLookAndFeelProperties.RENDERER_COLOR4_KEY)});
        } else {
            paint = Color.WHITE;
        }
        Paint oldPaint = g2d.getPaint();
        Composite composite = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2d.setPaint(paint);
        g2d.fill(rect);
        g2d.setComposite(composite);
        g2d.setPaint(oldPaint);
        super.paintCell(g, row, rowBounds, cellRenderer, dataModel, selModel, leadIndex);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
    }

    static class Renderer extends DefaultListCellRenderer {

        public Renderer() {
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(100, 20));
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setComponentOrientation(list.getComponentOrientation());
            if (value instanceof Icon) {
                setIcon((Icon) value);
                setText("");
            } else {
                setIcon(null);
                setText((value == null) ? "" : value.toString());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            return this;
        }
    }

    protected class Handler extends MouseAdapter {

        @Override
        public void mouseExited(MouseEvent e) {
            index = -1;
            list.revalidate();
            list.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            index = list.locationToIndex(e.getPoint());
            list.revalidate();
            list.repaint();
        }
    }
}
