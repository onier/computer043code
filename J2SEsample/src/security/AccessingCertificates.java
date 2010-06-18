/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.File;

public class AccessingCertificates {

    public static void main(String[] args) throws Exception {
        final String keyStore = "c:\\acme.txt";
        final String keyStorePass = "danger";
        final String keyPass = "password";
        File file = new File(keyStore);
        if (!file.exists()) {
            file.createNewFile();
        }
        // load information into a keystore
        java.security.KeyStore ks = java.security.KeyStore.getInstance("JKS");
        java.io.FileInputStream ksfis = new java.io.FileInputStream(file);
        java.io.BufferedInputStream ksbufin = new java.io.BufferedInputStream(ksfis);
        ks.load(ksbufin, keyStorePass.toCharArray());

        // list aliases in the keystore
        java.io.FileOutputStream fos = null;
        for (java.util.Enumeration theAliases = ks.aliases(); theAliases.hasMoreElements();) {
            String alias = (String) theAliases.nextElement();
            java.security.cert.Certificate cert = ks.getCertificate(alias);
            ByteUtils.saveBytesToFile(alias + ".cer", cert.getEncoded());
            ByteUtils.saveBytesToFile(alias + ".pubkey", cert.getPublicKey().getEncoded());
            java.security.PrivateKey privateKey = (java.security.PrivateKey) ks.getKey(alias, keyPass.toCharArray());
            ByteUtils.saveBytesToFile(alias + ".privKey", privateKey.getEncoded());
            System.out.println("### generated certificate information for -> " + alias);
            System.out.println(cert);
        }
    }
}
