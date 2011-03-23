/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author admin
 */
public class BeanDemo {

    public static void main(String[] args) throws IOException {
        GenericApplicationContext ctx = new GenericApplicationContext();
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
        ClassPathResource path = new ClassPathResource("applicationContext.xml");
        xmlReader.loadBeanDefinitions(path);
        PropertiesBeanDefinitionReader propReader = new PropertiesBeanDefinitionReader(ctx);
        propReader.loadBeanDefinitions(new ClassPathResource("web/WEB-INF/beaninfo.properties"));
        ctx.refresh();
        System.out.println(ctx.getBean("bean2"));
    }
}
