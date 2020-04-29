package com.hengxin.mall.ui.saleout;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;

/**
 * author : fflin
 * date   : 2020/4/29 12:43
 * desc   : 申请售后页面
 * version: 1.0
 */
public class AskForSaleActivity extends BaseActivity {

    public static void startAskForSaleAty(Context context) {
        Intent intent = new Intent(context, AskForSaleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_bar_title)).setText("申请售后");
        findViewById(R.id.ask_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskForSaleActivity.this.startActivity(new Intent(AskForSaleActivity.this,AskSuccActivity.class));
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_ask_sale;
    }
}
