/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.URL;

/**
 *
 * @author admin
 */
public class Main {

    public static void main(String[] argv) throws Exception {
        Authenticator.setDefault(new MyAuthenticator());
        URL url = new URL("http://www.java2s.com/Code/Java/Network-Protocol/StringjavanetAuthenticatorgetRequestingPrompt.htm");

        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String str;
        while ((str = in.readLine()) != null) {
            System.out.println(str);
        }
        in.close();
    }
}

class MyAuthenticator extends Authenticator {

    protected PasswordAuthentication getPasswordAuthentication() {
        String promptString = getRequestingPrompt();
        System.out.println(promptString);
        String hostname = getRequestingHost();
        System.out.println(hostname);
        InetAddress ipaddr = getRequestingSite();
        System.out.println(ipaddr);
        int port = getRequestingPort();

        String username = "name";
        String password = "password";
        return new PasswordAuthentication(username, password.toCharArray());
    }
}

