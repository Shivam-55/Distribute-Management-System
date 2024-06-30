package com.company.complainservice.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.company.complainservice.exception.NoSuchAlgorithmFoundException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class for decrypting text using AES encryption.
 */
public class DecodeText {

    /** The secret key used for encryption and decryption. */
    public static final String SECRET_KEY ="adkhsjflandljkbasdbasndllkasjsjk";

    /** The fixed initialization vector (IV) used for encryption and decryption. */
    private static final byte[] fixedIV = {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
    };

    /**
     * Generates a secret key for encryption and decryption.
     * @return The generated secret key.
     * @throws NoSuchAlgorithmException If the algorithm for key generation is not available.
     */
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        return  new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
    }

    /**
     * Generates an initialization vector (IV) for encryption and decryption.
     * @return The generated IV.
     */
    private static IvParameterSpec generateIv() {
        return new IvParameterSpec(fixedIV);
    }

    /**
     * Decrypts the given cipher text using the specified algorithm, secret key, and IV.
     * @param algorithm The encryption algorithm to use.
     * @param cipherText The cipher text to decrypt.
     * @param key The secret key for decryption.
     * @param iv The initialization vector (IV) for decryption.
     * @return The decrypted plain text.
     * @throws NoSuchPaddingException If the padding scheme is not available for the algorithm.
     * @throws NoSuchAlgorithmException If the algorithm is not available.
     * @throws InvalidAlgorithmParameterException If the IV is invalid for the algorithm.
     * @throws InvalidKeyException If the key is invalid.
     * @throws BadPaddingException If the padding is incorrect.
     * @throws IllegalBlockSizeException If the block size is incorrect.
     */
    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    /**
     * Decrypts the given cipher text using AES encryption with CBC mode and PKCS5 padding.
     * @param value The cipher text to decrypt.
     * @return The decrypted plain text.
     * @throws RuntimeException If an error occurs during decryption.
     */
    public static String decryptText(String value){
        SecretKey key = null;
        try {
            key = generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmFoundException(e.getMessage());
        }
        IvParameterSpec ivParameterSpec = generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        try {
            return decrypt(algorithm, value, key, ivParameterSpec);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException |
                 InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new NoSuchAlgorithmFoundException(e.getMessage());
        }
    }

}
