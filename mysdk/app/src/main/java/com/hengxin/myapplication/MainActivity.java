package com.hengxin.myapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hengxin.mall.init.MallInitHelper;
import com.hengxin.mall.init.MallPageIndex;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void toMallMainPage(View view) {
        MallInitHelper.getHelper().toMallPage(MallPageIndex.MallMainPage);
    }

    public void toMallOrderList(View view) {
        MallInitHelper.getHelper().toMallPage(MallPageIndex.MallOrderDetail);
    }

    public void toMallCar(View view) {
        MallInitHelper.getHelper().toMallPage(MallPageIndex.MallCarPage);
    }
}
