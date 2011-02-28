/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class Parent {

    public int one;
    protected int two;
    private int three;
    public Rectangle[] rect;
    public int[] a;
}

public class FieldD extends Parent {

    public int four;
    protected int five;
    private int six;

    static String getTypeName(Class type) {
        if (type.isArray()) {
            try {
                Class cl = type;
                int dimensions = 0;
                while (cl.isArray()) {
                    dimensions++;
                    cl = cl.getComponentType();
                }
                StringBuffer sb = new StringBuffer();
                sb.append(cl.getName());
                for (int i = 0; i < dimensions; i++) {
                    sb.append("[]");
                }
                return sb.toString();
            } catch (Throwable e) { /*FALLTHRU*/ }
        }
        return type.getName();
    }

    public static void main(String[] args) {
        String value = "123" + "\n" + "123";
        System.out.println(value);
        Class c = FieldD.class;
        System.out.println("The class " + c.getName() + " has the following fields: ");
        System.out.println("");

        Field[] fields = c.getFields();
        String name, typeName, className, fieldName;
        for (int i = 0; i < fields.length; i++) {
            int mod = fields[i].getModifiers();
            if (mod == 0) {
                name = "";
            } else {
                name = Modifier.toString(mod);
            }
            typeName = getTypeName((fields[i].getType()));
            className = getTypeName(fields[i].getDeclaringClass());
            fieldName = fields[i].getName();
            Rectangle rect = new Rectangle();
            System.out.println(name + "  " + typeName + "   " + className + "   " + fieldName);
        }
    }
}
