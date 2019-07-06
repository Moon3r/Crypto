package com.cmbc.firefly.security.crypto;

import static com.cmbc.firefly.security.crypto.CryptoUtils.bytes2HexString;
import static com.cmbc.firefly.security.crypto.CryptoUtils.hashTemplate;

/**
 * SHA512 message digest
 */
public class SHA512 {
    /**
     * Return the hex string of SHA512 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA512 encryption
     */
    public static String encryptSHA512ToString(final String data) throws Exception {
        if (data == null || data.length() == 0) {
            throw new NullPointerException("data 为空");
        }
        return encryptSHA512ToString(data.getBytes());
    }

    /**
     * Return the hex string of SHA512 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA512 encryption
     */
    public static String encryptSHA512ToString(final byte[] data) throws Exception {
        return bytes2HexString(encryptSHA512(data));
    }

    /**
     * Return the bytes of SHA512 encryption.
     *
     * @param data The data.
     * @return the bytes of SHA512 encryption
     */
    public static byte[] encryptSHA512(final byte[] data) throws Exception {
        return hashTemplate(data, CryptoConstants.SHA512_ALG);
    }
}
