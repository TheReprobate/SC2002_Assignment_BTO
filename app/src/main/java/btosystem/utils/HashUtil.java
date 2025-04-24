package btosystem.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class providing hashing functionalities.
 */
public class HashUtil {

    /**
     * Hashes the given password using the MD5 algorithm.
     *
     * @param password The password to be hashed.
     * @return The MD5 hash of the password as a hexadecimal string.
     * @throws RuntimeException If the MD5 hashing algorithm is not available.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 hashing algorithm not available.", e);
        }
    }
}