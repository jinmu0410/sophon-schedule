package com.sophon.schedule.api;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * TODO
 *
 * @Author jinmu
 * @Date 2023/12/1 23:13
 */
public class TestRSA {

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = generateKeyPair();

        // 获取公钥和私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 将公钥和私钥转换为字符串
        String publicKeyString = publicKeyToString(publicKey);
        String privateKeyString = privateKeyToString(privateKey);

        System.out.println("Public Key:\n" + publicKeyString);
        System.out.println("Private Key:\n" + privateKeyString);


        //String publicKeyString = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKoR8mX0rGKLqzcWmOzbfj64K8ZIgOdHnzkXSOVOZbFu/TJhZ7rFAN+eaGkl3C4buccQd/EjEsj9ir7ijT7h96MCAwEAAQ==";
        //String privateKeyString = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAqhHyZfSsYourNxaY7Nt+PrgrxkiA50efORdI5U5lsW79MmFnusUA355oaSXcLhu5xxB38SMSyP2KvuKNPuH3owIDAQABAkAfoiLyL+Z4lf4Myxk6xUDgLaWGximj20CUf+5BKKnlrK+Ed8gAkM0HqoTt2UZwA5E2MzS4EI2gjfQhz5X28uqxAiEA3wNFxfrCZlSZHb0gn2zDpWowcSxQAgiCstxGUoOqlW8CIQDDOerGKH5OmCJ4Z21v+F25WaHYPxCFMvwxpcw99EcvDQIgIdhDTIqD2jfYjPTY8Jj3EDGPbH2HHuffvflECt3Ek60CIQCFRlCkHpi7hthhYhovyloRYsM+IS9h/0BzlEAuO0ktMQIgSPT3aFAgJYwKpqRYKlLDVcflZFCKY7u3UP8iWi1Qw0Y=";

        // 前端通过 publicKeyString 使用公钥加密数据
        String encryptedData = encryptWithPublicKey("Hello, RSA!", publicKeyString);
        System.out.println("Encrypted Data (Base64):\n" + encryptedData);

        // 后端使用 privateKeyString 解密数据
        String decryptedData = decryptWithPrivateKey(encryptedData, privateKeyString);
        System.out.println("Decrypted Data:\n" + decryptedData);
    }

    private static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    private static String publicKeyToString(PublicKey publicKey) {
        byte[] publicKeyBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }

    private static String privateKeyToString(PrivateKey privateKey) {
        byte[] privateKeyBytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(privateKeyBytes);
    }

    private static String encryptWithPublicKey(String data, String publicKeyString) throws Exception {
        PublicKey publicKey = stringToPublicKey(publicKeyString);
        // TODO: 使用公钥 publicKey 加密数据
        RSA rsa = new RSA();
        rsa.setPublicKey(publicKey);
        return rsa.encryptBase64(data.getBytes(StandardCharsets.UTF_8), KeyType.PublicKey);
    }

    private static String decryptWithPrivateKey(String encryptedData, String privateKeyString) throws Exception {
        PrivateKey privateKey = stringToPrivateKey(privateKeyString);
        RSA rsa = new RSA();
        rsa.setPrivateKey(privateKey);
        // TODO: 使用私钥 privateKey 解密数据
        return rsa.decryptStr(encryptedData,KeyType.PrivateKey);
    }

    private static PublicKey stringToPublicKey(String publicKeyString) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(keySpec);
    }

    private static PrivateKey stringToPrivateKey(String privateKeyString) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return keyFactory.generatePrivate(keySpec);
    }
}

