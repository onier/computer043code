/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.example.exloprer;

import java.awt.datatransfer.Transferable;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.List;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.datatransfer.PasteType;

/**
 *
 * @author Zhenhai.Xu
 */
public class BeanClassNode extends BeanNode<Bean> {

    public BeanClassNode(Bean bean, String name) throws IntrospectionException {
        super(bean);
        this.setName(name);
    }

    public BeanClassNode(Bean bean, Children children, String name) throws IntrospectionException {
        super(bean, children);
        this.setName(name);
    }

    public void addNode(Bean... beans) throws IntrospectionException {
        for (int i = 0; i < beans.length; i++) {
            this.getChildren().add(new Node[]{new BeanClassNode(beans[i], i + "")});
        }
    }

    @Override
    protected void createProperties(Bean bean, BeanInfo info) {
        super.createProperties(bean, info);
    }

    @Override
    protected void createPasteTypes(Transferable t, List<PasteType> s) {
        super.createPasteTypes(t, s);
    }

    @Override
    public Transferable clipboardCut() throws IOException {
        return super.clipboardCut();
    }

    @Override
    public Transferable clipboardCopy() throws IOException {
        return super.clipboardCopy();
    }

    @Override
    public boolean canRename() {
        return true;
    }

    @Override
    public boolean canDestroy() {
        return true;
    }

    @Override
    public boolean canCut() {
        return true;
    }

    @Override
    public boolean canCopy() {
        return true;
    }
//    @Override
//    public Action[] getActions(boolean context) {
//        ArrayList<Action> list = new ArrayList<Action>();
//        list.add(new AddNodeAction(this));
//        list.add(new RenameAction(this));
////        CopyAction action = SystemAction.get(CopyAction.class);
////        action.setEnabled(true);
////        list.add(action);
//        Action[] actions = super.getActions(context);
//        for (int i = 0; i < actions.length; i++) {
////            if (actions[i] != null && !(actions[i] instanceof CopyAction)) {
//            list.add(actions[i]);
////            }
//        }
//        return list.toArray(new Action[0]);
//    }
}
