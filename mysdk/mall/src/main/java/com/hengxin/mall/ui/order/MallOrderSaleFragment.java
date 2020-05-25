package com.hengxin.mall.ui.order;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.hengxin.basic.util.Log;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/28 11:21
 * desc   :
 * version: 1.0
 */
public class MallOrderSaleFragment extends BaseFragment {

    private MagicIndicator indicator;
    private ViewPager viewPager;

    @Override
    protected int setLayout() {
        return R.layout.fm_mall_order_all;
    }

    @Override
    protected void initData() {
        Log.i("fflin","----- all init ------");
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("退款中");
        IndicatorHelper.getHelper().initIndicator(mContext,getChildFragmentManager(),indicator,list,viewPager,OrderConstant.SALE_ORDER_TAG);

    }

    @Override
    protected void initView(View mRootView) {
        indicator = mRootView.findViewById(R.id.order_indicator);
        viewPager = mRootView.findViewById(R.id.order_view_pager);
    }

    @Override
    protected void onReloadClick() {

    }
}
