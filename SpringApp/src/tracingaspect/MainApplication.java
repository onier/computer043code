/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tracingaspect;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author admin
 */
public class MainApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("build/springconfig.xml");

        IBusinessLogic testObject = (IBusinessLogic) ctx.getBean("businesslogicbean");

        testObject.foo();
    }
}
