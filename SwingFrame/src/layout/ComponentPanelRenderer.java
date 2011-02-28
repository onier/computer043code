/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.Dimension;
import javax.swing.JComponent;

/**
 *定义下拉式控件的内容.
 * @author Administrator
 */
public interface ComponentPanelRenderer extends DownComponentRenderer {

    /**
     * 下拉式控件的内容
     * @return
     */
    public JComponent getPopupPanelRenderer();

    /**
     * 或者组件被包含的组件
     * @return
     */
    public DownComponentRenderer[] getDownComponents();

    /**
     * 用于记录动画呈现的大小
     * @return
     */
    public Dimension getAnimationSize();

    /**
     * 用于记录动画呈现的大小
     * @return
     */
    public void setAnimationSize(Dimension size);
}
