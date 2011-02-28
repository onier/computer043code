/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import widget.WidgetUtils;

/**
 *
 * @author admin
 */
public class LocaleConnectionWidget extends Widget {

    public LocaleConnectionWidget(Scene scene) {
        super(scene);
        this.setPreferredSize(WidgetUtils.getSize(1, 1));
//        this.setOpaque(true);
//        this.setBackground(Color.RED);
    }

    @Override
    protected void paintWidget() {
        Graphics2D gr = getGraphics();
        gr.setColor(getForeground());
        GeneralPath path = new GeneralPath();
        Dimension size = this.getPreferredSize();
        path.moveTo(size.width / 2, 0);
        path.lineTo(size.width / 2, size.height);
        gr.draw(path);
        path = new GeneralPath();
        path.moveTo(size.width / 2, size.height);
        path.lineTo(size.width / 2 - 4, size.height - 12);
        path.lineTo(size.width / 2 + 4, size.height - 12);
        gr.setColor(Color.BLACK);
        gr.fill(path);
    }
}
