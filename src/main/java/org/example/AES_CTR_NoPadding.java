package org.example;

import jakarta.xml.bind.DatatypeConverter;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES_CTR_NoPadding {
    public static void main(String[] args) {
        try {
            byte[] initVector = generateRandomIV();
            SecretKey key = generateRandomKey();

            String plaintext = "Hello, I am VMD";
            System.out.println("PLAINTEXT: " + plaintext);

            String encrypted = encrypt(plaintext, key, initVector);
            System.out.println("ENCRYPT: " + encrypted);
            String decrypted = decrypt(encrypted, key, initVector);
            System.out.println("DECRYPT: " + decrypted);
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public static String encrypt(String plain, SecretKey skeySpec, byte[] iv) {
        try {

            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));

            byte[] encrypted = cipher.doFinal(plain.getBytes("UTF-8"));
            return DatatypeConverter.printBase64Binary(encrypted);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException
                 | NoSuchPaddingException | InvalidKeyException
                 | InvalidAlgorithmParameterException | IllegalBlockSizeException
                 | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static String decrypt(String encrypted, SecretKey skeySpec, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));

            byte[] original = cipher.doFinal(DatatypeConverter.parseBase64Binary(encrypted));

            return new String(original);
        } catch ( NoSuchAlgorithmException
                  | NoSuchPaddingException | InvalidKeyException
                  | InvalidAlgorithmParameterException | IllegalBlockSizeException
                  | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static byte[] generateRandomIV() throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        byte[] ivBytes = new byte[16];
        secureRandom.nextBytes(ivBytes);
        return ivBytes;
    }

    public static SecretKey generateRandomKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }
}
