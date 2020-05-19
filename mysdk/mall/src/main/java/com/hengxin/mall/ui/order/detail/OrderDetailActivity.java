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
    private TextView detailStatus, detailTime, transInfo, transTime, transName, transAdd;
    private TextView goodsName, goodsSpe, goodsPrice, goodsCount;
    private TextView detailOrderId, orderBtn,detailPayType,detailTransTime,detailPayTime,detailTransPay, detailPayMoney;
    private RoundImageView goodsImg;

    public static void startOrderDetailAty(Context context, String orderId) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(ORDERIDEXTRA, orderId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        orderId = getIntent().getStringExtra(ORDERIDEXTRA);
    }

    private void getViews() {
        detailStatus = findViewById(R.id.detail_status);
        detailTime = findViewById(R.id.detail_info);
        transInfo = findViewById(R.id.trans_info);
        transTime = findViewById(R.id.trans_time);
        transName = findViewById(R.id.trans_name);
        transAdd = findViewById(R.id.trans_add);
        goodsImg = findViewById(R.id.goods_img);
        goodsName = findViewById(R.id.goods_name);
        goodsSpe = findViewById(R.id.goods_specify);
        goodsPrice = findViewById(R.id.goods_price);
        goodsCount = findViewById(R.id.goods_count);

        orderBtn = findViewById(R.id.order_btn);
        detailOrderId = findViewById(R.id.detail_order_id);
        detailPayType = findViewById(R.id.detail_order_pay);
        detailTransTime = findViewById(R.id.detail_trans_time);
        detailPayTime = findViewById(R.id.detail_pay_time);
        detailTransPay = findViewById(R.id.detail_trans_pay);
        detailPayMoney = findViewById(R.id.detail_pay_money);
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("订单详情");
        getViews();
        // 物流信息页面
        findViewById(R.id.detail_transport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransportActivity.startTransportAty(OrderDetailActivity.this, orderId);
            }
        });
        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String orderId = detailOrderId.getText().toString().trim();
        findViewById(R.id.detail_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData mClipData = ClipData.newPlainText("Label", orderId);
                if (cmb != null) {
                    cmb.setPrimaryClip(mClipData);
                    ToastUtils.show(OrderDetailActivity.this, "复制成功");
                }
            }
        });



    }

    @Override
    protected int setLayout() {
        return R.layout.aty_order_detail;
    }
}
