package com.hengxin.mall.ui.saleout;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.ui.consult.ConsultActivity;

/**
 * author : fflin
 * date   : 2020/4/29 16:47
 * desc   : 售后退款详情
 * version: 1.0
 */
public class SaleOutDetailActivity extends BaseActivity {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_bar_title)).setText("退款详情");
        findViewById(R.id.saleout_btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 物流信息

                SaleOutDetailActivity.this.startActivity(new Intent(SaleOutDetailActivity.this, WriteTransportActivity.class));
            }
        });
        findViewById(R.id.consult_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleOutDetailActivity.this.startActivity(new Intent(SaleOutDetailActivity.this, ConsultActivity.class));
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_saleout_detail;
    }
}
