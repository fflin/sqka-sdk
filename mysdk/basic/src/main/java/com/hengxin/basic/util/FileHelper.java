package com.hengxin.basic.util;

import android.app.Application;
import android.content.Context;
import android.os.Environment;


import java.io.File;
import java.math.BigDecimal;

/**
 * Created by chunyang on 2017/11/10.
 */

public class FileHelper {

    private static class Holder {
        private static FileHelper IN = new FileHelper();
    }

    public static FileHelper get() {
        return Holder.IN;
    }

    private FileHelper() {
    }


    private File cache;
    public static void initialize(Application application) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//如果已经挂载
            get().cache = application.getExternalCacheDir();
            if (get().cache == null) {
                get().cache = new File(Environment.getExternalStorageDirectory(),
                        "Android/data/" + application.getPackageName() + "/cache");
            }
        } else {
            get().cache = application.getCacheDir();
        }
    }

    public File getCache() {
        return cache;
    }

    public File getH5Cache() {
        return new File(cache, "webcahce");
    }

    public File getCropedFile() {
        File file = new File(cache, "croptemp/crop_temp.jpg");
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

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(Long size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(1, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(1, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(1, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " G";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString()
                + " T";
    }

}
