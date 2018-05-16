package gkfire.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AES {

    public static String decryptQueryLinkEmail(String q)
            throws Exception {
        return decrypt(q.getBytes(), "Bker1989email___");
    }

    public static String encryptQueryLinkEmail(String link) throws Exception {
        return new String(encrypt(link, "Bker1989email___"));
    }

    public static byte[] encrypt(String text, String keyString) throws Exception {
        Key key = generateKey(keyString);
        Cipher c = Cipher.getInstance("AES");
        c.init(1, key);
        byte[] encVal = c.doFinal(text.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue.getBytes();
    }

    public static String decrypt(byte[] encryptedData, String keyString) throws Exception {
        if (encryptedData == null) {
            return null;
        }
        Key key = generateKey(keyString);
        Cipher c = Cipher.getInstance("AES");
        c.init(2, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(new String(encryptedData));
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);

        return decryptedValue;
    }

    private static Key generateKey(String keyValue) throws Exception {
        Key key = new SecretKeySpec(keyValue.getBytes(), "AES");
        return key;
    }
}
