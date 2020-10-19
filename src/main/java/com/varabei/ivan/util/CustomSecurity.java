package com.varabei.ivan.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CustomSecurity {
    private static final Logger log = LogManager.getLogger(CustomSecurity.class);
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String SYMBOLS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ALGORITHM_SHA_256 = "SHA-256";

    private CustomSecurity() {
    }

    public static void main(String... sda) {
        System.err.println(generateRandom(64));
    }

    public static String generateRandom(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(SYMBOLS.charAt(SECURE_RANDOM.nextInt(SYMBOLS.length())));
        }
        return sb.toString();
    }

    public static String generateHash(String originalString) {
        byte[] encodedHash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_SHA_256);
            encodedHash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            log.error(e);
        }
        return bytesToHex(encodedHash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
