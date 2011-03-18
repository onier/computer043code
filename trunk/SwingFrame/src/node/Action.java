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

    public static Action parseAction(String str) {
        if (str.equals("getProperty")) {
            return GETPROPERTY;
        } else if (str.equals("click")) {
            return CLICK;
        } else if (str.equals("setValue")) {
            return SETVALUE;
        } else if (str.equals("setProperty")) {
            return SETPROPERTY;
        } else {
            return SELECT;
        }
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
