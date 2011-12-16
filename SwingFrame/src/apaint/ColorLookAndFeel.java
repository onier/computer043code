/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apaint;

import java.awt.Color;
import javax.swing.UIDefaults;
import javax.swing.plaf.basic.BasicLookAndFeel;

/**
 *
 * @author admin
 */
public class ColorLookAndFeel extends BasicLookAndFeel {

    private String name = "Color";
    public static final String BUTTON_LAYOUT_LEFT = "left";
    public static final String BUTTON_LAYOUT_BOTH = "both";
    public static final String BUTTON_LAYOUT_RIGHT = "right";
    public static final String BUTTON_LAYOUT = "layout";
    public static final String BUTTON_SEPARATOR = "separator";
    public static final Boolean BUTTON_SEPARATOR_TRUE = true;
    public static final Boolean BUTTON_SEPARATOR_FALSE = false;
    public static final String BUTTON_BORDER = "bordercolor";
    public static final Color[] ROLLOVER_Color = new Color[]{new Color(255, 250, 212), new Color(255, 212, 73)};
    public static final Color[] PRESSED_Color = new Color[]{new Color(251, 161, 58), new Color(253, 238, 169)};

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected void initComponentDefaults(UIDefaults table) {
        super.initComponentDefaults(table);
    }

    @Override
    protected void initSystemColorDefaults(UIDefaults table) {
        super.initSystemColorDefaults(table);
        table.put("progressBar.color", Color.GREEN);
        table.put("progressBar.bordercolor", Color.YELLOW);
        table.put(" ProgressBar.repaintInterval", 25);
        table.put(ColorLookAndFeelProperties.TEXT_BORDER_COLOR_KEY, ColorLookAndFeelProperties.TEXT_BORDER_COLOR);
        table.put(ColorLookAndFeelProperties.TEXT_BACKGROUND_COLOR_KEY, ColorLookAndFeelProperties.TEXT_BACKGROUND_COLOR);
        table.put(ColorLookAndFeelProperties.TEXT_FOCUS_BORDER_COLOR_KEY, ColorLookAndFeelProperties.TEXT_FOCUS_BORDER_COLOR);
        table.put(ColorLookAndFeelProperties.TEXT_FOCUS_BACKGROUND_COLOR_KEY, ColorLookAndFeelProperties.TEXT_FOCUS_BACKGROUND_COLOR);
        table.put(ColorLookAndFeelProperties.TEXT_SLECT_COLOR_KEY, ColorLookAndFeelProperties.TEXT_SLECT_COLOR);

        table.put(ColorLookAndFeelProperties.LABEL_BACKGROUND_KEY, ColorLookAndFeelProperties.LABEL_BACKGROUND);

        table.put(ColorLookAndFeelProperties.RENDERER_COLOR1_KEY, ColorLookAndFeelProperties.RENDERER_COLOR1);
        table.put(ColorLookAndFeelProperties.RENDERER_COLOR2_KEY, ColorLookAndFeelProperties.RENDERER_COLOR2);
        table.put(ColorLookAndFeelProperties.RENDERER_COLOR3_KEY, ColorLookAndFeelProperties.RENDERER_COLOR3);
        table.put(ColorLookAndFeelProperties.RENDERER_COLOR4_KEY, ColorLookAndFeelProperties.RENDERER_COLOR4);
        
        table.put(ColorLookAndFeelProperties.MENU_ITEM_ICON_BackGround_KEY, ColorLookAndFeelProperties.MENU_ITEM_ICON_BackGround);
    }

    @Override
    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);
        table.putDefaults(new Object[]{"ButtonUI", "apaint.ColorButtonUI",
                    "DownButtonUI", "apaint.ColorDownButtonUI",
                    "RadioButtonUI", "apaint.ColorRadioButtonUI",
                    "CheckBoxUI", "apaint.ColorCheckBoxUI",
                    "EllipseButtonUI", "apaint.EllipseButtonUI",
                    "ToggleButtonUI", "apaint.ColorToggleButtonUI",
                    "EllipseToggleButtonUI", "apaint.ColorEllipseToggleButtonUI",
                    "TabbedPaneUI", "apaint.ColorTabbedPaneUI",
                    "ScrollBarUI", "apaint.ColorScrollBarUI",
                    "ProgressBarUI", "apaint.ColorProgressBarUI",
                    "ComboBoxUI", "apaint.ColorComboBoxUI",
                    "ScrollPaneUI", "apaint.ColorScrollPaneUI",
                    "SplitPaneUI", "apaint.ColorSplitPaneUI",
                    "TextFieldUI", "apaint.ColorTextFieldUI",
                    "FormattedTextFieldUI", "apaint.ColorTextFieldUI",
                    "RootPaneU", "apaint.ColorRootPaneUI",
                    "LabelUI", "apaint.ColorLabelUI",
                    "ListUI", "apaint.ColorListUI",
                    "MenuItemUI", "apaint.ColorMenuItemUI",
                    "ToolBarUI", "apaint.ColorToolBarUI",
                    "PanelUI", "apaint.ColorPanelUI",});
    }

    @Override
    public String getID() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Color Java Look And Feel";
    }

    @Override
    public boolean isNativeLookAndFeel() {
        return false;
    }

    @Override
    public boolean isSupportedLookAndFeel() {
        return true;
    }
}
