/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.XMLEncoder;
import javax.swing.ImageIcon;
import node.BlockBeanNodeElement;
import node.CaseBeanNodeElement;
import node.CheckPointBeanNodeElement;
import node.StartBeanNodeElement;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import shape.BeanNodeElement;
import testframe.BeanNodeGraphView.ConnectionContent;

/**
 *
 * @author admin
 */
public class WidgetUtils {

    public static int THICKNESS = 3, WIDTH = 150, HEIGHT = 70, SPACE = 10, GRID_SPACE = 2;
    public static Color color = Color.GREEN;
    public static Border ROUNDED_BORDER = BorderFactory.createRoundedBorder(5, 5, Color.yellow, new Color(52, 124, 150));
    public static ImageIcon START_IMAGE = new javax.swing.ImageIcon(Class.class.getResource("/icon/start.PNG"));
    public static ImageIcon END_IMAGE = new javax.swing.ImageIcon(Class.class.getResource("/icon/end.PNG"));
    public static ImageIcon LOOP_IMAGE = new javax.swing.ImageIcon(Class.class.getResource("/icon/loop.PNG"));
    public static ImageIcon MESSAGE_IMAGE = new javax.swing.ImageIcon(Class.class.getResource("/icon/message.PNG"));

    public static Widget createIconWidget(Scene scene, ImageIcon icon, BeanNodeElement node) {
        IconMessageWidget widget = new IconMessageWidget(scene, icon, node);
        widget.setImage(icon.getImage());
        widget.setBorder(ROUNDED_BORDER);
        widget.setPreferredSize(WidgetUtils.getSize(1, 1));
        widget.setOpaque(true);
        return widget;
    }

    public static String getCaseString(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '<') {
                result.append("&lt;");
            } else if (str.charAt(i) == '>') {
                result.append("&gt;");
            } else {
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }

    public static String getToolTipString(String str) {
        String[] values = str.split("\n");
        String result = "<html> ";
        for (int i = 0; i < values.length; i++) {
            result = result + values[i] + "<P>";
        }
        result = result + "</html> ";
        return result;
    }

    public static Point getPosition(Point point) {
        int x = point.x / WidgetUtils.WIDTH;
        int y = point.y / WidgetUtils.HEIGHT;
        point.x = x * WidgetUtils.WIDTH + WidgetUtils.SPACE + WidgetUtils.GRID_SPACE;
        point.y = y * WidgetUtils.HEIGHT + WidgetUtils.SPACE + WidgetUtils.GRID_SPACE;
        return point;
    }

    public static Dimension getSize(ProgramNodeWidget widget) {
        Dimension size = new Dimension();
        size.width = WidgetUtils.WIDTH * widget.getColumnCount() - 2 * WidgetUtils.SPACE + 2;
        size.height = WidgetUtils.HEIGHT * widget.getRowCount() - 2 * WidgetUtils.SPACE + 2;
        return size;
    }

    public static Dimension getSize(int row, int column) {
        Dimension size = new Dimension();
        size.width = WidgetUtils.WIDTH * column - 2 * WidgetUtils.SPACE + 2;
        size.height = WidgetUtils.HEIGHT * row - 2 * WidgetUtils.SPACE + 2;
        return size;
    }
}
