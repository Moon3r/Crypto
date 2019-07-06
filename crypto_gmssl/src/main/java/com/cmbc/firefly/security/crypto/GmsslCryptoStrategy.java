package com.cmbc.firefly.security.crypto;

import com.cmbc.firefly.security.crypto_adapter.ICryptoStrategy;
import com.cmbc.firefly.security.crypto_adapter.RSASignHashType;

import java.security.PrivateKey;
import java.security.PublicKey;

public class GmsslCryptoStrategy implements ICryptoStrategy {
    private static final String AES_ECB = "AES/ECB/PKCS5Padding";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    private static final String RSA_ECB = "RSA/ECB/PKCS1Padding";

    @Override
    public byte[] encryptMD5(byte[] data) throws Exception {
        return MD5.encryptMD5(data);
    }

    @Override
    public byte[] encryptMD5File(String filePath) {
        return MD5.encryptMD5File(filePath);
    }

    @Override
    public byte[] encryptSHA1(byte[] data) throws Exception {
        return SHA1.encryptSHA1(data);
    }

    @Override
    public byte[] encryptSHA256(byte[] data) throws Exception {
        return SHA256.encryptSHA256(data);
    }

    @Override
    public byte[] encryptSM3(byte[] data) throws Exception {
        return SM3.encryptSM3(data);
    }

    @Override
    public byte[] encryptAES128_ECB(byte[] data, byte[] key) throws Exception {
        return AES.encryptAES(data, key, AES_ECB, null);
    }

    @Override
    public byte[] decryptAES128_ECB(byte[] data, byte[] key) throws Exception {
        return AES.decryptAES(data, key, AES_ECB, null);
    }

    @Override
    public byte[] encryptAES256_ECB(byte[] data, byte[] key) throws Exception {
        return AES.encryptAES(data, key, AES_ECB, null);
    }

    @Override
    public byte[] decryptAES256_ECB(byte[] data, byte[] key) throws Exception {
        return AES.decryptAES(data, key, AES_ECB, null);
    }

    @Override
    public byte[] encryptAES128_CBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        return AES.encryptAES(data, key, AES_CBC, iv);
    }

    @Override
    public byte[] decryptAES128_CBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        return AES.decryptAES(data, key, AES_CBC, iv);
    }

    @Override
    public byte[] encryptAES256_CBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        return AES.encryptAES(data, key, AES_CBC, iv);
    }

    @Override
    public byte[] decryptAES256_CBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        return AES.decryptAES(data, key, AES_CBC, iv);
    }

    @Override
    public byte[] encryptRSA(byte[] data, byte[] publicKey) throws Exception {
        return RSA.encryptRSA(data, publicKey, true, RSA_ECB);
    }

    @Override
    public byte[] decryptRSA(byte[] data, byte[] privateKey) throws Exception {
        return RSA.decryptRSA(data, privateKey, false, RSA_ECB);
    }

    @Override
    public String rsaSign(String srcData, String privateKey, RSASignHashType rsaSignHashType) throws Exception {
        PrivateKey pKey = (PrivateKey) CryptoUtils.getPrivateKey(CryptoUtils.base64Decode(privateKey.getBytes()));
        return RSA.sign2Base64(srcData, pKey, rsaSignHashType);
    }

    @Override
    public boolean rsaVerifyByPublicKey(String srcData, String pbyPublicKey,
                                        String signature, RSASignHashType rsaSignHashType) throws Exception {
        PublicKey publicKey = (PublicKey) CryptoUtils.getPublicKey(CryptoUtils.base64Decode(pbyPublicKey.getBytes()));
        return RSA.verifyBase64(srcData, publicKey, signature, rsaSignHashType);
    }

    @Override
    public byte[] encryptSM2(byte[] data, byte[] publicKey) throws Exception {
        return SM2.encryptSM2(data, publicKey);
    }

    @Override
    public byte[] decryptSM2(byte[] data, byte[] privateKey) throws Exception {
        return SM2.decryptSM2(data, privateKey);
    }

    @Override
    public byte[] encryptSM4_CBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        return SM4.encryptSM4_CBC(data, key, iv);
    }

    @Override
    public byte[] decryptSM4_CBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        return SM4.decryptSM4_CBC(data, key, iv);
    }

    @Override
    public byte[] messageEncrypt(byte[] data, byte[] key) throws Exception {
        return MessageSecurity.messageEncrypt(data, key);
    }

    @Override
    public byte[] messageDecrypt(byte[] data, byte[] key) throws Exception {
        return MessageSecurity.messageDecrypt(data, key);
    }
}
