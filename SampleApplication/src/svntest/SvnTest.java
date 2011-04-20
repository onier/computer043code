/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package svntest;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

/**
 *
 * @author Administrator
 */
public class SvnTest {

    public static void main(String[] args) throws SVNException {
        DAVRepositoryFactory.setup();
        SVNRepository repository = DAVRepositoryFactory.create(SVNURL.parseURIEncoded("https://xuzh/svn/SVNTest"));
//        SVNRepositoryFactoryImpl.create(SVNURL.parseURIEncoded("https://xuzh/svn/SVNTest"));
//        FSRepositoryFactory.create(SVNURL.parseURIEncoded("https://xuzh/svn/SVNTest"));
    }
}
