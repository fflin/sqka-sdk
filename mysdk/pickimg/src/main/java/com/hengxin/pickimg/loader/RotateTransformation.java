package com.hengxin.pickimg.loader;

import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.hengxin.pickimg.utils.BitmapUtil;

import java.security.MessageDigest;

/**
 * Created by huangjun on 2017/4/11.
 */

class RotateTransformation extends BitmapTransformation {
    private String path;

    RotateTransformation( String path) {
        this.path = path;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(getId().getBytes());
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return BitmapUtil.reviewPicRotate(toTransform, path);
    }

    public String getId() {
        return "rotate_" + path.hashCode();
    }
}
