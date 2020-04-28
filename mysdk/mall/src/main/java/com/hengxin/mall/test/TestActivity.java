package com.hengxin.mall.test;

import android.support.v7.widget.RecyclerView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.base.VLRAdapter;
import com.hengxin.mall.manager.CrashBugLinearLayoutManager;
import com.hengxin.mall.ui.order.adapter.MallOrderItem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * author : fflin
 * date   : 2020/4/28 18:27
 * desc   :
 * version: 1.0
 */
public class TestActivity extends BaseActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView orderRv;

    @Override
    protected void initData() {
        MallOrderItem item = new MallOrderItem(this, 0);
        VLRAdapter adapter = new VLRAdapter(item);
        orderRv.setLayoutManager(new CrashBugLinearLayoutManager(this));
        orderRv.setAdapter(adapter);

        adapter.reLoadData(new TestUtil().getTestList());
    }

    @Override
    protected void initView() {
        refreshLayout = findViewById(R.id.order_smart_layout);
        orderRv = findViewById(R.id.order_recycler);
    }

    @Override
    protected int setLayout() {
        return R.layout.fm_order_recycler;
    }
}
