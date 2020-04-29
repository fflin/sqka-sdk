package com.hengxin.mall.ui.order.consult;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.base.VLRAdapter;
import com.hengxin.mall.manager.CrashBugLinearLayoutManager;
import com.hengxin.mall.test.TestUtil;
import com.hengxin.mall.ui.order.transport.TransportItem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/29 12:18
 * desc   : 协商详情，与物流详情使用同一个adapter item
 * version: 1.0
 */
public class ConsultActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    protected void initData() {
        List testList = new TestUtil().getTestList();
        TransportItem item = new TransportItem(this, testList, false);
        VLRAdapter adapter = new VLRAdapter(item);
        recyclerView.setLayoutManager(new CrashBugLinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.reLoadData(testList);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_bar_title)).setText("协商详情");
        SmartRefreshLayout smartRefreshLayout = findViewById(R.id.smart_refresh_layout);
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setEnableRefresh(false);
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_title_recycler_view;
    }
}
