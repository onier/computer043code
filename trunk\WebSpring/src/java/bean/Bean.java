/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringValueResolver;

/**
 *
 * @author admin
 */
public class Bean implements BeanFactoryPostProcessor /*implements DisposableBean,BeanFactoryPostProcessor, InitializingBean, FactoryBean, BeanFactoryAware, BeanNameAware */ {

    private Bean bean1;
    private Bean bean2;
    private String name = "green";
    private String age = "10";
    private String sex = "123";
    private HashMap<String, BeanContent> map;
    private ArrayList<BeanContent> dataList;
    private Properties property;

    public Bean() {
    }

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

    /**
     * @return the map
     */
    public HashMap<String, BeanContent> getMap() {
        return map;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory clbf) throws BeansException {
        String[] names = clbf.getBeanDefinitionNames();
        for (int i = 0; i < names.length; i++) {
            String string = names[i];
            BeanDefinition beanDefinition = clbf.getBeanDefinition(string);
            StringValueResolver valueResover = new StringValueResolver() {

                public String resolveStringValue(String strVal) {
                    System.out.println(strVal);
                    return strVal;
                }
            };
            BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResover);
            visitor.visitBeanDefinition(beanDefinition);
        }
    }

    /**
     * @param map the map to set
     */
    public void setMap(HashMap<String, BeanContent> map) {
        this.map = map;
    }

    /**
     * @return the dataList
     */
    public ArrayList<BeanContent> getDataList() {
        return dataList;
    }

    /**
     * @param dataList the dataList to set
     */
    public void setDataList(ArrayList<BeanContent> dataList) {
        this.dataList = dataList;
    }

    /**
     * @return the property
     */
    public Properties getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(Properties property) {
        this.property = property;
    }
}
