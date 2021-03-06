package com.hengxin.mall.init;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hengxin.basic.BasicData;
import com.hengxin.basic.ContextProvider;
import com.hengxin.basic.util.Log;
import com.hengxin.mall.factory.ImagePipelineConfigFactory;


/**
 * author : fflin
 * date   : 2020/4/20 15:31
 * desc   :
 * version: 1.0
 */
public class MallInitHelper {

    private MallInitHelper(){}

    private static MallInitHelper helper;

    public static MallInitHelper getHelper () {
        synchronized (MallInitHelper.class) {
            if (helper == null) {
                helper = new MallInitHelper();
            }
        }
        return helper;
    }

    public void initMall(String appId, String appSecret) {
        // 整个sdk的init入口
        BasicData.getData().setBasicData(appId, appSecret);
        Context context = ContextProvider.get().getContext();
        Fresco.initialize(context, ImagePipelineConfigFactory.getImagePipelineConfig(context));
    }

    public void toMallPage(int index) {
        Context mContext = ContextProvider.get().getContext();
        if (mContext != null) {
            Log.i("fflin","mContext=  "+mContext.hashCode());
            MallInitRouter.jumpByIndex(mContext, index);
        }
    }
}
