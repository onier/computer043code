/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author admin
 */
public class Bean implements DisposableBean, InitializingBean, FactoryBean, BeanFactoryAware, BeanNameAware {

    private Bean bean1;
    private Bean bean2;
    private String name = "green";
    private String age = "10";
    private String sex = "123";

    public static Bean createBeans() {
        return new Bean();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String str = "name :" + getName() + "  age: " + getAge() + "     sex:  " + getSex();
        return str;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the bean1
     */
    public Bean getBean1() {
        return bean1;
    }

    /**
     * @param bean1 the bean1 to set
     */
    public void setBean1(Bean bean1) {
        this.bean1 = bean1;
    }

    /**
     * @return the bean2
     */
    public Bean getBean2() {
        return bean2;
    }

    /**
     * @param bean2 the bean2 to set
     */
    public void setBean2(Bean bean2) {
        this.bean2 = bean2;
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }

    public void destroy() throws Exception {
        System.out.println("destroy");
    }

    public void setBeanFactory(BeanFactory bf) throws BeansException {
        System.out.println("setBeanFactory");
    }

    public void setBeanName(String string) {
        System.out.println(string);
    }

    public Object getObject() throws Exception {
        return this;
    }

    public Class getObjectType() {
        return null;
    }

    public boolean isSingleton() {
        return false;
    }
}
