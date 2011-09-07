/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbean.sample.palette;

import java.awt.Image;
import java.util.Properties;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Zhenhai.Xu
 */
public class ItemData {

    private Properties properties;
    private String id;
    private Image icon16;
    private Image icon32;
    private String displayName;
    private String comment;

    public ItemData(Properties properties) {
        this.properties = properties;
        this.id = properties.getProperty("id");
        this.icon16 = ImageUtilities.loadImage(properties.getProperty("icon16"));
        this.icon32 = ImageUtilities.loadImage(properties.getProperty("icon32"));
        this.displayName = properties.getProperty("displayName");
        this.comment = properties.getProperty("comment");
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the icon16
     */
    public Image getIcon16() {
        return icon16;
    }

    /**
     * @param icon16 the icon16 to set
     */
    public void setIcon16(Image icon16) {
        this.icon16 = icon16;
    }

    /**
     * @return the icon32
     */
    public Image getIcon32() {
        return icon32;
    }

    /**
     * @param icon32 the icon32 to set
     */
    public void setIcon32(Image icon32) {
        this.icon32 = icon32;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
