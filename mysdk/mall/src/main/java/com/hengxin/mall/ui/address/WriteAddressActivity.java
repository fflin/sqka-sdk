package com.hengxin.mall.ui.address;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;

/**
 * author : fflin
 * date   : 2020/4/29 17:57
 * desc   : 添加收货地址
 * version: 1.0
 */
public class WriteAddressActivity extends BaseActivity {

    public static void toWriteAddAty(Context context) {
        Intent intent = new Intent(context, WriteAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_bar_title)).setText("添加收货地址");
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_write_address;
    }
}
