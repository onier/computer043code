/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbean.sample;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;

/**
 *
 * @author Zhenhai.Xu
 */
public class PaletteSupport {

    public static PaletteController createController() {
        try {
            return PaletteFactory.createPalette("Cards", new MyActions());
        } catch (IOException ex) {
            Logger.getLogger(PaletteSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
