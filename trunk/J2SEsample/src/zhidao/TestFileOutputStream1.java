/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.io.*;

public class TestFileOutputStream1 {

    public static void main(String[] args) {
        FileInputStream in = null;
        FileOutputStream out = null;
        int b = 0;
        try {
            in = new FileInputStream("D:\\1.java");
            out = new FileOutputStream("D:\\2.txt");
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
            out.close();
            System.out.println("复制完成！");
        } catch (FileNotFoundException e) {
            System.out.println("读取文件不存在");
            System.exit(-1);
        } catch (IOException i) {
            System.out.println("读取出错");
            System.exit(-1);
        }
    }
}
