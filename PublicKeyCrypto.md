
非对称加密，是指加密和解密的密钥不是同一个，是一对密钥--密钥对，分为公钥和私钥。非对称加密算法也称"公钥加密算法"

公钥（publickey），是公开的；私钥（privatekey），只被通信的一方持有，保密的。如果用公钥对数据进行加密，只有用对应的私钥才能解密。

和对称加密比起来，非对称加密通常速度较慢，但安全强度很高。所以，非对称加密一般用来传输或保密对称密钥。

# 支持的算法

* RSA

> RSA加密算法是一种非对称加密算法. 对极大整数做因数分解的难度决定了RSA算法的可靠性。换言之，对一极大整数做因数分解愈困难，RSA算法愈可靠。假如有人找到一种快速因数分解的算法的话，那么用RSA加密的信息的可靠性就肯定会极度下降

* SM2

> SM2是国家密码管理局于2010年12月17日发布的椭圆曲线公钥密码算法，属于ECC算法的一种，其可靠性基于椭圆曲线数学（RSA基于极大整数做因数分解难题）。

# 支持的签名时使用的摘要算法

* MD5 
* SHA1
* SHA256


# 使用示例

```
dependencies {
    implementation project(':crypto_gmssl')
    implementation project(':crypto_adapter')
}
```

```
ICryptoStrategy GmsslStrategy = new GmsslCryptoStrategy();// 创建 GmsslCryptoStrategy
CryptoUtil.getInstance().setCryptoStrategy(GmsslStrategy); // 设置接口实现类
```

* SM2加密

``` C
//待加密数据
String sm2Data = "Crypto";

//SM2 公钥实例
String sm2PublicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE3wMoxl2U0wi5mz9Z6D5+4+//zjEkoVtJDeWL6w45P+incMnggAe0pxqkbf4Qkn8XXnYpTBQR0GJBdIlpRHmpVw==";

//SM2 私钥实例
String sm2PrivateKey = "MHcCAQEEIBfzm3ldrXqtgoGYPsDxDS4dE1yvi0tzOHVZTMcvGgwKoAoGCCqBHM9VAYItoUQDQgAE3wMoxl2U0wi5mz9Z6D5+4+//zjEkoVtJDeWL6w45P+incMnggAe0pxqkbf4Qkn8XXnYpTBQR0GJBdIlpRHmpVw=="；

try {
   //加密
   String encryptedData = CryptoUtil.getInstance().encryptSM2ToBase64(sm2Data, sm2PublicKey);
    
   //解密
   String decryptedData = CryptoUtil.getInstance().decryptBase64SM2(encryptedData, sm2PrivateKey);
} catch (Exception e) {
    e.printStackTrace();
}
```

* RSA私钥签名 公钥验签

``` C
//待签名数据
String rsaData = "Crypto";

//私钥数据
String rsaPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANxvb+XMMMQYWF8aXomQRwm/cnJh6dCQaBiR4b8hUgqWQ8GF3PnvfIyvuO0l/36VLK3Lgu4ycx+9EJu5AfivkeiqrPzhusaYwfDipkoZ0Ax/TJYbcCOMTOL1EE/sGr8svbeOL1Iq0rTtG5Bs2JbMrSG38aXsHrIfINe5shd72BWJAgMBAAECgYAMByib6WBf0tQ/nDi8WNsHdSLRivYvIzIK5SrkOvU3Dqvzy1IbcS3in1P/3KrsRUeC1C/4v1f4y2A0nwu0fh//8Bp3cKoMYdighY9E6FJquyisx++cpgmVnIZW0MqqF7YR6Y7AfmIlS9AZ74oiczsREUYYZ57RzL+xF4w2vkgDYQJBAPpP4ipmy2oHbxQv1K1a0wQr/Ul1vtHQQuTQWh8iIKIcaIKK+9eRAj+kH/BVrqZZp6fO3l+OA3BYdrJq0V91zycCQQDhccEmU56yqk0/TQwTKfs6vYXgjdLbQfKrK60hDZpczUp9kCVHCnRmEINpUhWVCUo3tmrvIFSDJnUgq56zquPPAkAvTaNfAfgU/HyoYvWdIRnZovqFxGOgQOwzU5CEa62hFR9G9D7tmPDE2B5VC8wqkazWUIjDpBHQTFd6FielE51nAkBCrTk6E6sxHmtAWJJErLrwEZOh6XA+hs+1znIi/3nzDoiEgHh1/WKVuTaUlMFrVdcMiTgxRzeXnm0v33JNozJDAkEAy9f4/JIHkLW0u28du+CFtBCdrj9NAJ+vVUwM/kueX2ha2k/3GQquaV99TMT9McUfo8ECGdJaGalQnP6683Ly5A==";

//公钥数据
String rsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcb2/lzDDEGFhfGl6JkEcJv3JyYenQkGgYkeG/IVIKlkPBhdz573yMr7jtJf9+lSyty4LuMnMfvRCbuQH4r5Hoqqz84brGmMHw4qZKGdAMf0yWG3AjjEzi9RBP7Bq/LL23ji9SKtK07RuQbNiWzK0ht/Gl7B6yHyDXubIXe9gViQIDAQAB";

try {
   //给数据签名
   String rsaSignResultData = CryptoUtil.getInstance().rsaSign(rsaData, rsaPrivateKey, RSASignHashType.SHA256);
    
   //验证签名
   boolean rsaVerifyByPublicKey = CryptoUtil.getInstance().rsaVerifyByPublicKey(rsaData, rsaPublicKey, rsaSignResultData, RSASignHashType.SHA256);
} catch (Exception e) {
    e.printStackTrace();
}
```


