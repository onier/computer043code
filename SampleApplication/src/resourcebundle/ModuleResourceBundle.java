/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcebundle;

import java.awt.Component;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.jdesktop.application.ResourceConverter;

/**
 *
 * @author Administrator
 */
public class ModuleResourceBundle {

    /**
     * 定义语言版本
     */
    private ResourceBundle.Control resourceBundleControl = new ResourceBundleTool();
    /**
     * 资源
     */
    private ResourceBundle resourceBundle;
    /**
     * 名称
     */
    private String name;

    public ModuleResourceBundle(String name) {
        this.name = name;
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    public Icon getIcon(String iconName) {
        File file = new File("resource" + File.separator + "icons" + File.separator + iconName);
        try {
            BufferedImage image = ImageIO.read(file);
            return new ImageIcon(image);
        } catch (IOException ex) {
            return null;
        }
    }

    public Boolean getBoolean(String key) {
        return getObject(key, Boolean.class);
    }

    public Integer getInteger(String key) {
        return getObject(key, Integer.class);
    }

    public Float getFloat(String key) {
        return getObject(key, Float.class);
    }

    public Short getShort(String key) {
        return getObject(key, Short.class);
    }

    public Long getLong(String key) {
        return getObject(key, Long.class);
    }

    public Double getDouble(String key) {
        return getObject(key, Double.class);
    }

    public Integer getInt(String key) {
        return getObject(key, Integer.class);
    }

    public URL getURL(String key) {
        return getObject(key, URL.class);
    }

    public URI getURI(String key) {
        return getObject(key, URI.class);
    }

    public <T> T getObject(String key, Class<T> paramClass) {
        try {
            String str = getResourceBundle().getString(key);
            Object object = ResourceConverter.forType(paramClass).parseString(str, null);
            return (T) object;
        } catch (ResourceConverter.ResourceConverterException localResourceConverterException) {
        }
        return null;
    }

    public void injectComponents(Container paramContainer) {
        injectComponent(paramContainer);
        for (Component localComponent : paramContainer.getComponents()) {
            if ((localComponent instanceof Container)) {
                injectComponents((Container) localComponent);
            } else {
                injectComponent(localComponent);
            }
        }
    }

    public void injectComponent(Component com) {
        Border border;
        try {
            String str1 = com.getName();
            if ((str1 == null) || (str1.isEmpty())) {
                return;
            }
            BeanInfo localBeanInfo = Introspector.getBeanInfo(com.getClass());
            for (PropertyDescriptor descriptor : localBeanInfo.getPropertyDescriptors()) {
                Method localMethod = descriptor.getWriteMethod();
                if (localMethod == null) {
                    continue;
                }
                Class localClass = descriptor.getPropertyType();
                if ((!localClass.equals(String.class)) && (ResourceConverter.forType(localClass) == null)) {
                    continue;
                }
                String str3 = str1 + "." + descriptor.getName();
                if (!getResourceBundle().containsKey(str3)) {
                    continue;
                }
                String str4 = getResourceBundle().getString(str3);
                Object localObject3 = str4;
                if (!localClass.equals(String.class)) {
                    localObject3 = ResourceConverter.forType(localClass).parseString(str4, null);
                }
                localMethod.invoke(com, new Object[]{localObject3});
            }
            String str2;
            if ((com instanceof JComponent)) {
                border = ((JComponent) com).getBorder();
                if ((border instanceof TitledBorder)) {
                    str2 = str1 + ".border.title";
                    if (getResourceBundle().containsKey(str2)) {
                        ((TitledBorder) border).setTitle(getResourceBundle().getString(str2));
                    }
                }
            }
            Container parent = com.getParent();
            if ((parent instanceof JTabbedPane)) {
                str2 = str1 + ".tab.title";
                if (getResourceBundle().containsKey(str2)) {
                    JTabbedPane localJTabbedPane = (JTabbedPane) parent;
                    int k = localJTabbedPane.indexOfComponent(com);
                    localJTabbedPane.setTitleAt(k, getResourceBundle().getString(str2));
                }
            }
        } catch (Exception localException) {
        }
    }

    public void injectAction(Action paramAction, String name) {
        if (paramAction == null) {
            throw new IllegalArgumentException();
        }
        name = name + ".Action.";
        String str1 = name + "text";
        if (getResourceBundle().containsKey(str1)) {
            String str = getString(str1);
            paramAction.putValue("Name", str);
            paramAction.putValue("MnemonicKey", null);
            paramAction.putValue("SwingDisplayedMnemonicIndexKey", Integer.valueOf(-1));
        }
        Object object = null;
        str1 = name + "icon";
        if (getResourceBundle().containsKey(str1)) {
            object = getIcon(getString(str1));
        }
        paramAction.putValue("SmallIcon", object);
        paramAction.putValue("SwingLargeIconKey", object);
        KeyStroke localKeyStroke = null;
        str1 = name + "accelerator";
        if (getResourceBundle().containsKey(str1)) {
            localKeyStroke = KeyStroke.getKeyStroke(getString(str1));
        }
        paramAction.putValue("AcceleratorKey", localKeyStroke);
        str1 = name + "selected";
        if (getResourceBundle().containsKey(str1)) {
            paramAction.putValue("selected", getBoolean(str1));
        }
    }

    public ResourceBundle getResourceBundle() {
        if (this.resourceBundle == null) {
            this.resourceBundle = ResourceBundle.getBundle(this.name, resourceBundleControl);
        }
        return this.resourceBundle;
    }

    class ResourceBundleTool extends ResourceBundle.Control {

        private List<String> formats = Arrays.asList(new String[]{"txt"});
        private Locale anyLocale = Locale.getDefault();

        @Override
        public List<String> getFormats(String baseName) {
            return this.formats;
        }

        @Override
        public List<Locale> getCandidateLocales(String baseName, Locale locale) {
            List localList = super.getCandidateLocales(baseName, locale);
            localList.add(Locale.getDefault());
            localList.add(Locale.ENGLISH);
            return localList;
        }

        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
            String path = "resource" + File.separator + baseName + "_" + locale + "." + format;
            File file = new File(path);
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            return new PropertyResourceBundle(reader);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }
}
