/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablerenderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *该类创建一个自定义组件加上有一个按钮的cellrenderer.
 * @author Administrator
 */
public abstract class AbstractCustomerRenderer implements TableCellRenderer {

    protected JPanel rendererPanel = new JPanel();
    protected JButton button = new JButton("...");
    protected JComponent renderer;
    protected JTable table;
    protected Dimension cellSize = new Dimension();

    protected AbstractCustomerRenderer() {
        rendererPanel.setLayout(new BorderLayout());
        renderer = getRendererComponent();
        rendererPanel.add(renderer, BorderLayout.CENTER);
        button.setPreferredSize(new Dimension(AbstractCustomerEditor.WIDTH, button.getPreferredSize().height));
        rendererPanel.add(button, BorderLayout.EAST);
    }

    /**
     * 获得cellrenderer显示的控件
     * @return
     */
    public abstract JComponent getRendererComponent();

    /**
     * 设置用户自定义显示模式
     * @param renderer   该对象为用户自定义的render对象
     * @param value
     */
    public abstract void setText(JComponent renderer, Object value);

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.table = table;
        setText(renderer, value);
        cellSize.height = table.getRowHeight();
        cellSize.width = table.getColumnModel().getColumn(column).getWidth();
        if (isSelected) {
            renderer.setBackground(table.getSelectionBackground());
            renderer.setForeground(table.getSelectionForeground());
            rendererPanel.setBackground(table.getSelectionBackground());
            rendererPanel.setForeground(table.getSelectionForeground());
        } else {
            renderer.setBackground(table.getBackground());
            renderer.setForeground(table.getForeground());
            rendererPanel.setBackground(table.getBackground());
            rendererPanel.setForeground(table.getForeground());
        }
        return rendererPanel;
    }

    public static class FontCustomerRenderer extends AbstractCustomerRenderer {

        @Override
        public JComponent getRendererComponent() {
            return new JLabel();
        }

        @Override
        public void setText(JComponent renderer, Object value) {
            JLabel text = (JLabel) renderer;
            if (value != null) {
                text.setText(((Font) (value)).getFontName());
            }
        }
    }

    public static class TextCustomerRenderer extends AbstractCustomerRenderer {

        @Override
        public JComponent getRendererComponent() {
            return new JLabel();
        }

        @Override
        public void setText(JComponent renderer, Object value) {
            JLabel text = (JLabel) renderer;
            if (value != null) {
                text.setText(value.toString());
            }
        }
    }

    public static class ArrayCustomerRenderer extends AbstractCustomerRenderer {

        @Override
        public JComponent getRendererComponent() {
            return new JLabel();
        }

        @Override
        public void setText(JComponent renderer, Object value) {
            if (value instanceof ArrayList) {
                ArrayList list = (ArrayList) value;
                ((JLabel) renderer).setText(TableCellEditorFactory.toShowString(list, ","));
            }
        }
    }

    public static class ColorCustomerRenderer extends AbstractCustomerRenderer {

        @Override
        public JComponent getRendererComponent() {
            return new JLabel();
        }

        @Override
        public void setText(JComponent renderer, Object value) {
            final Color color;
            if (value != null && value instanceof Color) {
                color = (Color) (value);
            } else {
                color = Color.RED;
            }
            JLabel label = (JLabel) renderer;
            final int size = table.getRowHeight() - 2;
            label.setIcon(new Icon() {

                public void paintIcon(Component c, Graphics g, int x, int y) {
                    g.setColor(color);
                    g.fillRect(1, 1, size, size);
                }

                public int getIconWidth() {
                    return size;
                }

                public int getIconHeight() {
                    return size;
                }
            });
            label.setText("R:" + color.getRed() + " G:" + color.getGreen() + " B:" + color.getBlue());
        }
    }

    public static class ProcessCustomerRenderer extends AbstractCustomerRenderer {

        @Override
        public JComponent getRendererComponent() {
            return new JLabel();
        }

        @Override
        public void setText(JComponent renderer, Object value) {
            final Color color;
            if (value != null && value instanceof Color) {
                color = (Color) (value);
            } else {
                color = Color.RED;
            }
            JLabel label = (JLabel) renderer;
            label.setIcon(new Icon() {

                public void paintIcon(Component c, Graphics g, int xx, int yy) {
                    Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE};
                    int[] values = new int[]{color.getRed(), color.getGreen(), color.getBlue()};
                    int n = color.getRed() + color.getGreen() + color.getBlue();
                    Graphics2D g2D = (Graphics2D) g;
                    for (int i = 0; i < 3; i++) {
                        Rectangle rect = new Rectangle(0, cellSize.height / 3 * i, (int) ((values[i] * 1.0 / n) * cellSize.width), (int) cellSize.height / 3);
                        g2D.setPaint(new GradientPaint(0, 0, colors[i], 0, (float) rect.width, colors[(i + 1) % 3]));
                        g2D.fill(rect);
//                        g2D.fillRoundRect(-2, cellSize.height / 3 * i, (int) ((values[i] * 1.0 / n) * cellSize.width), ((int) cellSize.height / 3), 2, 2);
                    }
                }

                public int getIconWidth() {
                    return cellSize.width;
                }

                public int getIconHeight() {
                    return cellSize.height;
                }
            });
            label.setText("R:" + color.getRed() + " G:" + color.getGreen() + " B:" + color.getBlue());
        }
    }
}
