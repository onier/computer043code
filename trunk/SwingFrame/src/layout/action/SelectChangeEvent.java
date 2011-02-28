/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layout.action;

import java.util.EventObject;
import layout.DownComponentRenderer;

/**
 *
 * @author Administrator
 */
public class SelectChangeEvent extends EventObject {

    private DownComponentRenderer renderer;

    public SelectChangeEvent(DownComponentRenderer renderer) {
        super(renderer);
        this.renderer = renderer;
    }

    /**
     * @return the renderer
     */
    public DownComponentRenderer getRenderer() {
        return renderer;
    }
}
