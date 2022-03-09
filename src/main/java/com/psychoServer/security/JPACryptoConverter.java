package com.psychoServer.security;

import org.springframework.security.crypto.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.Key;

public class JPACryptoConverter implements AttributeConverter<String, String> {
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String KEY = "b756c3071a1c7993";

    @Override
    public String convertToDatabaseColumn(String sensitive) {
        if (sensitive == null) {
            return "";
        }
        Key key = new SecretKeySpec(KEY.getBytes(), "AES");
        try {
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            final String encrypted = new String(Base64.encode(c.doFinal(sensitive.getBytes())), "UTF-8");
            return encrypted;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String sensitive) {
        if (sensitive == null) {
            return "";
        }
        Key key = new SecretKeySpec(KEY.getBytes(), "AES");
        try {
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            final String decrypted = new String(c.doFinal(Base64.decode(sensitive.getBytes("UTF-8"))));
            return decrypted;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
