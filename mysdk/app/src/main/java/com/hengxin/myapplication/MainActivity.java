package com.hengxin.myapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

        // String重新赋值后，hascode产生变化，重新赋值的过程是回收对象，再把值赋给新的String
        String a = "111";
        Log.i("test0000","111  a = "+a.hashCode());
        a = "123";
        Log.i("test0000","111  a = "+a.hashCode());
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
