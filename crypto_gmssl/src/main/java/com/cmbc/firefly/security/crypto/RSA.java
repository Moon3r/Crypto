package com.cmbc.firefly.security.crypto;

import com.cmbc.firefly.security.crypto_adapter.RSASignHashType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.cmbc.firefly.security.crypto.CryptoUtils.base64Decode;
import static com.cmbc.firefly.security.crypto.CryptoUtils.base64Encode;
import static com.cmbc.firefly.security.crypto.CryptoUtils.bytes2HexString;
import static com.cmbc.firefly.security.crypto.CryptoUtils.hexString2Bytes;
import static com.cmbc.firefly.security.crypto.CryptoUtils.rsaTemplate;

/**
 * RSA encryption
 */
public class RSA {

    /**
     * Return the Base64-encode bytes of RSA encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., <i>RSA/ECB/PKCS1Padding</i>.
     * @return the Base64-encode bytes of RSA encryption
     */
    public static byte[] encryptRSA2Base64(final byte[] data,
                                           final byte[] key,
                                           final boolean isPublicKey,
                                           final String transformation) throws Exception {
        return base64Encode(encryptRSA(data, key, isPublicKey, transformation));
    }

    /**
     * Return the hex string of RSA encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., <i>RSA/ECB/PKCS1Padding</i>.
     * @return the hex string of RSA encryption
     */
    public static String encryptRSA2HexString(final byte[] data,
                                              final byte[] key,
                                              final boolean isPublicKey,
                                              final String transformation) throws Exception {
        return bytes2HexString(encryptRSA(data, key, isPublicKey, transformation));
    }

    /**
     * Return the bytes of RSA encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., <i>RSA/ECB/PKCS1Padding</i>.
     * @return the bytes of RSA encryption
     */
    public static byte[] encryptRSA(final byte[] data,
                                    final byte[] key,
                                    final boolean isPublicKey,
                                    final String transformation) throws Exception {
        return rsaTemplate(data, key, isPublicKey, transformation, true);
    }

    /**
     * Return the bytes of RSA decryption for Base64-encode bytes.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., <i>RSA/ECB/PKCS1Padding</i>.
     * @return the bytes of RSA decryption for Base64-encode bytes
     */
    public static byte[] decryptBase64RSA(final byte[] data,
                                          final byte[] key,
                                          final boolean isPublicKey,
                                          final String transformation) throws Exception {
        return decryptRSA(base64Decode(data), key, isPublicKey, transformation);
    }

    /**
     * Return the bytes of RSA decryption for hex string.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., <i>RSA/ECB/PKCS1Padding</i>.
     * @return the bytes of RSA decryption for hex string
     */
    public static byte[] decryptHexStringRSA(final String data,
                                             final byte[] key,
                                             final boolean isPublicKey,
                                             final String transformation) throws Exception {
        return decryptRSA(hexString2Bytes(data), key, isPublicKey, transformation);
    }

    /**
     * Return the bytes of RSA decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., <i>RSA/ECB/PKCS1Padding</i>.
     * @return the bytes of RSA decryption
     */
    public static byte[] decryptRSA(final byte[] data,
                                    final byte[] key,
                                    final boolean isPublicKey,
                                    final String transformation) throws Exception {
        return rsaTemplate(data, key, isPublicKey, transformation, false);
    }

    /**
     * Obtain the certificate public key
     * @param filePath        Certificate path
     * @return
     */
    public static String getPublicKey(String filePath) {
        String key= null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            FileInputStream in = new FileInputStream(filePath);

            Certificate c = cf.generateCertificate(in);
            PublicKey publicKey = c.getPublicKey();
            byte[] encoded = publicKey.getEncoded();
            key = new String(base64Encode(encoded));

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * 签名
     *
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign2Base64(String data, PrivateKey privateKey,
                                     RSASignHashType rsaSignHashType) throws Exception{
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(CryptoConstants.RSA_ALG, "BC");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(CryptoUtils.getHashType(rsaSignHashType));
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(base64Encode(signature.sign()));
    }

    /**
     * 验签
     *
     * @param srcData 原始字符串
     * @param publicKey 公钥
     * @param sign 签名
     * @return 是否验签通过
     */
    public static boolean verifyBase64(String srcData, PublicKey publicKey, String sign,
                                       RSASignHashType rsaSignHashType) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(CryptoConstants.RSA_ALG, "BC");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(CryptoUtils.getHashType(rsaSignHashType));
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(base64Decode(sign.getBytes()));
    }
}
