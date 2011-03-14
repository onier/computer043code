/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.util.ArrayList;
import shape.BeanNodeElement;

/**
 *
 * @author admin
 */
public class BeanNodeElementUtils {

    public static String[] KEY_WORD = new String[]{"if", "do", "for", "//start", "//end", "for", "ar", "System.out.println", "switch", "while"};

    public static void parse(String code) {
    }

    static ArrayList<BraceContent> parseBraces(String code) {
        ArrayList<BraceContent> braces = new ArrayList<BraceContent>();
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '{') {
                braces.add(new BraceContent(-2, i));
            } else if (code.charAt(i) == '}') {
                braces.add(new BraceContent(2, i));
            }
        }
        if (braces.size() % 2 != 0) {
            throw new RuntimeException("code is wrong");
        }
        return braces;
    }

    static void parseBlock(ArrayList<ArrayList<BraceContent>> result, String code) {
        int s = code.indexOf("// Variables declaration");
        int n = code.indexOf("// End of variables declaration");
        while (code.trim().length() > 0) {
        }
    }

    static void parseBrotherBarces(ArrayList<BraceContent> braces, ArrayList<ArrayList<BraceContent>> result) {
        if (braces.size() > 0 && braces.size() % 2 == 0) {
            ArrayList<BraceContent> contents = new ArrayList<BraceContent>();
            for (int i = 0; i < braces.size() - 1; i++) {
                if (braces.get(i).brotherIndex + braces.get(i + 1).brotherIndex == 0) {
                    braces.get(i).setBorther(braces.get(i + 1));
                    contents.add(braces.get(i));
                    contents.add(braces.get(i + 1));
                }
            }
            braces.removeAll(contents);
            result.add(contents);
        }
    }

    static class BraceContent {

        public final static int LEFT = -2;
        public final static int RIGHT = 2;
        int tpye = -1;
        int index = -1;
        int brotherIndex = -1;

        BraceContent(int type, int index) {
            this.tpye = type;
            this.index = index;
        }

        public void setBorther(BraceContent b) {
            this.brotherIndex = b.index;
            b.brotherIndex = index;
        }

        /**
         * @return the tpye
         */
        public int getTpye() {
            return tpye;
        }

        /**
         * @param tpye the tpye to set
         */
        public void setTpye(int tpye) {
            this.tpye = tpye;
        }

        /**
         * @return the index
         */
        public int getIndex() {
            return index;
        }

        /**
         * @param index the index to set
         */
        public void setIndex(int index) {
            this.index = index;
        }

        /**
         * @return the brotherIndex
         */
        public int getBrotherIndex() {
            return brotherIndex;
        }

        /**
         * @param brotherIndex the brotherIndex to set
         */
        public void setBrotherIndex(int brotherIndex) {
            this.brotherIndex = brotherIndex;
        }
    }

    public static class ParseElementException extends RuntimeException {

        private BeanNodeElement beanNodeElement;

        public ParseElementException(BeanNodeElement beanNodeElement) {
            this.beanNodeElement = beanNodeElement;
        }

        public ParseElementException(BeanNodeElement beanNodeElement, String message) {
            super(message);
            this.beanNodeElement = beanNodeElement;
        }

        /**
         * @return the beanNodeElement
         */
        public BeanNodeElement getBeanNodeElement() {
            return beanNodeElement;
        }
    }
}
