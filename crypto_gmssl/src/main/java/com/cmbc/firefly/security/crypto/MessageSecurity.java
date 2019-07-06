package com.cmbc.firefly.security.crypto;

import java.util.Arrays;

import static com.cmbc.firefly.security.crypto.CryptoUtils.base64Decode;
import static com.cmbc.firefly.security.crypto.CryptoUtils.base64Encode;

public class MessageSecurity {

    public static byte[] messageEncrypt2Base64(byte[] data, byte[] key) throws Exception {
        return base64Encode(messageEncrypt(data, key));
    }

    public static byte[] messageBase64Decrypt(byte[] data, byte[] key) throws Exception {
        return messageDecrypt(base64Decode(data), key);
    }

    /**
     *  1、生成一个随机数，计算随机数的MD5值
     *  2、拼接MD5值和key,得到新字节串，计算新字节串的SHA256
     *  3、SHA256 前128比特为KEY，后128比特为IV
     *  4、用KEY，IV，采用SM4 CBC模式加密明文，得到密文
     *  5、无符号byte值 byte number = 0 ,将MD5值的所有字节按照无符号值，依次与number相加，得到最终的number值
     *  6、如果number>15，那么去number前4比特(number >> 4)，否则后4比特(number & 0x0F)，得到index；
     *  7、取密文的前16字节，围成一个环，将index位置的字节提到第1位置，index+1提到第2位置，index+2提到第3位值......，
     *  8、将MD5值插入密文最前
     * @param data
     * @param key
     * @return
     */
    public static byte[] messageEncrypt(byte[] data, byte[] key) throws Exception {
        checkByteArrayParams(data, key);

        String randomString = CryptoUtils.getRandomString(16);
        byte[] md5 = MD5.encryptMD5(randomString.getBytes());
        byte[] concat = CryptoUtils.concatAll(md5, key);
        byte[] sha256 = SHA256.encryptSHA256(concat);

        byte[] realKey = Arrays.copyOfRange(sha256, 0, 16);
        byte[] realIv = Arrays.copyOfRange(sha256, 16, sha256.length);

        byte[] cipher = SM4.encryptSM4(data, realKey, realIv, CryptoConstants.SM4Mode.SMS4_CBC);

        byte number = 0;
        for (byte b : md5) {
            number += b;
        }

        int index;
        if (number > 15) {
            index = number >> 4;
        } else {
            index = number & 0x0F;
        }

        byte[] cipher1 = Arrays.copyOfRange(cipher, 0, index + 1);
        byte[] cipher2 = Arrays.copyOfRange(cipher, index + 1, 16);
        byte[] cipher3 = Arrays.copyOfRange(cipher, 16, cipher.length);

        cipher = CryptoUtils.concatAll(md5, cipher2, cipher1, cipher3);

        return cipher;
    }

    public static byte[] messageDecrypt(byte[] data, byte[] key) throws Exception {
        checkByteArrayParams(data, key);

        byte[] md5 = Arrays.copyOfRange(data, 0, 16);
        byte[] concat = CryptoUtils.concatAll(md5, key);
        byte[] sha256 = SHA256.encryptSHA256(concat);

        byte number = 0;
        for (byte b : md5) {
            number += b;
        }

        int index;
        if (number > 15) {
            index = number >> 4;
        } else {
            index = number & 0x0F;
        }

        byte[] cipher = Arrays.copyOfRange(data, 16, data.length);
        byte[] cipher1 = Arrays.copyOfRange(cipher, 0, 16 - index - 1);
        byte[] cipher2 = Arrays.copyOfRange(cipher, 16 - index - 1,  16);
        byte[] cipher3 = Arrays.copyOfRange(cipher, 16,  cipher.length);

        cipher = CryptoUtils.concatAll(cipher2, cipher1, cipher3);

        byte[] realKey = Arrays.copyOfRange(sha256, 0, 16);
        byte[] realIv = Arrays.copyOfRange(sha256, 16, sha256.length);

        byte[] realData = SM4.decryptSM4(cipher, realKey, realIv, CryptoConstants.SM4Mode.SMS4_CBC);

        return realData;
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
