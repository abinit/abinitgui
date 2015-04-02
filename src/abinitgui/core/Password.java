/*
 AbinitGUI - Created in 2009
 
 Copyright (c) 2009-2015 Flavio Miguel ABREU ARAUJO (abreuaraujo.flavio@gmail.com)
                         Yannick GILLET (yannick.gillet@hotmail.com)

 Université catholique de Louvain, Louvain-la-Neuve, Belgium
 All rights reserved.

 AbinitGUI is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 AbinitGUI is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with AbinitGUI.  If not, see <http://www.gnu.org/licenses/>.

 For more information on the project, please see
 <http://gui.abinit.org/>.
 */

package abinitgui.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            MainFrame.printDEB("NoSuchAlgorithmException: " + ex.getMessage());
        } catch (NoSuchPaddingException ex) {
            MainFrame.printDEB("NoSuchPaddingException: " + ex.getMessage());
        }

    }
    
    public String p_encrypt()
    {
        try {
            return encrypt(this.passwd);
        } catch (InvalidKeyException ex) {
            MainFrame.printDEB("InvalidKeyException: " + ex.getMessage());
        } catch (IllegalBlockSizeException ex) {
            MainFrame.printDEB("IllegalBlockSizeException: " + ex.getMessage());
        } catch (BadPaddingException ex) {
            MainFrame.printDEB("BadPaddingException: " + ex.getMessage());
        } catch (GeneralSecurityException ex) {
            MainFrame.printDEB("GeneralSecurityException: " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            MainFrame.printDEB("UnsupportedEncodingException: " + ex.getMessage());
        }
        
        return null;
    }
    
    public static Password p_decrypt(String encrypted)
    {
        try {
            return new Password(decrypt(encrypted));
        } catch (InvalidKeyException ex) {
            MainFrame.printDEB("InvalidKeyException: " + ex.getMessage());
        } catch (GeneralSecurityException ex) {
            MainFrame.printDEB("GeneralSecurityException: " + ex.getMessage());
        } catch (IOException ex) {
            MainFrame.printDEB("IOException: " + ex.getMessage());
        }
        
        return null;
    }
    
    @Override
    public String toString()
    {
        return this.passwd;
    }
    
    private static String encrypt(String property) throws GeneralSecurityException, 
            UnsupportedEncodingException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key2 = keyFactory.generateSecret(new PBEKeySpec("AbinitGUI".toCharArray()));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key2, new PBEParameterSpec("AGUIAGIO".getBytes("UTF-8"), 20));
        return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
    }

    private static String base64Encode(byte[] bytes) {
        // NB: This class is internal, and you probably should use another impl
        return new BASE64Encoder().encode(bytes);
        // Utiliser com.jcraft.jsch.Util.toBase64(); qui n'est pas eccÃ©ssible
        // il faut reprendre le code source de cette classe qui contient beaucoup
        // de fonctions trÃ¨s pratiques !!
    }

    private static String decrypt(String property) throws GeneralSecurityException, IOException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key2 = keyFactory.generateSecret(new PBEKeySpec("AbinitGUI".toCharArray()));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key2, new PBEParameterSpec("AGUIAGIO".getBytes("UTF-8"), 20));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        // NB: This class is internal, and you probably should use another impl
        return new BASE64Decoder().decodeBuffer(property);
        // Utiliser com.jcraft.jsch.Util.fromBase64(); qui n'est pas eccÃ©ssible
        // il faut reprendre le code source de cette classe qui contient beaucoup
        // de fonctions trÃ¨s pratiques !!
    }
}
