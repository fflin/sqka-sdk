package com.hengxin.mall.ui.address;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.base.VLRAdapter;
import com.hengxin.mall.manager.CrashBugLinearLayoutManager;
import com.hengxin.mall.test.TestUtil;
import com.hengxin.mall.ui.address.adapter.AddressItem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * author : fflin
 * date   : 2020/4/29 18:34
 * desc   : 收货地址列表
 * version: 1.0
 */
public class AddressListActivity extends BaseActivity {

    public static void startAddressListAty(Context context, String id) {
        Intent intent = new Intent(context, AddressListActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private RecyclerView recyclerView;

    @Override
    protected void initData() {
        AddressItem item = new AddressItem(this);
        VLRAdapter adapter = new VLRAdapter(item);
        recyclerView.setLayoutManager(new CrashBugLinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.reLoadData(new TestUtil().getTestList());
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_bar_title)).setText("收货地址");
        SmartRefreshLayout smartRefreshLayout = findViewById(R.id.smart_refresh_layout);
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadMore(false);
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_title_recycler_view;
    }
}
