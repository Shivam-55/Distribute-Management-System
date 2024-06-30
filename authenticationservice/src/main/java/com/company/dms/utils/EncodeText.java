package com.company.dms.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.company.dms.exception.NoSuchAlgorithmFoundException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class for encrypting text using AES encryption.
 */
public class EncodeText {

    /** The secret key used for encryption and decryption. */
    public static final String SECRET_KEY = "adkhsjflandljkbasdbasndllkasjsjk";

    /** The fixed initialization vector (IV) used for encryption and decryption. */
    private static final byte[] fixedIV = {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F
    };

    /**
     * Generates a secret key for encryption and decryption.
     * @return The generated secret key.
     */
    public static SecretKey generateKey() {
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
     * Encrypts the given input text using the specified algorithm, secret key, and IV.
     * @param algorithm The encryption algorithm to use.
     * @param input The plain text to encrypt.
     * @param key The secret key for encryption.
     * @param iv The initialization vector (IV) for encryption.
     * @return The encrypted cipher text.
     * @throws NoSuchPaddingException If the padding scheme is not available for the algorithm.
     * @throws NoSuchAlgorithmException If the algorithm is not available.
     * @throws InvalidAlgorithmParameterException If the IV is invalid for the algorithm.
     * @throws InvalidKeyException If the key is invalid.
     * @throws BadPaddingException If the padding is incorrect.
     * @throws IllegalBlockSizeException If the block size is incorrect.
     */
    public static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    /**
     * Encrypts the given input text using AES encryption with CBC mode and PKCS5 padding.
     * @param value The plain text to encrypt.
     * @return The encrypted cipher text.
     * @throws RuntimeException If an error occurs during encryption.
     */
    public static String encrytString(String value){
        SecretKey key = generateKey();

        IvParameterSpec ivParameterSpec = generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        try {
            return encrypt(algorithm, value, key, ivParameterSpec);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException |
                 InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new NoSuchAlgorithmFoundException(e.getMessage());
        }
    }

}
