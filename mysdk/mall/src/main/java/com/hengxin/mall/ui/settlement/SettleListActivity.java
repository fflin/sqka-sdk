package com.hengxin.mall.ui.settlement;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.base.VLRAdapter;
import com.hengxin.mall.test.TestUtil;
import com.hengxin.mall.ui.pay.PayChannelActivity;
import com.hengxin.mall.ui.settlement.adapter.SettleListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/27 10:28
 * desc   : 结算页面，包含合并支付，显示收货地址，支付信息
 * version: 1.0
 */
public class SettleListActivity extends BaseActivity {


    private TextView settleLocation;
    private RecyclerView settleRv;
    private TextView settleTotalPrice;//总价
    private TextView settleWeight;//总重量
    private TextView settleTrans;//运费
    private TextView settlePayPrice;//实际支付

    @Override
    protected void initData() {

        VLRAdapter adapter = new VLRAdapter(new SettleListItem(this));
        settleRv.setLayoutManager(new LinearLayoutManager(this));
        settleRv.setAdapter(adapter);

        adapter.reLoadData(new TestUtil().getTestList());
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_bar_title)).setText("结算");
        settleLocation = findViewById(R.id.settle_location);
        settleRv = findViewById(R.id.settle_rv);
        settleTotalPrice = findViewById(R.id.settle_total_price);
        settleWeight = findViewById(R.id.settle_weight);
        settleTrans = findViewById(R.id.settle_transport);
        settlePayPrice = findViewById(R.id.settle_pay_price);
        findViewById(R.id.settle_pay_btn).setOnClickListener(v -> {
            PayChannelActivity.startPayChannelAty(SettleListActivity.this,"666","123456");
            finish();
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_settle_list;
    }
}
