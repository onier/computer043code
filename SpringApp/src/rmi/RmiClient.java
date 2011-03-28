/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.*;
import java.rmi.registry.*;

public class RmiClient {

    static public void main(String args[]) {
        ReceiveMessageInterface rmiServer;
        Registry registry;
        String serverAddress = "192.168.1.222";
        String serverPort = "3232";
        String text = "78979798946464646554646564546";
        System.out.println("sending " + text + " to " + serverAddress + ":" + serverPort);
        try {
            // get the “registry”
            registry = LocateRegistry.getRegistry(serverAddress, (new Integer(serverPort)).intValue());
            // look up the remote object
            rmiServer = (ReceiveMessageInterface) (registry.lookup("rmiServer"));
            // call the remote method
            rmiServer.receiveMessage(text);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
