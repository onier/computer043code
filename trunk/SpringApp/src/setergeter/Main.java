/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setergeter;

import java.lang.reflect.Method;

import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;

public class Main {

    public static void main(String[] args) {
        SampleBean target = new SampleBean();
        ComposablePointcut pc = new ComposablePointcut(ClassFilter.TRUE, new GetterMethodMatcher());
        pc.union(new SetterMethodMatcher());
        SampleBean proxy = getProxy(pc, target);
        testInvoke(proxy);
    }

    private static SampleBean getProxy(ComposablePointcut pc, SampleBean target) {
        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleBeforeAdvice());
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);
        return (SampleBean) pf.getProxy();
    }

    private static void testInvoke(SampleBean proxy) {
        proxy.getAge();
        proxy.getName();
        proxy.setName("QQQ");
    }
}

class GetterMethodMatcher extends StaticMethodMatcher {

    public boolean matches(Method method, Class cls) {
        return (method.getName().startsWith("get"));
    }
}

class SetterMethodMatcher extends StaticMethodMatcher {

    public boolean matches(Method method, Class cls) {
        return (method.getName().startsWith("set"));
    }
}

class SampleBean {

    public String getName() {
        return "AAA";
    }

    public void setName(String name) {
    }

    public int getAge() {
        return 100;
    }
}

class SimpleBeforeAdvice implements MethodBeforeAdvice {

    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("Before method " + method);
    }
}

