/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testframe;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Administrator
 */
public class WidgetAdapter implements WidgetAction {

    public State mouseClicked(Widget widget, WidgetMouseEvent wme) {
        return State.CONSUMED;
    }

    public State mousePressed(Widget widget, WidgetMouseEvent wme) {
        return State.CONSUMED;
    }

    public State mouseReleased(Widget widget, WidgetMouseEvent wme) {
        return State.CONSUMED;
    }

    public State mouseEntered(Widget widget, WidgetMouseEvent wme) {
        return State.CONSUMED;
    }

    public State mouseExited(Widget widget, WidgetMouseEvent wme) {
        return State.CONSUMED;
    }

    public State mouseDragged(Widget widget, WidgetMouseEvent wme) {
        return State.CONSUMED;
    }

    public State mouseMoved(Widget widget, WidgetMouseEvent wme) {
        return State.CONSUMED;
    }

    public State mouseWheelMoved(Widget widget, WidgetMouseWheelEvent wmwe) {
        return State.CONSUMED;
    }

    public State keyTyped(Widget widget, WidgetKeyEvent wke) {
        return State.CONSUMED;
    }

    public State keyPressed(Widget widget, WidgetKeyEvent wke) {
        return State.CONSUMED;
    }

    public State keyReleased(Widget widget, WidgetKeyEvent wke) {
        return State.CONSUMED;
    }

    public State focusGained(Widget widget, WidgetFocusEvent wfe) {
        return State.CONSUMED;
    }

    public State focusLost(Widget widget, WidgetFocusEvent wfe) {
        return State.CONSUMED;
    }

    public State dragEnter(Widget widget, WidgetDropTargetDragEvent wdtde) {
        return State.CONSUMED;
    }

    public State dragOver(Widget widget, WidgetDropTargetDragEvent wdtde) {
        return State.CONSUMED;
    }

    public State dropActionChanged(Widget widget, WidgetDropTargetDragEvent wdtde) {
        return State.CONSUMED;
    }

    public State dragExit(Widget widget, WidgetDropTargetEvent wdte) {
        return State.CONSUMED;
    }

    public State drop(Widget widget, WidgetDropTargetDropEvent wdtde) {
        return State.CONSUMED;
    }
}
