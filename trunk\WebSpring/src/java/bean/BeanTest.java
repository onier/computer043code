/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

/**
 *
 * @author admin
 */
public class BeanTest {

    public static void main(String[] args) {
        FileSystemResource resource = new FileSystemResource("web/WEB-INF/applicationContext.xml");
        XmlBeanFactory beanFactory = new XmlBeanFactory(resource);
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {

            public Object postProcessBeforeInitialization(Object o, String string) throws BeansException {
                System.out.println("postProcessBeforeInitialization :" + o + "             " + string);
                return o;
            }

            public Object postProcessAfterInitialization(Object o, String string) throws BeansException {
                System.out.println("postProcessAfterInitialization :" + o + "             " + string);
                return o;
            }
        });
        Bean bean = (Bean) beanFactory.getBean("bean");
        System.out.println(bean);
        bean = (Bean) beanFactory.getBean("bean1");
        System.out.println(bean);
        bean = (Bean) beanFactory.getBean("bean2");
        System.out.println(bean);
    }
}
