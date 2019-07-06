package com.cmbc.firefly.security.crypto;

public class CryptoConstants {

    public final static String MD5_ALG = "MD5";
    public final static String SHA1_ALG = "SHA-1";
    public final static String SHA256_ALG = "SHA-256";
    public final static String SHA512_ALG = "SHA-512";

    public final static String AES_ALG = "AES";
    public final static String RSA_ALG = "RSA";

    public final static String SM2_ALG = "SM2";
    public final static String SM3_ALG = "SM3";
    public final static String SM4_ALG = "SM4";

    public final static String SM9_ALG = "SM9";

    public static enum SM4Mode {
        SMS4,
        SMS4_CBC, // default Cipher Block Chaining(CBC)
        SMS4_ECB, // Electronic Code Book(ECB)
        SMS4_CTR, // Calculator
        SMS4_CFB, // Cipher Feedback
        SMS4_OFB  // Output Feedback
    }
}
