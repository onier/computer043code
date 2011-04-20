/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package svntest1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.io.SVNRepository;

/**
 *
 * @author Administrator
 */
public class SVNAction extends JPopupMenu {

    private String str = "";
    private JTree tree;
    private JEditorPane pane;
    private JList list;
    private long version;

    public SVNAction(JTree tree, JEditorPane pane, JList list) {
        this.tree = tree;
        this.pane = pane;
        this.list = list;
        tree.getSelectionModel().addTreeSelectionListener(null);
        JMenuItem item = this.add(this.getAction("addFile"));
        item.setText("添加文件");
        item = this.add(this.getAction("delete"));
        item.setText("删除");
        item = this.add(this.getAction("addDir"));
        item.setText("添加目录");
        item = this.add(this.getAction("getFile"));
        item.setText("读取");
        item = this.add(this.getAction("commit"));
        item.setText("提交");
        item = this.add(this.getAction("getLog"));
        item.setText("获得版本");
        item = this.add(this.getAction("getHistory"));
        item.setText("回滚");
    }

    private javax.swing.Action getAction(String name) {
        return Application.getInstance().getContext().getActionMap(this).get(name);
    }

    @Action
    public void getHistory() {
        Object obj = list.getSelectedValue();
        if (obj != null) {
            SVNLogEntry entry = (SVNLogEntry) obj;
            TreePath treePath = tree.getSelectionPath();
            Object[] objs = treePath.getPath();
            String dir = "";
            for (int i = 0; i < objs.length; i++) {
                dir = dir + objs[i] + "/";
            }
            dir = dir.trim();
            dir = dir.substring(0, dir.length() - 1);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                version = entry.getRevision();
                SVNRepository repository = SVNClientUtils.createSVNRepository(dir);
                SVNClientUtils.getFile(repository, "", baos, entry.getRevision());
                SVNClientUtils.close(repository);
                str = new String(baos.toByteArray());
                pane.setText(new String(baos.toByteArray()));
                this.tree.setModel(new DefaultTreeModel(SVNClientUtils.getSVNFileTreeRootNode(dir, -1)));
            } catch (SVNException ex) {
                Logger.getLogger(SVNAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Action
    public void getLog() {
        TreePath treePath = tree.getSelectionPath();
        Object[] objs = treePath.getPath();
        String dir = "";
        for (int i = 0; i < objs.length; i++) {
            dir = dir + objs[i] + "/";
        }
        dir = dir.trim();
        dir = dir.substring(0, dir.length() - 1);
        try {
            SVNRepository repository = SVNClientUtils.createSVNRepository(dir);
            Object[] values = SVNClientUtils.getHistoryVersion(repository, 0, -1).toArray();
            SVNClientUtils.close(repository);
            DefaultListModel model = new DefaultListModel();
            int size = values.length;
            for (int i = 0; i < size; i++) {
                model.addElement(values[i]);
            }
            list.setModel(model);
            this.tree.setModel(new DefaultTreeModel(SVNClientUtils.getSVNFileTreeRootNode(dir, -1)));
        } catch (SVNException ex) {
            Logger.getLogger(SVNAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Action
    public void commit() {
        TreePath treePath = tree.getSelectionPath();
        Object[] objs = treePath.getPath();
        String dir = "";
        for (int i = 0; i < objs.length; i++) {
            dir = dir + objs[i] + "/";
        }
        dir = dir.trim();
        dir = dir.substring(0, dir.length() - 1);
        try {
            SVNRepository repository = SVNClientUtils.createSVNRepository(dir);
            System.out.println(SVNClientUtils.checkConflicted(repository, version));
            version = -2;
            SVNClientUtils.modifyFile(repository, "提交", "", new ByteArrayInputStream(str.getBytes()), new ByteArrayInputStream(pane.getText().getBytes()));
            SVNClientUtils.close(repository);
        } catch (SVNException ex) {
            Logger.getLogger(SVNAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Action
    public void getFile() {
        TreePath treePath = tree.getSelectionPath();
        Object[] objs = treePath.getPath();
        String dir = "";
        for (int i = 0; i < objs.length; i++) {
            dir = dir + objs[i] + "/";
        }
        dir = dir.trim();
        dir = dir.substring(0, dir.length() - 1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            SVNRepository repository = SVNClientUtils.createSVNRepository(dir);
            version = SVNClientUtils.getCurrentVersion(repository);
            SVNClientUtils.getFile(repository, "", baos, -1);
            SVNClientUtils.close(repository);
            str = new String(baos.toByteArray());
            pane.setText(new String(baos.toByteArray()));
        } catch (SVNException ex) {
            Logger.getLogger(SVNAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Action
    public void addDir() {
        TreePath treePath = tree.getSelectionPath();
        Object[] objs = treePath.getPath();
        String dir = "";
        for (int i = 0; i < objs.length; i++) {
            dir = dir + objs[i] + "/";
        }
        dir = dir.trim();
        dir = dir.substring(0, dir.length() - 1);
        String name = JOptionPane.showInputDialog("输入名称");
        try {
            SVNRepository repository = SVNClientUtils.createSVNRepository(dir);
            SVNClientUtils.addDir(repository, "添加" + name, name);
            this.tree.setModel(new DefaultTreeModel(SVNClientUtils.getSVNFileTreeRootNode(dir, -1)));
            SVNClientUtils.close(repository);
        } catch (SVNException ex) {
            Logger.getLogger(SVNAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Action
    public void delete() {
        TreePath treePath = tree.getSelectionPath();
        Object[] objs = treePath.getPath();
        String dir = "";
        for (int i = 0; i < objs.length - 1; i++) {
            dir = dir + objs[i] + "/";
        }
        dir = dir.trim();
        dir = dir.substring(0, dir.length() - 1);
        try {
            SVNRepository repository = SVNClientUtils.createSVNRepository(dir);
            SVNClientUtils.deleteDir(repository, "删除" + dir, objs[objs.length - 1].toString());
            this.tree.setModel(new DefaultTreeModel(SVNClientUtils.getSVNFileTreeRootNode(dir, -1)));
            SVNClientUtils.close(repository);
        } catch (SVNException ex) {
            Logger.getLogger(SVNAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Action
    public void addFile() {
        TreePath treePath = tree.getSelectionPath();
        Object[] objs = treePath.getPath();
        String dir = "";
        for (int i = 0; i < objs.length; i++) {
            dir = dir + objs[i] + "/";
        }
        dir = dir.trim();
        String name = JOptionPane.showInputDialog("输入名称");
        String filePath = dir + name + ".txt";
        byte[] bytes = pane.getText().getBytes();
        try {
            SVNRepository repository = SVNClientUtils.createSVNRepository(dir);
            SVNClientUtils.addDir(repository, "添加文件", dir, filePath, bytes);
            this.tree.setModel(new DefaultTreeModel(SVNClientUtils.getSVNFileTreeRootNode(dir, -1)));
            SVNClientUtils.close(repository);
        } catch (SVNException ex) {
            Logger.getLogger(SVNAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
