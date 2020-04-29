package com.hengxin.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hengxin.mall.init.MallInitHelper;
import com.hengxin.mall.init.MallPageIndex;
import com.hengxin.mall.ui.address.AddressListActivity;
import com.hengxin.mall.ui.address.WriteAddressActivity;
import com.hengxin.mall.ui.saleout.AskForSaleActivity;

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

    public void toSaleOut(View view) {
        AskForSaleActivity.startAskForSaleAty(this);
    }

    public void toWriteAddress(View view) {
        WriteAddressActivity.toWriteAddAty(this);
    }

    public void toAddressList(View view) {
        AddressListActivity.startAddressListAty(this,"111");
    }
}
