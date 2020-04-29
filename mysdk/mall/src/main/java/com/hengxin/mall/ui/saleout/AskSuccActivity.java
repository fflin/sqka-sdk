package com.hengxin.mall.ui.saleout;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;

/**
 * author : fflin
 * date   : 2020/4/29 16:51
 * desc   : 售后提交成功
 * version: 1.0
 */
public class AskSuccActivity extends BaseActivity {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_bar_title)).setText("提交成功");
        findViewById(R.id.ask_sale_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskSuccActivity.this.startActivity(new Intent(AskSuccActivity.this, SaleOutDetailActivity.class));
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_ask_succ;
    }
}
