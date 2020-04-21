package com.hengxin.basic.util;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Marcus on 2016-06-01.
 */
public class CryptoUtils {

    private static final String myIV = "5efd3f6060e20330";
    // /** 算法/模式/填充 **/
    private static final String CipherMode = "AES/CBC/PKCS5Padding";

    // /** 创建密钥 **/
    private static SecretKeySpec createKey(String key) {
        byte[] data = null;
        if (key == null) {
            key = "";
        }
        StringBuffer sb = new StringBuffer(16);
        sb.append(key);
        while (sb.length() < 16) {
            sb.append("0");
        }
        if (sb.length() > 16) {
            sb.setLength(16);
        }

        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(data, "AES");
    }

    private static IvParameterSpec createIV(String password) {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuffer sb = new StringBuffer(16);
        sb.append(password);
        while (sb.length() < 16) {
            sb.append("0");
        }
        if (sb.length() > 16) {
            sb.setLength(16);
        }

        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new IvParameterSpec(data);
    }

    public static String encrypt(String arg2) {
        String encryptStr = encrypt(arg2, "9ec242a914de06cd");
//        LibLogger.e("encrypt before ", "d=" + encryptStr);
        return encryptStr;
    }
    public static String encryptPassword(String arg2) {
        String encryptStr = encrypt(arg2, "a9b8e0f5b061d74c");
        return encryptStr;
    }

    // /** 加密(结果为16进制字符串) **/
    public static String encrypt(String content, String password) {
        byte[] data = null;
        try {
            data = content.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = encrypt(data, password, myIV);
        //String result = ToHexUtil.byte2hex(data);
        String result = Base64.encodeToString(data, Base64.NO_PADDING | Base64.NO_WRAP);
        return result;
    }

    // /** 加密字节数据 **/
    public static byte[] encrypt(byte[] content, String password, String iv) {
        try {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, key, createIV(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String decrypt(String target) {
        String decryptStr = decrypt(target, "c7d8e0f5b061d79e");
        return decryptStr;
    }

    // /** 解密 **/
    public static String decrypt(String content, String password) {
        byte[] data = null;
        try {
            //data = ToHexUtil.hex2byte(content);
            data = Base64.decode(content, Base64.NO_PADDING | Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = decrypt(data, password, myIV);
        if (data == null)
            return null;
        String result = null;
        try {
            result = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    // /** 解密字节数组 **/
    public static byte[] decrypt(byte[] content, String password, String iv) {
        try {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, key, createIV(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
