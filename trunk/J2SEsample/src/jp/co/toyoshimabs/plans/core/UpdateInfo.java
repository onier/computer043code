/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.toyoshimabs.plans.core;

public class UpdateInfo {

    private String actionName;
    private boolean adjusting;

    public UpdateInfo(String actionName, boolean adjusting) {
        this.actionName = actionName;
        this.adjusting = adjusting;
    }

    public String getActionName() {
        return this.actionName;
    }

    public boolean isAdjusting() {
        return this.adjusting;
    }
}