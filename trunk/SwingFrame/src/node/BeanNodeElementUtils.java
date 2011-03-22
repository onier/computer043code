/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import shape.BeanNodeElement;

/**
 *
 * @author admin
 */
public class BeanNodeElementUtils {

    public static String[] KEY_WORD = new String[]{"if", "do", "for", "//start", "//End", "ar", "System.out.println", "while", "switch", "//ScriptBeanNodeStart", "//checkPoint"};

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        final JTextArea text = new JTextArea();
        frame.getContentPane().add(text, BorderLayout.CENTER);
        JButton button = new JButton("123");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                parse(text.getText());
            }
        });
        frame.getContentPane().add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(3);
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    public static ArrayList<BeanNodeElement> parse(String source) {
        source = source.trim();
        ArrayList<BeanNodeElement> result = new ArrayList<BeanNodeElement>();
        ArrayList<BraceContent> brances = parseBraces(source);
        ArrayList<BraceContent> branceGroup = new ArrayList<BraceContent>();
        parseBrotherBarces(brances, branceGroup);
        brances.clear();
        for (int i = 0; i < branceGroup.size(); i++) {
            if (branceGroup.get(i).type == -2) {
                brances.add(branceGroup.get(i));
            }
        }
        brances = getFirstLayer(brances);
        Collections.sort(brances, new Comparator<BraceContent>() {

            public int compare(BraceContent o1, BraceContent o2) {
                if (o1.index > o2.index) {
                    return 1;
                }
                if (o1.index < o2.index) {
                    return -1;
                }
                return 0;
            }
        });
        int n = 0, m = 0, brotherIndex, index1;
        String block, code = source;
        if (code.trim().length() > 0) {
            int index = parseHeader(result, code);
            if (index == -1 || index > code.length()) {
                return result;
            }
            code = code.substring(index, code.length());
            do {
                boolean b = false;
                code = source.substring(index, source.length());
                if (code.trim().startsWith("for")) {
                    n = indexOf(code, ";");
                } else if (code.trim().startsWith("switch")) {
                    n = code.indexOf("{");
                    if (n >= 0) {
                        n++;
                    } else {
                        return result;
                    }
                } else if (code.trim().startsWith("do")) {
                    b = true;
                    n = code.indexOf(";");
                } else {
                    n = code.indexOf(";");
                }
                if (n >= 0) {
                    n = n + index;
                    m = search(brances, n);
                    if (m == -1) {
                        block = code.substring(0, n - index);
                        BeanNodeElementUtils.parseBlock(result, block);
                        index = n + 1;
                    } else {
                        index1 = brances.get(m).getIndex();
                        brotherIndex = brances.get(m).getBrotherIndex();
                        String While = source.substring(brotherIndex + 1, source.length());
                        if (While.trim().startsWith("while") && b) {
                            int t = While.indexOf(";");
                            brotherIndex = brotherIndex + t + 1;
                        }
                        if (code.trim().startsWith("if")) {
                            String If = source.substring(index, brotherIndex + 1);
                            String Else = source.substring(brotherIndex + 1, source.length());
                            if (Else.trim().startsWith("else")) {
                                Else = source.substring(brances.get(m + 1).index, brances.get(m + 1).brotherIndex + 1);
                                block = If + " _e_l_s_e_ " + Else;
                                index = brances.get(m + 1).brotherIndex + 1;
                            } else {
                                index = brotherIndex + 1;
                                block = If;
                            }
                            BeanNodeElementUtils.parseBlock(result, block);
                        } else {
                            block = source.substring(index, brotherIndex + 1);
                            index = brotherIndex + 1;
                            BeanNodeElementUtils.parseBlock(result, block);
                        }
                    }
                } else if (code.trim().startsWith("//End")) {
                    BeanNodeElementUtils.parseBlock(result, code);
                    return result;
                } else {
                    return result;
                }
            } while (index < source.length());
        }
        return result;
    }

    static int indexOf(String str, String s) {
        int n = str.indexOf(s);
        if (n >= 0) {
            n = str.indexOf(s, n + 1);
            if (n >= 0) {
                n = str.indexOf(s, n + 1);
                if (n >= 0) {
                    return n;
                }
            }
        }
        return -1;
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

    static void parseBlock(ArrayList<BeanNodeElement> nodeList, String code) {
        boolean b = true;
        for (int i = 0; i < KEY_WORD.length; i++) {
            if (code.trim().startsWith(KEY_WORD[i])) {
                b = false;
                switch (i) {
                    case 0:
                        nodeList.add(CaseBeanNodeElement.parseElement(code.trim()));
                        return;
                    case 1:
                        nodeList.add(DoWhileBeanNodeElement.parseElement(code.trim()));
                        return;
                    case 2:
                        nodeList.add(ForBeanNodeElement.parseElement(code));
                        return;
//                    case 3:
                    case 4:
                        nodeList.add(EndBeanNodeElement.parseElement(code.trim()));
                        return;
                    case 5:
                        nodeList.add(GeneralBeanNodeElement.parseElement(code.trim()));
                        return;
                    case 6:
                        nodeList.add(PrintBeanNodeElement.parseElement(code.trim()));
                        return;
                    case 7:
                        nodeList.add(WhileBeanNodeElement.parseElement(code.trim()));
                        return;
                    case 8:
                        nodeList.add(SwitchBeanNodeElement.parseElement(code.trim()));
                        return;
                    case 9:
                        nodeList.add(ScriptBeanNodeElement.parseElement(code.trim()));
                        return;
                    case 10:
                        nodeList.add(CheckPointBeanNodeElement.parseElement(code.trim()));
                        return;
                    default:
                        nodeList.add(BlockBeanNodeElement.parseElement(code.trim()));
                        return;
                }
            }
        }
        if (b) {
            nodeList.add(BlockBeanNodeElement.parseElement(code.trim() + ";"));
        }
    }

    static int parseHeader(ArrayList<BeanNodeElement> nodeList, String code) {
        int ss = code.indexOf("//start");
        int s = code.indexOf("// Variables declaration");
        int n = code.indexOf("// End of variables declaration");
        if (ss >= 0) {
            if (s >= 0 && n >= 0) {
                nodeList.add(StartBeanNodeElement.parseElement(code.substring(s + 24, n)));
                return n + 31;
            } else {
                nodeList.add(StartBeanNodeElement.parseElement(""));
                return 8;
            }
        } else {
            nodeList.add(StartBeanNodeElement.parseElement(""));
            return 0;
        }
    }

    static int search(ArrayList<BraceContent> braces, int index) {
        for (int i = 0; i < braces.size(); i++) {
            if (index > Math.min(braces.get(i).index, braces.get(i).brotherIndex) && index < Math.max(braces.get(i).index, braces.get(i).brotherIndex)) {
                return i;
            }
        }
        return -1;
    }

    static ArrayList<BraceContent> getFirstLayer(ArrayList<BraceContent> braces) {
        ArrayList<BraceContent> result = new ArrayList<BraceContent>();
        for (int i = 0; i < braces.size(); i++) {
            if (braces.get(i).type == -2) {
                if (!braces.get(i).contains(braces)) {
                    result.add(braces.get(i));
                }
            }
        }
        return result;
    }

    static void parseBrotherBarces(ArrayList<BraceContent> braces, ArrayList<BraceContent> result) {
        if (braces.size() > 0 && braces.size() % 2 == 0) {
            ArrayList<BraceContent> contents = new ArrayList<BraceContent>();
            for (int i = 0; i < braces.size() - 1; i++) {
                if (braces.get(i).type == -2 && braces.get(i + 1).type == 2) {
                    braces.get(i).setBorther(braces.get(i + 1));
                    contents.add(braces.get(i));
                    contents.add(braces.get(i + 1));
                    i++;
                }
            }
            braces.removeAll(contents);
            result.addAll(contents);
            parseBrotherBarces(braces, result);
        }
    }

    static class BraceContent {

        public final static int LEFT = -2;
        public final static int RIGHT = 2;
        int type = -1;
        int index = -1;
        int brotherIndex = -1;

        public boolean contains(BraceContent bc) {
            if (type == -1 || index == -1 || bc.index == -1 || brotherIndex == -1 || bc.type == -1 || bc.brotherIndex == -1) {
                throw new RuntimeException("not init");
            }
            boolean b = brotherIndex < bc.brotherIndex && index > bc.index;
            return b;
        }

        public boolean contains(ArrayList<BraceContent> list) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).type == -2) {
                    if (this.contains(list.get(i))) {
                        return true;
                    }
                }
            }
            return false;
        }

        BraceContent(int type, int index) {
            this.type = type;
            this.index = index;
        }

        public void setBorther(BraceContent b) {
            this.brotherIndex = b.index;
            b.brotherIndex = index;
        }

        /**
         * @return the tpye
         */
        public int getType() {
            return type;
        }

        /**
         * @param tpye the tpye to set
         */
        public void setType(int tpye) {
            this.type = tpye;
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
