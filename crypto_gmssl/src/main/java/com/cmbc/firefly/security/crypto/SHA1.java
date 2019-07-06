package com.cmbc.firefly.security.crypto;

import static com.cmbc.firefly.security.crypto.CryptoUtils.bytes2HexString;
import static com.cmbc.firefly.security.crypto.CryptoUtils.hashTemplate;

/**
 * SHA1 message digest
 */
public class SHA1 {

    /**
     * Return the hex string of SHA1 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA1 encryption
     */
    public static String encryptSHA1ToString(final String data) throws Exception {
        if (data == null || data.length() == 0) {
            throw new NullPointerException("data 为空");
        }
        return encryptSHA1ToString(data.getBytes());
    }

    /**
     * Return the hex string of SHA1 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA1 encryption
     */
    public static String encryptSHA1ToString(final byte[] data) throws Exception {
        return bytes2HexString(encryptSHA1(data));
    }

    /**
     * Return the bytes of SHA1 encryption.
     *
     * @param data The data.
     * @return the bytes of SHA1 encryption
     */
    public static byte[] encryptSHA1(final byte[] data) throws Exception {
        return hashTemplate(data, CryptoConstants.SHA1_ALG);
    }
}
