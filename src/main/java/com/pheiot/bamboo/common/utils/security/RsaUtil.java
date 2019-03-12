package com.pheiot.bamboo.common.utils.security;

import com.pheiot.bamboo.common.utils.text.EncodeUtil;
import com.google.common.collect.Maps;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

public class RsaUtil {

    private final static String KEY_RSA = "RSA";

    private final static String KEY_RSA_SIGNATURE = "MD5withRSA";

    private final static String KEY_RSA_PUBLICKEY = "RSAPublicKey";

    private final static String KEY_RSA_PRIVATEKEY = "RSAPrivateKey";

    /**
     * key size
     */
    private final static int KEY_SIZE = 1024;


    /**
     * Init key pair.
     *
     * @return
     */
    public static Map<String, Object> generateKeyPair() {
        Map<String, Object> map = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
            generator.initialize(KEY_SIZE);
            KeyPair keyPair = generator.generateKeyPair();

            // Public
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

            // Private
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            // set the key pair in map.
            map = Maps.newHashMap();
            map.put(KEY_RSA_PUBLICKEY, publicKey);
            map.put(KEY_RSA_PRIVATEKEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Generate signature by private key.
     *
     * @param data       data
     * @param privateKey private key
     * @return
     */
    public static String sign(byte[] data, String privateKey) {
        String str = "";
        try {
            // Decode the base64 private key.
            byte[] bytes = EncodeUtil.decodeBase64(privateKey);

            // Build the PKCS8EncodedKeySpec Object.
            PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);

            // Set the crypto.
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);

            // Get private key.
            PrivateKey key = factory.generatePrivate(pkcs);

            // Generate signature by private key.
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initSign(key);
            signature.update(data);
            str = EncodeUtil.encodeBase64(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * Verify sign
     *
     * @param data      data with encrypted
     * @param publicKey public key
     * @param sign      sign
     * @return true/false success/fail
     */
    public static boolean verify(byte[] data, String publicKey, String sign) {
        boolean flag = false;
        try {
            // decode by base64.
            byte[] bytes = EncodeUtil.decodeBase64(publicKey);

            // Get X509EncodedKeySpec obj
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);

            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);

            // Get public key object
            PublicKey key = factory.generatePublic(keySpec);

            // Signature by public key.
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initVerify(key);
            signature.update(data);
            flag = signature.verify(EncodeUtil.decodeBase64(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * Decrypt by private key.
     *
     * @param data data
     * @param key  private key
     * @return
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) {
        byte[] result = null;
        try {
            // decode private key
            byte[] bytes = EncodeUtil.decodeBase64(key);

            // get private key
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);

            // Decrypt data
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Decrypt by public key.
     *
     * @param data data
     * @param key  public key
     * @return
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) {
        byte[] result = null;
        try {
            // decode public key
            byte[] bytes = EncodeUtil.decodeBase64(key);
            // get public key
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            // Decrypt data
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Encrypt by public key.
     *
     * @param data data
     * @param key  public key
     * @return
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) {
        byte[] result = null;
        try {
            byte[] bytes = EncodeUtil.decodeBase64(key);
            // Get public key
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            // Encrypt
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Encrypt private key.
     *
     * @param data data
     * @param key  private key
     * @return
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key) {
        byte[] result = null;
        try {
            byte[] bytes = EncodeUtil.decodeBase64(key);
            // 取得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get public key.
     *
     * @param map
     * @return
     */
    public static String getPublicKey(Map<String, Object> map) {
        String str = "";
        try {
            Key key = (Key) map.get(KEY_RSA_PUBLICKEY);
            str = EncodeUtil.encodeBase64(key.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * Get private key.
     *
     * @param map
     * @return
     */
    public static String getPrivateKey(Map<String, Object> map) {
        String str = "";
        try {
            Key key = (Key) map.get(KEY_RSA_PRIVATEKEY);
            str = EncodeUtil.encodeBase64(key.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


}
