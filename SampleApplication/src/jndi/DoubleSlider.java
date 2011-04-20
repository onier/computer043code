/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jndi;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DoubleSlider extends JFrame {

    Box sliderBox = new Box(BoxLayout.Y_AXIS);
    JTextField showVal = new JTextField();
    ChangeListener listener;

    /**
     * Launch the application
     * @param args
     */
    public static void main(String args[]) {
        try {
            DoubleSlider frame = new DoubleSlider();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the frame
     */
    public DoubleSlider() {
        super();
        setBounds(100, 100, 500, 375);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        init();
//        final JButton button = new JButton();
//        button.setText("New JButton");
//        button.setBounds(10, 304, 472, 29);
//        getContentPane().add(button);
    }

    public void init() {
        listener = new ChangeListener() {

            public void stateChanged(ChangeEvent event) {
                //取出滑动条的值，并在文本中显示出来
                JSlider source = (JSlider) event.getSource();
                showVal.setText("当前滑动条的值为：" + source.getValue());
            }
        };

        JSlider slider1 = new JSlider();
        JSlider slider2 = new JSlider();
        addSlider(slider1, slider2, "普通滑动条");

//        add(sliderBox, BorderLayout.CENTER);
//        add(showVal, BorderLayout.SOUTH);
        showVal.setBounds(10, 304, 472, 29);
        add(showVal);
    }

    public void addSlider(JSlider slider1, JSlider slider2, String description) {
        slider1.addChangeListener(listener);
        slider1.setBounds(10, 78, 472, 29);
        slider1.setValue(5);
//        slider1.setPaintTrack(false);
        slider1.setOpaque(false);

        slider2.addChangeListener(listener);
        slider2.setBounds(10, 78, 472, 29);
        slider2.setValue(95);
//        slider2.setPaintTrack(false);
        slider2.setOpaque(false);

        add(slider1);
        add(slider2);
    }
}
