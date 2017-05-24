package com.baojing.credit.utils;

import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * User: dpj
 * Date: 13-10-17
 * Time: 下午3:19
 */
public class AesEncrypt {
public final static String UTF_8="UTF-8";
    /**
     * AES 加密
     *
     * @param sSrc  加密内容 必填 ( 必须为UTF_8)
     * @param sKey 密钥 必填
     * @return 成功或失败或异常信息
     */
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            throw   new  InvalidKeyException("The key's length must be 16");
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec("Xadiapdfaxi0s91D".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(UTF_8));
        return Base64.encodeBase64String(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /**
     * AES 解密
     *
     * @param sSrc  解密密文 必填(HEX字符串)
     * @param sKey 密钥 必填
     * @return 解密明文 ( 为UTF_8编码集)
     */
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("Xadiapdfaxi0s91D"
                    .getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original,UTF_8);
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
}

