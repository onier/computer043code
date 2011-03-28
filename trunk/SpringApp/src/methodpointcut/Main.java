/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package methodpointcut;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;

public class Main {

    public static void main(String[] args) {
        RegexpBean target = new RegexpBean();

        JdkRegexpMethodPointcut pc = new JdkRegexpMethodPointcut();
        pc.setPattern(".*foo.*");
        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleAdvice());

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);
        RegexpBean proxy = (RegexpBean) pf.getProxy();

        proxy.foo1();
        proxy.foo2();
        proxy.bar();
    }
}

class SimpleAdvice implements MethodInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("SimpleAdvice.Invoking:" + invocation.getMethod().getName());
        Object retVal = invocation.proceed();
        System.out.println("SimpleAdvice.Done");
        return retVal;
    }
}

class RegexpBean {

    public void foo1() {
        System.out.println("RegexpBean.foo1");
    }

    public void foo2() {
        System.out.println("RegexpBean.foo2");
    }

    public void bar() {
        System.out.println("RegexpBean.bar");
    }
}


