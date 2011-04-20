/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animatino;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class UseCaseTracker {

    public static void main(String[] args) {
        List<Integer> useCases = new ArrayList<Integer>();
        Collections.addAll(useCases, 47, 48,49, 50);
        trackUseCases(useCases, PasswordUtils.class);
    }

    public static void trackUseCases(List<Integer> useCases, Class<?> cl) {
        for (Method m : cl.getDeclaredMethods()) {
            UseCase uc = m.getAnnotation(UseCase.class);
            if (uc != null) {
                System.out.println(uc.id() + " " + uc.description());
            }
        }
        for (int i : useCases) {
            System.out.println("Warnint : Missing use case " + i);
        }
    }
}
