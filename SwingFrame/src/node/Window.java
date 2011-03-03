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

    @Override
    public String toString() {
        return name;
    }
}
