/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package svntest;
/*              == ==================================================================
 * Copyright (c) 2004-2009 TMate Software Ltd.  All rights reserved.
 *
 * This software is licensed as described in the file COPYING, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://svnkit.com/license.html
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 * ====================================================================
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.tmatesoft.svn.core.SVNCommitInfo;

import org.tmatesoft.svn.core.SVNErrorCode;

import org.tmatesoft.svn.core.SVNErrorMessage;

import org.tmatesoft.svn.core.SVNException;

import org.tmatesoft.svn.core.SVNNodeKind;

import org.tmatesoft.svn.core.SVNURL;

import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;

import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;

import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;

import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;

import org.tmatesoft.svn.core.io.ISVNEditor;

import org.tmatesoft.svn.core.io.SVNRepository;

import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;

import org.tmatesoft.svn.core.wc.SVNWCUtil;

/*
 * This is an example of how to commit several types of changes to a repository:
 *  - a new directory with a file,
 *  - modification to anexisting file,
 *  - copying a directory into a branch,
 *  - deletion of the directory and its entries.
 *
 * Main aspects of performing a commit with the help of ISVNEditor:
 * 0)initialize the library (this is done in setupLibrary() method);
 *
 * 1)create an SVNRepository driver for a particular  repository  location, that
 * will be the root directory for committing - that is all paths that are  being
 * committed will be below that root;
 *
 * 2)provide user's authentication information - name/password, since committing
 * generally requires authentication;
 *
 * 3)"ask" your SVNRepository for a commit editor:
 *
 *     ISVNCommitEditor editor = SVNRepository.getCommitEditor();
 *
 * 4)"edit"  a repository - perform a sequence of edit calls (for
 * example, here you "say" to the server that you have added such-and-such a new
 * directory at such-and-such a path as well as a new file). First of all,
 * ISVNEditor.openRoot()  is called to 'open' the root directory;
 *
 * 5)at last you close the editor with the  ISVNEditor.closeEdit()  method  that
 * fixes your modificaions in the repository finalizing the commit.
 *
 * For  each  commit  a  new  ISVNEditor is required - that is after having been
 * closed the editor can no longer be used!
 *
 * This example can be run for a locally installed Subversion repository via the
 * svn:// protocol. This is how you can do it:
 *
 * 1)after   you   install  the   Subversion  pack (available  for  download  at
 * http://subversion.tigris.org)  you  should  create  a  new  repository in  a
 * directory, like this (in a command line under a Windows OS):
 *
 * >svnadmin create X:\path\to\rep
 *
 * 2)after the repository is created you can add a new account: navigate to your
 * repository root (X:\path\to\rep\),  then  move  to  \conf  and  open the file
 * 'passwd'. In the file you'll see the section [users]. Uncomment it and add  a
 * new account below the section name, like:
 *
 * [users]
 * userName = userPassword.
 *
 * In the program you may further use this account as user's credentials.
 *
 * 3)the next step is to launch the custom Subversion  server  (svnserve)  in  a
 * background mode for the just created repository:
 *
 * >svnserve -d -r X:\path\to
 *
 * That's all. The repository is now available via svn://localhost/rep.
 *
 */
public class Commit {

    public static void main(String[] args) {
        /*
         * Initialize the library. It must be done before calling any
         * method of the library.
         */
        setupLibrary();

        /*
         * Run commit example and process error if any.
         */
        try {
            commitExample();
        } catch (SVNException e) {
            SVNErrorMessage err = e.getErrorMessage();
            /*
             * Display all tree of error messages.
             * Utility method SVNErrorMessage.getFullMessage() may be used instead of the loop.
             */
            while (err != null) {
                System.err.println(err.getErrorCode().getCode() + " : " + err.getMessage());
                err = err.getChildErrorMessage();
            }
            System.exit(1);
        }
        System.exit(0);
    }

    private static void commitExample() throws SVNException {
        /*
         * URL that points to repository.
         */
        SVNURL url = SVNURL.parseURIEncoded("https://xuzh/svn/SVNDemo/");
        String userName = "test";
        String userPassword = "1111";


        byte[] contents = "This 的积分卡拉斯将放宽拉近风口浪尖啦江苏大丰健康啊了经多方了解啊双方均按警方的卡拉加快了福建爱欧日哦诶如饿哦案件进口拉进来就死定了附近拉近覅uioweuriojajfksjflkajldjf 双方就俺看来减肥了卡斯就付款拉近了的解放辣椒的理解 a new file".getBytes();
        byte[] modifiedContents = "Thissdsdsa地方撒打发  is the same file but modified a little.".getBytes();


        SVNRepository repository = SVNRepositoryFactory.create(url);

        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, userPassword);
        repository.setAuthenticationManager(authManager);

