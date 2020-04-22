package com.hengxin.mall.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseFragment;
import com.hengxin.mall.model.HomeModel;

import java.io.Serializable;

/**
 * author : fflin
 * date   : 2020/4/22 16:26
 * desc   :
 * version: 1.0
 */
public class MallHomeClassifyFragment extends BaseFragment {

    private static final String BUNDLE_KEY = "modelElement";
    private FlexboxLayout flexboxLayout;
    private View mRootView;

    public static Fragment newInstance(HomeModel.HomePageItem homePageItem) {
        MallHomeClassifyFragment fragment = new MallHomeClassifyFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY,homePageItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.home_fragment_classify;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        assert bundle != null;
        HomeModel.HomePageItem homePageItem = (HomeModel.HomePageItem) bundle.getSerializable(BUNDLE_KEY);

       /* for (int i = 0; i < list.size(); i++) {*/
            View item = LayoutInflater.from(mContext).inflate(R.layout.home_classify_item,null);
            TextView textView = item.findViewById(R.id.pageTypeTv);
            ImageView simpleDraweeView = item.findViewById(R.id.pageTypeIv);
            /*SimpleDraweeView simpleDraweeView = item.findViewById(R.id.pageTypeIv);*/
            String title = homePageItem.title;
            if (textView != null) {
                textView.setText(title);
            }
            if (simpleDraweeView != null) {
                Glide.with(mRootView).load(homePageItem.pic).into(simpleDraweeView);
//                simpleDraweeView.setImageURI(list.get(i).pic);
            }
            flexboxLayout.addView(item);
            //通过FlexboxLayout.LayoutParams 设置子元素支持的属性
            ViewGroup.LayoutParams params = item.getLayoutParams();
            if(params instanceof FlexboxLayout.LayoutParams){
                FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) params;
                layoutParams.setFlexBasisPercent(0.2f);//控制一行显示item的百分比
            }
            item.setOnClickListener(v -> {
            });
        /*}*/
    }

    @Override
    protected void initView(View mRootView) {
        this.mRootView = mRootView;
        flexboxLayout = mRootView.findViewById(R.id.flex_layout);
    }
}
