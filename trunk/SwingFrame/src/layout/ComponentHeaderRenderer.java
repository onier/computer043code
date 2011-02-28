/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.event.ActionListener;
import javax.swing.JComponent;

/**
 * 下拉式的头
 * @author Administrator
 */
public interface ComponentHeaderRenderer extends DownComponentRenderer{

    /**
     * 返回下来的头部控件
     * @return
     */
    public JComponent getPopupHeaderRenderer();

    /**
     * 添加头部控件的动作
     */
    public void addActionListener(ActionListener action);

    /**
     * 获得所有监听
     * @return
     */
    public ActionListener[] getActionListeners();

    /**
     * 删除监听
     * @param listener
     */
    public void removeActionListener(ActionListener listener);
}
