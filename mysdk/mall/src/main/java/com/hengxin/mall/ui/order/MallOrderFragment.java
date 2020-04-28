package com.hengxin.mall.ui.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hengxin.basic.util.Log;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseFragment;
import com.hengxin.mall.base.VLRAdapter;
import com.hengxin.mall.manager.CrashBugLinearLayoutManager;
import com.hengxin.mall.test.TestUtil;
import com.hengxin.mall.ui.order.adapter.MallOrderItem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * author : fflin
 * date   : 2020/4/28 15:05
 * desc   : 订单recyclerView--测试数据写个合并订单
 * version: 1.0
 */
public class MallOrderFragment extends BaseFragment {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView orderRv;
    private static final String INDEX_TAG = "indexTag";
    private static final String ORDER_TAG = "orderTag";
    private int orderType;//订单类型
    private int orderTag;//订单分类

    /**
     * 优惠购订单页面
     * @param i 区分订单类型，待付款，待发货...
     * @param orderTag 对应OrderConstant的tag，区分全部订单和售后订单
     * @return fragment
     */
    public static Fragment newInstance(int i, int orderTag) {
        Bundle bundle = new Bundle();
        bundle.putInt(INDEX_TAG, i);
        bundle.putInt(ORDER_TAG,orderTag);
        MallOrderFragment fragment = new MallOrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int setLayout() {
        return R.layout.fm_order_recycler;
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            orderType = getArguments().getInt(INDEX_TAG);
            orderTag = getArguments().getInt(ORDER_TAG);
        }
        Log.i("fflin","orderType  = "+orderType+"; orderTag = "+orderTag);

        MallOrderItem item = new MallOrderItem(mContext, orderTag);
        VLRAdapter adapter = new VLRAdapter(item);
        orderRv.setLayoutManager(new CrashBugLinearLayoutManager(mContext));
        orderRv.setAdapter(adapter);

        adapter.reLoadData(new TestUtil().getTestList());

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });

    }

    @Override
    protected void initView(View mRootView) {
        refreshLayout = mRootView.findViewById(R.id.order_smart_layout);
        orderRv = mRootView.findViewById(R.id.order_recycler);
    }
}
