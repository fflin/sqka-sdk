package com.hengxin.mall.ui.saleout;

import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;

/**
 * author : fflin
 * date   : 2020/4/29 17:49
 * desc   : 填写物流信息
 * version: 1.0
 */
public class WriteTransportActivity extends BaseActivity {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_bar_title)).setText("填写物流信息");
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_write_transport;
    }
}
