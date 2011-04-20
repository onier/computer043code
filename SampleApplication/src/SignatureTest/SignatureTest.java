package SignatureTest;

/**
@version 1.10 2004-09-14
@author Cay Horstmann
 */
import java.io.*;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
This program demonstrates how to sign a message with a private DSA key 
and verify it with the matching public key. Usage:
java SignatureTest -genkey public private
java SignatureTest -sign message signed private
java SignatureTest -verify signed public
 */
public class SignatureTest {

    public static void main(String[] args) {
        createKey();
        createSign();
        cereateVerify();
    }

    static void cereateVerify() {
        try {
            ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream("c:/public.key"));
            PublicKey pubkey = (PublicKey) keyIn.readObject();
            keyIn.close();
            Signature verifyalg = Signature.getInstance("DSA");
            verifyalg.initVerify(pubkey);
            File infile = new File("c:/test1.xml");
            DataInputStream in = new DataInputStream(new FileInputStream(infile));
            int signlength = in.readInt();
            byte[] signature = new byte[signlength];
            in.read(signature, 0, signlength);
            int length = (int) infile.length() - signlength - 4;
            byte[] message = new byte[length];
            in.read(message, 0, length);
            in.close();
            verifyalg.update(message);
            if (!verifyalg.verify(signature)) {
                System.out.print("not ");
            }
            System.out.println("verified");
        } catch (Exception ex) {
            Logger.getLogger(SignatureTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void createSign() {
        try {
            ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream("c:/private.key"));
            PrivateKey privkey = (PrivateKey) keyIn.readObject();
            keyIn.close();
            Signature signalg = Signature.getInstance("DSA");
            signalg.initSign(privkey);
            File infile = new File("c:/test.xml");
            InputStream in = new FileInputStream(infile);
            int length = (int) infile.length();
            byte[] message = new byte[length];
            in.read(message, 0, length);
            in.close();
            signalg.update(message);
            byte[] signature = signalg.sign();
            DataOutputStream out = new DataOutputStream(new FileOutputStream("c:/test1.xml"));
            int signlength = signature.length;
            out.writeInt(signlength);
            out.write(signature, 0, signlength);
            out.write(message, 0, length);
            out.close();
        } catch (Exception ex) {
            Logger.getLogger(SignatureTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void createKey() {
        try {
            KeyPairGenerator pairgen = KeyPairGenerator.getInstance("DSA");
            SecureRandom random = new SecureRandom();
            pairgen.initialize(KEYSIZE, random);
            KeyPair keyPair = pairgen.generateKeyPair();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("c:/public.key"));
            out.writeObject(keyPair.getPublic());
            out.close();
            out = new ObjectOutputStream(new FileOutputStream("c:/private.key"));
            out.writeObject(keyPair.getPrivate());
            out.close();
        } catch (Exception ex) {
            Logger.getLogger(SignatureTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static final int KEYSIZE = 512;
}
