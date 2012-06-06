/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcom;

import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.JComException;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;
import org.openide.util.Exceptions;

/**
 *
 * @author zhenhai.xu
 */
public class ComTest {

    public static void main(String[] args) {
        try {
            ReleaseManager rm = new ReleaseManager();
            IDispatch wdApp = new IDispatch(rm, "Outlook.application");
            wdApp.put("Visible", new Boolean(true));
        } catch (JComException ex) {
            ex.printStackTrace();
        }
    }
}
