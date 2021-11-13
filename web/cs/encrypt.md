Base64

64个可打印字符来表示二进制数据的方法，网络上最常见的用于传输8Bit字节码的编码方式之一。可用于在HTTP环境下传递较长的标识信息。

原理：把每三个8Bit的字节转换为四个6Bit的字节（3*8 = 4*6 = 24），然后把6Bit再添两位高位0，组成四个8Bit的字节。也就是说，转换后的字符串理论上将要比原来的长1/3。



实现

1. 获得byte数组
2. 创建输出的byte数组，长度时之前的3/4
3. 长度有两种情况，能不能被3整除，不能3整除的长度要加上余数+1。比如输入有两个byte，则要用3个byte存，即使有空位。



JDK中自带的Base64工具







```
AES/ECB/PKCS5Padding
```



```
Cipher
```



# AES



是一个**高级加密标准**(Advanced Encryption Standard)，用来替换DES，对称加密算法，分组加密(且分组长度只能是128位)。是一个区块加密标准：置换-组合架构

又称Rijndael加密法，但不完全相同，AES的区块长度固定为128位，密钥长度则可以是128，192或256位。Rijndael使用的密钥和区块长度可以是32位的整数倍，以128位为下限，256位为上限。

AES加密过程是在一个4×4的字节矩阵上运作进行区块交换。



* 明文P
* 密钥K
* 加密函数E
* 解密函数D
* 密文C

加密：`C = E(P, K)`

解密：`P = D(C, K) `



| AES     | 密钥长度（32位比特字) | 分组长度(32位比特字) | 加密轮数 |
| ------- | --------------------- | -------------------- | -------- |
| AES-128 | 4                     | 4                    | 10       |
| AES-192 | 6                     | 4                    | 12       |
| AES-256 | 8                     | 4                    | 14       |

​		AES的加密公式为C = E(K,P)，在加密函数E中，会执行一个轮函数，并且执行10次这个轮函数，这个轮函数的前9次执行的操作是一样的，只有第10次有所不同。

​		AES的处理单位是字节，128位(16字节)，16字节被分成一个4x4的矩阵，

<img src="/Users/chenguanlin/Documents/note/0img/AES矩阵加密.png" alt="image-20210218101829669" style="zoom: 33%;" />





Java实现：

KeyGenerator 此类提供(对称加密算法:AES,DES 等等)密钥生成器的功能。

一般是通过此类的静态方法getInstance()方法获得指定算法的key

初始化KeyGenerator

init(SecureRandom sRandoom);用于初始化KeyGenerator对象,通过随机源的方式
init(int size);通过指定生成秘钥的大小,来初始化的方式
init(AlgorithmParameterSpec params);通过指定参数集来初始化
init(AlgorithmParameterSpec params,SecureRandom sRandoom);通过指定参数集和随机数源的方式生成

SecureRandom，密码学意义上的安全随机数，要求必须保证其**不可预测性**。



非物理真随机数产生器有：

1. Linux操作系统的**/dev/random**设备接口
2. Windows操作系统的CryptGenRandom接口



密码学安全的伪随机数产生器,包括JDK的java.security.SecureRandom等





## DES



AES(Advenced Encryption Standard)高级加密标准，

* 对称/分组密码一般分为流加密(如OFB、CFB等)和块加密(如ECB、CBC等)

* 是美国联邦政府采用的一种区块加密标准，替代原来的DES。

* AES的区块长度固定为128位，密钥长度则可以是128，192或256位
* 对称加密算法(加密和解密密钥相同)
* [参考文档](https://blog.csdn.net/qq_28205153/article/details/55798628)

C = E(K, P)把明文p和密钥k传入加密函数E，得到密文C

P = D(K, C)通过相同的密钥进行解密



AES的基本结构

* AES为分组密码，分组密码也就是把明文分成一组一组的，每组长度相等，每次加密一组数据，直到加密完整个明文。在AES标准规范中，分组长度只能是128位，也就是说，每个分组为16个字节（每个字节8位）。密钥的长度可以使用128位、192位或256位





MD5



Apache.Common的DigestUtils













