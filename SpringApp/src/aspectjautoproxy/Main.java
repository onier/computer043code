/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aspectjautoproxy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:/aspectjautoproxy/context.xml");
        TestBean2 testBean = (TestBean2) ac.getBean("test");
        SimpleBean simpleBean = (SimpleBean) ac.getBean("simple");
        testBean.work();
        testBean.stop();
        simpleBean.sayHello();
        simpleBean.x("a", "b");
    }
}

class TestBean2 {

    private SimpleBean simpleBean;

    public void work() {
        this.simpleBean.sayHello();
        System.out.println("work");
    }

    public void stop() {
        this.simpleBean.sayHello();
        System.out.println("stop");
    }

    public void setSimpleBean(SimpleBean simpleBean) {
        this.simpleBean = simpleBean;
    }
}

class SimpleBean {

    public void sayHello() {
        System.out.println("Hello");
    }

    public void x(CharSequence a, String b) {
    }
}

