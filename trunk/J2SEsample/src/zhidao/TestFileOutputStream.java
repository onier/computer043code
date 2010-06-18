package zhidao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestFileOutputStream {

    public static void main(String[] args) {
        int b = 0;
        FileInputStream in = null;
        FileOutputStream out = null;
        byte[] datas = new byte[1024];
        try {
            in = new FileInputStream("E:\\Sample\\Test\\src\\zhidao\\TestFileOutputStream.java");

            while ((b = in.read(datas)) != -1) {   //注1行
                System.out.print(new String(datas,"UTF-8").trim());//注2行
            }
            in.close();
        } catch (FileNotFoundException e2) {
            System.out.println("找不到指定文件");
            System.exit(-1);
        } catch (IOException e1) {
            System.out.println("文件复制错误");
            System.exit(-1);
        }
        System.out.println("文件已复制");
    }
}
