package com.hengxin.pickimg.loader;

import android.content.Context;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class PickerImageLoader {
    public static void initCache() {
    }

    public static void clearCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(() -> Glide.get(context).clearDiskCache()).start();
            } else {
                Glide.get(context).clearDiskCache();
//                String ImageExternalCatchDir=ExternalPreferredCacheDiskCacheFactory;
//                deleteFolderFile(ImageExternalCatchDir, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void display(final String thumbPath, final String originalPath, final ImageView imageView, final int defResource) {

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(defResource)
                .error(defResource)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new RotateTransformation(originalPath));

        Glide.with(imageView.getContext())
                .asBitmap()
                .load(thumbPath)
                .apply(requestOptions)
                .into(imageView);
    }
}
