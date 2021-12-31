package com.manage.common.util.xiuTool;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class CipherTool {

    /**
     *      * 使用AES加密数据,加密模式是CBC
     *      *
     *      * @param input          : 原文
     *      * @param key            : 密钥(DES,密钥的长度16个字节)
     *      * @return : 密文
     *      * @throws Exception
     */
    public static String encryptAES(String encryptData) throws Exception {
        String key = "JSR8Z1iIs8xQMGeu";

        String transformation = "AES/CBC/PKCS5Padding";
        String algorithm = "AES";

        // 获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 创建加密规则
        // 第一个参数key的字节
        // 第二个参数表示加密算法
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);
        // 创建iv向量，iv向量，是使用到CBC加密模式
        // 在使用iv向量进行加密的时候，iv的字节也必须是8个字节
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        // ENCRYPT_MODE：加密模式
        // DECRYPT_MODE: 解密模式
        // 初始化加密模式和算法
        cipher.init(Cipher.ENCRYPT_MODE,sks,iv);
        // 加密
        byte[] bytes = cipher.doFinal(encryptData.getBytes());

        // 输出加密后的数据
        String encode = Base64.encodeBase64String(bytes).replaceAll("\r|\n", "");
        return encode;

    }

    /**
     * 使用AES解密数据，加密模式是CBC
     * @param encryptData  密文
     * @return 解密数据
     */
    public static String decryptAES(String encryptData) throws Exception {
        String key = "JSR8Z1iIs8xQMGeu";

        String transformation = "AES/CBC/PKCS5Padding";
        String algorithm = "AES";

        // 获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 创建加密规则
        // 第一个参数key的字节
        // 第二个参数表示加密算法
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),algorithm);
        // 创建iv向量，iv向量，是使用到CBC加密模式
        // 在使用iv向量进行加密的时候，iv的字节也必须是8个字节
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        //Cipher.DECRYPT_MODE:表示解密
        // 解密规则
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,iv);
        // 解密，传入密文
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(encryptData));

        return new String(bytes);
    }


    /**
     *      * 使用AES加密数据,加密模式是CBC
     *      *
     *      * @param input          : 原文
     *      * @param key            : 密钥(DES,密钥的长度16个字节)
     *      * @return : 密文
     *      * @throws Exception
     */

    public static String encryptAES(String input,String key) throws Exception {

        String transformation = "AES/CBC/PKCS5Padding";
        String algorithm = "AES";


        // 获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 创建加密规则
        // 第一个参数key的字节
        // 第二个参数表示加密算法
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);
        // 创建iv向量，iv向量，是使用到CBC加密模式
        // 在使用iv向量进行加密的时候，iv的字节也必须是8个字节
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        // ENCRYPT_MODE：加密模式
        // DECRYPT_MODE: 解密模式
        // 初始化加密模式和算法
        cipher.init(Cipher.ENCRYPT_MODE,sks,iv);
        // 加密
        byte[] bytes = cipher.doFinal(input.getBytes());

        // 输出加密后的数据
        String encode = Base64.encodeBase64String(bytes).replaceAll("\r|\n", "");


        return encode;
    }

    /**
     * 使用AES解密数据，加密模式是CBC
     * @param encryptData  密文
     * @param key         密钥
     * @return 解密数据
     */
    public static String decryptAES(String encryptData, String key) throws Exception {
        String transformation = "AES/CBC/PKCS5Padding";
        String algorithm = "AES";

        // 获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 创建加密规则
        // 第一个参数key的字节
        // 第二个参数表示加密算法
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),algorithm);
        // 创建iv向量，iv向量，是使用到CBC加密模式
        // 在使用iv向量进行加密的时候，iv的字节也必须是8个字节
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        //Cipher.DECRYPT_MODE:表示解密
        // 解密规则
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,iv);
        // 解密，传入密文
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(encryptData));

        return new String(bytes);
    }
    /**
     * 使用私钥加密数据
     *
     * @param input          : 原文
     * @param privatePath     : 私钥路径
     * @return : 密文
     * @throws Exception
     */

    public static String encryptPriRSA(String input, String privatePath) throws Exception {
        String algorithm = "RSA";
        PrivateKey privateKey = getPrivateKey(privatePath);

        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 对加密进行初始化
        // 第一个参数：加密的模式
        // 第二个参数：私钥进行加密
        cipher.init(Cipher.ENCRYPT_MODE,privateKey);
        // 使用私钥进行加密
        byte[] bytes = cipher.doFinal(input.getBytes());

        String result = java.net.URLEncoder.encode(Base64.encodeBase64String(bytes).replaceAll("\r|\n", ""), "UTF-8");
        return result;
    }
    /**
     * 使用公钥加密数据
     *
     * @param input          : 原文
     * @param publicPath      : 公钥路径
     * @return : 密文
     * @throws Exception
     */

    public static String encryptPubRSA(String input,String publicPath) throws Exception {
        String algorithm = "RSA";
        PublicKey publicKey = getPublicKey(publicPath);

        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 对加密进行初始化
        // 第一个参数：加密的模式
        // 第二个参数：公钥进行加密
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        // 使用公钥进行加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        return Base64.encodeBase64String(bytes).replaceAll("\r|\n", "");
    }

    /**
     * 使用私钥解密数据
     *
     * @param encrypted      : 密文
     * @param privatePath     : 私钥路径
     * @return : 原文
     * @throws Exception
     */

    public static String decryptPriRSA(String encrypted,String privatePath) throws Exception {
        String algorithm = "RSA";
        PrivateKey privateKey = getPrivateKey(privatePath);

        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 私钥解密
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        // 使用base64进行转码
        byte[] decode = Base64.decodeBase64(encrypted);

        // 使用私钥进行解密
        byte[] bytes1 = cipher.doFinal(decode);
        return new String(bytes1);
    }
    /**
     * 使用公钥解密数据
     *
     * @param encrypted      : 密文
     * @param publicPath      : 公钥路径
     * @return : 原文
     * @throws Exception
     */

    public static String decryptPubRSA(String encrypted, String publicPath) throws Exception {
        String decode1 = encrypted;

        if(encrypted.contains("%")){
            decode1 = URLDecoder.decode(encrypted, "UTF-8");

        }
        String algorithm = "RSA";
        PublicKey publicKey = getPublicKey(publicPath);

        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 公钥解密
        cipher.init(Cipher.DECRYPT_MODE,publicKey);
        // 使用base64进行转码
        byte[] decode = Base64.decodeBase64(decode1);

        // 使用私钥进行解密
        byte[] bytes1 = cipher.doFinal(decode);
        return new String(bytes1);
    }


    /**
     * 保存公钥和私钥，把公钥和私钥保存到根目录
     * @param algorithm 算法
     * @param pubPath 公钥路径
     * @param priPath 私钥路径
     */

    public static void generateKeyToFile(String algorithm, String pubPath, String priPath) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 生成私钥
        PrivateKey privateKey = keyPair.getPrivate();
        // 生成公钥
        PublicKey publicKey = keyPair.getPublic();
        // 获取私钥的字节数组
        byte[] privateKeyEncoded = privateKey.getEncoded();
        // 获取公钥字节数组
        byte[] publicKeyEncoded = publicKey.getEncoded();
        // 使用base64进行编码
        String privateEncodeString = Base64.encodeBase64String(privateKeyEncoded);
        String publicEncodeString = Base64.encodeBase64String(publicKeyEncoded);
        // 把公钥和私钥保存到根目录
        FileUtils.writeStringToFile(new File(pubPath),publicEncodeString, Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File(priPath),privateEncodeString, Charset.forName("UTF-8"));

    }


    /**
     * 读取公钥
     * @param publicPath 公钥路径
     * @return
     */
    public static PublicKey getPublicKey(String publicPath) throws Exception{
        String algorithm = "RSA";

        String publicKeyString = FileUtils.readFileToString(new File(publicPath), Charset.defaultCharset());
        // 创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 创建公钥规则
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        return keyFactory.generatePublic(keySpec);
    }

    /**
     *  读取私钥
     * @param priPath 私钥的路径
     * @return 返回私钥的key对象
     */
    public static PrivateKey getPrivateKey(String priPath) throws Exception{
        String algorithm = "RSA";

        String privateKeyString = FileUtils.readFileToString(new File(priPath), Charset.defaultCharset());
        // 创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 创建私钥key的规则
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        // 返回私钥对象
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 校验签名
     * @param input 表示原文
     * @param algorithm 表示算法
     * @param publicKey 公钥key
     * @param signaturedData 签名密文
     * @return
     */
    public static boolean verifySignature(String input, String algorithm, PublicKey publicKey, String signaturedData) throws Exception{
        // 获取签名对象
        Signature signature = Signature.getInstance(algorithm);
        // 初始化校验
        signature.initVerify(publicKey);
        // 传入原文
        signature.update(input.getBytes());
        // 校验数据
        return signature.verify(Base64.decodeBase64(signaturedData));
    }

    /**
     * 生成数字签名
     * @param input 表示原文
     * @param algorithm  表示算法
     * @param privateKey 私钥key
     * @return
     */
    public static String getSignature(String input, String algorithm, PrivateKey privateKey) throws Exception{
        // 获取签名对象
        Signature signature = Signature.getInstance(algorithm);
        // 初始化签名
        signature.initSign(privateKey);
        // 传入原文
        signature.update(input.getBytes());
        // 开始签名
        byte[] sign = signature.sign();
        // 使用base64进行编码
        return Base64.encodeBase64String(sign);
    }

    /**
     * 生成AES算法的随机密钥
     * @return
     */
    public static String RandomKey16AES() throws Exception{
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecretKey deskey = keygen.generateKey();
        String keytest = java.util.Base64.getEncoder().encodeToString(deskey.getEncoded());
        String key = keytest.substring(0, 16);

        return key;

    }

    /**
     * 生成AES算法的随机密钥
     * @return
     */
    public static String getKeyAES() throws Exception{
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecretKey deskey = keygen.generateKey();
        String keytest = java.util.Base64.getEncoder().encodeToString(deskey.getEncoded());
        String key = keytest.substring(0, 16);

        return key;
    }
}
