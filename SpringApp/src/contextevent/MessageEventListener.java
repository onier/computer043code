/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contextevent;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

class MessageEventListener implements ApplicationListener {

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof MessageEvent) {
            MessageEvent messageEvent = (MessageEvent) event;
            System.out.println("Received " + messageEvent.getMessage());
        }
    }
}
