/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.awt.event.*;
import java.applet.*;

public class AvauleanWars extends JApplet {

    MapWindow map;
    JScrollPane scrollbars;
    OptionBar options;
    Container applet;

    public void init() {
        applet = this.getContentPane();
        applet.setLayout(null);

        map = new MapWindow();

        scrollbars = new JScrollPane(map,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollbars.setBounds(0, 20, getSize().width, getSize().height - 20);

        options = new OptionBar();
        options.setBounds(0, 0, getSize().width, 20);

        applet.add(scrollbars, "South");
        applet.add(options, "North");
    }

    class MapWindow extends JPanel {

        Image images[];
        Tile tiles[];

        public MapWindow() {
            setLayout(null);

            images = new Image[1];
            images[0] = getImage(getDocumentBase(), "tile_1.gif");

            tiles = new Tile[50];
            for (int x = 0; x < 50; x++) {
                tiles[x] = new Tile();
                tiles[x].setImage(images[0]);
                tiles[x].setBounds(x * 33, 0, 32, 32);
                add(tiles[x]);
            }
        }
    }

    class Tile extends JPanel {

        Image img;

        public Tile() {
            super();
        }

        public void setImage(Image tile) {
            img = tile;
        }

        public Image getImage() {
            return img;
        }

        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(img, 0, 0, this);
        }
    }

    class OptionBar extends JToolBar {

        public OptionBar() {
            super();
        }
    }
}

