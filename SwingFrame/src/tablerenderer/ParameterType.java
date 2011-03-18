/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablerenderer;

/**
 *
 * @author admin
 */
public enum ParameterType {

    INT(1), FLOAT(2), DOUBLE(3), STRING(4), OBJECT(5), NULL(6);
    private int type;
    private String initValue;

    private ParameterType(int type) {
        this.type = type;
        switch (type) {
            case 1:
                initValue = "1";
                break;
            case 2:
                initValue = "1.0f";
                break;
            case 3:
                initValue = "1.0";
                break;
            case 4:
                initValue = "";
                break;
            case 5:
                initValue = "null";
                break;
            case 6:
                initValue = "No";
                break;
        }
    }

    public static ParameterType parseType(String str) {
        if (str.equals("int")) {
            return INT;
        }
        if (str.equals("float")) {
            return FLOAT;
        }
        if (str.equals("double")) {
            return DOUBLE;
        }
        if (str.equals("String")) {
            return STRING;
        }
        if (str.equals("Object")) {
            return OBJECT;
        } else {
            return INT;
        }
    }

    @Override
    public String toString() {
        switch (type) {
            case 1:
                return "int";
            case 2:
                return "float";
            case 3:
                return "double";
            case 4:
                return "String";
            case 5:
                return "Object";
            case 6:
                return "var";
        }
        return super.toString();
    }

    /**
     * @return the initValue
     */
    public Object getInitValue() {
        return initValue;
    }

    /**
     * @param initValue the initValue to set
     */
    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }
}
