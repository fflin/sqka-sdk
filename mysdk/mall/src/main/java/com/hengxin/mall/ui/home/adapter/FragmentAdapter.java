package com.hengxin.mall.ui.home.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * author : fflin
 * date   : 2019/8/12 10:38
 * desc   :
 * version: 1.0
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private FragmentManager mManager;
    private List<Fragment> mFragments;

    public FragmentAdapter(FragmentManager childFragmentManager, List<Fragment> fragments) {
        super(childFragmentManager);
        this.mManager = childFragmentManager;
        this.mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

}