        SVNNodeKind nodeKind = repository.checkPath("", -1);

        if (nodeKind == SVNNodeKind.NONE) {
            SVNErrorMessage err = SVNErrorMessage.create(SVNErrorCode.UNKNOWN, "No entry at URL ''{0}''", url);
            throw new SVNException(err);
        } else if (nodeKind == SVNNodeKind.FILE) {
            SVNErrorMessage err = SVNErrorMessage.create(SVNErrorCode.UNKNOWN, "Entry at URL ''{0}'' is a file while directory was expected", url);
            throw new SVNException(err);
        }

        long latestRevision = repository.getLatestRevision();
        System.out.println("Repository latest revision (before committing): " + latestRevision);
        ISVNEditor editor;
        SVNCommitInfo commitInfo;
//        editor = repository.getCommitEditor("directory and file added", null);
//
//        commitInfo = addDir(editor, "test/sample", "test/sample/file1.txt", contents);
//        System.out.println("The directory was added: " + commitInfo);
//
//        editor = repository.getCommitEditor("file contents changed", null);
//        commitInfo = modifyFile(editor, "test", "test/file.txt", contents, modifiedContents);
//        System.out.println("The file was changed: " + commitInfo);
//
//
//        String absoluteSrcPath = repository.getRepositoryPath("test");
//        long srcRevision = repository.getLatestRevision();
//
//        editor = repository.getCommitEditor("directory copied", null);
//
//        commitInfo = copyDir(editor, absoluteSrcPath, "test2", srcRevision);
//        System.out.println("The directory was copied: " + commitInfo);
//
        editor = repository.getCommitEditor("directory deleted", null);
        commitInfo = deleteDir(editor, "B");
        System.out.println("");
        System.out.println("The directory was deleted: " + commitInfo);
//
//        editor = repository.getCommitEditor("copied directory deleted", null);
//        commitInfo = deleteDir(editor, "/JavaApplication/src/jp");
//        System.out.println("The copied directory was deleted: " + commitInfo);
//
//        latestRevision = repository.getLatestRevision();
//        System.out.println("Repository latest revision (after committing): " + latestRevision);
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
    private static SVNCommitInfo addDir(ISVNEditor editor, String dirPath,
            String filePath, byte[] data) throws SVNException {
        editor.openRoot(-1);
        String[] dirs = dirPath.split("/");
        for (int i = 0; i < dirs.length; i++) {
            if (dirs[i] != null && dirs[i].length() > 0) {
                editor.addDir(dirs[i], null, -1);
            }
        }
        editor.addFile(filePath, null, -1);
        editor.applyTextDelta(filePath, null);
        SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
        String checksum = deltaGenerator.sendDelta(filePath, new ByteArrayInputStream(data), editor, true);
        editor.closeFile(filePath, checksum);
        editor.closeDir();
        return editor.closeEdit();
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
    private static SVNCommitInfo addDir(ISVNEditor editor, String dirPath,
            String filePath, File file) throws SVNException, FileNotFoundException {
        editor.openRoot(-1);
        editor.addDir(dirPath, null, -1);
        editor.addFile(filePath, null, -1);
        editor.applyTextDelta(filePath, null);
        SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
        String checksum = deltaGenerator.sendDelta(filePath, new FileInputStream(file), editor, true);
        editor.closeFile(filePath, checksum);
        editor.closeDir();
        editor.closeDir();
        return editor.closeEdit();
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
    private static SVNCommitInfo modifyFile(ISVNEditor editor, String dirPath,
            String filePath, byte[] oldData, byte[] newData) throws SVNException {
        editor.openRoot(-1);
        editor.openDir(dirPath, -1);
        editor.openFile(filePath, -1);
        editor.applyTextDelta(filePath, null);

        SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
        String checksum = deltaGenerator.sendDelta(filePath, new ByteArrayInputStream(oldData), 0, new ByteArrayInputStream(newData), editor, true);

        editor.closeFile(filePath, checksum);
        editor.closeDir();

        editor.closeDir();
        return editor.closeEdit();
    }

    private static SVNCommitInfo deleteDir(ISVNEditor editor, String dirPath) throws SVNException {
        editor.openRoot(-1);
        editor.deleteEntry(dirPath, -1);
        editor.closeDir();
        return editor.closeEdit();
    }

    private static SVNCommitInfo copyDir(ISVNEditor editor, String srcDirPath,
            String dstDirPath, long revision) throws SVNException {
        editor.openRoot(-1);
        editor.addDir(dstDirPath, srcDirPath, revision);
        editor.closeDir();
        editor.closeDir();
        return editor.closeEdit();
    }

    private static void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }
}
