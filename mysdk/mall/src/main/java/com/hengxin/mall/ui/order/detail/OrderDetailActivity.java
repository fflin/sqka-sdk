package com.hengxin.mall.ui.order.detail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.ui.transport.TransportActivity;
import com.hengxin.mall.view.RoundImageView;


/**
 * author : fflin
 * date   : 2020/4/29 10:05
 * desc   :
 * version: 1.0
 */
public class OrderDetailActivity extends BaseActivity {

    public static final String ORDERIDEXTRA = "orderId";
    private String orderId;
    private TextView detailStatus, detailTime,transInfo,transTime,transName,transAdd;
    private RoundImageView goodsImg;

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
        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String orderId = ((TextView)findViewById(R.id.detail_order_id)).getText().toString().trim();
        findViewById(R.id.detail_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData mClipData = ClipData.newPlainText("Label", orderId);
                if (cmb != null) {
                    cmb.setPrimaryClip(mClipData);
                    ToastUtils.show(OrderDetailActivity.this,"复制成功");
                }
            }
        });
        detailStatus = findViewById(R.id.detail_status);
        detailTime = findViewById(R.id.detail_info);
        transInfo = findViewById(R.id.trans_info);
        transTime = findViewById(R.id.trans_time);
        transName = findViewById(R.id.trans_name);
        transAdd = findViewById(R.id.trans_add);
        goodsImg = findViewById(R.id.goods_img);
        transAdd = findViewById(R.id.trans_add);
        transAdd = findViewById(R.id.trans_add);
        transAdd = findViewById(R.id.trans_add);
        transAdd = findViewById(R.id.trans_add);


    }

    @Override
    protected int setLayout() {
        return R.layout.aty_order_detail;
    }
}
