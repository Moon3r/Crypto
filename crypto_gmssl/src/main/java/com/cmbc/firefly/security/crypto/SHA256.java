package com.cmbc.firefly.security.crypto;

import static com.cmbc.firefly.security.crypto.CryptoUtils.bytes2HexString;
import static com.cmbc.firefly.security.crypto.CryptoUtils.hashTemplate;

/**
 * SHA256 message digest
 */
public class SHA256 {

    /**
     * Return the hex string of SHA256 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA256 encryption
     */
    public static String encryptSHA256ToString(final String data) throws Exception {
        if (data == null || data.length() == 0) {
            throw new NullPointerException("data 为空");
        }
        return encryptSHA256ToString(data.getBytes());
    }

    /**
     * Return the hex string of SHA256 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA256 encryption
     */
    public static String encryptSHA256ToString(final byte[] data) throws Exception {
        return bytes2HexString(encryptSHA256(data));
    }

    /**
     * Return the bytes of SHA256 encryption.
     *
     * @param data The data.
     * @return the bytes of SHA256 encryption
     */
    public static byte[] encryptSHA256(final byte[] data) throws Exception {
        return hashTemplate(data, CryptoConstants.SHA256_ALG);
    }
}
