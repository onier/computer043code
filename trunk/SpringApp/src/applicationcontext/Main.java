package applicationcontext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/applicationcontext/context.xml");
        Resource res1 = ctx.getResource("file:///tmp");
        System.out.println(res1.getClass());
        Resource res2 = ctx.getResource("classpath:/bean/MyClass.class");
        System.out.println(res2.getURL());
        Resource res3 = ctx.getResource("http://www.google.com");
        System.out.println(res3.getURL());
    }
}
