散列 也叫杂凑、哈希；就是把任意长度的输入，通过散列算法，变换成固定长度的输出，该输出就是散列值。这种转换是一种压缩映射，也就是，散列值的空间通常远小于输入的空间，不同的输入可能会散列成相同的输出，而不可能从散列值来唯一的确定输入值。简单的说就是一种将任意长度的消息压缩到某一固定长度的消息摘要的函数。

# 支持的算法

- MD5

> MD5消息摘要算法（MD5 Message-Digest Algorithm），一种被广泛使用的密码散列函数，可以产生出一个128位（16字节）的散列值（hash value）.
> 严格来说，MD5没有16或者32位之说，结果一定是128比特或16字节，其输入与输出均是原始字节流

- SHA1

> 安全哈希算法（Secure Hash Algorithm）主要适用于数字签名标准 （Digital Signature Standard DSS）里面定义的数字签名算法（Digital Signature Algorithm DSA）。对于长度小于2^64位的消息，SHA1会产生一个160位的消息摘要

- SHA256

> SHA256 算法的哈希值大小为 256 位。

- SHA512

> SHA512 算法的哈希值大小为 512 位。

- SM3

> SM3是中华人民共和国政府采用的一种密码散列函数标准,相关标准为“GM/T 0004-2012 《SM3密码杂凑算法》”。
> 在商用密码体系中，SM3主要用于数字签名及验证、消息认证码生成及验证、随机数生成等，其算法公开。据国家密码管理局表示，其安全性及效率与SHA256相当。
> SM3 算法的哈希值大小为 256 位。

# 使用示例

crypto_adapter力求提供简洁清晰的调用接口，尽量能够`望文生义`。

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

- 计算数据的MD5值

```C
try {
    CryptoUtil.getInstance().encryptMD5ToHexString("Crypto");
	// 也可以直接使用MD5类
	MD5.encryptMD5ToString("Crypto");
} catch (Exception e) {
    e.printStackTrace();
}
```

- 计算数据的SHA256值

```C
try {
    CryptoUtil.getInstance().encryptSHA256ToHexString("Crypto");
	// 也可以直接使用SHA256类
	SHA256.encryptSHA256ToString("Crypto");
} catch (Exception e) {
    e.printStackTrace();
}
```

- 计算数据的SM3值

```C
try {
   CryptoUtil.getInstance().encryptSM3ToHexString("Crypto");
   // 也可以直接使用SM3类
   SM3.encryptSM3ToString("Crypto"); 
} catch (Exception e) {
    e.printStackTrace();
}
```

# 接口（ICryptoStrategy ）

### 计算数据摘要 

```
byte[] encryptMD5(byte[] data);
byte[] encryptMD5File(String filePath);
byte[] encryptSHA1(byte[] data);
byte[] encryptSHA256(byte[] data);
byte[] encryptSM3(byte[] data);
```

|  参数名称   |     说明     |
| :-: | :-:  |
|    data     |     数据    |

返回数据的摘要值

# 工具类（CryptoUtil）

### 计算数据摘要 

```
byte[] encryptMD5(byte[] data);
byte[] encryptMD5File(String filePath);
byte[] encryptSHA1(byte[] data);
byte[] encryptSHA256(byte[] data);
byte[] encryptSM3(byte[] data);
```

### 计算数据摘要，返回十六进制小写表示

``` 
String encryptMD5ToHexString(byte[] data);
String encryptMD5File2HexString(String filePath);
String encryptSHA1ToHexString(byte[] data);
String encryptSHA256ToHexString(byte[] data);
String encryptSM3ToHexString(byte[] data);
```