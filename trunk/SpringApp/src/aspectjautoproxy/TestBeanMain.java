/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aspectjautoproxy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author admin
 */
public class TestBeanMain {

    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/aspectjautoproxy/Config.xml");
        TestBeanProxy proxy = (TestBeanProxy) appContext.getBean("testBeanProxy");
        proxy.hello();
        proxy.wrold();
    }
}

class TestBeanProxy {

    private TestBean testBean;

    public void hello() {
        System.out.println("hello");
        testBean.hello();
    }

    public void wrold() {
        System.out.println("wrold");
        testBean.wrold();
    }

    /**
     * @return the testBean
     */
    public TestBean getTestBean() {
        return testBean;
    }

    /**
     * @param testBean the testBean to set
     */
    public void setTestBean(TestBean testBean) {
        this.testBean = testBean;
    }
}

class TestBean {

    public void hello() {
        System.out.println("hello");
    }

    public void wrold() {
        System.out.println("wrold");
    }
}
