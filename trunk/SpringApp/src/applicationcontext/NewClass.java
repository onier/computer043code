/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationcontext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import sun.net.www.protocol.http.HttpURLConnection;

class WebContent {

    public String getOneHtml(String htmlurl) throws IOException {
        URL url;
        String temp;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(htmlurl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            while ((temp = in.readLine()) != null) {
                sb.append(temp);
            }
            in.close();
        } catch (MalformedURLException me) {
            System.out.println("your url is wrong,please input");
            me.getMessage();
            throw me;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        WebContent web = new WebContent();
        URL url = new URL("http://www.sohu.com/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.getContentEncoding();
        String str = "加分考生答卷反垃圾打开噶速度";
        byte[] bytes = str.getBytes("UTF-8");

        str = new String(bytes, "UTF-8");
        System.out.println(str);
        //        GZIPInputStream input = new GZIPInputStream(con.getInputStream());
//        InputStream input = con.getInputStream();
//        byte[] bs = new byte[1024];
//        input.read(bs, 0, bs.length);
//        System.out.println(new String(bs));
    }
}
