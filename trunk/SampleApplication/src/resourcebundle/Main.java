/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcebundle;

import java.util.Locale;

/**
 *
 * @author Administrator
 */
public class Main {

    public static void main(String[] args) {
        ModuleResourceBundle sample = new ModuleResourceBundle("sample");
        sample.getResourceBundle();
        sample.getIcon("123.png");
        System.out.println(sample.getInt("Int"));
        System.out.println(sample.getBoolean("Boolean"));
        System.out.println(sample.getDouble("Double"));
        System.out.println(sample.getFloat("Float"));
        System.out.println(sample.getURI("URI"));
        System.out.println(sample.getURL("URL"));
        System.out.println(Locale.getDefault());
//        System.out.println(Locale.TAIWAN);
//        System.out.println(Locale.JAPAN.getLanguage());

    }
}
