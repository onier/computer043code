/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout.action;

import javax.swing.event.EventListenerList;

/**
 *  默认的SelectChange支持对象.
 * @author Administrator
 */
public class SelectChangeSupport {

    protected EventListenerList listenerList = new EventListenerList();

    public void addSelectChangeListener(SelectChangeListener l) {
        listenerList.add(SelectChangeListener.class, l);
    }

    public void removeSelectChangeListener(SelectChangeListener l) {
        listenerList.remove(SelectChangeListener.class, l);
    }

    public SelectChangeListener[] getSelectChangeListeners() {
        return listenerList.getListeners(SelectChangeListener.class);
    }

    public void fireSelectChangePerformed(SelectChangeEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SelectChangeListener.class) {
                ((SelectChangeListener) listeners[i + 1]).selectChange(e);
            }
        }
    }
}
