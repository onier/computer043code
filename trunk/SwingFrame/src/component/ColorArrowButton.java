/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package component;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 *
 * @author admin
 */
public class ColorArrowButton extends BasicArrowButton {

    /**
     * Creates a {@code BasicArrowButton} whose arrow
     * is drawn in the specified direction.
     *
     * @param direction the direction of the arrow; one of
     *        {@code SwingConstants.NORTH}, {@code SwingConstants.SOUTH},
     *        {@code SwingConstants.EAST} or {@code SwingConstants.WEST}
     */
    public ColorArrowButton(int direction) {
        super(direction);
    }

    public void paint(Graphics g) {
        Color origColor;
        boolean isPressed, isEnabled;
        int w, h, size;

        w = getSize().width;
        h = getSize().height;
        origColor = g.getColor();
        isPressed = getModel().isPressed();
        isEnabled = isEnabled();

        // If there's no room to draw arrow, bail
        if (h < 5 || w < 5) {
            g.setColor(origColor);
            return;
        }

        if (isPressed) {
            g.translate(1, 1);
        }

        // Draw the arrow
        size = Math.min((h - 4) / 3, (w - 4) / 3);
        size = Math.max(size, 2);
        paintTriangle(g, (w - size) / 2, (h - size) / 2,
                size, direction, isEnabled);

        // Reset the Graphics back to it's original settings
        if (isPressed) {
            g.translate(-1, -1);
        }
        g.setColor(origColor);

    }
}
