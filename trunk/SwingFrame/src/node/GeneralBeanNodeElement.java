/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import shape.AbstractBeanNodeElement;
import shape.BeanNodeElement;

/**
 *
 * @author admin
 */
public class GeneralBeanNodeElement extends AbstractBeanNodeElement {

    public GeneralBeanNodeElement(BeanNodeElement parent, List<BeanNodeElement> children, TreeMap<String, Class> beanInfo, TreeMap<String, Object> beanValue, ImageIcon icon, String disctription) {
        super(parent, children, beanInfo, beanValue, icon, disctription);
    }

    public GeneralBeanNodeElement() {
        this.beanInfo.put("Window", Window.class);
        this.beanValue.put("Window", Window.WINDOW);
        this.beanInfo.put("Window.Parameter", String.class);
        this.beanValue.put("Window.Parameter", "");
//        this.beanInfo.put("Com", String.class);
//        this.beanValue.put("Com", "");
        this.beanInfo.put("Action", Action.class);
        this.beanValue.put("Action", Action.CLICK);
        this.beanInfo.put("Action.Parameter1", String.class);
        this.beanValue.put("Action.Parameter1", "");
        this.beanInfo.put("Action.Parameter2", String.class);
        this.beanValue.put("Action.Parameter2", "");
        this.beanInfo.put("Action.Parameter3", String.class);
        this.beanValue.put("Action.Parameter3", "");
        this.disctription = "general";
    }

    public GeneralBeanNodeElement(GeneralBeanNodeElement e) {
        super(e);
    }

//    public AbstractPropertiesModel[] getPropertiesModel() {
//        AbstractPropertiesModel tableMode = new AbstractPropertiesModel() {
//
//            @Override
//            public boolean isCellEditable(int rowIndex, int columnIndex) {
//                return columnIndex != 0;
//            }
//
//            public int getRowCount() {
//                return beanInfo.keySet().size();
//            }
//
//            @Override
//            public int getColumnCount() {
//                return 2;
//            }
//
//            private void injectAction(Action action) {
//                switch (action) {
//                    case GETPROPERTY:
//                        beanInfo.put("parameter", String.class);
//                        if (beanValue.get("parameter") == null) {
//                            beanValue.put("parameter", "");
//                        }
//                        beanInfo.put("result", String.class);
//                        if (beanValue.get("result") == null) {
//                            beanValue.put("result", "");
//                        }
//                        break;
//                    case CLICK:
//                        beanInfo.remove("parameter");
//                        beanInfo.remove("result");
//                        break;
//                    case SETVALUE:
//                    case SETPROPERTY:
//                    case SELECT:
//                        beanInfo.put("parameter", String.class);
//                        if (beanValue.get("parameter") == null) {
//                            beanValue.put("parameter", "");
//                        }
//                        beanInfo.remove("result");
//                        break;
//                }
//                this.fireTableStructureChanged();
//            }
//
//            @Override
//            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//                Object[] objs = beanInfo.keySet().toArray();
//                firePropertyChange(objs[rowIndex].toString(), beanValue.get(objs[rowIndex].toString()), aValue);
//                beanValue.put(objs[rowIndex].toString(), aValue);
//                firePropertyChange("change", true, false);
//                if (aValue instanceof Action) {
//                    this.injectAction((Action) aValue);
//                }
//            }
//
//            public Object getValueAt(int rowIndex, int columnIndex) {
//                Object[] objs = beanInfo.keySet().toArray();
//                if (columnIndex == 0) {
//                    return objs[rowIndex].toString();
//                }
//                return beanValue.get(objs[rowIndex].toString());
//            }
//
//            @Override
//            public String getPropertyName() {
//                return "property";
//            }
//        };
//        return new AbstractPropertiesModel[]{tableMode};
//    }
    @Override
    public String toString() {
        String str = "ar." + getBeanValue().get("Window").toString() + " (\"" + getBeanValue().get("Window.Parameter").toString() + "\").";
        str = str + getBeanValue().get("Action").toString();
        str = str + " (\"" + getBeanValue().get("Action.Parameter1").toString() + "\"," + " \"" + getBeanValue().get("Action.Parameter2").toString() + "\"," + " \"" + getBeanValue().get("Action.Parameter3").toString() + "\"" + ");";
        return str + "\n";
    }

    public BeanNodeElement getEditNode() {
        return new GeneralBeanNodeElement(this);
    }

    public void loadEncoderDelegate(Encoder encoder) {
        encoder.setPersistenceDelegate(GeneralBeanNodeElement.class, new DefaultPersistenceDelegate(new String[]{"parent", "children", "beanInfo", "beanValue", "icon", "disctription"}) {

            @Override
            protected Expression instantiate(Object oldInstance, Encoder out) {
                GeneralBeanNodeElement test = (GeneralBeanNodeElement) oldInstance;
                return new Expression(test, test.getClass(), "new", new Object[]{test.getParent(), test.getChildren(), test.getBeanInfo(), test.getBeanValue(), test.getIcon(), test.getDisctription()});
            }
        });
    }

//    private void parseAction(String str, GeneralBeanNodeElement e) {
//    }
//
//    private void parseWindow(String str, GeneralBeanNodeElement e) {
//        int n = str.indexOf("(");
//        if (n > 0) {
//            str = str.substring(0, n);
//            e.beanValue.put("Window", Window.parseWindow(str));
//            str = str.substring(n+1, str.length());
//            str
//        }
//    }
    public static GeneralBeanNodeElement parseElement(String str) {
//        ar.window ("4 ").click ("1 ",2 ",3 ");
        str = str.trim();
        if (str.trim().endsWith(";")) {
            str = str.substring(0, str.length() - 1);
        }
        String[] values = str.split("\\.");
        GeneralBeanNodeElement e = new GeneralBeanNodeElement();
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null && values[i].length() > 0) {
                list.add(values[i]);
            }
        }
        if (list.size() == 3) {
            str = list.get(1);
            int n = str.indexOf("(");
            if (n >= 0) {
                str = str.substring(0, n);
                e.beanValue.put("Window", Window.parseWindow(str.trim()));
            }
            n = list.get(1).indexOf("\"");
            int m = list.get(1).lastIndexOf("\"");
            if (n >= 0 && m >= 0) {
                e.beanValue.put("Window.Parameter", list.get(1).substring(n + 1, m).trim());
            }
            str = list.get(2);
            n = str.indexOf("(");
            if (n >= 0) {
                e.beanValue.put("Action", Action.parseAction(str.substring(0, n).trim()));
                str = str.substring(n + 1, str.length() - 1);
                values = str.split(",");
                if (values.length == 3) {
                    values[0] = values[0].trim();
                    values[1] = values[1].trim();
                    values[2] = values[2].trim();
                    e.beanValue.put("Action.Parameter1", values[0].substring(1, values[0].length() - 1).trim());
                    e.beanValue.put("Action.Parameter2", values[1].substring(1, values[1].length() - 1).trim());
                    e.beanValue.put("Action.Parameter3", values[2].substring(1, values[2].length() - 1).trim());
                }
            }
        }
        return e;
    }

    public static void main(String[] args) {
        String str = "ar.window (" + "\"4 \"" + ").click (" + "\"1\"" + ",\"2\"" + ",\"3\" " + ");";
//        System.out.println(Arrays.toString(str.split("^\"*$\"")));
        System.out.println(str);
        GeneralBeanNodeElement e = new GeneralBeanNodeElement();
        System.out.println(e.parseElement(str));
    }
}
