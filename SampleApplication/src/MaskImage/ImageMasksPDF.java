/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MaskImage;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;

public class ImageMasksPDF {

    public static void main(String[] args) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("ImageMasksPDF.pdf"));

            document.open();
            Paragraph p = new Paragraph("Some text behind a masked image.");
//            for (int i = 0; i < 50; i++) {
//                document.add(p);
//            }

            PdfContentByte cb = writer.getDirectContent();
            byte maskr[] = {(byte) 0x77, (byte) 0x77, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x77, (byte) 0x77};

            Image mask = Image.getInstance(8, 8, 1, 1, maskr);
            mask.makeMask();
            mask.setInvertMask(true);

            Image image = Image.getInstance("logo.png");
            image.setImageMask(mask);
            image.setAlignment(Image.TEXTWRAP);
            image.setAbsolutePosition(60, 550);

            cb.addImage(image);

            document.close();
        } catch (Exception de) {
            de.printStackTrace();
        }
    }
}

