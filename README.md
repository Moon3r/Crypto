# Crypto
这里`Crypto`指代我们平常所说的散列、加密、签名等，这个模块可以实现信息安全的一部分需求

- 保密性
- 真实性
- 完整性

本库提供主流使用的加解密算法，如RSA、AES、SM2、SM4。通过预埋证书，使用签名验签功能，可以提升通信双方的可靠性，保证消息是完整的。

# 功能特性

提供以下实现：

- 散列（Hash），也称之哈希、或者摘要
- 加密解密，包括对称加解密、非对称加解密
- 签名验签

# crypto_adapter模块

## ICryptoStrategy

- 定义Crypto统一接口

- 输入和输出均为byte数组的API，数组均为不经编码的标准数据

  ```
  byte[] encryptRSA(byte[] data, byte[] key) throws Exception;
  ```

## CryptoUtil

- 基于ICryptoStrategy，提供简单的加解密API封装和扩展

- 输入和输出均为byte数组的API，数组均为不经编码的标准数据

- 提供对输入或输出进行编码的API，如返回Base64编码的加密结果

  ```
  String encryptRSA2Base64(String sData, String sKey) throws Exception;
  ```

# crypto_gmssl

需依赖crypto_adapter,使用分为两部分：

- 加解密基础类，如MD5、SHA1、SHA256、SHA512、SM3；AES、SM4、MessageSecurity；RSA、SM2等，使用者依赖firefly_crypto_gmssl后，可直接使用各个基础类的功能
- GmsslCryptoStrategy，Crypto统一接口ICryptoStrategy的实现，内部具体实现依赖加解密基础类

# 如何使用

- 可直接使用其加解密基础类的API,以MD5为例

  ```
  try {
      MD5.encryptMD5("test".getBytes()); // crypto_gmssl
  } catch (Exception e) {
      e.printStackTrace();
  }
  ```

- 可以使用crypto_adapter中CryptoUtil算法工具类。

  ```
  ICryptoStrategy GmsslStrategy = new GmsslCryptoStrategy();// 创建 GmsslCryptoStrategy
  CryptoUtil.getInstance().setCryptoStrategy(GmsslStrategy); // 设置接口实现类
  
  try {
      CryptoUtil.encryptMD5("test".getBytes());
  } catch (Exception e) {
      e.printStackTrace();
  }
  ```

# 如何扩展

自定义加解密库可以实现ICryptoStrategy接口，然后使用CryptoUtil类，这样可以使用统一接口提供的算法，屏蔽现有`crypto_gmssl`和`自定义的加解密库`之间的差异；若统一接口提供的算法无法满足使用，则可以直接使用加解密基础类提供的功能。

# 功能清单

- [摘要](Digest.md)
- [对称加密](SymmetryCrypto.md)
- [非对称加密](PublicKeyCrypto.md)

