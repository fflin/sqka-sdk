package com.hengxin.mall.ui.search;

import android.view.View;

import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseFragment;
import com.hengxin.mall.ui.search.inter.OnFilterClickListener;

/**
 * author : fflin
 * date   : 2020/5/8 14:51
 * desc   :
 * version: 1.0
 */
public class SearchFilterFragment extends BaseFragment {
    @Override
    protected int setLayout() {
        return R.layout.aty_test;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View mRootView) {

    }


    public void setOnFilterClick(OnFilterClickListener listener) {
        /*listener.onFilterClick("","橙子🍊");*/
    }
}
