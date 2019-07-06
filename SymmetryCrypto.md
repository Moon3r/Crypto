对称加密，采用单钥密码系统的加密方法，加密和解密所使用的密钥是同一个。

其安全性不仅取决于加密算法本身，密钥管理的安全性更是重要。

通常对称加密的速度较快，在加密大量数据的场景，对称加密是首选。

# 支持的算法

* AES 

> 高级加密标准（英语：Advanced Encryption Standard，缩写：AES），是美国联邦政府采用的一种区块加密标准。这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用。
AES的区块长度固定为128 比特，密钥长度则可以是128，192或256比特;需要注意，AES中初始化向量(IV)的长度固定为128比特

* SM4

> SM4（原名SMS4）是中华人民共和国政府采用的一种分组密码标准,其设计安全性等同于AES-128，但是近年来的一些密码分析表明的其全性略弱于AES-128.
在商用密码体系中，SM4主要用于数据加密，其算法公开，分组长度与密钥长度均为128bit，


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

* 使用AES256加密数据、解密数据

``` C
//待加密的数据
String aesData = "Crypto AES";

//创建256位（32字节）的密钥
String aesKey = "12345678876543211234567887654321";

//创建128位（16字节）的初始化向量（IV）
String aesIv = "1234567887654321";

try {
   //加密
   String encryptedData = CryptoUtil.getInstance().encryptAES256_CBC2Base64(aesData, aesKey, aesIv);
    
   //解密
   String decryptedData = CryptoUtil.getInstance().decryptBase64AES256_CBC(encryptedData,    aesKey, aesIv);
} catch (Exception e) {
    e.printStackTrace();
}
```

# 接口（ICryptoStrategy）

## AES协议

### 加密模式

* ECB、CBC 加密块链模式 

### 补全模式


* PKCS7

### 加密

```
byte[] encryptAES128_ECB(byte[] data, byte[] key);
byte[] encryptAES256_ECB(byte[] data, byte[] key);
```
|  参数名称   |     说明     |
| :-: | :-:  |
|    data     |     明文    |
|    key     |     密钥，长度为128比特或256比特    |

```
byte[] encryptAES128_CBC(byte[] data, byte[] key, byte[] iv);
byte[] encryptAES256_CBC(byte[] data, byte[] key, byte[] iv);
```
|  参数名称   |     说明     |
| :-: | :-:  |
|    data     |     明文    |
|    key     |     密钥，长度为128比特或256比特   |
|    iv     |    初始化向量,不可为null，且长度为128比特  |

返回值为密文

### 解密
```
byte[] decryptAES128_ECB(byte[] data, byte[] key);
byte[] decryptAES256_ECB(byte[] data, byte[] key);
```
|  参数名称   |     说明     |
| :-: | :-:  |
|    data     |     密文    |
|    key     |     密钥 ；需要和加密时使用的值保持一致   |


```
byte[] decryptAES128_CBC(byte[] data, byte[] key, byte[] iv);
byte[] decryptAES256_CBC(byte[] data, byte[] key, byte[] iv);
```
|  参数名称   |     说明     |
| :-: | :-:  |
|    data     |     密文    |
|    key     |     密钥 ；需要和加密时使用的值保持一致   |
|    iv     |     初始化向量；需要和加密时使用的值保持一致  |

返回值为明文

## SM4协议

### 加密模式

* CBC 加密块链模式 

### 补全模式

* PKCS7

### 加密
```
byte[] encryptSM4_CBC(byte[] data, byte[] key, byte[] iv);
```
|  参数名称   |     说明     |
| :-: | :-:  |
|    data     |     明文    |
|    key     |     密钥，长度为128比特或256比特   |
|    iv     |     初始化向量,不可为null，且长度为128比特  |

返回值为密文

### 解密
```
byte[] decryptSM4_CBC(byte[] data, byte[] key, byte[] iv);
```
|  参数名称   |     说明     |
| :-: | :-:  |
|    data     |     密文    |
|    key     |     密钥 ；需要和加密时使用的值保持一致   |
|    iv     |     初始化向量；需要和加密时使用的值保持一致  |

返回值为明文

### 密钥和初始化向量实现说明

|  算法   |    密钥长度(比特)     |  初始化向量(IV)长度 （比特） | 其他说明|
| :-: | :-: | :-: | :-: |
|    AES    |    128/192/256    |  128 | 无 |
|    SM4    |    128    |  128 | 无|


# 工具类（CryptoUtil）
## AES协议

### 加密

```
public String encryptAES128_ECB2Base64(String sData, String sKey);
public byte[] encryptAES128_ECB(byte[] data, byte[] key);
public String encryptAES256_ECB2Base64(String sData, String sKey);
public byte[] encryptAES256_ECB(byte[] data, byte[] key);
public String encryptAES128_CBC2Base64(String sData, String sKey, String sIv);
public byte[] encryptAES128_CBC(byte[] data, byte[] key, byte[] iv);
public String encryptAES256_CBC2Base64(String sData, String sKey, String sIv);
public String encryptAES256_CBC2HexString(String sData, String sKey, String sIv);
public byte[] encryptAES256_CBC(byte[] data, byte[] key, byte[] iv);
```

### 解密
```
public String decryptBase64AES128_ECB(String sData, String sKey);
public byte[] decryptAES128_ECB(byte[] data, byte[] key);
public String decryptBase64AES256_ECB(String sData, String sKey);
public byte[] decryptAES256_ECB(byte[] data, byte[] key);
public String decryptBase64AES128_CBC(String sData, String sKey, String sIv);
public byte[] decryptAES128_CBC(byte[] data, byte[] key, byte[] iv);
public String decryptBase64AES256_CBC(String sData, String sKey, String sIv);
public String decryptHexStringAES256_CBC(String sData, String sKey, String sIv);
public byte[] decryptAES256_CBC(byte[] data, byte[] key, byte[] iv)
```

## SM4协议

### 加密

```
public String encryptSM4_CBC2Base64(String sData, String sKey, String sIv);
public byte[] encryptSM4_CBC(byte[] data, byte[] key, byte[] iv);
```

### 解密
```
public String decryptBase64SM4_CBC(String sData, String sKey, String sIv);
public byte[] decryptSM4_CBC(byte[] data, byte[] key, byte[] iv);
```

