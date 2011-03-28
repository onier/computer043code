/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package traceinterceptor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:/traceinterceptor/context.xml");
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



