/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ComboBox;

import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

public class TestCombo {

    public static void main(String[] args) {
        JFrame aFrame = new JFrame();
        myCombo JComboBox1 = new myCombo();
        JComboBox1.setPreferredSize(new Dimension(20, 20));
        JComboBox1.setMaximumSize(new Dimension(30, 20));
        String[] aArr = {"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "BBAAAAAAAAAAA", "CCCCC", "DDDDDDDDDDDDD", "EEEEEEEEEEEE", "FFFFFFFFFFFFFFFFFFFFFFFFFFFF", "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "BBAAAAAAAAAAA", "CCCCC", "DDDDDDDDDDDDD", "EEEEEEEEEEEE", "FFFFFFFFFFFFFFFFFFFFFFFFFFFF", "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG"};
        for (int i = 0; i < aArr.length; ++i) {
            JComboBox1.addItem(aArr[i]);
        }
        aFrame.getContentPane().add(JComboBox1);
        aFrame.getContentPane().setSize(100, 100);
        aFrame.pack();
        aFrame.show();
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
