/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tracingaspect;

import java.lang.reflect.Method;

/**
 *
 * @author admin
 */
public class TracingBeforeAdvice {

    public void before(Method m, Object[] args, Object target) throws Throwable {
        System.out.println("Hello world! (by " + this.getClass().getName() + ")");
    }
}
