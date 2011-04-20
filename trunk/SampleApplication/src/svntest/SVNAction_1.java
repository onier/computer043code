/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package svntest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
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
        this.addSeparator();
        item = this.add(this.getAction("checkOut"));
        item.setText("签出");
        item = this.add(this.getAction("doImport"));
        item.setText("导入");
        item = this.add(this.getAction("doExport"));
        item.setText("导出");
        item = this.add(this.getAction("doAdd"));
        item.setText("添加");
        item = this.add(this.getAction("doCommit"));
        item.setText("提交本地工作目录");
    }

    private javax.swing.Action getAction(String name) {
        return Application.getInstance().getContext().getActionMap(this).get(name);
    }

    /**
     * 添加本地工作目录
     */
    public void doCommit() {
    }

    @Action
    public void doAdd() throws SVNException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        if (file == null) {
            return;
        }
        SVNClientUtils.add(new File("D:/svntest/trunk/src"));
    }

    @Action
    public void doExport() throws SVNException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        if (file == null) {
            return;
        }
        TreePath treePath = tree.getSelectionPath();
        Object[] objs = treePath.getPath();
        String dir = "";
        for (int i = 0; i < objs.length; i++) {
            dir = dir + objs[i] + "/";
        }
        dir = dir.trim();
        dir = dir.substring(0, dir.length() - 1);
        SVNClientUtils.doExport(dir, file, 55);
    }

    @Action
    public void doImport() throws SVNException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        if (file == null) {
            return;
        }
        TreePath treePath = tree.getSelectionPath();
        Object[] objs = treePath.getPath();
        String dir = "";
        for (int i = 0; i < objs.length; i++) {
            dir = dir + objs[i] + "/";
        }
        dir = dir.trim();
        dir = dir.substring(0, dir.length() - 1);
        SVNClientUtils.doImport(file, dir, "导入文件" + file);
    }

    @Action
    public void checkOut() throws SVNException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        if (file == null) {
            return;
        }
        TreePath treePath = tree.getSelectionPath();
        Object[] objs = treePath.getPath();
        String dir = "";
        for (int i = 0; i < objs.length; i++) {
            dir = dir + objs[i] + "/";
        }
        dir = dir.trim();
        dir = dir.substring(0, dir.length() - 1);
        SVNClientUtils.doCheckOut(file, dir, -1l);
        this.tree.setModel(new DefaultTreeModel(SVNClientUtils.createFileTreeNodel(file)));
    }

    @Action
    public void getHistory() throws FileNotFoundException, IOException {
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
//            JFileChooser chooser = new JFileChooser();
//            chooser.setFileFilter(new FileNameExtensionFilter("xls", "xls"));
//            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//            chooser.showOpenDialog(null);
//            File file = chooser.getSelectedFile();
//            if (file == null) {
//                return;
//            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
//                if (dir.equals(SVNClientUtils.url)) {
//                    SVNClientUtils.removeAllDir(dir);
//                }
//                version = entry.getRevision();
//                SVNRepository repository = SVNClientUtils.createSVNRepository(dir);
//                SVNClientUtils.getHistory(repository, "", baos, entry.getRevision());
                SVNClientUtils.rollBackHistory(dir, entry.getRevision(), "回滚");
//                tree.setModel(new DefaultTreeModel(SVNClientUtils.rollBackHistory(dir, entry.getRevision())));
//                SVNClientUtils.close(repository);
//                str = new String(baos.toByteArray());
//                pane.setText(new String(baos.toByteArray()));
//                this.tree.setModel(new DefaultTreeModel(SVNClientUtils.getSVNFileTreeRootNode(dir, -1)));
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
        try {
//            JFileChooser chooser = new JFileChooser();
//            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//            chooser.setFileFilter(new FileNameExtensionFilter("xls", "xls"));
//            chooser.showOpenDialog(null);
//            File file = chooser.getSelectedFile();
//            if (file == null) {
//                return;
//            }
//            FileOutputStream baos = new FileOutputStream(file);
            SVNRepository repository = SVNClientUtils.createSVNRepository(dir);
//            version = SVNClientUtils.getCurrentVersion(repository);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            SVNClientUtils.getFile(repository, "", baos, -1);
            SVNClientUtils.close(repository);
            baos.flush();
            baos.close();
            str = new String(baos.toByteArray());
            pane.setText(new String(baos.toByteArray()));
        } catch (Exception ex) {
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
//            SVNClientUtils.addAllDir(dir, name);
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
            SVNClientUtils.addFile(SVNClientUtils.createSVNRepository(dir), "添加文件", dir, filePath, bytes);
            this.tree.setModel(new DefaultTreeModel(SVNClientUtils.getSVNFileTreeRootNode(dir, -1)));
        } catch (SVNException ex) {
            Logger.getLogger(SVNAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
