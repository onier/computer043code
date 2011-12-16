/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcom;

import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;

public class ExcelDemo {

    public static void main(String[] args) throws Exception {
        ReleaseManager rm = new ReleaseManager();
        try {

            System.out.println("EXCEL.Application");
            IDispatch xlApp = new IDispatch(rm, "Excel.Application");
            xlApp.put("Visible", new Boolean(true));
            IDispatch xlBooks = (IDispatch) xlApp.get("Workbooks");
            IDispatch xlBook = (IDispatch) xlBooks.method("Add", null);	// create new book
            IDispatch xlSheet = (IDispatch) xlApp.get("ActiveSheet");

            // set string to cell A1 .
            Object[] arglist = new Object[1];
            arglist[0] = "A1";
            IDispatch xlRange = (IDispatch) xlSheet.get("Range", arglist);
            xlRange.put("Value", "JCom 我来了");

            // copy cell from A1 to B2 .
            Object[] copyargs = new Object[1];
            copyargs[0] = (IDispatch) xlSheet.get("Range", new Object[]{"B2"});
            xlRange.method("Copy", copyargs);

            IDispatch xlRangeA1B2 = (IDispatch) xlSheet.get("Range", new Object[]{"A1:B2"});
            copyargs = new Object[1];
            copyargs[0] = (IDispatch) xlSheet.get("Range", new Object[]{"C1"});
            xlRangeA1B2.method("Copy", copyargs);

            copyargs = new Object[2];
            copyargs[0] = null;
            copyargs[1] = xlSheet;

            xlSheet.method("Copy", copyargs);

            IDispatch shapes = (IDispatch) xlSheet.get("Shapes");
            /**划线*/
            shapes.method("AddLine", new Object[]{10, 10, 200, 200});
            /**这个不是知道啥玩意反正就是画上去了*/
            shapes.method("AddCallout", new Object[]{4/**Callout类型,后面是两个点，详细请看API*/
                        , 40, 30, 80, 60});

            shapes.method("AddConnector", new Object[]{2/**连接线类型,后面是两个点，详细请看API*/
                        , 100, 120, 150, 150});

            shapes.method("AddFormControl", new Object[]{1, 100, 120, 50, 50});  /*check box*/

            shapes.method("AddLabel", new Object[]{5, 100, 180, 80, 10});  /*垂直Label*/

            shapes.method("AddTextbox", new Object[]{5, 100, 210, 80, 10});  /*垂直Text*/

            shapes.method("AddPicture", new Object[]{"C:/Documents and Settings/zhenhai.xu/My Documents/My Pictures/stiml_logo.png", -1, -1, 250, 250, 300, 300}); /*图片*/
            System.out.println("Hit [Enter] key to exit.");
            System.in.read();
            Object[] arglist3 = new Object[3];
            arglist3[0] = new Boolean(false);
            arglist3[1] = null;
            arglist3[2] = new Boolean(false);
            xlBook.method("Close", arglist3);
            xlApp.method("Quit", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rm.release();
        }
    }
}
