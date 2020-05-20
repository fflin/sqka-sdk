package com.hengxin.mall.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hengxin.basic.util.Log;
import com.hengxin.mall.R;


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
        View titleBack = findViewById(R.id.title_bar_back);
        if (titleBack != null) {
            titleBack.setOnClickListener(v -> finish());
        }
        initData();
        Log.i("fflin","当前activity = "+getClass().getSimpleName());
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
