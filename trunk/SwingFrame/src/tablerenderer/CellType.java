/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablerenderer;

import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public enum CellType {

    /**
     * 缺省
     */
    DEFAULT(1),
    /**
     *颜色
     */
    COLOR(2),
    /**
     * list
     */
    LIST(3),
    /**
     * CheckBox
     */
    CHECKBOX(4),/**
     * button
     */
    BUTTON(5);
    private int type;

    private CellType(int i) {
        type = i;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(ParameterType.class.getEnumConstants()));
    }

    public String getText() {
        return this.valueOf(null).toString();
    }
}
