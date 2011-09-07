/*
 * item.java
 *
 * Created on Jun 4, 2007, 12:53:46 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.javasourcefilepalette.items;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import org.netbeans.modules.javasourcefilepalette.items.resources.ItemCustomizer;
import org.openide.text.ActiveEditorDrop;

/**
 *
 * @author gw152771
 */
public class Item implements ActiveEditorDrop {
    
    private String comment = "";
    
    public Item() {
    }
    
    private String createBody() {
        String comment = getComment();
        StringBuffer buffer = new StringBuffer();
        buffer.append( "/** " );
        buffer.append( comment );
        buffer.append( " */\n" );
        buffer.append( "public static void main( String[] args ) {\n" );
        buffer.append( "}\n\n" );
        return buffer.toString();
    }
    
    public boolean handleTransfer(JTextComponent targetComponent) {
        
        ItemCustomizer c = new ItemCustomizer(this, targetComponent);
        boolean accept = c.showDialog();
        if (accept) {
            String body = createBody();
            try {
                JavaSourceFilePaletteUtilities.insert(body, targetComponent);
            } catch (BadLocationException ble) {
                accept = false;
            }
        }
        return accept;
        
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
}

