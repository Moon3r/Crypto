package com.cmbc.firefly.security.crypto;

/**
 * SM4 symmetric encryption
 */
public class SM4 {

    public static byte[] encryptSM4_CBC2Base64(byte[] content, byte[] key, byte[] iv) throws Exception {
        return CryptoUtils.base64Encode(encryptSM4(content, key, iv, CryptoConstants.SM4Mode.SMS4_CBC));
    }

    public static byte[] decryptBase64SM4_CBC(byte[] content, byte[] key, byte[] iv) throws Exception {
        return decryptSM4(CryptoUtils.base64Decode(content), key, iv, CryptoConstants.SM4Mode.SMS4_CBC);
    }

    public static byte[] encryptSM4_CBC(byte[] content, byte[] key, byte[] iv) throws Exception {
        return encryptSM4(content, key, iv, CryptoConstants.SM4Mode.SMS4_CBC);
    }

    public static byte[] decryptSM4_CBC(byte[] content, byte[] key, byte[] iv) throws Exception {
        return decryptSM4(content, key, iv, CryptoConstants.SM4Mode.SMS4_CBC);
    }

    /**
     * @param data    The data.
     * @param key     The key.
     * @param iv      The buffer with the IV. The contents of the
     *                buffer are copied to protect against subsequent modification.
     * @param sm4Mode Operating mode. e.g., <i>SMS4-ECB, SMS4-CBC, SMS4-CFB, SMS4-OFB, SMS4-CTR</>.
     * @return        the bytes of SM4 encryption
     */
    public static byte[] encryptSM4(byte[] data, byte[] key, byte[] iv, CryptoConstants.SM4Mode sm4Mode)throws Exception {
        checkData(data);
        checkKey(key);
        checkIv(iv);

        String cipher = getCipher(sm4Mode);

        byte[] encryptResults = GmSSL.getInstance().symmetricEncrypt(cipher, data, key, iv);

        return encryptResults;
    }

    /**
     * @param data    The data.
     * @param key     The key.
     * @param iv      The buffer with the IV. The contents of the
     *                buffer are copied to protect against subsequent modification.
     * @param sm4Mode Working mode. e.g., <i>SMS4-ECB, SMS4-CBC, SMS4-CFB, SMS4-OFB, SMS4-CTR</>.
     * @return        the bytes of SM4 decryption
     */
    public static byte[] decryptSM4(byte[] data, byte[] key, byte[] iv, CryptoConstants.SM4Mode sm4Mode) throws Exception{
        checkData(data);
        checkKey(key);
        checkIv(iv);

        String cipher = getCipher(sm4Mode);

        byte[] encryptResults = GmSSL.getInstance().symmetricDecrypt(cipher, data, key, iv);

        return encryptResults;
    }

    // According to the working mode, obtain the corresponding algorithm
    private static String getCipher(CryptoConstants.SM4Mode sm4Mode) {
        String cipher;
        switch (sm4Mode) {
            case SMS4:
                cipher = "SMS4";
                break;
            case SMS4_CBC:
                cipher = "SMS4-CBC";
            break;
            case SMS4_CFB:
                cipher = "SMS4-CFB";
            break;
            case SMS4_CTR:
                cipher = "SMS4-CTR";
            break;
            case SMS4_ECB:
                cipher = "SMS4-ECB";
            break;
            case SMS4_OFB:
                cipher = "SMS4-OFB";
            break;
            default:
                cipher = "SMS4-CBC";
                break;
        }

        return cipher;
    }

    private static void checkIv(byte[] iv) {
        if (iv == null || iv.length == 0) {
            throw new NullPointerException("iv 为空");
        }
        if (iv.length != 16) {
            throw new IllegalArgumentException("iv的长度为 16字节");
        }
    }

    private static void checkKey(byte[] key) {
        if (key == null || key.length == 0) {
            throw new NullPointerException("key 为空");
        }

        if (key.length != 16) {
            throw new IllegalArgumentException("key的长度为 16字节");
        }
    }

    private static void checkData(byte[] data) {
        if (data == null || data.length == 0) {
            throw new NullPointerException("data 为空");
        }
    }
}
