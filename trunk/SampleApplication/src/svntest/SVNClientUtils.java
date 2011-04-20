/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package svntest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.tree.DefaultMutableTreeNode;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 *
 * @author Administrator
 */
public class SVNClientUtils {

    private static String url = "https://svn.kenai.com/svn/javaapplication~subversion";
    private static String name = "daay1986";
    private static String password = "dgzqjjls";
    public static final String SPLIT = "_";

    static {
        SVNClientUtils.setupLibrary();
    }

    public static SVNRepository createSVNRepository(String url, String name, String password) throws SVNException {
        SVNClientUtils.url = url;
        SVNClientUtils.name = name;
        SVNClientUtils.password = password;
        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        repository.setAuthenticationManager(authManager);
        return repository;
    }

    public static SVNRepository createSVNRepository(String url) throws SVNException {
        SVNClientUtils.url = url;
        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        repository.setAuthenticationManager(authManager);
        return repository;
    }

    public static SVNRepository createSVNRepository(SVNURL url) throws SVNException {
        SVNRepository repository = SVNRepositoryFactory.create(url);
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        repository.setAuthenticationManager(authManager);
        return repository;
    }

    public static SVNRepository createSVNRepository() throws SVNException {
        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        repository.setAuthenticationManager(authManager);
        return repository;
    }

    /**
     * 获得历史版本
     * @param targetPaths
     * @param startRevision
     * @param endRevision
     * @return
     * @throws SVNException
     */
    public static Collection getLogVersion(SVNRepository repository, String[] targetPaths, long startRevision, long endRevision) throws SVNException {
        return repository.log(targetPaths, null, startRevision, endRevision, true, true);
    }

    /**
     * 获得文件
     * @param filePath
     * @param outputStream
     * @param version
     * @param repository
     * @throws SVNException
     */
    public static void getFile(SVNRepository repository, String filePath, OutputStream outputStream, long version) throws SVNException {
        SVNNodeKind nodeKind = repository.checkPath(filePath, -1);
        if (nodeKind == SVNNodeKind.NONE) {
            System.err.println("There is no entry at '" + filePath + "'.");
        } else if (nodeKind == SVNNodeKind.DIR) {
            System.err.println("The entry at '" + filePath
                    + "' is a directory while a file was expected.");
        }
        SVNProperties fileProperties = new SVNProperties();
        repository.getFile(filePath, version, fileProperties, outputStream);
    }

    /**
     * 获得历史版本信息,如果为目录则返回目录包含的信息,文件返回为空
     * @param repository
     * @param filePath
     * @param outputStream
     * @param version
     * @return
     * @throws SVNException
     */
    public static DefaultMutableTreeNode rollBackHistory(String filePath, long version, String logMessage) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, authManager);
        SVNUpdateClient copyClient = manager.getUpdateClient();
        File file = new File("c:\\demo\\" + System.currentTimeMillis());
        copyClient.doCheckout(SVNURL.parseURIDecoded(filePath), file, SVNRevision.create(- 1), SVNRevision.create(-1), SVNDepth.fromRecurse(true), true);
        manager.getUpdateClient().doUpdate(file, SVNRevision.create(version), SVNDepth.fromRecurse(true), false, true);
//        copyClient.doCheckout(SVNURL.parseURIDecoded(filePath), file, SVNRevision.create(version - 1), SVNRevision.create(version), SVNDepth.fromRecurse(true), true);
//        manager.getWCClient().doRevert(new File[]{file}, SVNDepth.fromRecurse(true), null);
        //        copyClient.doExport(SVNURL.parseURIDecoded(filePath), file, SVNRevision.create(-1), SVNRevision.create(version), filePath, true, SVNDepth.fromRecurse(true));
        SVNCommitClient commit = manager.getCommitClient();
        //        commit.doDelete(new SVNURL[]{SVNURL.parseURIDecoded(filePath)}, "rollBackdelete");
        //        commit.doMkDir(new SVNURL[]{SVNURL.parseURIDecoded(filePath)}, "rollBackaddDir");
        //        commit.doImport(file, SVNURL.parseURIDecoded(filePath), "rollBack", new SVNProperties(), true, true, SVNDepth.fromRecurse(true));
        SVNCommitInfo info = commit.doCommit(new File[]{file}, false, logMessage, new SVNProperties(), null, false, false, SVNDepth.fromRecurse(true));
        DefaultMutableTreeNode node = SVNClientUtils.getSVNFileTreeRootNode(filePath, version);
        file.delete();
        return node;
    }

    public static void rollBackHistory(long version, String filePath) throws SVNException {
        SVNProperties properties = new SVNProperties();
        SVNRepository repository = SVNClientUtils.createSVNRepository(filePath);
        ArrayList list = new ArrayList();
        SVNClientUtils.getDir(repository, "", properties, version, list);
        for (Object obj : list) {
            SVNDirEntry entry = (SVNDirEntry) obj;
//            if (entry.getKind().equals(SVNNodeKind.DIR)) {
//                repository.
//            }
            System.out.println("________________________________");
            System.out.println(entry.getName());
            System.out.println(entry.getURL());
            System.out.println("________________________________");
        }
    }
