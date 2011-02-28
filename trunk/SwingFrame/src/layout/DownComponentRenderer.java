/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.awt.Color;
import layout.action.SelectChangeListener;

/**
 * 成为下拉式控件的通用属性.
 * @author Administrator
 */
public interface DownComponentRenderer {

    /**
     * 清除下拉控件的选择状态
     */
    public void clearSelection(DownComponentRenderer renderer);

    /**
     * 添加选择变化监听,以方便进行统一的选择状态管理
     */
    public void addSelectChangeListener(SelectChangeListener l);

    /**
     * 获得已经添加的监听对象.
     */
    public SelectChangeListener[] getSelectChangeListeners();

    /**
     * 删除已经存在的监听者
     * @param l
     */
    public void removeSelectChangeListener(SelectChangeListener l);

    /**
     * 设置选择的背景色
     * @param color
     */
    public void setSelectionBackground(Color color);
}
