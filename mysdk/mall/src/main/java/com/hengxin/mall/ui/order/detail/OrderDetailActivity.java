package com.hengxin.mall.ui.order.detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.ui.transport.TransportActivity;


/**
 * author : fflin
 * date   : 2020/4/29 10:05
 * desc   :
 * version: 1.0
 */
public class OrderDetailActivity extends BaseActivity {

    public static final String ORDERIDEXTRA = "orderId";
    private String orderId;

    public static void startOrderDetailAty(Context context, String orderId) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(ORDERIDEXTRA,orderId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        orderId = getIntent().getStringExtra(ORDERIDEXTRA);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_bar_title)).setText("订单详情");
        // 物流信息页面
        findViewById(R.id.detail_transport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransportActivity.startTransportAty(OrderDetailActivity.this, orderId);
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_order_detail;
    }
}
