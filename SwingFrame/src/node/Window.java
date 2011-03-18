/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

/**
 *
 * @author admin
 */
public enum Window {

    IEFRAME("ieFrame"), JAVA("java"), WINDOW("window");
    private String name;

    private Window(String str) {
        this.name = str;
    }

    public static Window parseWindow(String str) {
        if (str.equals("ieFrame")) {
            return IEFRAME;
        } else if (str.equals("java")) {
            return JAVA;
        } else {
            return WINDOW;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
