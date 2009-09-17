/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Button;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

public class CursorTest extends Frame {

    public CursorTest() {
        Button button = new Button("Click");
        button.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
        button.setCursor(createCursor());
        add(button);
        setVisible(true);
        setSize(400, 400);
    }

    public static Cursor createCursor() {
        String fileName = "D:\\Check\\PLANS-Check\\resources\\cursors\\penInsertToolCursors.pngg";
        Image cursor = Toolkit.getDefaultToolkit().createImage(fileName);
        return Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(10, 10), "newCursor");
    }

    public static void main(String[] args) {
        new CursorTest();
    }
}


