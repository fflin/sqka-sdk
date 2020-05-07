package com.hengxin.mall.ui.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseFragment;
import com.hengxin.mall.model.TagModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * author : fflin
 * date   : 2020/5/6 18:16
 * desc   : 搜索结果页面
 * version: 1.0
 */
public class SearchResultFragment extends BaseFragment implements SearchContract.View {

    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private SearchPresenter presenter;

    @Override
    protected int setLayout() {
        return R.layout.aty_title_recycler_view;
    }

    @Override
    protected void initData() {
        presenter = new SearchPresenter();
        presenter.onAttach(this);
    }

    @Override
    protected void initView(View mRootView) {
        mRootView.findViewById(R.id.title_parent).setVisibility(View.GONE);
        smartRefreshLayout = mRootView.findViewById(R.id.smart_refresh_layout);
        recyclerView = mRootView.findViewById(R.id.recycler_view);
    }

    public void startToSearch(String inputText) {
        ToastUtils.show(mContext, "开始搜索-"+inputText);
        presenter.startToSearch(inputText);
    }

    @Override
    public void getHotWordsSucc(TagModel data) {

    }

    @Override
    public void getSearchResultSucc() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String message) {

    }
}
