package com.pk.springboot;


import org.apache.tomcat.util.codec.binary.Base64;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: TODO
 * @author: wjl
 * @date: 2021/6/4 15:31
 * @version: v1.0
 */
public class asd {
    public static void main(String[] args) throws Exception {

        Map<String, String> keyMap = new HashMap<>();//genKeyPair();
        keyMap.put("publicKey", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4Ii8WyD+NcY/2xvIsoOQFtppOAg/ETBo8wzghFbeF8B+YDoDVmQISFaRvbmSPdES/J8YsjAbLyhSgahjrqLthDCEF4uZ2lfv9BK3E9AtHIQMvTa275OfoNFeNMkfpnU5WEmbdWoWtQhZg5UXGjUo2m358UiBykeWHYgZ8pHY8AwIDAQAB");
        keyMap.put("privateKey", "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALgiLxbIP41xj/bG8iyg5AW2mk4CD8RMGjzDOCEVt4XwH5gOgNWZAhIVpG9uZI90RL8nxiyMBsvKFKBqGOuou2EMIQXi5naV+/0ErcT0C0chAy9Nrbvk5+g0V40yR+mdTlYSZt1aha1CFmDlRcaNSjabfnxSIHKR5YdiBnykdjwDAgMBAAECgYAWc5yYQ4x0O6+ZIILw7CFBjRVdg6TWg40Dca5LYnGBNFk12jbdVI3LFLZ7G9PJJh0nzbq9PcmqWZaQRL+LJITMSDdPkq9N6MpPZYwGPbnaq3Tpfv/nAX4B/6y/jFsrmQv+QMhv2Ymz8UjsnC5Alwl8rMbqFjECpeeQ2cXXMn+tQQJBAPPhGITlNrnn9HJlld8m7p0pM+0/ou8akpDS+kLPQ8eutK6zYH+HwBakfTrkBLVrSR/L1ea3VykuZZlMWgWMuuMCQQDBSO+kU/KNvjwnbaQNneLFy4kCxqUa7nGuMahVCNfuj4nqCcXFMI9eWrsKXcKSIDnx3CQgJ6FrJKvI1UVODqRhAkB9SmYzWCK+bYkrAD93zmOGADX6K9hEKI2ls434psy2mG2g1uy7d/1aZJUlnSFCJuUBXdH9XrF7qaduk0goU71rAkEAvIlfsMRuKdMxrCgnyVyEEvpJyFpqPM3wN8GaeG/q6Xo8Rf2IW6PVhW0tW5w/qfgjbYeV+YOO0gtwQbO/KbfgoQJBAOPLHp61R4m4O9AvGJigd7yjpL6YNalNjmA6xUU294LG2sSg6UtSrvHjbNYsQlmSQHxU0ZIC64xwp3ijos17HLw=");
        String publicKey = keyMap.get("publicKey");
        String privateKey = keyMap.get("privateKey");
        String message = "04b29480233f4def5c875875b6bdc3b1";
        String point = "财信人寿jxlife921";

        String messageEn = publicKeyEncrypt(message, publicKey, point);
        System.out.println("前端请求的加密:\n" + messageEn);

        String messageDe = privateKeyDecrypt(messageEn, privateKey, point);
        System.out.println("后端解密出来的数据:\n" + messageDe);

    }

    /**
     * 随机生成密钥对
     *
     * @param point
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, String> genKeyPair(String point) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        Map<String, String> map = new HashMap<>();
        map.put("publicKey", publicKeyString);
        map.put("privateKey", privateKeyString);
        return map;
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String publicKeyEncrypt(String str, String publicKey, String point) throws Exception {
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").
                generatePublic(new X509EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @param point
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String privateKeyDecrypt(String str, String privateKey, String point) throws Exception {
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }


    /**
     * RSA私钥加密
     *
     * @param str
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String privateKeyEncrypt(String str, String privateKey, String point) throws Exception {
        byte[] decoded = Base64.decodeBase64(privateKey);
        PrivateKey priKey = KeyFactory.getInstance("RSA").
                generatePrivate(new PKCS8EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes()));
        return outStr;
    }

    /**
     * RSA公钥解密
     *
     * @param str
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String publicKeyDecrypt(String str, String publicKey, String point) throws Exception {
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        byte[] decoded = Base64.decodeBase64(publicKey);
        PublicKey pubKey = KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}
