/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package baisicdatasource;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class Main {

    public static void main(String args[]) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/baisicdatasource/newSpringXMLConfig.xml");
        EmployeeDaoImpl ws = (EmployeeDaoImpl) ctx.getBean("employeeDao");
    }
}
