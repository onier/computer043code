/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icon;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Zhenhai.Xu
 */
public class NewClass {

    public static void main(String[] args) {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = environment.getAllFonts();
        Map map = fonts[0].getAttributes();
        Collection collection= map.values();
        System.out.println(collection.toArray());
    }
}
