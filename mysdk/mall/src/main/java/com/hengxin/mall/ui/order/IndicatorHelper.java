package com.hengxin.mall.ui.order;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.hengxin.mall.R;
import com.hengxin.mall.view.ColorFlipPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fflin
 * date   : 2019/8/20 16:23
 * desc   :
 * version: 1.0
 */
public class IndicatorHelper {

    private IndicatorHelper() {
    }

    private static IndicatorHelper helper;

    public static IndicatorHelper getHelper() {
        if (helper == null) {
            helper = new IndicatorHelper();
        }
        return helper;
    }


    public void initIndicator(Context context, FragmentManager fragmentManager, MagicIndicator magicIndicator, List<String> list, ViewPager viewPager, int orderTag) {
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(orderTag == OrderConstant.SALE_ORDER_TAG);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(list.get(index));
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.settle_text_black));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.greed));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setOnClickListener(v -> viewPager.setCurrentItem(index, true));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 20));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(context, R.color.greed));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        initViewPager(fragmentManager,viewPager, orderTag, list);
    }

    private void initViewPager(FragmentManager fragmentManager, ViewPager viewPager, int orderTag, List<String> list) {
        viewPager.setAdapter(new OrderFragmentAdapter(fragmentManager, getFragments(orderTag,list)));
    }

    private List<Fragment> getFragments(int orderTag, List<String> list) {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            fragmentList.add(MallOrderFragment.newInstance(i, orderTag));
        }
        return fragmentList;
    }
}
