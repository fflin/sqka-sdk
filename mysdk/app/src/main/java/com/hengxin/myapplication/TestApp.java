package com.hengxin.myapplication;

import android.app.Application;

import com.hengxin.mall.init.MallInitHelper;

/**
 * author : fflin
 * date   : 2020/4/20 15:53
 * desc   :
 * version: 1.0
 */
public class TestApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MallInitHelper.getHelper().initMall("123","123");
    }
}
