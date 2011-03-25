/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tracingaspect;

import org.springframework.aop.AfterReturningAdvice;

public class TracingAfterAdvice implements AfterReturningAdvice {

    public void afterReturning(Object o, java.lang.reflect.Method method, Object[] os, Object o1) throws Throwable {
        System.out.println("Hello world! (by " + this.getClass().getName() + ")");
    }
}
