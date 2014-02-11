/*
 Copyright (c) 2009-2014 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

Université catholique de Louvain, Louvain-la-Neuve, Belgium
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions, and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions, and the disclaimer that follows
these conditions in the documentation and/or other materials
provided with the distribution.

3. The names of the author may not be used to endorse or promote
products derived from this software without specific prior written
permission.

In addition, we request (but do not require) that you include in the
end-user documentation provided with the redistribution and/or in the
software itself an acknowledgement equivalent to the following:
"This product includes software developed by the
Abinit Project (http://www.abinit.org/)."

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

For more information on the Abinit Project, please see
<http://www.abinit.org/>.
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
        // Utiliser com.jcraft.jsch.Util.toBase64(); qui n'est pas eccéssible
        // il faut reprendre le code source de cette classe qui contient beaucoup
        // de fonctions très pratiques !!
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
        // Utiliser com.jcraft.jsch.Util.fromBase64(); qui n'est pas eccéssible
        // il faut reprendre le code source de cette classe qui contient beaucoup
        // de fonctions très pratiques !!
    }
}
