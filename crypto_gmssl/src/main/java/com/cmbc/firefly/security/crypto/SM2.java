package com.cmbc.firefly.security.crypto;

/**
 * SM2 encryption
 */
public class SM2 {

    private static final String SM2_ALGOR = "sm2encrypt-with-sm3";

    public static byte[] encryptSM2Base64(byte[] data, byte[] publicKey) throws Exception {
        return CryptoUtils.base64Encode(encryptSM2(data, publicKey));
    }

    public static byte[] decryptBase64SM2(byte[] data, byte[] privateKey) throws Exception {
        return decryptSM2(CryptoUtils.base64Decode(data), privateKey);
    }

    /**
     * @param data      The data.
     * @param publicKey The publicKey.
     * @return          the bytes of SM2 decryption
     */
    public static byte[] encryptSM2(byte[] data, byte[] publicKey) throws Exception {
        checkByteArrayParams(data, publicKey);

        return GmSSL.getInstance().publicKeyEncrypt(SM2_ALGOR, data, publicKey);
    }

    /**
     * @param data      The data.
     * @param privateKey The privateKey.
     * @return        the bytes of SM2 decryption
     */
    public static byte[] decryptSM2(byte[] data, byte[] privateKey) throws Exception {
        checkByteArrayParams(data, privateKey);
        return GmSSL.getInstance().publicKeyDecrypt(SM2_ALGOR, data, privateKey);
    }

    private static void checkByteArrayParams(byte[] data, byte[] key) {
        if (data == null || data.length == 0) {
            throw new NullPointerException("data 为空");
        }

        if (key == null || key.length == 0) {
            throw new NullPointerException("key 为空");
        }
    }
}
