package com.sophon.schedule.api;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.sun.org.apache.bcel.internal.classfile.ConstantString;
import jdk.nashorn.internal.ir.debug.ClassHistogramElement;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;


/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/12/1 16:00
 */
public class EncryptionUtil {
    static final String originKeyStr = "0123456789Abc@@@"; // 必须16个字符
    private static final String ENCODE_KEY = "0123456789Abc@@@";
    private static final String IV_KEY = "0000000000000000";
    private static SymmetricCrypto aes;


    // 加密并编码字符串
    public static String encryptURLEncodeStr(String str) {
        try {
            if (aes == null) {
                SecretKey aesKey = new SecretKeySpec(originKeyStr.getBytes(StandardCharsets.UTF_8), "AES");
                byte[] key = aesKey.getEncoded();
                aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
            }
            byte[] encrypt = aes.encrypt(str.getBytes(StandardCharsets.UTF_8));
            String s = Base64.getUrlEncoder().encodeToString(encrypt);
            return s;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    // 解码并解密字符串
    public static String decryptURLDecodeStr(String encStr) {
        try {
            if (aes == null) {
                SecretKey aesKey = new SecretKeySpec(originKeyStr.getBytes(StandardCharsets.UTF_8), "AES");
                byte[] key = aesKey.getEncoded();
                aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
            }
            byte[] bytes = Base64.getUrlDecoder().decode(encStr);
            String s = aes.decryptStr(bytes);
            return s;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String encryptFromString(String data, Mode mode, Padding padding) {
        AES aes;
        if (Mode.CBC == mode) {
            aes = new AES(mode, padding,
                    new SecretKeySpec(ENCODE_KEY.getBytes(), "AES"),
                    new IvParameterSpec(IV_KEY.getBytes()));
        } else {
            aes = new AES(mode, padding,
                    new SecretKeySpec(ENCODE_KEY.getBytes(), "AES"));
        }
        return aes.encryptBase64(data, StandardCharsets.UTF_8);
    }

    public static String decryptFromString(String data, Mode mode, Padding padding) {
        AES aes;
        if (Mode.CBC == mode) {
            aes = new AES(mode, padding,
                    new SecretKeySpec(ENCODE_KEY.getBytes(), "AES"),
                    new IvParameterSpec(IV_KEY.getBytes()));
        } else {
            aes = new AES(mode, padding,
                    new SecretKeySpec(ENCODE_KEY.getBytes(), "AES"));
        }
        byte[] decryptDataBase64 = aes.decrypt(data);
        return new String(decryptDataBase64, StandardCharsets.UTF_8);
    }

    private static String publicKeyToString(PublicKey publicKey) {
        byte[] publicKeyBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }
    private static String privateKeyToString(PrivateKey privateKey) {
        byte[] privateKeyBytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(privateKeyBytes);
    }

    public static void main(String[] args) {
        String str = "http://192.168.217.162:9000/shortv/2023/12/01/cb80ea9001c74d389a1a5ef2ca4c8578.html";
//        String encodeStr = encryptURLEncodeStr(str);
//        System.out.println(encodeStr);
//        System.out.println(decryptURLDecodeStr(encodeStr));



        KeyPair keyPair = SecureUtil.generateKeyPair(AsymmetricAlgorithm.RSA.getValue());
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        System.out.println("公钥="+publicKeyToString(publicKey));
        System.out.println("私钥="+privateKeyToString(privateKey));
        System.out.println("----------");


    }
}