# 接口（ICryptoStrategy）

## RSA协议
### 加密

采用`pkcs1 padding`补全模式

```
byte[] encryptRSA(byte[] data, byte[] publicKey);

```

|  参数名称   |     说明     |
| :-: | :-:  |
|    data     |     明文    |
|    publicKey     |     密钥(公钥),编码为  ASN.1 DER  |

返回值为密文


### 解密

采用`pkcs1 padding`补全模式

``` 
byte[] decryptRSA(byte[] data, byte[] privateKey);
```
|  参数名称   |     说明     |
| :-: | :-:  |
|    data     |     密文    |
|    privateKey     |     密钥(私钥),编码为  ASN.1 DER；和加密时使用的密钥成对，即公私钥对 |

返回值为明文

### 签名
RSA支持的签名时使用的摘要算法

``` 
public enum RSASignHashType {
    MD5, SHA1, SHA256
}
```

采用`pkcs1 padding`补全模式

```
String rsaSign(String srcData, String privateKey, RSASignHashType rsaSignHashType);
```
|  参数名称   |     说明     |
| :-: | :-:  |
|    srcData     |     需要签名的消息数据    |
|    privatekey     |     私钥   |
|    rsaSignHashType     |     摘要类型 如MD5，SHA256   |

返回值为签名

### 验签

采用`pkcs1 padding`补全模式

```
boolean rsaVerifyByPublicKey(String srcData, 
                      String publicKey, 
                      String signature, 
                      RSASignHashType rsaSignHashType);
```
|  参数名称   |     说明     |
| :-: | :-:  |
|    srcData     |     需要签名的消息数据    |
|    publicKey     |     公钥   |
|    signature     |     签名   |
|    rsaSignHashType     |     摘要类型 如MD5，SHA256   |

返回值为验签结果

## SM2协议

### 加密

密文编码方式 `ASN1.DER`

```
byte[] encryptSM2(byte[] data, byte[] publicKey);
```

|  参数名称   |     说明     |
| :-: | :-:  |
|    data     |     明文    |
|    key     |     密钥    |

返回值为密文


### 解密

密文编码方式 `ASN1.DER`

``` 
byte[] decryptSM2(byte[] data, byte[] privateKey);
```
|  参数名称   |     说明     |
| :-: | :-:  |
|    data     |     密文    |
|    key     |     密钥 ；和加密时使用的密钥成对，即公私钥对   |

返回值为明文

# CryptoUtil（工具类）
## RSA协议

### 加密

```
public String encryptRSA2Base64(String sData, String sKey);
public byte[] encryptRSA(byte[] data, byte[] key);
```

### 解密
```
public String decryptBase64RSA(String sData, String sKey);
public byte[] decryptRSA(byte[] data, byte[] key);
```

### 签名、验签
```
public String rsaSign(String data, String privateKey, RSASignHashType rsaSignHashType)；
public boolean rsaVerifyByPublicKey(String pbySrcData, String pbyPublicKey, String pbySignature, RSASignHashType rsaSignHashType)；
```

## SM2协议

### 加密

```
public String encryptSM2ToBase64(String sData, String sKey);
public byte[] encryptSM2(byte[] data, byte[] publicKey);
```

### 解密
```
public String decryptBase64SM2(String sData, String sKey);
public byte[] decryptSM2(byte[] data, byte[] privateKey);
```