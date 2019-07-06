package com.cmbc.firefly.security.crypto_adapter;

public interface ICryptoStrategy {
    byte[] encryptMD5(byte[] data) throws Exception;
    byte[] encryptMD5File(String filePath) throws Exception;

    byte[] encryptSHA1(byte[] data) throws Exception;
    byte[] encryptSHA256(byte[] data) throws Exception;

    byte[] encryptSM3(byte[] data) throws Exception;

    byte[] encryptAES128_ECB(byte[] data, byte[] key) throws Exception;
    byte[] decryptAES128_ECB(byte[] data, byte[] key) throws Exception;
    byte[] encryptAES256_ECB(byte[] data, byte[] key) throws Exception;
    byte[] decryptAES256_ECB(byte[] data, byte[] key) throws Exception;
    byte[] encryptAES128_CBC(byte[] data, byte[] key, byte[] iv) throws Exception;
    byte[] decryptAES128_CBC(byte[] data, byte[] key, byte[] iv) throws Exception;
    byte[] encryptAES256_CBC(byte[] data, byte[] key, byte[] iv) throws Exception;
    byte[] decryptAES256_CBC(byte[] data, byte[] key, byte[] iv) throws Exception;

    byte[] encryptSM4_CBC(byte[] data, byte[] key, byte[] iv) throws Exception;
    byte[] decryptSM4_CBC(byte[] data, byte[] key, byte[] iv) throws Exception;

    byte[] encryptRSA(byte[] data, byte[] publicKey) throws Exception;
    byte[] decryptRSA(byte[] data, byte[] privateKey) throws Exception;
    String rsaSign(String srcData, String privateKey,
                   RSASignHashType rsaSignHashType) throws Exception;
    boolean rsaVerifyByPublicKey(String srcData, String publicKey, String signature,
                                 RSASignHashType rsaSignHashType) throws Exception;

    byte[] encryptSM2(byte[] data, byte[] publicKey) throws Exception;
    byte[] decryptSM2(byte[] data, byte[] privateKey) throws Exception;

    byte[] messageEncrypt(byte[] data, byte[] key) throws Exception;
    byte[] messageDecrypt(byte[] data, byte[] key) throws Exception;
}
