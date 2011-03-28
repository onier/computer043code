/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author admin
 */
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Config {

    public static void main(String[] args) {
        XmlBeanFactory factory = new XmlBeanFactory(new FileSystemResource("web/WEB-INF/conf.xml"));

        // 如果要在BeanFactory中使用，bean factory post-processor必须手动运行:
        PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
        cfg.setLocation(new FileSystemResource("web/WEB-INF/jdbc.properties"));
        cfg.postProcessBeanFactory(factory);

        DriverManagerDataSource dataSource = (DriverManagerDataSource) factory.getBean("dataSource");
        System.out.println(dataSource.getUrl());

        // 注意，ApplicationContext能够自动辨认和应用在其上部署的实现了BeanFactoryPostProcessor的bean。
        //这就意味着，当使用ApplicationContext的时候应用PropertyPlaceholderConfigurer会非常的方便。
        // 由于这个原因，建议想要使用这个或者其他bean
        // factory postprocessor的用户使用ApplicationContext代替BeanFactroy。
        ApplicationContext context = new FileSystemXmlApplicationContext("web/WEB-INF/conf.xml");
        DriverManagerDataSource dataSource2 = (DriverManagerDataSource) context.getBean("dataSource");
        System.out.println(dataSource2.getPassword());
    }
}
