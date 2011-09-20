/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package font;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;
import java.util.List;

public class FontAlgo {

    private static final char CHAR_TO_PATTERN = '@';
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final boolean isReverse = true;
    private static final Font appliedFont = new Font("Couirer new", Font.BOLD, 30);

    private static TextualChar getTextualChar(char a_char) throws Throwable {
        BufferedImage bImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = bImg.getGraphics();
        g.setColor(Color.green);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setFont(appliedFont);
        g.setColor(Color.black);
        g.drawString(new String(new char[]{a_char}), 10, g.getFontMetrics().getHeight());
        PixelGrabber p = new PixelGrabber(bImg, 0, 0, WIDTH, HEIGHT, true);

        if (p.grabPixels()) {
            char[][] pattern = new char[WIDTH][HEIGHT];
            int baseColourPixel = 0, contrastColourPixel = 0, x1 = 0, x2 = 0, y1 = 0, y2 = 0;
            int[] pixels = (int[]) p.getPixels();
            baseColourPixel = pixels[0];
            // System.out.println("base: " + base);
            int xCounter = 0, yCounter = 0;
            for (int iPixel : pixels) {
                // System.out.println(iX + " - " + iY);
                if (isReverse) {
                    pattern[xCounter][yCounter] = iPixel == baseColourPixel ? CHAR_TO_PATTERN : ' ';
                } else {
                    pattern[xCounter][yCounter] = iPixel != baseColourPixel ? CHAR_TO_PATTERN : ' ';
                }

                yCounter++;
                if (yCounter > 49) {
                    xCounter++;
                    yCounter = 0;
                }

                if (contrastColourPixel == 0 && iPixel != baseColourPixel) {
                    contrastColourPixel = iPixel;
                    x1 = xCounter - 2;
                    y1 = yCounter - 3;
                    y2 = yCounter + 3;
                }

                if (contrastColourPixel == iPixel) {
                    x2 = xCounter + 3;

                    if (y1 > (yCounter - 3)) {
                        y1 = yCounter - 3;
                    }

                    if (y2 < (yCounter + 3)) {
                        y2 = yCounter + 3;
                    }
                }
            }
            return new TextualChar(x1, x2, y1, y2, pattern);
        }
        return null;
    }

    private static List<TextualChar> getTexualChars(String strText) throws Throwable {
        List<TextualChar> returnList = new ArrayList<TextualChar>();
        for (byte lbyte : strText.getBytes()) {
            TextualChar tChar = getTextualChar((char) lbyte);
            returnList.add(tChar);
        }
        return returnList;
    }

    public static void main(String[] args) throws Throwable {
        List<TextualChar> textualCharList = getTexualChars("Aniruddha");

        TextualChar tChar1 = textualCharList.get(0);
        int endPos = tChar1.getxPos2();
        for (int iCounter = tChar1.getxPos1(); iCounter < endPos; iCounter++) {

            for (TextualChar tChar : textualCharList) {
                if (endPos < tChar.getxPos2()) {
                    endPos = tChar.getxPos2();
                }
                for (int iInnerCounter = tChar.getyPos1(); iInnerCounter < tChar.getyPos2(); iInnerCounter++) {
                    System.out.print(tChar.getPixelPattern()[iCounter][iInnerCounter]);
                }
            }
            System.out.println();
        }
    }

    static class TextualChar {

        private int xPos1 = 0;
        private int xPos2 = 0;
        private int yPos1 = 0;
        private int yPos2 = 0;
        private char[][] pixelPattern = new char[WIDTH][HEIGHT];

        public TextualChar(int xPos1, int xPos2, int yPos1, int yPos2, char[][] a_pattern) {
            this.xPos1 = xPos1;
            this.xPos2 = xPos2;
            this.yPos1 = yPos1;
            this.yPos2 = yPos2;
            this.pixelPattern = a_pattern;
        }

        public char[][] getPixelPattern() {
            return pixelPattern;
        }

        public int getxPos1() {
            return xPos1;
        }

        public int getxPos2() {
            return xPos2;
        }

        public int getyPos1() {
            return yPos1;
        }

        public int getyPos2() {
            return yPos2;
        }
    }
}
