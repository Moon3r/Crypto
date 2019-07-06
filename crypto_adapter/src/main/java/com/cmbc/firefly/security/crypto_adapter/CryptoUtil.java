package com.cmbc.firefly.security.crypto_adapter;

import android.content.Context;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class CryptoUtil {

    private static CryptoUtil mInstance;
    private ICryptoStrategy mStrategy;
    private static final String TAG = "CryptoUtil";

    private CryptoUtil() {
    }

    public void setCryptoStrategy(ICryptoStrategy iCryptoStrategy) {
        this.mStrategy = iCryptoStrategy;
    }

    public ICryptoStrategy getCryptoStrategy() {
        return mStrategy;
    }

    // 单例模式，节省资源
    public static CryptoUtil getInstance() {
        if (mInstance == null) {
            synchronized (CryptoUtil.class) {
                if (mInstance == null) {
                    mInstance = new CryptoUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public byte[] encryptMD5(byte[] data) throws Exception {
        checkCryptoStrategy();
        return mStrategy.encryptMD5(data);
    }

    public String encryptMD5ToHexString(String sData) throws Exception {
        checkData(sData);
        return encryptMD5ToHexString(sData.getBytes());
    }

    public String encryptMD5ToHexString(byte[] data) throws Exception {
        return ConvertUtil.bytes2HexString(encryptMD5(data));
    }

    public byte[] encryptMD5File(String filePath) throws Exception {
        checkFilePath(filePath);
        checkCryptoStrategy();
        return mStrategy.encryptMD5File(filePath);
    }

    public String encryptMD5File2HexString(String filePath) throws Exception {
        return ConvertUtil.bytes2HexString(encryptMD5File(filePath));
    }

    public boolean checkFileMD5(String filePath, String md5) throws Exception {
        checkFilePath(filePath);
        checkMd5(md5);

        String temp = encryptMD5File2HexString(filePath);
        if (!TextUtils.isEmpty(temp) && md5.toLowerCase().equals(temp.toLowerCase())) {
            return true;
        }
        return false;
    }

    public boolean checkAssetFileMD5(Context context, String filePath, String md5) throws Exception {
        checkContext(context);
        checkFilePath(filePath);
        checkMd5(md5);

        String temp = getAssetFileMD5(context, filePath);
        if (!TextUtils.isEmpty(temp) && md5.toLowerCase().equals(temp.toLowerCase())) {
            return true;
        }
        return false;
    }

    public String getAssetFileMD5(Context context, String filePath) throws Exception {
        checkContext(context);
        checkFilePath(filePath);

        InputStream in = context.getResources().getAssets().open(filePath);
        return encryptMD5ToHexString(InputStreamToByte(in));
    }

    private byte[] InputStreamToByte(InputStream is) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer=new byte[1024 * 8];
        int ch;
        while ((ch = is.read(buffer)) != -1) {
            outputStream.write(buffer,0,ch);
        }
        byte data[] = outputStream.toByteArray();
        outputStream.close();
        return data;
    }

    public String encryptSHA1ToHexString(String sData) throws Exception {
        checkData(sData);
        return ConvertUtil.bytes2HexString(encryptSHA1(sData.getBytes()));
    }

    public byte[] encryptSHA1(byte[] data) throws Exception {
        return mStrategy.encryptSHA1(data);
    }

    public String encryptSHA256ToHexString(String sData) throws Exception {
        checkData(sData);
        return ConvertUtil.bytes2HexString(encryptSHA256(sData.getBytes()));
    }

    public byte[] encryptSHA256(byte[] data) throws Exception {
        return mStrategy.encryptSHA256(data);
    }

    public String encryptSM3ToHexString(String sData) throws Exception {
        checkData(sData);
        return ConvertUtil.bytes2HexString(encryptSM3(sData.getBytes()));
    }

    public byte[] encryptSM3(byte[] data) throws Exception {
        return mStrategy.encryptSM3(data);
    }

    public String encryptAES128_ECB2Base64(String sData, String sKey) throws Exception {
        checkData(sData);
        checkKey(sKey);
        return ConvertUtil.base64Encode2String(encryptAES128_ECB(sData.getBytes(), sKey.getBytes()));
    }

    public byte[] encryptAES128_ECB(byte[] data, byte[] key) throws Exception {
        checkCryptoStrategy();
        return mStrategy.encryptAES128_ECB(data, key);
    }

    public String decryptBase64AES128_ECB(String sData, String sKey) throws Exception {
        checkData(sData);
        checkKey(sKey);
        return new String(decryptAES128_ECB(ConvertUtil.base64Decode(sData.getBytes()), sKey.getBytes()));
    }

    public byte[] decryptAES128_ECB(byte[] data, byte[] key) throws Exception {
        checkCryptoStrategy();
        return mStrategy.decryptAES128_ECB(data, key);
    }

    public String encryptAES256_ECB2Base64(String sData, String sKey) throws Exception {
        checkData(sData);
        checkKey(sKey);
        return ConvertUtil.base64Encode2String(encryptAES256_ECB(sData.getBytes(), sKey.getBytes()));
    }

    public byte[] encryptAES256_ECB(byte[] data, byte[] key) throws Exception {
        checkCryptoStrategy();
        return mStrategy.encryptAES256_ECB(data, key);
    }

    public String decryptBase64AES256_ECB(String sData, String sKey) throws Exception {
        checkData(sData);
        checkKey(sKey);
        return new String(decryptAES256_ECB(ConvertUtil.base64Decode(sData.getBytes()), sKey.getBytes()));
    }

    public byte[] decryptAES256_ECB(byte[] data, byte[] key) throws Exception {
        checkCryptoStrategy();
        return mStrategy.decryptAES256_ECB(data, key);
    }

    public String encryptAES128_CBC2Base64(String sData, String sKey, String sIv) throws Exception {
        checkData(sData);
        checkKey(sKey);
        checkIv(sIv);
        byte[] bytes = encryptAES128_CBC(sData.getBytes(), sKey.getBytes(), sIv.getBytes());
        return ConvertUtil.base64Encode2String(bytes);
    }

    public byte[] encryptAES128_CBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        checkCryptoStrategy();
        return mStrategy.encryptAES128_CBC(data, key, iv);
    }

    public String decryptBase64AES128_CBC(String sData, String sKey, String sIv) throws Exception {
        checkData(sData);
        checkKey(sKey);
        checkIv(sIv);
        byte[] bytes = decryptAES128_CBC(ConvertUtil.base64Decode(sData.getBytes()), sKey.getBytes(), sIv.getBytes());
        return new String(bytes);
    }

    public byte[] decryptAES128_CBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        checkCryptoStrategy();
        return mStrategy.decryptAES128_CBC(data, key, iv);
    }

    public String encryptAES256_CBC2Base64(String sData, String sKey, String sIv) throws Exception {
        checkData(sData);
        checkKey(sKey);
        checkIv(sIv);
        byte[] bytes = encryptAES256_CBC(sData.getBytes(), sKey.getBytes(), sIv.getBytes());
        return ConvertUtil.base64Encode2String(bytes);
    }

    public String decryptBase64AES256_CBC(String sData, String sKey, String sIv) throws Exception {
        checkData(sData);
        checkKey(sKey);
        checkIv(sIv);
        byte[] bytes = decryptAES256_CBC(ConvertUtil.base64Decode(sData.getBytes()), sKey.getBytes(), sIv.getBytes());
        return new String(bytes);
    }

    public String encryptAES256_CBC2HexString(String sData, String sKey, String sIv) throws Exception {
        checkData(sData);
        checkKey(sKey);
        checkIv(sIv);
        byte[] bytes = encryptAES256_CBC(sData.getBytes(), sKey.getBytes(), sIv.getBytes());
        return ConvertUtil.bytes2HexString(bytes);
    }

    public String decryptHexStringAES256_CBC(String sData, String sKey, String sIv) throws Exception {
        checkData(sData);
        checkKey(sKey);
        checkIv(sIv);
        byte[] bytes = decryptAES256_CBC(ConvertUtil.hexString2Bytes(sData), sKey.getBytes(), sIv.getBytes());
        return new String(bytes);
    }

    public byte[] encryptAES256_CBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        checkCryptoStrategy();
        return mStrategy.encryptAES256_CBC(data, key, iv);
    }

    public byte[] decryptAES256_CBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        checkCryptoStrategy();
        return mStrategy.decryptAES256_CBC(data, key, iv);
    }

    public String encryptRSA2HexString(String sData, String sKey) throws Exception {
        checkData(sData);
        checkKey(sKey);
        byte[] bytes = encryptRSA(sData.getBytes(), ConvertUtil.base64Decode(sKey.getBytes()));
        return ConvertUtil.bytes2HexString(bytes);
    }

    public String decryptHexStringRSA(String sData, String sKey) throws Exception {
        checkData(sData);
        checkKey(sKey);
        byte[] bytes = decryptRSA(ConvertUtil.hexString2Bytes(sData),
                ConvertUtil.base64Decode(sKey.getBytes()));
        return new String(bytes);
    }

    public String encryptRSA2Base64(String sData, String sKey) throws Exception {
        checkData(sData);
        checkKey(sKey);
        byte[] bytes = encryptRSA(sData.getBytes(), ConvertUtil.base64Decode(sKey.getBytes()));
        return ConvertUtil.base64Encode2String(bytes);
    }

    public String decryptBase64RSA(String sData, String sKey) throws Exception {
        checkData(sData);
        checkKey(sKey);
        byte[] bytes = decryptRSA(ConvertUtil.base64Decode(sData.getBytes()),
                ConvertUtil.base64Decode(sKey.getBytes()));
        return new String(bytes);
    }

    public byte[] encryptRSA(byte[] data, byte[] key) throws Exception {
        checkCryptoStrategy();
        return mStrategy.encryptRSA(data, key);
    }

    public byte[] decryptRSA(byte[] data, byte[] key) throws Exception {
        checkCryptoStrategy();
        return mStrategy.decryptRSA(data, key);
    }

    public String rsaSign(String data, String privateKey, RSASignHashType rsaSignHashType) throws Exception {
        checkData(data);
        checkKey(privateKey);
        checkCryptoStrategy();
        return mStrategy.rsaSign(data, privateKey, rsaSignHashType);
    }

    public boolean rsaVerifyByPublicKey(String pbySrcData, String pbyPublicKey,
                                        String pbySignature, RSASignHashType rsaSignHashType) throws Exception {
        checkData(pbySrcData);
        checkKey(pbyPublicKey);
        if (TextUtils.isEmpty(pbySignature)) {
            throw new NullPointerException("签名数据 为空");
        }
        return mStrategy.rsaVerifyByPublicKey(pbySrcData, pbyPublicKey, pbySignature, rsaSignHashType);
    }

    public String encryptSM2ToBase64(String sData, String sKey) throws Exception {
        checkData(sData);
        checkKey(sKey);
        byte[] bytes = encryptSM2(sData.getBytes(),
                ConvertUtil.base64Decode(sKey.getBytes()));
        return ConvertUtil.base64Encode2String(bytes);
    }

    public String decryptBase64SM2(String sData, String sKey) throws Exception {
        checkData(sData);
        checkKey(sKey);
        byte[] bytes = decryptSM2(ConvertUtil.base64Decode(sData.getBytes()),
                ConvertUtil.base64Decode(sKey.getBytes()));
        return new String(bytes);
    }

    public byte[] encryptSM2(byte[] data, byte[] publicKey) throws Exception {
        checkCryptoStrategy();
        return mStrategy.encryptSM2(data, publicKey);
    }

    public byte[] decryptSM2(byte[] data, byte[] privateKey) throws Exception {
        checkCryptoStrategy();
        return mStrategy.decryptSM2(data, privateKey);
    }

    public String encryptSM4_CBC2HexString(String sData, String sKey, String sIv) throws Exception {
        checkData(sData);
        checkKey(sKey);
        checkIv(sIv);
        byte[] bytes = encryptSM4_CBC(sData.getBytes(), sKey.getBytes(), sIv.getBytes());
        return ConvertUtil.bytes2HexString(bytes);
    }

    public String decryptHexStringSM4_CBC(String sData, String sKey, String sIv) throws Exception {
        checkData(sData);
        checkKey(sKey);
        checkIv(sIv);
        byte[] bytes = decryptSM4_CBC(ConvertUtil.hexString2Bytes(sData), sKey.getBytes(), sIv.getBytes());
        return new String(bytes);
    }

    public String encryptSM4_CBC2Base64(String sData, String sKey, String sIv) throws Exception {
        checkData(sData);
        checkKey(sKey);
        checkIv(sIv);
        byte[] bytes = encryptSM4_CBC(sData.getBytes(), sKey.getBytes(), sIv.getBytes());
        return ConvertUtil.base64Encode2String(bytes);
    }

    public String decryptBase64SM4_CBC(String sData, String sKey, String sIv) throws Exception {
        checkData(sData);
        checkKey(sKey);
        checkIv(sIv);
        byte[] bytes = decryptSM4_CBC(ConvertUtil.base64Decode(sData.getBytes()), sKey.getBytes(), sIv.getBytes());
        return new String(bytes);
    }

    public byte[] encryptSM4_CBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        checkCryptoStrategy();
        return mStrategy.encryptSM4_CBC(data, key, iv);
    }

    public byte[] decryptSM4_CBC(byte[] data, byte[] key, byte[] iv) throws Exception {
        checkCryptoStrategy();
        return mStrategy.decryptSM4_CBC(data, key, iv);
    }

    public byte[] messageEncrypt(byte[] data, byte[] key) throws Exception {
        checkCryptoStrategy();
        return mStrategy.messageEncrypt(data, key);
    }

    public byte[] messageDecrypt(byte[] data, byte[] key) throws Exception {
        checkCryptoStrategy();
        return mStrategy.messageDecrypt(data, key);
    }

    public String messageEncrypt2Base64(String sData, String sKey) throws Exception {
        checkData(sData);
        checkKey(sKey);
        byte[] bytes = messageEncrypt(sData.getBytes(), sKey.getBytes());
        return ConvertUtil.base64Encode2String(bytes);
    }

    public String messageDecryptBase64(String sData, String sKey) throws Exception {
        checkData(sData);
        checkKey(sKey);
        byte[] bytes = mStrategy.messageDecrypt(ConvertUtil.base64Decode(sData.getBytes()), sKey.getBytes());
        return new String(bytes);
    }

    private void checkCryptoStrategy() {
        if (mStrategy == null) {
            throw new NullPointerException("需要创建ICryptoStrategy实例，" +
                    "并调用setCryptoStrategy(ICryptoStrategy iCryptoStrategy)");
        }
    }

    private void checkKey(String sKey) {
        if (TextUtils.isEmpty(sKey)) {
            throw new NullPointerException("key 为空");
        }
    }

    private void checkData(String sData) {
        if (TextUtils.isEmpty(sData)) {
            throw new NullPointerException("data 为空");
        }
    }

    private void checkIv(String sIv) {
        if (TextUtils.isEmpty(sIv)) {
            throw new NullPointerException("iv 为空");
        }
    }

    private void checkContext(Context context) {
        if (context == null) {
            throw new NullPointerException("context 为空");
        }
    }

    private void checkFilePath(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            throw new NullPointerException("filePath 为空");
        }
    }

    private void checkMd5(String md5) {
        if (TextUtils.isEmpty(md5)) {
            throw new NullPointerException("md5 为空");
        }
    }
}
