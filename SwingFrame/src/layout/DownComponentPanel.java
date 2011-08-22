/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;
import layout.action.SelectChangeEvent;
import layout.action.SelectChangeListener;
import layout.action.SelectChangeSupport;

/**
 *创建popuppanel对象,该类会自动添加一个action完成基本的展开收起的功能.
 * @author Administrator
 */
public class DownComponentPanel extends JPanel implements ActionListener, ComponentPanelRenderer, SelectChangeListener {

    public static final Color color = Color.GRAY;
    protected SelectChangeSupport support = new SelectChangeSupport();
    protected ComponentHeaderRenderer headerRenderer;
    protected ComponentPanelRenderer panelRenderer;
    private AnimationListener animation;
    private Timer animationTimer;
    private Dimension animationSize = new Dimension();

    public DownComponentPanel(ComponentHeaderRenderer headerRenderer, ComponentPanelRenderer panelRenderer) {
        this.headerRenderer = headerRenderer;
        headerRenderer.setSelectionBackground(color);
        this.panelRenderer = panelRenderer;
        headerRenderer.addSelectChangeListener(this);
        panelRenderer.addSelectChangeListener(this);
        panelRenderer.setSelectionBackground(color);
        this.setLayout(new DownComponentLayout());
        this.add(headerRenderer.getPopupHeaderRenderer());
        this.add(panelRenderer.getPopupPanelRenderer());
        this.injectActionListener();
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        animation = new AnimationListener();
        animationTimer = new Timer(10, animation);
        animationTimer.setRepeats(true);
        animationTimer.stop();
    }

    public void injectActionListener() {
        getHeaderRenderer().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (panelRenderer instanceof ShapeComponentListPanelRenderer) {
                panelRenderer.getPopupPanelRenderer().setVisible(!panelRenderer.getPopupPanelRenderer().isVisible());
                } else {
                    setExpand(panelRenderer.getPopupPanelRenderer().isVisible());
                }
            }
        });
    }

    public void setExpand(boolean expand) {
        JComponent com = panelRenderer.getPopupPanelRenderer();
        if (com.isVisible()) {
            Rectangle rect = com.getBounds();
            panelRenderer.setAnimationSize(new Dimension(rect.width, rect.height));
        }
        animation.setExpand(expand);
        animationTimer.start();
    }

    public void actionPerformed(ActionEvent e) {
        getPanelRenderer().getPopupPanelRenderer().setVisible(!panelRenderer.getPopupPanelRenderer().isVisible());
        this.revalidate();
    }

    /**
     * @return the headerRenderer
     */
    public ComponentHeaderRenderer getHeaderRenderer() {
        return headerRenderer;
    }

    /**
     * @return the panelRenderer
     */
    public ComponentPanelRenderer getPanelRenderer() {
        return panelRenderer;
    }

    public JComponent getPopupPanelRenderer() {
        return this;
    }

    public void addSelectChangeListener(SelectChangeListener l) {
        support.addSelectChangeListener(l);
    }

    public SelectChangeListener[] getSelectChangeListeners() {
        return support.getSelectChangeListeners();
    }

    public void removeSelectChangeListener(SelectChangeListener l) {
        support.removeSelectChangeListener(l);
    }

    public void setSelectionBackground(Color color) {
        headerRenderer.setSelectionBackground(color);
        panelRenderer.setSelectionBackground(color);
    }

    public DownComponentRenderer[] getDownComponents() {
        return new DownComponentRenderer[]{headerRenderer, panelRenderer};
    }

    public void selectChange(SelectChangeEvent event) {
        support.fireSelectChangePerformed(event);
    }

    public void clearSelection(DownComponentRenderer renderer) {
        headerRenderer.clearSelection(renderer);
        panelRenderer.clearSelection(renderer);
    }

    protected class AnimationListener implements ActionListener {

        private boolean expand = true;

        private void setAnimationBound(Rectangle rect) {
            JComponent com = panelRenderer.getPopupPanelRenderer();
            com.setBounds(rect.x, rect.y, rect.width, rect.height);
            com.setPreferredSize(new Dimension(rect.width, rect.height));
            DownComponentPanel.this.revalidate();
            DownComponentPanel.this.updateUI();
            DownComponentPanel.this.repaint();
        }

        public void actionPerformed(ActionEvent e) {
            JComponent com = panelRenderer.getPopupPanelRenderer();
            Rectangle rect = com.getBounds();
            if (expand) {
                if (rect.height <= 0) {
                    animationTimer.stop();
                    DownComponentPanel.this.revalidate();
                    DownComponentPanel.this.repaint();
                    com.setVisible(false);
                }
                rect.height = rect.height - 20;
                rect.height = Math.max(0, rect.height);
            } else {
                if (rect.height >= panelRenderer.getAnimationSize().height) {
                    animationTimer.stop();
                    setAnimationBound(new Rectangle(rect.x, rect.y, panelRenderer.getAnimationSize().width, panelRenderer.getAnimationSize().height));
                    DownComponentPanel.this.revalidate();
                    DownComponentPanel.this.repaint();
                }
                if (!com.isVisible()) {
                    com.setVisible(true);
                    rect = com.getBounds();
                }
                rect.height = rect.height + 20;
                rect.height = Math.min(rect.height, panelRenderer.getAnimationSize().height);
            }

            setAnimationBound(rect);
        }

        /**
         * @param expand the expand to set
         */
        public void setExpand(boolean expand) {
            this.expand = expand;
        }
    }

    public Dimension getAnimationSize() {
        return this.animationSize;
    }

    public void setAnimationSize(Dimension size) {
        this.animationSize = size;
    }
}
