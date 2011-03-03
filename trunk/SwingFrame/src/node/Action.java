/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

/**
 *
 * @author admin
 */
public enum Action {

    GETPROPERTY("getProperty"), CLICK("click"), SETVALUE("setValue"), SETPROPERTY("setProperty"), SELECT("select");
    private String name;

    private Action(String str) {
        this.name = str;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
