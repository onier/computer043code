/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.util.ArrayList;
import javax.swing.JPanel;
import layout.action.SelectChangeEvent;
import layout.action.SelectChangeListener;

/**
 *
 * @author Administrator
 */
public class JDownComponentPanelContainer extends JPanel implements SelectChangeListener {

    private ArrayList<DownComponentPanel> popupPanelList = new ArrayList<DownComponentPanel>();

    public JDownComponentPanelContainer() {
        this.setLayout(new DownComponentLayout());
    }

    public void addPopupPanel(DownComponentPanel panel) {
        this.add(panel);
        popupPanelList.add(panel);
        panel.addSelectChangeListener(this);
    }

    public void removeAllComponent() {
        for (int i = 0; i < popupPanelList.size(); i++) {
            this.remove(popupPanelList.get(i));
        }
        popupPanelList.clear();
    }

    public DownComponentPanel getPopupPanel(int index) {
        return this.popupPanelList.get(index);
    }

    public void selectChange(SelectChangeEvent event) {
        DownComponentRenderer renderer = event.getRenderer();
        for (int i = 0; i < popupPanelList.size(); i++) {
            popupPanelList.get(i).clearSelection(renderer);
        }
    }
}
