/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ScrollPane;

//--------------------------------------------------------------------
import java.awt.*;
import javax.swing.*;

public class FixedScrollPane extends JScrollPane {

    public FixedScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
    }

    public FixedScrollPane(Component view) {
        super(view, VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public FixedScrollPane(int vsbPolicy, int hsbPolicy) {
        super(null, vsbPolicy, hsbPolicy);
    }

    public FixedScrollPane() {
        super(null, VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public JScrollBar createVerticalScrollBar() {
        JScrollBar sb = super.createVerticalScrollBar();
        sb.setModel(new FixedBoundedRangeModel(0, 10, 0, 100));
        return sb;
    }

    public JScrollBar createHorizontalScrollBar() {
        JScrollBar sb = super.createHorizontalScrollBar();
        sb.setModel(new FixedBoundedRangeModel(0, 10, 0, 100));
        return sb;
    }
}

class FixedBoundedRangeModel extends DefaultBoundedRangeModel {

    public FixedBoundedRangeModel() {
    }

    public FixedBoundedRangeModel(int value, int extent, int min, int max) {
        super(value, extent, min, max);
    }

    protected void fireStateChanged() {
        if (!getValueIsAdjusting()) {
            super.fireStateChanged();
        }
    }
}
