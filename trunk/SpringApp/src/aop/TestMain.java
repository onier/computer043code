/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author admin
 */
public class TestMain {

    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/aop/context.xml");
        AbastractTestBeanSeriverImpl servier = (AbastractTestBeanSeriverImpl) appContext.getBean("testBeanSeriver");
        servier.show();
    }
}

class TestBean {

    private String name = "sdf";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

interface TestBeanServier {

    void add(TestBean bean);

    void show();
}

interface TestBeanDao {

    public TestBean getTestBean();
}

class TestBeanDaoImpl implements TestBeanDao {

    private TestBean bean;

    public TestBean getTestBean() {
        return getBean();
    }

    /**
     * @return the bean
     */
    public TestBean getBean() {
        return bean;
    }

    /**
     * @param bean the bean to set
     */
    public void setBean(TestBean bean) {
        this.bean = bean;
    }
}

abstract class AbastractTestBeanSeriverImpl implements TestBeanServier {

    public void add(TestBean bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public abstract TestBeanDao getTestBeanDao();

    public void show() {
        TestBean bean = this.getTestBeanDao().getTestBean();
        System.out.println(bean.getName());
    }
}
