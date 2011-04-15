/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testframe;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.jdesktop.application.AbstractBean;
import org.jdesktop.application.Action;
import org.jdesktop.application.Action.Parameter;
import org.jdesktop.application.Application;
import org.openide.util.Exceptions;

/**
 *
 * @author admin
 */
public class NewClass extends AbstractBean {

    @Action(name = "doA")
    public void doA(@Parameter(value = "valuel1") Application value1) {
        System.out.println("sssssssssss");
        System.out.println(value1.getClass());
    }

    public static void main(String[] args) {
        NewClass n = new NewClass();
        try {
            Method m = n.getClass().getMethod("doA", Action.class);
            Annotation[][] arrayOfAnnotation = m.getParameterAnnotations();
            Class[] arrayOfClass = m.getParameterTypes();
            Object[] arrayOfObject = new Object[arrayOfClass.length];
            Object localObject3;
            for (int i = 0; i < arrayOfClass.length; i++) {
                localObject3 = null;
                for (Annotation localAnnotation : arrayOfAnnotation[i]) {
                    if ((localAnnotation instanceof Action.Parameter)) {
                        localObject3 = ((Action.Parameter) localAnnotation).value();
                        break;
                    }
                }
                System.out.println(arrayOfClass[i]);
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
