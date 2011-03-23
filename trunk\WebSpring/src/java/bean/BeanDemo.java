/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

/**
 *
 * @author admin
 */
public class BeanDemo {

    public static void main(String[] args) throws IOException {
        XmlBeanFactory ctx = new XmlBeanFactory(new FileSystemResource("web/WEB-INF/applicationContext.xml"));
        PropertyPlaceholderConfigurer configuere = new PropertyPlaceholderConfigurer();
        configuere.setLocation(new FileSystemResource("web/WEB-INF/beaninfo.properties"));
        configuere.postProcessBeanFactory(ctx);
//        ctx.refresh();
        System.out.println(ctx.getBean("bean2"));
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:bean/applicationContext.xml");
        Bean bean = (Bean) appContext.getBean("bean2");
        System.out.println(bean);
    }
}
