package com.hengxin.mall.ui.order;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.hengxin.basic.util.Log;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/28 11:20
 * desc   : 全部订单
 * version: 1.0
 */
public class MallOrderAllFragment extends BaseFragment {

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
        list.add("待付款");
        list.add("待发货");
        list.add("待收货");
        list.add("已完成");
        list.add("已取消");
        IndicatorHelper.getHelper().initIndicator(getContext(),getChildFragmentManager(),indicator,list,viewPager,OrderConstant.ALL_ORDER_TAG);
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