//
//    private static void rollback(SVNRepository repository, long version, ArrayList list) {
//        for (int i = 0; i < list.size(); i++) {
//            SVNEntry entry = (SVNEntry) list.get(i);
//            if (entry != null) {
//                if (entry.getKind().equals(SVNNodeKind.DIR)) {
//                    try {
//                        SVNClientUtils.addAllDir(repository, "回滚历史", entry.getName());
//                    } catch (SVNException ex) {
//                        System.out.println("回滚失败" + entry.getName());
//                    }
//                } else if (entry.getKind().equals(SVNNodeKind.FILE)) {
//                    SVNClientUtils.getFile(repository, entry.getName(), null, version);
//                }
//            }
//
//        }
//        SVNClientUtils.getFile(repository, url, null, version)
//
//
//
//    }

    /**
     * 获得目录
     * @param filePath
     * @param outputStream
     * @param version
     * @param repository
     * @throws SVNException
     */
    public static void getDir(SVNRepository repository, String filePath, SVNProperties properties, long version,
            Collection dirEntries) throws SVNException {
        SVNNodeKind nodeKind = repository.checkPath(filePath, -1);
//        if (nodeKind == SVNNodeKind.NONE) {
//            System.err.println("There is no entry at '" + filePath + "'.");
//        } else if (nodeKind == SVNNodeKind.DIR) {
//            System.err.println("The entry at '" + filePath
//                    + "' is a directory while a file was expected.");
//        }
        repository.getDir(filePath, version, properties, dirEntries);
    }

    /**
     * load library
     */
    public static void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();


    }

    /**
     * 添加数据
     * @param editor ISVNEditor
     * @param dirPath  目录
     * @param filePath  文件目录
     * @param data  添加的数据
     * @return
     * @throws SVNException
     */
    public static SVNCommitInfo addDir(SVNRepository repository, String logMessage, String dirPath,
            String filePath, byte[] data) throws SVNException {
        ISVNEditor editor = repository.getCommitEditor(logMessage, null);
        editor.openRoot(-1);
        editor.addFile(filePath, null, -1);
        editor.applyTextDelta(filePath, null);
        SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
        String checksum = deltaGenerator.sendDelta(filePath, new ByteArrayInputStream(data), editor, true);
        editor.closeFile(filePath, checksum);
        editor.closeDir();
        return editor.closeEdit();
    }

    public static void close(SVNRepository repository) {
        repository.closeSession();
    }

    /**
     * 添加文件
     * @param editor  ISVNEditor
     * @param dirPath  目录
     * @param filePath 文件目录
     * @param file 添加的文件
     * @return
     * @throws SVNException
     * @throws FileNotFoundException
     */
    public static SVNCommitInfo addDir(SVNRepository repository, String logMessage, String dirPath,
            String filePath, File file) throws SVNException, FileNotFoundException {
        ISVNEditor editor = repository.getCommitEditor(logMessage, null);
        String[] values = SVNClientUtils.getValidValue(filePath.split("/"));
        for (int i = 0; i < values.length; i++) {
            repository.checkPath(values[i], -1);
        }
        editor.addFile(filePath, null, -1);
        editor.applyTextDelta(filePath, null);
        SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
        String checksum = deltaGenerator.sendDelta(filePath, new FileInputStream(file), editor, true);
        editor.closeFile(filePath, checksum);
        editor.closeDir();
        return editor.closeEdit();
    }

    /**
     * 判断是否冲突.true 冲突 false 不冲突 (此方法不关闭repository)
     * @param repository
     * @param oldVersion  老版本号
     * @return
     */
    public static boolean checkConflicted(SVNRepository repository, long oldVersion) throws SVNException {
        long version = getCurrentVersion(repository);
        if (version != -1) {
            return oldVersion != version;
        }
        return false;
    }

    /**
     *获得当前版本,如果不存在版本信息则返回-2.(此方法不关闭repository)
     * @param repository
     * @return  返回版本号
     */
    public static long getCurrentVersion(SVNRepository repository) throws SVNException {
        Collection collection = getHistoryVersion(repository, -1, -1);
        Object[] logs = collection.toArray();
        if (logs != null && logs.length > 0) {
            SVNLogEntry entry = (SVNLogEntry) logs[logs.length - 1];
            return entry.getRevision();
        }
        return -2;
    }

    public static Collection getHistoryVersion(SVNRepository repository, long startRevision, long endRevision) throws SVNException {
        return repository.log(new String[]{""}, null,
                startRevision, endRevision, true, true);
    }

    public static ISVNEditor openDir(SVNRepository repository, String logMessage, String dirPath) throws SVNException {
        ISVNEditor editor = repository.getCommitEditor(logMessage, null);
        editor.openRoot(-1);
        String[] dirs = dirPath.split("/");
        for (int i = 0; i < dirs.length; i++) {
            if (dirs[i] != null && dirs[i].length() > 0) {
                editor.openDir(dirs[i], -1);
            }
        }
        return editor;
    }

    /**
     * dirPath所有的节点将会被视为新节点添加
     * @param logMessage
     * @param dirPath
     * @return
     * @throws SVNException
     */
    public static ISVNEditor addAllDir(SVNRepository repository, String logMessage, String dirPath) throws SVNException {
        ISVNEditor editor = repository.getCommitEditor(logMessage, null);
        editor.openRoot(-1);
        String[] dirs = dirPath.split("/");
        dirs = SVNClientUtils.getValidValue(dirs);
        for (int i = 0; i < dirs.length; i++) {
            editor.addDir(dirs[i], null, -1);
        }
        if (dirs.length > 0) {
            editor.closeDir();
            editor.closeEdit();
        }
        return editor;
    }

    /**
     * dirPath的最后一个节点将会被视为新节点添加
     * @param logMessage
     * @param dirPath
     * @return
     * @throws SVNException
     */
    public static ISVNEditor addDir(SVNRepository repository, String logMessage, String dirPath) throws SVNException {
        ISVNEditor editor = repository.getCommitEditor(logMessage, null);
        editor.openRoot(-1);
        String[] dirs = dirPath.split("/");
        dirs = SVNClientUtils.getValidValue(dirs);
        for (int i = 0; i < dirs.length; i++) {
            editor.addDir(dirs[i], null, -1);
        }
        if (dirs.length > 0) {
            editor.closeDir();
            editor.closeEdit();
        }
        return editor;
    }

    /**
     * 修改数据
     * @param editor
     * @param dirPath
     * @param filePath
     * @param oldData
     * @param newData
     * @return
     * @throws SVNException
     */
    public static SVNCommitInfo modifyFile(SVNRepository repository, String logMessage,
            String filePath, InputStream oldData, InputStream newData) throws SVNException {
        ISVNEditor editor = repository.getCommitEditor(logMessage, null);
        editor.openRoot(-1);
        editor.openFile(filePath, -1);
        editor.applyTextDelta(filePath, null);
        SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
        String checksum = deltaGenerator.sendDelta(filePath, oldData, 0, newData, editor, true);
        editor.closeFile(filePath, checksum);
        editor.closeDir();
        return editor.closeEdit();
    }

    public static SVNCommitInfo deleteDir(SVNRepository repository, String logMessage, String dirPath) throws SVNException {
        ISVNEditor editor = repository.getCommitEditor(logMessage, null);
        editor.openRoot(-1);
        String[] dirs = dirPath.split("/");
        dirs = SVNClientUtils.getValidValue(dirs);
        for (int i = 0; i < dirs.length; i++) {
            editor.deleteEntry(dirs[i], -1);
        }
        editor.closeDir();
        return editor.closeEdit();
    }

    public static void getFile(SVNRepository repository, String fileName, long version, SVNProperties fileProperties, OutputStream outputStream) throws SVNException {
        repository.getFile(fileName, version, fileProperties, outputStream);
    }

    public SVNCommitInfo copyDir(SVNRepository repository, String logMessage, String srcDirPath,
            String dstDirPath, long revision) throws SVNException {
        ISVNEditor editor = repository.getCommitEditor(logMessage, null);
        editor.openRoot(-1);
        editor.addDir(dstDirPath, srcDirPath, revision);
        editor.closeDir();
        return editor.closeEdit();
    }

    /**
     * 产生树形数据,返回的结果中第一个list包含的节点为第一层的节点.
     * @param paths  目录结构字符
     * @return
     */
    public static ArrayList<ArrayList<DefaultMutableTreeNode>> createSVNFileTreeNode(ArrayList<String> paths) {
        ArrayList<ArrayList<DefaultMutableTreeNode>> treeNodes = new ArrayList<ArrayList<DefaultMutableTreeNode>>();
        DefaultMutableTreeNode temp;
        String value;
        DefaultMutableTreeNode node;
        for (int i = 0; i
                < paths.size(); i++) {
            String str = paths.get(i);
            String[] values = str.split("/");
            values = getValidValue(values);
            for (int j = 0; j < values.length; j++) {
                if (treeNodes.size() == j) {
                    ArrayList<DefaultMutableTreeNode> list = new ArrayList<DefaultMutableTreeNode>();
                    treeNodes.add(list);
                }
                if (j > 0) {
                    node = new DefaultMutableTreeNode(values[j]);
                    if (contain(treeNodes.get(j), values[j]) == null) {
                        treeNodes.get(j).add(node);
                        temp = contain(treeNodes.get(j - 1), values[j - 1]);
                        if (temp != null) {
                            temp.add(node);
                        }
                    }
                } else {
                    if (contain(treeNodes.get(j), values[j]) == null) {
                        treeNodes.get(j).add(new DefaultMutableTreeNode(values[j]));
                    }
                }
            }
        }
        return treeNodes;


    }

    /**
     * 按照拆分字符串展开文件名,以便生产树
     * @param list
     * @param split
     */
    public static void splitPaths(ArrayList<String> list, String split) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            String[] values = list.get(i).split(split);
            String str = "";
            for (int j = 0; j
                    < values.length; j++) {
                if (values[j] != null && values[j].length() > 0) {
                    str = str + "/" + values[j];
                    result.add(str);
                }
            }
        }
        list.clear();
        list.addAll(result);
    }

    public static DefaultMutableTreeNode getSVNFileTreeRootNode(String url, long version) throws SVNException {
        SVNRepository repository = createSVNRepository(url);
        ArrayList<String> paths = new ArrayList<String>();
        SVNClientUtils.listEntries(repository, version, "", paths);
        ArrayList<ArrayList<DefaultMutableTreeNode>> nodeList = new ArrayList<ArrayList<DefaultMutableTreeNode>>();
        nodeList = SVNClientUtils.createSVNFileTreeNode(paths);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(url);
        if (nodeList.size() > 0) {
            for (int i = 0; i
                    < nodeList.get(0).size(); i++) {
                root.add(nodeList.get(0).get(i));
            }
        }
        return root;
    }

    private static DefaultMutableTreeNode contain(ArrayList<DefaultMutableTreeNode> list, String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUserObject().toString().equals(name)) {
                return list.get(i);
            }
        }
        return null;
    }

    private static String[] getValidValue(String[] values) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i
                < values.length; i++) {
            String string = values[i];


            if (string != null && string.length() > 0) {
                list.add(string);


            }
        }
        values = new String[list.size()];


        return list.toArray(values);


    }

    public static void listEntries(SVNRepository repository, long version, String path, ArrayList<String> paths)
            throws SVNException {

        Collection entries = repository.getDir(path, version, null,
                (Collection) null);
        Iterator iterator = entries.iterator();


        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();


            if (path.equals("")) {
                paths.add("/" + entry.getName());


            } else {
                paths.add("/" + path + "/" + entry.getName());


            }
            System.out.println("/" + path + "/"
                    + entry.getName());
            /*
             * Checking up if the entry is a directory.
             */


            if (entry.getKind() == SVNNodeKind.DIR) {
                listEntries(repository, version, (path.equals("")) ? entry.getName()
                        : path + "/" + entry.getName(), paths);


            }
        }
//        splitPaths(paths, SPLIT);

    }
}
