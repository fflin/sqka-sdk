package com.hengxin.mall.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hengxin.basic.util.NetworkUtils;
import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;


/**
 * author : fflin
 * date   : 2020/4/20 17:07
 * desc   :
 * version: 1.0
 */
public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected Context mContext;
    protected Activity mActivity;

    /**
     * 绑定Activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (Activity) context;
        initArgs(getArguments());
    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 进行复用判断
        if (mRootView == null) {
            mRootView = inflater.inflate(setLayout(), container, false);
        }


        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView(mRootView);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    // 接收向fragment传过来的值
    protected void initArgs(Bundle arguments) {
    }

    /**
     * 绑定布局
     */
    protected abstract int setLayout();

    /**
     * 设置数据等逻辑代码
     */
    protected abstract void initData();

    /**
     * 初始化控件视图及监听
     */
    protected abstract void initView(View mRootView);

    public boolean reLoadEnable() {
        boolean enable = false;
        if (!NetworkUtils.getNetworkType(getActivity()).equals(NetworkUtils.NET_TYPE.TYPE_NONE)) {
            enable = true;
        } else {
            ToastUtils.show(getActivity(), getActivity().getResources().getString(R.string.net_work_error));
        }
        return enable;
    }
}
