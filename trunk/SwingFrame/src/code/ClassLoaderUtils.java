/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.*;
import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListModel;

public class ClassLoaderUtils {

    private static boolean getJar = true;
    private static ArrayList<String> systemClasses;
    private static ArrayList<String> libClasses;
    public static final CustomClassLoader classLoader;

    static {
        systemClasses = loadSystemClass();
        libClasses = loadLibraryClass();
        classLoader = new CustomClassLoader();
    }

    public static ListModel serachClass(String name) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < systemClasses.size(); i++) {
            if (systemClasses.get(i).endsWith("." + name)) {
                result.add(systemClasses.get(i));
            }
        }
        for (int i = 0; i < libClasses.size(); i++) {
            if (libClasses.get(i).endsWith("." + name)) {
                result.add(libClasses.get(i));
            }
        }
        final ArrayList<String> temp = new ArrayList<String>();
        Object obj;
        for (int i = 0; i < result.size(); i++) {
            try {
                Class c = Class.forName(result.get(i));
//                Class c = classLoader.findClass(result.get(i));
                if (c != null) {
                    temp.add(result.get(i));
                }
            } catch (Exception ex) {
            }
        }
        return new AbstractListModel() {

            public int getSize() {
                return temp.size();
            }

            public Object getElementAt(int index) {
                return temp.get(index);
            }
        };
    }

    public static void listAllFileClass(ArrayList<String> arrayList, File file, String root) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                listAllFileClass(arrayList, files[i], root);
            }
        } else {
            String rs = checkClass(file.getPath());
            if (rs != null) {
                arrayList.add(rs.substring(root.length() + 1, rs.length()).trim());
            }
        }
    }

    public static ArrayList<String> listAllJarClass(String jarName) throws IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
        JarEntry jarEntry;
        String str;
        while (true) {
            jarEntry = jarFile.getNextJarEntry();
            if (jarEntry == null) {
                break;
            }
            str = checkClass(jarEntry.getName());
            if (str != null) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public static String checkClass(String str) {
        str = str.replaceAll("[$]", ".");
        str = str.replaceAll("/", ".");
        str = str.replaceAll("\\\\", ".");
        if (str.endsWith("class")) {
            str = str.substring(0, str.length() - 6);
        } else {
            return null;
        }
        if (!str.matches("^.+[.]{1}[0-9]+$")) {
            return str;
        }
        return null;
    }

    public static ArrayList<String> getClasseNamesInPackage(String jarName, String packageName) {
        ArrayList arrayList = new ArrayList();
        packageName = packageName.replaceAll("\\.", "/");
        if (getJar) {
            System.out.println("Jar " + jarName + " for " + packageName);
        }
        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
            JarEntry jarEntry;
            while (true) {
                jarEntry = jarFile.getNextJarEntry();
                if (jarEntry == null) {
                    break;
                }
                System.out.println(jarEntry.getName());
                if ((jarEntry.getName().startsWith(packageName))
                        && (jarEntry.getName().endsWith(".class"))) {
                    arrayList.add(jarEntry.getName().replaceAll("[$]", "."));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<String> loadSystemClass() {
        ArrayList<String> result = new ArrayList<String>();
        String path = System.getProperty("sun.boot.class.path");
        String[] paths = path.split(";");
        for (int i = 0; i < paths.length; i++) {
            String string = paths[i];
            try {
                System.out.println(string);
                result.addAll(listAllJarClass(string));
            } catch (IOException ex) {
            }
        }
        return result;
    }

    public static ArrayList<String> loadLibraryClass() {
        ArrayList<String> result = new ArrayList<String>();
        String path = System.getProperty("sun.boot.class.path");
        String[] paths = path.split(";");
        path = System.getProperty("java.class.path");
        paths = path.split(";");
        for (int i = 0; i < paths.length; i++) {
            if (paths[i].endsWith("classes")) {
                listAllFileClass(result, new File(paths[i]), paths[i]);
            } else if (paths[i].toLowerCase().endsWith("jar")) {
                try {
                    result.addAll(listAllJarClass(paths[i]));
                } catch (IOException ex) {
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        frame.setContentPane(new JList(serachClass("ClassFinder")));
        frame.setVisible(true);
    }

    /**
     *
     * @author Administrator
     */
    public static class CustomClassLoader extends URLClassLoader {

        public CustomClassLoader() {
            super(new URL[0]);
            try {
                loadLibrary(new File("lib"));
            } catch (MalformedURLException ex) {
            }
        }

        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            return super.findClass(name);
        }

        private void loadLibrary(File file) throws MalformedURLException {
            if (file.isFile() && file.getName().toString().endsWith("jar")) {
                this.addURL(file.toURI().toURL());
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    loadLibrary(files[i]);
                }
            }
        }
    }
}
