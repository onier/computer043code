/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testframe;

import java.io.File;
import java.net.MalformedURLException;
import javax.swing.ImageIcon;

/**
 *
 * @author zhenhai.xu
 */
public class IconUtils {

//    public static ImageIcon START_IMAGE = new javax.swing.ImageIcon(Class.class.getResource("/icon/start.PNG"));
//    public static ImageIcon END_IMAGE = new javax.swing.ImageIcon(Class.class.getResource("/icon/end.PNG"));
//    public static ImageIcon LOOP_IMAGE = new javax.swing.ImageIcon(Class.class.getResource("/icon/loop.PNG"));
//    public static ImageIcon MESSAGE_IMAGE = new javax.swing.ImageIcon(Class.class.getResource("/icon/message.PNG"));

    public static ImageIcon getStartIcon() {
        return getIcon("src/icon/start.PNG");
    }

    public static ImageIcon getEndIcon() {
        return getIcon("src/icon/end.PNG");
    }

    public static ImageIcon getLoopIcon() {
        return getIcon("src/icon/loop.PNG");
    }

    public static ImageIcon getMessageIcon() {
        return getIcon("src/icon/message.PNG");
    }

    public static ImageIcon getIcon(String name) {
        try {
            return new ImageIcon(new File(name).toURI().toURL());
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    public static ImageIcon getUpIcon() {
        return getIcon("src/layout/Up.gif");
    }

    public static ImageIcon getDownIcon() {
        return getIcon("src/layout/Down.gif");
    }
}
