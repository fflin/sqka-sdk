package com.hengxin.mall.ui.transport;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.base.VLRAdapter;
import com.hengxin.mall.manager.CrashBugLinearLayoutManager;
import com.hengxin.mall.test.TestUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/29 10:28
 * desc   :
 * version: 1.0
 */
public class TransportActivity extends BaseActivity {

    public static final String TRANSPORTID = "transportId";
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private ImageView goodsImg;
    private TextView company;
    private TextView transOrder;

    public static void startTransportAty(Context context, String orderId) {
        Intent intent = new Intent(context, TransportActivity.class);
        intent.putExtra(TRANSPORTID, orderId);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        List testList = new TestUtil().getTestList();
        TransportItem item = new TransportItem(this, testList, true);
        VLRAdapter adapter = new VLRAdapter(item);
        recyclerView.setLayoutManager(new CrashBugLinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.reLoadData(testList);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_bar_title)).setText("物流信息");
        smartRefreshLayout = findViewById(R.id.smart_refresh_layout);
        recyclerView = findViewById(R.id.recycler_view);
        goodsImg = findViewById(R.id.goods_img);
        company = findViewById(R.id.transport_company);
        transOrder = findViewById(R.id.transport_order);
        findViewById(R.id.copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(TransportActivity.this,"复制订单号");
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_transport_info;
    }
}
