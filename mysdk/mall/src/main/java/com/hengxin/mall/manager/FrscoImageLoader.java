package com.hengxin.mall.manager;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * author:author
 * date: 2018/9/5
 */

public class FrscoImageLoader extends ImageLoader {

    private boolean isFist = false;

    public FrscoImageLoader setFist(boolean fist) {
        isFist = fist;
        return this;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String wrapPath = (String) path;
        if (wrapPath.endsWith("€***€")) {
            wrapPath = wrapPath.replace("€***€", "");
            if (isFist) {
                isFist = false;
//                imageView.setTag("first");//Glide加载图片无法使用tag
            }
        }
        Uri uri = Uri.parse(wrapPath);
//        imageView.setImageURI(uri);//OOM  Fresco实现  302
//        Log.i("fflin","displayImage  uri = "+uri);
        Glide.with(context).load(uri).into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        /*WrapContentDraweeView simpleDraweeView = new WrapContentDraweeView(context);
        simpleDraweeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return simpleDraweeView;*/
        return new ImageView(context);
    }
}
