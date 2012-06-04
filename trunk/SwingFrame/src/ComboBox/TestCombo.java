/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ComboBox;

import java.awt.Dimension;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import org.openide.util.Exceptions;

public class TestCombo {

    static class T1 {

        private int i = getJ();
        private int j = 11;
        private ArrayList list = new ArrayList();

        public T1() {
        }

        private int getJ() {
            System.out.println(list);
            return list.size();
        }
    }

    /**
    08
     * @param args
    09
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        T1 t = new T1();
        System.out.println(t.i + " " + t.j);
    }

    public static void main1(String[] args) {
        try {
            //        JFrame aFrame = new JFrame();
            //        myCombo JComboBox1 = new myCombo();
            //        JComboBox1.setPreferredSize(new Dimension(20, 20));
            //        JComboBox1.setMaximumSize(new Dimension(30, 20));
            //        String[] aArr = {"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "BBAAAAAAAAAAA", "CCCCC", "DDDDDDDDDDDDD", "EEEEEEEEEEEE", "FFFFFFFFFFFFFFFFFFFFFFFFFFFF", "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "BBAAAAAAAAAAA", "CCCCC", "DDDDDDDDDDDDD", "EEEEEEEEEEEE", "FFFFFFFFFFFFFFFFFFFFFFFFFFFF", "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG"};
            //        for (int i = 0; i < aArr.length; ++i) {
            //            JComboBox1.addItem(aArr[i]);
            //        }
            //        aFrame.getContentPane().add(JComboBox1);
            //        aFrame.getContentPane().setSize(100, 100);
            //        aFrame.pack();
            //        aFrame.show();
            URL url = new URL("http://www.aol.com");
            URLConnection connection = url.openConnection();
            InputStream input = connection.getInputStream();
            byte[] bs = new byte[input.available()];
            input.read(bs);
            System.out.println(new String(bs));
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}

class myCombo extends JComboBox {

    public myCombo() {
        super();
        setUI(new myComboUI());
    }//end of default constructor

    public class myComboUI extends BasicComboBoxUI {

        protected ComboPopup createPopup() {
            BasicComboPopup popup = new BasicComboPopup(comboBox) {

                protected JScrollPane createScroller() {
                    return new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                }//end of method createScroller
            };
            return popup;
        }//end of method createPopup
    }//end of inner class myComboUI
}
