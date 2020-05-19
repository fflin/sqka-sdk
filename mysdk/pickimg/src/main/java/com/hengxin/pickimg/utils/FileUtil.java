package com.hengxin.pickimg.utils;

import android.content.Context;

import java.io.File;

/**
 * author : fflin
 * date   : 2020/5/19 17:11
 * desc   :
 * version: 1.0
 */
public class FileUtil {

    private static class Holder {
        private static FileUtil IN = new FileUtil();
    }

    public static FileUtil get() {
        return Holder.IN;
    }

    private FileUtil() {
    }

    public File getCropFile(Context context) {
        File file = new File(context.getExternalCacheDir(),"croptemp/crop_temp.jpg");
        if (file.exists()) {
            file.delete();
            return file;
        }
        File parentFile = file.getParentFile();
        if (parentFile.exists()) {
            return file;
        } else {
            parentFile.mkdirs();
            return file;
        }
    }
}
