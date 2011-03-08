/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testframe;

import shape.BeanNodeElement;
import shape.NodeElement;

/**
 *
 * @author admin
 */
public class NodeConnection {

    protected NodeElement source = null;
    protected NodeElement target = null;
    protected BeanNodeGraphView scene;

    public NodeConnection(BeanNodeElement target, BeanNodeElement source, BeanNodeGraphView scene) {
        this.source = source;
        this.target = target;
        this.scene = scene;
    }

    /**
     * @return the source
     */
    public NodeElement getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(NodeElement source) {
        this.source = source;
    }

    /**
     * @return the target
     */
    public NodeElement getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(NodeElement target) {
        this.target = target;
    }

    /**
     * @return the scene
     */
    public BeanNodeGraphView getScene() {
        return scene;
    }

    /**
     * @param scene the scene to set
     */
    public void setScene(BeanNodeGraphView scene) {
        this.scene = scene;
    }
}
