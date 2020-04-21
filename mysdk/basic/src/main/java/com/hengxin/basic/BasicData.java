package com.hengxin.basic;


import com.hengxin.basic.util.Log;

/**
 * author : fflin
 * date   : 2020/4/20 14:36
 * desc   :
 * version: 1.0
 */
public class BasicData {

    private String appKey, appSecret;
    private BasicData() {}

    private static BasicData data;

    public static BasicData getData() {
        synchronized (BasicData.class) {
            if (data == null) {
                data = new BasicData();
            }
        }
        return data;
    }

    public void setBasicData(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        Log.i("fflin","appKey = "+appKey+"; appSecret = "+appSecret);
    }

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }
}
