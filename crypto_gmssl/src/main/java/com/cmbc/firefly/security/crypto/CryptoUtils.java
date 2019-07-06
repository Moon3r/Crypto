package com.cmbc.firefly.security.crypto;

import android.util.Base64;

import com.cmbc.firefly.security.crypto_adapter.RSASignHashType;

import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.cmbc.firefly.security.crypto.StringUtils.isSpace;

public class CryptoUtils {

    private static final char HEX_DIGITS[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    // Return the bytes array of hex string.
    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null)
            return "";
        int len = bytes.length;
        if (len <= 0)
            return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    public static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString))
            return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toLowerCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    public static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    private static byte[] joins(final byte[] prefix, final byte[] suffix) {
        byte[] ret = new byte[prefix.length + suffix.length];
        System.arraycopy(prefix, 0, ret, 0, prefix.length);
        System.arraycopy(suffix, 0, ret, prefix.length, suffix.length);
        return ret;
    }

    /**
     * Return the bytes of hash encryption.
     *
     * @param data      The data.
     * @param algorithm The name of hash encryption. MD2, MD5, SHA-1, SHA-256,SHA-512 eg
     * @return the bytes of hash encryption
     */
    public static byte[] hashTemplate(final byte[] data, final String algorithm) throws Exception {
        checkData(data);
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(data);
        return md.digest();
    }

    /**
     * Return the bytes of symmetric encryption or decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param algorithm      The name of algorithm. AES, DES, 3DES eg
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param isEncrypt      True to encrypt, false otherwise.
     * @return the bytes of symmetric encryption or decryption
     */
    public static byte[] symmetricTemplate(final byte[] data,
                                           final byte[] key,
                                           final String algorithm,
                                           final String transformation,
                                           final byte[] iv,
                                           final boolean isEncrypt) throws Exception {
        checkData(data);
        checkKey(key);

        if (key.length != 16 && key.length != 24 && key.length != 32) {
            throw new IllegalArgumentException("key的长度应为 16字节、24字节或32字节");
        }
        SecretKey secretKey;
        if ("DES".equals(algorithm)) {
            DESKeySpec desKey = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
            secretKey = keyFactory.generateSecret(desKey);
        } else {
            secretKey = new SecretKeySpec(key, algorithm);
        }
        Cipher cipher = Cipher.getInstance(transformation);
        if (iv == null || iv.length == 0) {
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey);
        } else {
            if (iv.length != 16) {
                throw new IllegalArgumentException("iv的长度为 16字节");
            }
            AlgorithmParameterSpec params = new IvParameterSpec(iv);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, params);
        }
        return cipher.doFinal(data);
    }

    /**
     * Return the bytes of RSA encryption or decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., <i>RSA/ECB/PKCS1Padding</i>.
     * @param isEncrypt      True to encrypt, false otherwise.
     * @return the bytes of RSA encryption or decryption
     */
    public static byte[] rsaTemplate(final byte[] data,
                                     final byte[] key,
                                     final boolean isPublicKey,
                                     final String transformation,
                                     final boolean isEncrypt) throws Exception {
        checkData(data);
        checkKey(key);

        Key rsaKey;
        if (isPublicKey) {
            rsaKey = getPublicKey(key);
        } else {
            rsaKey = getPrivateKey(key);
        }
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, rsaKey);
        int blockSize = cipher.getBlockSize();
        int len = data.length;
        int maxLen = blockSize;
        int count = len / maxLen;
        if (count > 0) {
            byte[] ret = new byte[0];
            byte[] buff = new byte[maxLen];
            int index = 0;
            for (int i = 0; i < count; i++) {
                System.arraycopy(data, index, buff, 0, maxLen);
                ret = joins(ret, cipher.doFinal(buff));
                index += maxLen;
            }
            if (index != len) {
                int restLen = len - index;
                buff = new byte[restLen];
                System.arraycopy(data, index, buff, 0, restLen);
                ret = joins(ret, cipher.doFinal(buff));
            }
            return ret;
        } else {
            return cipher.doFinal(data);
        }
    }

    public static Key getPrivateKey(byte[] key) throws Exception {
        checkKey(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
        Key rsaKey = KeyFactory.getInstance(CryptoConstants.RSA_ALG, "BC").generatePrivate(keySpec);
        return rsaKey;
    }

    public static Key getPublicKey(byte[] key) throws Exception {
        checkKey(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
        Key rsaKey = KeyFactory.getInstance(CryptoConstants.RSA_ALG, "BC").generatePublic(keySpec);
        return rsaKey;
    }

    public static String getHashType(RSASignHashType rsaSignHashType) {
        switch (rsaSignHashType) {
            case MD5:
                return "MD5withRSA";
            case SHA1:
                return "SHA1withRSA";
            case SHA256:
                return "SHA256withRSA";
        }
        return null;
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static byte[] concatAll(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    private static void checkData(byte[] data) {
        if (data == null || data.length == 0) {
            throw new NullPointerException("data 为空");
        }
    }

    private static void checkKey(byte[] key) {
        if (key == null || key.length == 0) {
            throw new NullPointerException("key 为空");
        }
    }
}