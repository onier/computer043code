/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import shape.AbstractBeanNodeElement;
import shape.BeanNodeElement;

/**
 *
 * @author admin
 */
public class GeneralBeanNodeElement extends AbstractBeanNodeElement {

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
        String str = "ar." + getBeanValue().get("Window").toString() + " (\"" + getBeanValue().get("Window.Parameter").toString() + " \").";
        str = str + getBeanValue().get("Action").toString();
        str = str + " (\"" + getBeanValue().get("Action.Parameter1").toString() + " \"," + getBeanValue().get("Action.Parameter2").toString() + " \"," + getBeanValue().get("Action.Parameter3").toString() + " \"" + ");";
        return str + "\n";
    }

    public BeanNodeElement getEditNode() {
        return new GeneralBeanNodeElement(this);
    }
}
