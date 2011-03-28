package bean;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class Main {

    public static void main(String[] args) {
        MyClass target = new MyClass();

        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression("execution(* bean..*.get*(..))");

        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleAfterAdvice());

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);

        MyClass proxy = (MyClass) pf.getProxy();
        System.out.println(proxy.getName());
        proxy.setName("New Name");
        System.out.println(proxy.getHeight());

    }
}
