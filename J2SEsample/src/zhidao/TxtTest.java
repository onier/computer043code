/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zhidao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TxtTest {

    public static void main(String[] args) {
        try {
            File file = new File("c:\\wd.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(reader);
            ArrayList<String> list = new ArrayList<String>();
            String temp = "";
            int n = 0;
            bp:
            while (true) {
                temp = bReader.readLine();
                if (temp == null) {
                    break bp;
                }
                temp = n + "  " + temp ;
                n++;
                list.add(temp);
            }
            bReader.close();
            reader.close();
            file.delete();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            BufferedWriter bWriter = new BufferedWriter(writer);
            for (int i = 0; i < list.size(); i++) {
                bWriter.write(list.get(i));
                bWriter.newLine();
            }
            bWriter.flush();
        } catch (Exception ex) {
            Logger.getLogger(TxtTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
