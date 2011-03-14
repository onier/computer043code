/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompRcv {

    public static void main(String[] args) throws Exception {
        ServerSocket ssock = new ServerSocket(9999);
        System.out.println("Listening");
        Socket sock = ssock.accept();
        GZIPInputStream zip = new GZIPInputStream(sock.getInputStream());
        while (true) {
            int c;
            c = zip.read();
            if (c == -1) {
                break;
            }
            System.out.print((char) c);
        }
    }
}

class CompSend {

    public static void main(String[] args) throws Exception {
        Socket sock = new Socket(InetAddress.getLocalHost(), 9999);
        GZIPOutputStream zip = new GZIPOutputStream(sock.getOutputStream());
        String line;
        BufferedReader bis = new BufferedReader(new FileReader("c:/test.xml"));
        while (true) {
            try {
                line = bis.readLine();
                if (line == null) {
                    break;
                }
                line = line + "\n";
                zip.write(line.getBytes(), 0, line.length());
            } catch (Exception e) {
                break;
            }
        }
        zip.finish();
        zip.close();
        sock.close();
    }
}
