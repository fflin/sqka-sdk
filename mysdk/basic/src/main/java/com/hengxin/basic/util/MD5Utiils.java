package com.hengxin.basic.util;

import android.net.Uri;

import com.hengxin.basic.base.BaseRequest;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * author:author
 * date: 2018/4/9
 */

public class MD5Utiils {
    public static String MD5(String s) throws NoSuchAlgorithmException {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] btInput = s.getBytes();
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        // 使用指定的字节更新摘要
        mdInst.update(btInput);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }


    /**
     * 加密算法，提供访问的
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static String SECRET_KEY = "~!@#$(*&^%$&";
    public static String sign = null;

    public static String getSign(BaseRequest baseRequest, HashMap<String, String> paramMap) {
//        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            Class<?> clazz = baseRequest.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(baseRequest);
                paramMap.put(fieldName, value == null ? "" : value.toString());
            }
            ArrayList<String> list = new ArrayList<>(paramMap.keySet());
            //然后通过比较器来实现排序
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            String md5Str = "";
            for (String mapping : list) {
                Log.i("fflin","公共参数："+mapping + ":" + paramMap.get(mapping));
                md5Str += mapping + "=" + Uri.encode(paramMap.get(mapping)) + "&";
            }
            Log.i("addBaseProperty", md5Str);
            sign = MD5(md5Str + SECRET_KEY);//md5自行实现
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {

        }
        return sign;
    }

    public static String getHashMapSign(Map<String, String> paramMap) {
        try {
            ArrayList<String> list = new ArrayList<>(paramMap.keySet());
            //然后通过比较器来实现排序
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            String md5Str = "";
            for (String mapping : list) {
                Log.i("getSign",mapping + ":" + paramMap.get(mapping));
//                md5Str += mapping + "=" + Uri.encode(paramMap.get(mapping )) + "&";
                md5Str += mapping + "=" + paramMap.get(mapping) + "&";
            }
            sign = MD5(md5Str + SECRET_KEY);//md5自行实现
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sign;
    }

}
