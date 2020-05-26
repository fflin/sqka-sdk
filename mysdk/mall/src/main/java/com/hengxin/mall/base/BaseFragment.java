package com.hengxin.mall.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hengxin.basic.util.NetworkUtils;
import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;


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

    protected abstract void onReloadClick();

    public boolean reLoadEnable() {
        boolean enable = false;
        if (!NetworkUtils.getNetworkType(getActivity()).equals(NetworkUtils.NET_TYPE.TYPE_NONE)) {
            enable = true;
        } else {
            ToastUtils.show(getActivity(), getActivity().getResources().getString(R.string.net_work_error));
        }
        return enable;
    }

    //显示加载出错的布局  -- 缺点 每个布局xml里都要include一个error布局
    public void showLoadError(View view) {
        if (view != null) {
            view.findViewById(R.id.rl_net_error).setVisibility(View.VISIBLE);
            view.findViewById(R.id.rl_net_error).requestFocus();
            view.findViewById(R.id.tv_net_reload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onReloadClick();
                    hideLoadError(view);
                }
            });
        }
    }



    //隐藏加载失败的布局
    public void hideLoadError(View view) {
        if (view != null) {
            view.findViewById(R.id.rl_net_error).setVisibility(View.GONE);
        }
    }

    public void resetSmartRefreshStatu(SmartRefreshLayout smartRefreshLayout) {
        if (smartRefreshLayout == null) return;

        if (smartRefreshLayout.getState() == RefreshState.Refreshing) {
            smartRefreshLayout.finishRefresh();
        }

        if (smartRefreshLayout.getState() == RefreshState.Loading) {
            smartRefreshLayout.finishLoadMore();
        }
    }
}
