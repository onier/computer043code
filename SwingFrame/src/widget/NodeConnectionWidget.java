/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import testframe.NodeConnection;

/**
 *
 * @author admin
 */
public class NodeConnectionWidget extends ConnectionWidget {

    protected NodeConnection conneciont;

    public NodeConnectionWidget(NodeConnection conneciont, Scene scene) {
        super(scene);
        this.conneciont = conneciont;
    }

    /**
     * @return the conneciont
     */
    public NodeConnection getConneciont() {
        return conneciont;
    }

    /**
     * @param conneciont the conneciont to set
     */
    public void setConneciont(NodeConnection conneciont) {
        this.conneciont = conneciont;
    }
}
