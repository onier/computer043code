/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * $Id: MultiSplitPaneDemo.java 1164 2009-11-03 04:22:00Z kschaefe $
 *
 * Copyright 2009 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.jdesktop.swingx.demos.multisplitpane;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;

import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;

import org.jdesktop.application.Application;

import com.sun.swingset3.DemoProperties;
import javax.swing.JInternalFrame;

/**
 * A demo for the {@code JXMultiSplitPane}.
 *
 * @author Karl George Schaefer, Luan O'Carroll
 */
@DemoProperties(value = "JXMultiSplitPane Demo",
category = "Containers",
description = "Demonstrates JXMultiSplitPane, a container that allows arbitrary resizing children.",
sourceFiles = {
    "org/jdesktop/swingx/demos/multisplitpane/MultiSplitPaneDemo.java",
    "org/jdesktop/swingx/demos/multisplitpane/resources/MultiSplitPaneDemo.properties",
    "org/jdesktop/swingx/demos/multisplitpane/resources/MultiSplitPaneDemo.html",
    "org/jdesktop/swingx/demos/multisplitpane/resources/images/MultiSplitPaneDemo.png"
})
@SuppressWarnings("serial")
public class MultiSplitPaneDemo extends JPanel {

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                JFrame frame = new JFrame(MultiSplitPaneDemo.class.getAnnotation(DemoProperties.class).value());

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new MultiSplitPaneDemo());
                frame.setPreferredSize(new Dimension(800, 600));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public MultiSplitPaneDemo() {
        createMultiSplitPaneDemo();

        Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);

        bind();
    }

    public static JInternalFrame createInternalFrame() {
        JInternalFrame iframe1 = new JInternalFrame();
        iframe1.setVisible(true);
        iframe1.setIconifiable(true);
        iframe1.setMaximizable(true);
        iframe1.setResizable(true);
        return iframe1;
    }
    //TODO enable resource injection for the components in this demo

    private void createMultiSplitPaneDemo() {
        setLayout(new BorderLayout());
//
        JXMultiSplitPane msp = new JXMultiSplitPane();
//        JXMultiSplitPane msp1 = new JXMultiSplitPane();
//        String layout1 = "(Row(LEAF name=a)(LEAF name=b))";
//        MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel(layout1);
//        msp1.getMultiSplitLayout().setModel(modelRoot);
//        msp1.add(new JButton("a"), "a");
//        msp1.add(new JButton("b"), "b");
//        JXMultiSplitPane msp2 = new JXMultiSplitPane();
//        String layout2 = "(Row(LEAF name=c)(LEAF name=d))";
//        modelRoot = MultiSplitLayout.parseModel(layout2);
//        msp2.getMultiSplitLayout().setModel(modelRoot);
//        msp2.add(new JButton("c"), "c");
//        msp2.add(new JButton("d"), "d");
//        String layout = "(COLUMn(LEAF name=e)(LEAF name=f))";
//        modelRoot = MultiSplitLayout.parseModel(layout);
//        msp.getMultiSplitLayout().setModel(modelRoot);
//        msp.add(msp1, "e");
//        msp.add(msp2, "f");
        String layoutDef = "";
        layoutDef = "(COLUMN "
                + "(ROW (COLUMN (LEAF name=left.top ) (LEAF name=left.middle))(LEAF name=editor )) "
                + "(LEAF name=bottom ))";
//        layoutDef = "(ROW (LEAF name=left weight=0.2)(LEAF name=middle weight=0.3) (LEAF name=right weight=0.5))";
//        layoutDef = "(COLUMN(LEAF name=top weight=0.3)(LEAF name=middle weight=0.3)(LEAF name=bottom weight=0.4))";
//        layoutDef = "(COLUMN "
//                + "(ROW (COLUMN (LEAF name=left.top ) (LEAF name=left.middle))) "
//                + "(ROW(LEAF name=bottom )(LEAF name=editor )))";
        MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel(layoutDef);
        msp.getMultiSplitLayout().setModel(modelRoot);

        msp.add(createInternalFrame(), "left.top");
        msp.add(createInternalFrame(), "left.middle");
        msp.add(createInternalFrame(), "editor");
        msp.add(createInternalFrame(), "bottom");

//        msp.add(new JButton("a"), "a");
//        msp.add(new JButton("b"), "b");
//        msp.add(new JButton("c"), "c");
//        msp.add(new JButton("d"), "d");
//        msp.add(new JButton("middle"), "middle");

        // ADDING A BORDER TO THE MULTISPLITPANE CAUSES ALL SORTS OF ISSUES
        msp.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        add(msp, BorderLayout.CENTER);
    }

    private void bind() {
        //no bindings
    }
}
