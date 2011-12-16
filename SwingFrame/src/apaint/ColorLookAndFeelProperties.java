/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 *
 * @author Zhenhai.Xu
 */
public class ColorLookAndFeelProperties {

    public static String TEXT_BORDER_COLOR_KEY = "TextFieldBorderColor";
    public static Color TEXT_BORDER_COLOR = new Color(171, 193, 222);
    public static String TEXT_BACKGROUND_COLOR_KEY = "TextFieldBackGroundKey";
    public static Color TEXT_BACKGROUND_COLOR = new Color(234, 242, 251);
    public static String TEXT_FOCUS_BORDER_COLOR_KEY = "TextFieldFocusBorderColor";
    public static Color TEXT_FOCUS_BORDER_COLOR = new Color(179, 199, 225);
    public static String TEXT_FOCUS_BACKGROUND_COLOR_KEY = "TextFieldFocusBackGroundKey";
    public static Color TEXT_FOCUS_BACKGROUND_COLOR = new Color(255, 255, 255);
    public static String TEXT_SLECT_COLOR_KEY = "textFieldSelectColor";
    public static Color TEXT_SLECT_COLOR = new Color(174, 196, 232);
    /**label*/
    public static String LABEL_BACKGROUND_KEY = "labelBackground";
    public static Color LABEL_BACKGROUND = new Color(255, 255, 255);
    /**render*/
    public static String RENDERER_COLOR1_KEY = "rendererColor1";
    public static Color RENDERER_COLOR1 = new Color(255, 255, 255);
    public static String RENDERER_COLOR2_KEY = "rendererColor2";
    public static Color RENDERER_COLOR2 = new Color(255, 235, 178);
    public static String RENDERER_COLOR3_KEY = "rendererColor3";
    public static Color RENDERER_COLOR3 = new Color(255, 215, 110);
    public static String RENDERER_COLOR4_KEY = "rendererColor4";
    public static Color RENDERER_COLOR4 = new Color(255, 230, 161);
    /**menu item*/
    public static String MENU_ITEM_ICON_BackGround_KEY = "menuItemColor";
    public static Color MENU_ITEM_ICON_BackGround = new Color(233, 238, 238);

    public static Rectangle getScreenSize() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        Insets inset = Toolkit.getDefaultToolkit().getScreenInsets(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration());
        return new Rectangle(inset.left, inset.top, size.width - inset.right, size.height - inset.bottom);
    }
}
