/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package methodbeforeadvice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:/methodbeforeadvice/newSpringXMLConfig.xml");
        MtBean testBean = (MtBean) beanFactory.getBean("afterBean");
        testBean.showValues();
    }
}

class MtBean {

    private String firstName;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void showValues() {
        System.out.println("First name: " + this.firstName);
    }
}

class NullArgumentsNotAllowedBeforeAdvice implements MethodBeforeAdvice {

    public void before(Method method, Object[] arguments, Object target) throws Throwable {
        if (arguments == null || arguments.length == 0) {
            return;
        }
        for (int i = 0; i < arguments.length; i++) {
            Object argument = arguments[i];
            if (argument == null) {
                throw new IllegalArgumentException(
                        "Value for argument [" + i + "] is required but not present "
                        + "for method [" + method + "]!");
            }
        }
    }
}

