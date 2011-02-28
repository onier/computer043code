/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package component;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author admin
 */
public class JTabbedPaneHeader extends JComponent {

    private boolean action = false;
    private DefaultButtonModel buttonModel = new DefaultButtonModel();
    private JButton button;
    private GradientPaint rolloverSelectPaint;
    private GradientPaint rolloverUnselectPaint;
    private GradientPaint selectPaint;
    private boolean rollover = false;
    private boolean armed = false;
    private Hanlder hanlder = new Hanlder();
    private String text;
    private JLabel label;
    private JTabbedPane tabbedPane;
    private int tabIndex = -1;

    public JTabbedPaneHeader(String text) {
        super();
        this.text = text;
        initComponent();
    }

    private void initComponent() {
        button = new ColorArrowButton(ColorArrowButton.SOUTH);
        button.setOpaque(false);
        label = new JLabel(text);
        label.setOpaque(false);
        setOpaque(false);
        button.setModel(buttonModel);
        this.setLayout(new BorderLayout());
        if (action) {
            this.add(button, BorderLayout.EAST);
        }
        this.add(label, BorderLayout.CENTER);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("dfsfsadf");
            }
        });
        this.addMouseListener(hanlder);
        this.addMouseMotionListener(hanlder);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 4, 1));
    }

    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(map);
        if (this.tabIndex == this.tabbedPane.getSelectedIndex()) {
            g2d.setPaint(this.getSelectPaint());
            g2d.fill(new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight() + 10, 10, 10));
            g2d.setColor(Color.GRAY);
            g2d.draw(new RoundRectangle2D.Float(0, 0, this.getWidth() - 1, this.getHeight() + 10, 10, 10));
            if (this.rollover) {
                g2d.setColor(Color.YELLOW);
                g2d.setStroke(new BasicStroke(3f));
                g2d.draw(new RoundRectangle2D.Float(0, 0, this.getWidth() - 1, this.getHeight() + 10, 10, 10));
            }
        } else if (this.rollover) {
            g2d.setPaint(this.getRolloverUnselect());
            g2d.fill(new RoundRectangle2D.Float(0, 0, this.getWidth(), this.getHeight() + 10, 10, 10));
            g2d.setColor(Color.GRAY);
            g2d.draw(new RoundRectangle2D.Float(0, 0, this.getWidth() - 1, this.getHeight() + 10, 10, 10));
            if (this.rollover) {
                g2d.setColor(Color.YELLOW);
                g2d.setStroke(new BasicStroke(3f));
                g2d.draw(new RoundRectangle2D.Float(0, 0, this.getWidth() - 1, this.getHeight() + 10, 10, 10));
            }
        }
        super.paint(g);
    }

    public void addActionListener(ActionListener l) {
        getButtonModel().addActionListener(l);
    }

    public ActionListener[] getActionListeners() {
        return getButtonModel().getActionListeners();
    }

    public void removeActionListener(ActionListener l) {
        getButtonModel().removeActionListener(l);
    }

    public JTabbedPaneHeader(String text, boolean flag) {
        super();
        this.text = text;
        action = flag;
        initComponent();
    }

    /**
     * @return the buttonModel
     */
    public DefaultButtonModel getButtonModel() {
        return buttonModel;
    }

    /**
     * @param buttonModel the buttonModel to set
     */
    public void setButtonModel(DefaultButtonModel buttonModel) {
        this.firePropertyChange("buttonModel", this.buttonModel, buttonModel);
        this.buttonModel = buttonModel;
        button.setModel(buttonModel);
    }

    /**
     * @return the rolloverSelect
     */
    private GradientPaint getRolloverSelect() {
        if (rolloverSelectPaint == null) {
            rolloverSelectPaint = new GradientPaint(0, 0, Color.YELLOW, 0, this.getHeight(), Color.YELLOW);
        }
        return rolloverSelectPaint;
    }

    /**
     * @return the rolloverUnselect
     */
    private GradientPaint getRolloverUnselect() {
        if (rolloverUnselectPaint == null) {
            rolloverUnselectPaint = new GradientPaint(0, 0, new Color(232, 239, 249), 0, this.getHeight(), Color.YELLOW);
        }
        return rolloverUnselectPaint;
    }

    /**
     * @return the selectPaint
     */
    private GradientPaint getSelectPaint() {
        if (selectPaint == null) {
            selectPaint = new GradientPaint(0, 0, new Color(236, 243, 253), 0, this.getHeight(), new Color(236, 243, 253));
        }
        return selectPaint;
    }

    /**
     * @return the tabIndex
     */
    public int getTabIndex() {
        return tabIndex;
    }

    /**
     * @param tabIndex the tabIndex to set
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    /**
     * @param tabbedPane the tabbedPane to set
     */
    public void setTabbedPane(final JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
        tabbedPane.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() == tabIndex) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
        });
    }

    protected class Hanlder extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            rollover = true;
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            rollover = false;
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            armed = true;
            tabbedPane.setSelectedIndex(tabIndex);
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            armed = false;
            repaint();
        }
    }
}
