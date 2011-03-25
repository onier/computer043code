package bean;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;


public class SimpleAfterAdvice implements AfterReturningAdvice{
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("After method: " + method);
    }
}
