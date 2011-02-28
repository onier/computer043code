/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Administrator
 */
public class ClassListModel extends AbstractListModel {

    protected ArrayList<ListElement> listData = new ArrayList<ListElement>();

    public ClassListModel() {
       
    }

    private ArrayList<?> createList() {
        return new ArrayList<String>();
    }

    public void addListElement(ListElement element) {
        listData.add(element);
    }

    public ClassListModel(Class cl) {
        Field[] fields = cl.getFields();
        ArrayList<ListElement> temp = new ArrayList<ListElement>();
        for (int i = 0; i < fields.length; i++) {
            temp.add(new FieldElement(fields[i]));
        }
        Collections.sort(temp);
        listData.addAll(temp);
        temp.clear();
        Method[] methods = cl.getMethods();
        for (int i = 0; i < methods.length; i++) {
            temp.add(new MethodElement(methods[i]));
        }
        Collections.sort(temp);
        listData.addAll(temp);
    }

    public int getSize() {
        return listData.size();
    }

    public ListElement getElementAt(int index) {
        return listData.get(index);
    }

    public static interface ListElement<T extends ListElement> extends Comparable<T> {

        public String getName();

        public String getType();

        public String getDeclareClass();

        public String getInputValue();
    }

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

    static class FieldElement implements ListElement<FieldElement> {

        private Field field;

        public FieldElement(Field field) {
            this.field = field;
        }

        public String getType() {
            return getTypeName((field.getType()));
        }

        public String getName() {
            return field.getName();
        }

        public String getDeclareClass() {
            return getTypeName(field.getDeclaringClass());
        }

        public int compareTo(FieldElement o) {
            return this.getName().compareTo(o.getName());
        }

        public String getInputValue() {

            return field.getName();
        }
    }

    static class MethodElement implements ListElement<MethodElement> {

        private Method method;

        public MethodElement(Method method) {
            this.method = method;
        }

        public String getDeclareClass() {
            return getTypeName(method.getDeclaringClass());
        }

        public String getName() {
            StringBuilder sb = new StringBuilder();
            sb.append(method.getName()).append("(");
            Class[] params = method.getParameterTypes(); // avoid clone
            for (int j = 0; j < params.length; j++) {
                sb.append(FieldD.getTypeName(params[j]));
                sb.append("  para").append(j);
                if (j < params.length - 1) {
                    sb.append(",");
                }
            }
            sb.append(")");
            return sb.toString();
        }

        public String getType() {
            String name = getTypeName(method.getReturnType());
            return name;
        }

        public int compareTo(MethodElement o) {
            return getName().compareTo(o.getName());
        }

        public String getInputValue() {
            StringBuilder sb = new StringBuilder();
            sb.append(method.getName()).append("(");
            Class[] params = method.getParameterTypes(); // avoid clone
            for (int j = 0; j < params.length; j++) {
                sb.append("para").append(j);
                if (j < params.length - 1) {
                    sb.append(",");
                }

            }
            sb.append(")");
            return sb.toString();
        }
    }
}
