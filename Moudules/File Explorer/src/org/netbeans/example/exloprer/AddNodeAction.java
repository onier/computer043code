/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.example.exloprer;

import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.NotifyDescriptor.InputLine;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Zhenhai.Xu
 */
public class AddNodeAction extends AbstractAction {

    private BeanClassNode node;

    public AddNodeAction(BeanClassNode node) {
        this.putValue(Action.NAME, "add");
        this.node = node;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Node parent = node.getParentNode();
        try {
            parent.getChildren().add(new Node[]{new BeanClassNode(new Bean(), "new")});
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static class RenameAction extends AbstractAction {

        private BeanClassNode node;

        public RenameAction(BeanClassNode node) {
            this.putValue(Action.NAME, "rename");
            this.node = node;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            InputLine input = new NotifyDescriptor.InputLine("new name", "name");
            Object obj = DialogDisplayer.getDefault().notify(input);
            if (NotifyDescriptor.YES_OPTION.equals(obj)) {
                node.setName(input.getInputText());
            }
        }
    }
}
