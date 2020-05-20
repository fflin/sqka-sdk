package com.hengxin.mall.utils;

/**
 * author: Y_Qing
 * date: 2019/1/3
 */
public class FileUtils {
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot );
            }
        }
        return "";
    }
}
