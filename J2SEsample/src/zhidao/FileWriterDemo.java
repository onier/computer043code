/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileWriterDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {

        int b = 0;

        FileReader fr = null;
        FileWriter fw = null;

        try {

            fr = new FileReader("d:/FileReaderDemo.java");
            fw = new FileWriter("c:/wd.txt");

            while ((b = fr.read()) != -1) {
                fw.write(b);
            }
            fw.flush();
        } catch (FileNotFoundException e1) {
            try {
                fr.close();
                fw.close();
                System.out.println("找不到指定文件");
                System.exit(-1);
            } catch (IOException ex) {
                Logger.getLogger(FileWriterDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException e2) {
            try {
                fr.close();
                fw.close();
                e2.getStackTrace();
                System.out.println("文件复制出错!");
                System.exit(-1);
            } catch (IOException ex) {
                Logger.getLogger(FileWriterDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("文件复制成功!");
        System.exit(-1);
    }
}

