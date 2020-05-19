package com.hengxin.pickimg.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * author: Y_Qing
 * date: 2018/12/29
 * 压缩图片 https://www.jianshu.com/p/871f0c1f0006
 */
public class PhotoUtils {

    /**
     *  采样率压缩方式，降低图片像素
     *  优点：不会把大图片读到内存中， 降低内存使用
     *  缺点：整数压缩，图片质量差异大
     */
    public static void compressBitmap(String filePath, File file){
        // 数值越高，图片像素越低
        int inSampleSize = 8;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //采样率
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 ,baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 质量压缩，保持像素的前提下改变图片的位深和透明度
     * 不压缩像素，压缩图片大小
     */
    public static void qualityCompress(Context context, String filePath, CompressCallBack callBack) {
        Log.i("fflin","filePath = "+filePath+";"+";"+ Environment.getExternalStorageDirectory().getAbsolutePath());
        Luban.with(context)
                .load(filePath)//传入原图
                .ignoreBy(100)//设置开启压缩条件
                .setTargetDir(Environment.getExternalStorageDirectory().getAbsolutePath())//缓存压缩图片路径
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //  压缩开始前调用，可以在方法内启动 loading UI
                        Log.i("fflin","onStart ------------- ");
                    }

                    @Override
                    public void onSuccess(File file) {
                        //  压缩成功后调用，返回压缩后的图片文件
                        Log.i("fflin","onSuccess : "+file.getAbsolutePath()+";"+file.getTotalSpace()+";"+file.getUsableSpace()+"; "+file.length());
                        callBack.onCompressSucc(file.getAbsolutePath());

                    }

                    @Override
                    public void onError(Throwable e) {
                        //  当压缩过程出现问题时调用
                        Log.i("fflin","onError ------------- "+e.getMessage());
                        callBack.onCompressFiled(e.getMessage());
                    }
                }).launch();
        /*BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);


        //采样率，这里压缩的是像素，比如3120x4160的图片，设置options.inSampleSize=4，最终得到的图片像素是780*1040
        options.inSampleSize = computeSampleSize(options, -1, 128*128);
        Log.i("fflin","options.inSampleSize = "+options.inSampleSize);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        if (bitmap != null) {
            // 0-100 100为不压缩
            int quality = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i("fflin","------------------ bitmap = null");
        }*/


    }

    //动态计算采样率的方法，返回的bitmap=null
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :(int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

}
