package com.hengxin.mall.ui.home.viewholder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.hengxin.basic.util.Log;
import com.hengxin.mall.R;
import com.hengxin.mall.model.HomeModel;
import com.hengxin.mall.ui.home.MallHomeClassifyFragment;
import com.hengxin.mall.ui.home.adapter.FragmentAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.ArrayList;
import java.util.List;

public class ClassifyViewHolder extends RecyclerView.ViewHolder {


    private final ViewPager classifyVp;
    private final MagicIndicator classifyId;

    public ClassifyViewHolder(@NonNull View itemView) {
        super(itemView);
        classifyVp = itemView.findViewById(R.id.classify_view_pager);
        classifyId = itemView.findViewById(R.id.classify_indicator);
    }

    public void bindView(Context context, FragmentManager mFragmentManager, Object data) {
        HomeModel.HomePageItem homePageItem = (HomeModel.HomePageItem) data;
        homePageItem.list.addAll(homePageItem.list);
        homePageItem.list.addAll(homePageItem.list);
        Log.i("fflin","title = "+homePageItem.title+"; "+homePageItem.list.size());
        for (int i = 0; i < homePageItem.list.size(); i++) {
            Log.i("fflin","title --- "+homePageItem.list.get(i).title);
        }
        //测试数据20条

        initClassifyViewPager(mFragmentManager, homePageItem.list);
        CircleNavigator scaleCircleNavigator = new CircleNavigator (context);
        int scrollBarSize = scaleCircleNavigator.getScrollBarSize();
        Log.i("fflin","size= "+scrollBarSize);



        scaleCircleNavigator.setCircleCount(homePageItem.list.size());
        scaleCircleNavigator.setCircleColor(ContextCompat.getColor(context, R.color.greed));
        scaleCircleNavigator.setCircleClickListener(index -> classifyVp.setCurrentItem(index));
        classifyId.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(classifyId, classifyVp);


    }

    private void initClassifyViewPager(FragmentManager fragmentManager, List<HomeModel.HomePageItem> list) {
        classifyVp.setAdapter(new FragmentAdapter(fragmentManager, getClassifyFragments(list)));
        classifyVp.setOffscreenPageLimit(10);
    }

    private List<Fragment> getClassifyFragments(List<HomeModel.HomePageItem> list) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            fragments.add(MallHomeClassifyFragment.newInstance(list.get(i)));
        }
        return fragments;
    }
}