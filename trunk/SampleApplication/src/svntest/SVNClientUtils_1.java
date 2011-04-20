/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package svntest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import org.tmatesoft.svn.core.SVNCancelException;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;
import org.tmatesoft.svn.core.wc.ISVNConflictHandler;
import org.tmatesoft.svn.core.wc.ISVNEventHandler;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.ISVNStatusHandler;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNConflictChoice;
import org.tmatesoft.svn.core.wc.SVNConflictDescription;
import org.tmatesoft.svn.core.wc.SVNConflictReason;
import org.tmatesoft.svn.core.wc.SVNConflictResult;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNEvent;
import org.tmatesoft.svn.core.wc.SVNEventAction;
import org.tmatesoft.svn.core.wc.SVNMergeFileSet;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusClient;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 *SVN工具类
 * @author Administrator
 */
public class SVNClientUtils {

    public static String url = "https://svn.kenai.com/svn/javaapplication~subversion";
    private static String name = "test";
    private static String password = "1111";
    public static final String SPLIT = "_";

    static {
        SVNClientUtils.setupLibrary();
    }

    public static SVNRepository createSVNRepository(String url, String name, String password) throws SVNException {
        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        repository.setAuthenticationManager(authManager);
        return repository;
    }

    public static SVNRepository createSVNRepository(String url) throws SVNException {
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
     * 添加工作目录到本地svn版本控制中,path必须是本地版本控制文件,该方法会添加paths里包含的目录和所有文件.
     * 本方法只是将在本地版本控制空添加新的文件,而不会提交到svn的版本控制中.
     * @param path
     */
    public static void add(File... paths) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager anthManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, anthManager);
        SVNWCClient wcClient = manager.getWCClient();
        wcClient.doAdd(paths, true, false, true, SVNDepth.fromRecurse(true), true, true, true);
    }

    /**
     * 获得本地工作目录文件的状态信息，与对应的版本做比较.
     * normal   正常,unversioned   未纳入版本控制,,modified   本地修改,conflicted  冲突,missing  直接删除文件,deleted  本地版本库删除
     * @param file  本地工作目录文件
     * @param version 要对比的版本
     * @return  返回svn状态信息SVNStatus对象,对应每一个文件.
     * @throws SVNException
     */
    public static ArrayList<SVNStatus> doStatus(File file, SVNRevision version) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager anthManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, anthManager);
        SVNStatusClient status = manager.getStatusClient();
        final ArrayList<SVNStatus> list = new ArrayList<SVNStatus>();
        status.doStatus(file, version, SVNDepth.INFINITY, true, true, true, true, new ISVNStatusHandler() {

            public void handleStatus(SVNStatus status) throws SVNException {
                list.add(status);
            }
        }, null);
        return list;
    }

    /**
     * 更新本地工作目录
     * @param version   版本号,可以为历史版本  -1为更新到最新版本
     * @param paths    本地svn工作目录
     * @param conflictHandler  冲突控制 (如果为空将会是默认冲突解决方案)
     * @return 返回更新的详细信息,包含每一个文件信息.
     * @throws SVNException
     */
    public static ArrayList<SVNEvent> doUpdate(long version, ISVNConflictHandler conflictHandler, File... paths) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager anthManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, anthManager);
        SVNUpdateClient update = manager.getUpdateClient();
        SVNEventHandler handler = new SVNEventHandler();
        update.setEventHandler(handler);
        DefaultSVNOptions option = (DefaultSVNOptions) update.getOptions();
        if (conflictHandler == null) {
            conflictHandler = new ConflictResolverHandler();
        }
        option.setConflictHandler(conflictHandler);
        update.doUpdate(paths, SVNRevision.create(version), SVNDepth.INFINITY, true, true);
        return handler.getUpdateInfo();
    }

    /**
     * 还原本地对于文件修改的内容,同时还会删除仅仅是添加到本地工作目录的文件和文件夹.
     * (这些文件和文件夹没有被提交到svn服务,该方法也不会从本地工作目录中删除这些文件,仅仅只是从本地工作目录版本控制中删除这些文件.add(File...files)的反向过程
     * @param paths 本地svn目录副本目录
     */
    public static void doRevert(File... paths) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager anthManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, anthManager);
        SVNWCClient wcclient = manager.getWCClient();
        wcclient.doRevert(paths, SVNDepth.INFINITY, null);
    }

    /**
     * 提交本地SVN工作目录,必须是svn服务器副本路径.如果要提交本地新增的目录或者文件,
     * 请先调用add(File...files)方法把这些文件加入到本地版本控制,然后doCommmit.
     * @param keepLock  是否保持锁
     * @param commitMessage  提交的信息
     * @param paths   提交的文件
     * @throws SVNException
     */
    public static SVNCommitInfo doCommit(boolean keepLock, String commitMessage, File... paths) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager anthManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, anthManager);
        SVNCommitClient commit = manager.getCommitClient();
        return commit.doCommit(paths, keepLock, commitMessage, null, null, true, true, SVNDepth.INFINITY);
    }

    /**
     * svn merge --dry-run -r:73:68 http://my.repository.com/my/project/trunk
     *  svn merge -r:73:68 http://my.repository.com/my/project/trunk
     * svn commit -m "Reverted to revision 68."
     * Merges changes from url1/revision1 to url2/revision2 into the working-copy path dstPath. 
     * @param url1
     * @param url2
     * @param version1   版本一,起始版本
     * @param version2   版本二,目标版本.一般为要回滚的版本
     * @param file
     * @throws SVNException
     */
    public static void doRevertToVersion(boolean keepLock, String commitMessage, String url1, String url2, SVNRevision version1, SVNRevision version2, File file) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager anthManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, anthManager);
        SVNDiffClient diffClient = manager.getDiffClient();
        diffClient.doMerge(SVNURL.parseURIDecoded(url1), version1, SVNURL.parseURIDecoded(url1), version2, file, SVNDepth.INFINITY, true, true, false, false);
        doCommit(keepLock, commitMessage, file);
    }

    /**
     * 回滚本地到历史,并提交是的svn服务器回滚,在调用此方法前尽量确保无本地修改.以免造成不必要的冲突.
     * 如果有冲突将无法提交成功,无法成功回滚服务器版本,需解决本地冲突之后再行提交回滚服务器版本.
     * @param  keepLock 是否保持锁定
     * @param  commitMessage 回滚时添加的信息
     * @param url  SVN URL
     * @param version  要回滚的版本
     * @param file     url对应的本地工作目录
     * @throws SVNException
     */
    public static void doRevertToVersion(boolean keepLock, String commitMessage, String url, long version, File file) throws SVNException {
        doRevertToVersion(keepLock, commitMessage, url, url, SVNRevision.HEAD, SVNRevision.create(version), file);
    }

    /**
     * 解决冲突,此方法仅仅是删除冲突信息,具体的冲突处理由用户和调用者决定.如果交给此方法默认处理是完全无视服务器的内容进行冲突解决.
     * @param file
     */
    public static void doResolve(File file) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager anthManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, anthManager);
        SVNWCClient wcClient = manager.getWCClient();
        wcClient.doResolve(file, SVNDepth.INFINITY, SVNConflictChoice.MINE_CONFLICT);
    }

    public static void main(String[] args) throws SVNException {
        //        add(new File("D:\\svntest\\trunk\\src"));
//        SVNCommitInfo info = doCommit(false, "提交", new File("D:\\svntest"));
//        SVNErrorMessage errorMessage = info.getErrorMessage();
//        if(errorMessage.getType()==SVNErrorMessage)
        //        doRevert(new File("D:\\svntest\\trunk\\src"));
//        modifyFileName("https://xuzh/svn/svntest", "a.txt", "b.txt", "修改");
//        doUpdate(-1, null, new File("D:\\svntest"));
//        doResolve(new File("D:\\svntest"));
        //        doRevertToVersion(false, "回滚", "https://xuzh/svn/svntest/", 2, new File("D:\\svntest"));
        doStatus(new File("D:\\svntest"), SVNRevision.HEAD);
    }

    /**
     * 导出历史版本.导出该文件不能作为本地工作目录副本,如果要获得工作目录副本只能checkout.
     * @param url  目标svn URL
     * @param detFile  要导出的目标文件
     * @param version    历史版本,只能是历史版本
     * @throws SVNException
     */
    public static void doExport(String url, File detFile, long version) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager anthManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, anthManager);
        SVNUpdateClient updateClient = manager.getUpdateClient();
        if (version == -1) {
            updateClient.doExport(SVNURL.parseURIDecoded(url), detFile, SVNRevision.create(-1), SVNRevision.HEAD, "doExport", true, SVNDepth.fromRecurse(true));
        } else {
            updateClient.doExport(SVNURL.parseURIDecoded(url), detFile, SVNRevision.create(-1), SVNRevision.create(version), "doExport", true, SVNDepth.fromRecurse(true));
        }
    }

    /**
     * 导入到svn目录中去,不能往已存在的svn目录中导入,而且该目录不应该是svn本地副本的路径.
     * @param importPath  要导入的文件目录
     * @param dstURL         目标svn URL
     * @param commitMessage   提交的版本信息
     * @return
     * @throws SVNException
     */
    public static SVNCommitInfo doImport(File importPath, String dstURL, String commitMessage) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, authManager);
        SVNCommitClient commitClient = manager.getCommitClient();
        return commitClient.doImport(importPath, SVNURL.parseURIDecoded(dstURL), commitMessage, new SVNProperties(), true, true, SVNDepth.fromRecurse(true));
    }

    /**
     * 签出到本地工作目录
     * @param file   本地工作目录文件
     * @param url     svn url
     * @param version   要签出的版本
     * @return
     * @throws SVNException
     */
    public static long doCheckOut(File file, String url, long version) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, authManager);
        SVNUpdateClient updataClient = manager.getUpdateClient();
        return updataClient.doCheckout(SVNURL.parseURIDecoded(url), file, SVNRevision.HEAD, SVNRevision.create(version), SVNDepth.fromRecurse(true), true);
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
        SVNProperties fileProperties = new SVNProperties();
        repository.getFile(filePath, version, fileProperties, outputStream);
    }

    /**
     * 回滚文件
     * @param filePath  文件路径
     * @param version  历史版本号
     * @param logMessage
     * @throws SVNException
     */
    public static void rollBackFileHistory(String filePath, long version, String logMessage) throws SVNException, FileNotFoundException, IOException {
        SVNRepository repository = SVNClientUtils.createSVNRepository(filePath);
        String path = "c:\\demo\\" + System.currentTimeMillis();
        File newFile = new File(path + "new" + ".svn");
        File oldFile = new File(path + "old" + ".svn");
        FileOutputStream newData = new FileOutputStream(newFile);
        FileOutputStream oldData = new FileOutputStream(oldFile);
        SVNClientUtils.getFile(repository, "", newData, version);
        SVNClientUtils.getFile(repository, "", oldData, -1);
        newData.flush();
        newData.close();
        oldData.flush();
        oldData.close();
        SVNClientUtils.modifyFile(repository, logMessage + filePath, "", new FileInputStream(oldFile), new FileInputStream(newFile));
        repository.closeSession();
    }

    /**
     * 回滚历史
     * @param filePath  文件名路径或者是目录路径
     * @param version    要回滚的版本
     * @param logMessage
     * @throws SVNException
     */
    public static void rollBackHistory(String filePath, long version, String logMessage) throws SVNException, FileNotFoundException, IOException {
        SVNRepository repository = SVNClientUtils.createSVNRepository(filePath);
        SVNNodeKind kind = repository.checkPath("", version);
        if (kind.equals(SVNNodeKind.DIR)) {
            rollBackDirHistory(filePath, version, logMessage);
        } else if (kind.equals(SVNNodeKind.FILE)) {
            rollBackFileHistory(filePath, version, logMessage);
        }
    }

    /**
     * 修改目录名
     * @param dirPath  要修改的目录的上层目录
     * @param oldName  旧文件夹名
     * @param newName  新文件夹名
     * @param logMessage
     * @throws SVNException
     */
    public static void modifyDirName(String dirPath, String oldName, String newName, String logMessage) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNRepository repository = SVNClientUtils.createSVNRepository(dirPath + "/" + oldName);
        long version = SVNClientUtils.getCurrentVersion(repository);
        repository.closeSession();
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, authManager);
        SVNUpdateClient updataClient = manager.getUpdateClient();
        File file = new File("c:\\demo\\" + System.currentTimeMillis());
        SVNCommitClient commit = manager.getCommitClient();
        updataClient.doExport(SVNURL.parseURIDecoded(dirPath + "/" + oldName), file, SVNRevision.create(version), SVNRevision.HEAD, dirPath, true, SVNDepth.INFINITY);
        commit.doDelete(new SVNURL[]{SVNURL.parseURIDecoded(dirPath + "/" + oldName)}, logMessage + dirPath);
        commit.doMkDir(new SVNURL[]{SVNURL.parseURIDecoded(dirPath + "/" + newName)}, logMessage + dirPath);
        commit.doImport(file, SVNURL.parseURIDecoded(dirPath + "/" + newName), logMessage, new SVNProperties(), true, true, SVNDepth.INFINITY);
        file.delete();
    }

    /**
     * 修改文件名
     * @param dirPath   文件所在的目录
     * @param oldName   旧文件名
     * @param newName   新文件名
     * @param logMessage
     * @throws SVNException
     */
    public static void modifyFileName(String dirPath, String oldName, String newName, String logMessage) throws SVNException {
        SVNRepository repository = SVNClientUtils.createSVNRepository(dirPath + "/" + oldName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SVNClientUtils.getFile(repository, "", out, -1);
        ISVNEditor editor = repository.getCommitEditor(logMessage, null);
        SVNClientUtils.deleteDir(editor, "");
        repository.closeSession();
        repository = SVNClientUtils.createSVNRepository(dirPath);
        SVNClientUtils.addFile(repository, logMessage, dirPath, newName, out.toByteArray());
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
    public static void rollBackDirHistory(String dirPath, long version, String logMessage) throws SVNException {
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
        SVNClientManager manager = SVNClientManager.newInstance(options, authManager);
        SVNUpdateClient updataClient = manager.getUpdateClient();
        File file = new File("c:\\demo\\" + System.currentTimeMillis());
        SVNCommitClient commit = manager.getCommitClient();
        updataClient.doExport(SVNURL.parseURIDecoded(dirPath), file, SVNRevision.create(-1), SVNRevision.create(version), dirPath, true, SVNDepth.fromRecurse(true));
        if (!dirPath.equals(url)) {
            commit.doDelete(new SVNURL[]{SVNURL.parseURIDecoded(dirPath)}, logMessage + "回滚到历史版本删除目录" + dirPath);
            commit.doMkDir(new SVNURL[]{SVNURL.parseURIDecoded(dirPath)}, logMessage + "回滚到历史版本添加目录" + dirPath);
        }
        commit.doImport(file, SVNURL.parseURIDecoded(dirPath), logMessage, new SVNProperties(), true, true, SVNDepth.fromRecurse(true));
        file.delete();
    }

    /**
     * 删除当前版本filePath目录下所有目录和文件,只在删除根目录时使用
     * @param filePath
     */
    public static void removeAllDir(String filePath) throws SVNException {
        ArrayList list = new ArrayList();
        SVNRepository repository = SVNClientUtils.createSVNRepository(filePath);
        repository.getDir("", -1, new SVNProperties(), list);
        for (Object obj : list) {
            SVNDirEntry entry = (SVNDirEntry) obj;
            try {
                ISVNEditor editor = repository.getCommitEditor("回滚到历史版本删除目录" + entry.getName(), null);
                deleteDir(editor, entry.getName());
            } catch (Exception e) {
            }
        }
        repository.closeSession();
    }

    private static SVNCommitInfo deleteDir(ISVNEditor editor, String dirPath) throws SVNException {
        editor.openRoot(-1);
        editor.deleteEntry(dirPath, -1);
        editor.closeDir();
        return editor.closeEdit();
    }

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
    public static SVNCommitInfo addFile(SVNRepository repository, String logMessage, String dirPath,
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

    /**
     * clear repository.
     * @param repository
     */
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
    public static SVNCommitInfo addDir(SVNRepository repository, String logMessage,
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

    public static long getCurrentVersion(SVNRepository repository) throws SVNException {
        return repository.getLatestRevision();
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
        return editor;
    }

    /**
     * 添加目录
     * @param url   当前要添加目录的url
     * @param logMessage
     * @param dirPath 目录可以包含已经存在的目录
     * @return
     */
    public static void addAllDir(String logMessage, String dirPath) {
        String[] dirs = dirPath.split("/");
        dirs = SVNClientUtils.getValidValue(dirs);
        String temp = url;
        for (int i = 0; i < dirs.length; i++) {
            try {
                addDir(temp, logMessage, dirs[i]);
                temp = temp + "/" + dirs[i];
            } catch (SVNException ex) {
                Logger.getLogger(SVNClientUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * 添加目录
     * @param url  当前要添加目录的url
     * @param logMessage
     * @param dirPath  目录名,只能是单个目录名
     * @return
     * @throws SVNException
     */
    private static void addDir(String url, String logMessage, String dirPath) throws SVNException {
        SVNRepository repository = SVNClientUtils.createSVNRepository(url);
        SVNNodeKind kind = repository.checkPath(dirPath, -1);
        ISVNEditor commit = repository.getCommitEditor(logMessage, null);
        commit.openRoot(-1);
        if (kind.equals(SVNNodeKind.NONE)) {
            commit.addDir(dirPath, null, -1);
            commit.closeDir();
            commit.closeEdit();
            System.out.println(commit.getClass());
        }
        repository.closeSession();
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
        for (int i = 0; i < paths.size(); i++) {
            String str = paths.get(i);
            String[] values = str.split("/");
            values = getValidValue(values);
            for (int j = 0; j
                    < values.length; j++) {
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
        for (int i = 0; i
                < list.size(); i++) {
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
            for (int i = 0; i < nodeList.get(0).size(); i++) {
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
        for (int i = 0; i < values.length; i++) {
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

    public static class FileNodeNode extends DefaultMutableTreeNode {

        public File file;

        public FileNodeNode(File file) {
            this.file = file;
        }

        @Override
        public boolean isLeaf() {
            return file.isFile();
        }

        @Override
        public String toString() {
            return file.getName();
        }
    }

    public static DefaultMutableTreeNode createFileTreeNodel(File file) {
        FileNodeNode node = new FileNodeNode(file);
        if (file.isFile() && !file.isHidden()) {
            return node;
        } else {
            File[] files = file.listFiles();
            for (File filetemp : files) {
                if (!filetemp.isHidden()) {
                    DefaultMutableTreeNode temp = createFileTreeNodel(filetemp);
                    if (temp != null) {
                        node.add(temp);
                    }
                }
            }
        }
        return node;
    }

    private static class ConflictResolverHandler implements ISVNConflictHandler {

        public SVNConflictResult handleConflict(SVNConflictDescription conflictDescription) throws SVNException {
            SVNConflictReason reason = conflictDescription.getConflictReason();
            SVNMergeFileSet mergeFiles = conflictDescription.getMergeFiles();
            File localFile = mergeFiles.getLocalFile();
            File repositoryFile = mergeFiles.getRepositoryFile();
            new SVNDialog(localFile, repositoryFile).setVisible(true);
            SVNConflictChoice choice = SVNConflictChoice.THEIRS_FULL;
            if (reason == SVNConflictReason.EDITED) {
                //If the reason why conflict occurred is local edits, chose local version of the file
                //Otherwise the repository version of the file will be chosen.
                choice = SVNConflictChoice.MINE_FULL;
            }
            System.out.println("Automatically resolving conflict for " + mergeFiles.getWCFile()
                    + ", choosing " + (choice == SVNConflictChoice.MINE_FULL ? "local file" : "repository file"));
            return new SVNConflictResult(choice, mergeFiles.getResultFile());
        }
    }

    public static class SVNEventHandler implements ISVNEventHandler {

        private ArrayList<SVNEvent> updateInfo = new ArrayList<SVNEvent>();
        /*
         * progress  is  currently  reserved  for future purposes and now is always
         * ISVNEventHandler.UNKNOWN
         */

        public void handleEvent(SVNEvent event, double progress) {
            getUpdateInfo().add(event);
            /*
             * Gets the current action. An action is represented by SVNEventAction.
             * In case of an update an  action  can  be  determined  via  comparing
             * SVNEvent.getAction() and SVNEventAction.UPDATE_-like constants.
             */
            SVNEventAction action = event.getAction();

            String pathChangeType = " ";
            if (action == SVNEventAction.UPDATE_ADD) {
                /*
                 * the item was added
                 */
                pathChangeType = "A";
            } else if (action == SVNEventAction.UPDATE_DELETE) {
                /*
                 * the item was deleted
                 */
                pathChangeType = "D";
            } else if (action == SVNEventAction.UPDATE_UPDATE) {
                /*
                 * Find out in details what  state the item is (after  having  been
                 * updated).
                 *
                 * Gets  the  status  of  file/directory  item   contents.  It   is
                 * SVNStatusType  who contains information on the state of an item.
                 */
                SVNStatusType contentsStatus = event.getContentsStatus();
                if (contentsStatus == SVNStatusType.CHANGED) {
                    /*
                     * the  item  was  modified in the repository (got  the changes
                     * from the repository
                     */
                    pathChangeType = "U";
                } else if (contentsStatus == SVNStatusType.CONFLICTED) {
                    /*
                     * The file item is in  a  state  of Conflict. That is, changes
                     * received from the repository during an update, overlap  with
                     * local changes the user has in his working copy.
                     */
                    pathChangeType = "C";
                } else if (contentsStatus == SVNStatusType.MERGED) {
                    /*
                     * The file item was merGed (those  changes that came from  the
                     * repository  did  not  overlap local changes and were  merged
                     * into the file).
                     */
                    pathChangeType = "G";
                }
            } else if (action == SVNEventAction.UPDATE_EXTERNAL) {
                /*for externals definitions*/
                System.out.println("Fetching external item into '"
                        + event.getFile().getAbsolutePath() + "'");
                System.out.println("External at revision " + event.getRevision());
                return;
            } else if (action == SVNEventAction.UPDATE_COMPLETED) {
                /*
                 * Updating the working copy is completed. Prints out the revision.
                 */
                System.out.println("At revision " + event.getRevision());
                return;
            } else if (action == SVNEventAction.ADD) {
                System.out.println("A     " + event.getFile());
                return;
            } else if (action == SVNEventAction.DELETE) {
                System.out.println("D     " + event.getFile());
                return;
            } else if (action == SVNEventAction.LOCKED) {
                System.out.println("L     " + event.getFile());
                return;
            } else if (action == SVNEventAction.LOCK_FAILED) {
                System.out.println("failed to lock    " + event.getFile());
                return;
            }

            /*
             * Now getting the status of properties of an item. SVNStatusType  also
             * contains information on the properties state.
             */
            SVNStatusType propertiesStatus = event.getPropertiesStatus();
            /*
             * At first consider properties are normal (unchanged).
             */
            String propertiesChangeType = " ";
            if (propertiesStatus == SVNStatusType.CHANGED) {
                /*
                 * Properties were updated.
                 */
                propertiesChangeType = "U";
            } else if (propertiesStatus == SVNStatusType.CONFLICTED) {
                /*
                 * Properties are in conflict with the repository.
                 */
                propertiesChangeType = "C";
            } else if (propertiesStatus == SVNStatusType.MERGED) {
                /*
                 * Properties that came from the repository were  merged  with  the
                 * local ones.
                 */
                propertiesChangeType = "G";
            }

            /*
             * Gets the status of the lock.
             */
            String lockLabel = " ";
            SVNStatusType lockType = event.getLockStatus();

            if (lockType == SVNStatusType.LOCK_UNLOCKED) {
                /*
                 * The lock is broken by someone.
                 */
                lockLabel = "B";
            }

            System.out.println(pathChangeType
                    + propertiesChangeType
                    + lockLabel
                    + "       "
                    + event.getFile());
        }

        /*
         * Should be implemented to check if the current operation is cancelled. If
         * it is, this method should throw an SVNCancelException.
         */
        public void checkCancelled() throws SVNCancelException {
        }

        /**
         * @return the updateInfo
         */
        public ArrayList<SVNEvent> getUpdateInfo() {
            return updateInfo;
        }
    }
}
