/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Class to represent a password
 * @author yannick
 */
public class Password
{
    private String passwd;
    private static SecretKeySpec key;
    private static Cipher aes;
    
    public Password(String passwd)
    {
        try {
            this.passwd = passwd;
            
            String passphrase = "correct horse battery staple";
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(passphrase.getBytes());
            key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
            aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public String p_encrypt()
    {
        try {
            return encrypt(this.passwd);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static Password p_decrypt(String encrypted)
    {
        try {
            return new Password(decrypt(encrypted));
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Password.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public String toString()
    {
        return this.passwd;
    }
    
    private static String encrypt(String property) throws GeneralSecurityException, 
            UnsupportedEncodingException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec("AbinitGUI".toCharArray()));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec("AGUIAGIO".getBytes("UTF-8"), 20));
        return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
    }

    private static String base64Encode(byte[] bytes) {
        // NB: This class is internal, and you probably should use another impl
        return new BASE64Encoder().encode(bytes);
    }

    private static String decrypt(String property) throws GeneralSecurityException, IOException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec("AbinitGUI".toCharArray()));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec("AGUIAGIO".getBytes("UTF-8"), 20));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        // NB: This class is internal, and you probably should use another impl
        return new BASE64Decoder().decodeBuffer(property);
    }
    
}
