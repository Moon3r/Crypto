package com.cmbc.firefly.security.crypto;

import static com.cmbc.firefly.security.crypto.CryptoUtils.bytes2HexString;

/**
 * SM3 message digest
 */
public class SM3 {

    public static byte[] encryptSM3(byte[] data) throws Exception {

        if (data == null || data.length == 0) {
            throw new NullPointerException("data 为空");
        }

        return GmSSL.getInstance().digest(CryptoConstants.SM3_ALG, data);
    }

    public static String encryptSM3ToString(byte[] data) throws Exception {
        return bytes2HexString(encryptSM3(data));
    }
}
