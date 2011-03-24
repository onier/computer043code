/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package propertieseditor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author admin
 */
public class EditorTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/propertieseditor/dateConfig.xml");
        DateBean dataBeans = (DateBean) context.getBean("datetestBean");
        System.out.println(dataBeans.getDate());
    }
}
