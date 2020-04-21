package com.hengxin.mall.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * author : fflin
 * date   : 2020/4/20 17:05
 * desc   :
 * version: 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgs(getIntent());
        setContentView(setLayout());
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int setLayout();

    protected void initArgs(Intent intent) {
    }
}
