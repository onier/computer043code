/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author admin
 */
public class Base64Decoder {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        BASE64Decoder decoder = new BASE64Decoder();
        ByteArrayOutputStream by = new ByteArrayOutputStream();
        encoder.encodeBuffer(new FileInputStream(new File("c:/test.xml")), by);
        System.out.println(new String(decoder.decodeBuffer(by.toString())));
    }
}
