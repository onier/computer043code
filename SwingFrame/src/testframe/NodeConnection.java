/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testframe;

import shape.BeanNodeElement;

/**
 *
 * @author admin
 */
public class NodeConnection {

    protected BeanNodeElement source = null;
    protected BeanNodeElement target = null;
    protected BeanNodeGraphView scene;

    public NodeConnection(BeanNodeElement source, BeanNodeElement target, BeanNodeGraphView scene) {
        this.source = source;
        this.target = target;
        this.scene = scene;
    }

    /**
     * @return the source
     */
    public BeanNodeElement getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(BeanNodeElement source) {
        this.source = source;
    }

    /**
     * @return the target
     */
    public BeanNodeElement getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(BeanNodeElement target) {
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
