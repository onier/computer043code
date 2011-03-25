/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contextevent;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/contextevent/context.xml");
        EventPublisherDemo pub = (EventPublisherDemo) ctx.getBean("publisher");
        pub.report("A");
        pub.report("B");
    }
}

class EventPublisherDemo implements ApplicationContextAware {

    private ApplicationContext ctx;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public void report(String message) {
        ctx.publishEvent(new MessageEvent(this, message));
    }
}

class MessageEvent extends ApplicationEvent {

    private String message;

    public MessageEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}


