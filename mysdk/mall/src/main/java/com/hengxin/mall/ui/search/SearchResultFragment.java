package com.hengxin.mall.ui.search;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseFragment;
import com.hengxin.mall.manager.CrashBugGridLayoutManager;
import com.hengxin.mall.manager.CrashBugLinearLayoutManager;
import com.hengxin.mall.model.ConditionListModel;
import com.hengxin.mall.model.TagModel;
import com.hengxin.mall.ui.search.adapter.SearchResultAdapter;
import com.hengxin.mall.ui.search.adapter.SearchResultHotAdapter;
import com.hengxin.mall.ui.search.helper.ResultDataHelper;
import com.hengxin.mall.ui.search.inter.OnFilterClickListener;
import com.hengxin.mall.ui.search.inter.OnHotWordsClick;
import com.hengxin.mall.ui.search.inter.OnResultTopClick;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/5/6 18:16
 * desc   : 搜索结果页面 顶部加一块区域 一行存放销量等 一行展示热搜(横向rv)  https://suggest.taobao.com/sug?code=utf-8&q=%E8%BF%90%E5%8A%A8%E6%9C%8D%E9%A5%B0
 * version: 1.0
 */
public class SearchResultFragment extends BaseFragment implements SearchResultContract.View, OnHotWordsClick, OnResultTopClick, OnFilterClickListener {

    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView topRv, resultRv;
    private SearchResultPresenter presenter;
    private LinearLayout topLinearParent;
    private SearchResultHotAdapter hotAdapter;
    private ResultDataHelper dataHelper;
    private View topLine;
    private SearchResultAdapter resultAdapter;
    private CrashBugGridLayoutManager resultLinearManager;
    private String mKeyWords;
    private DrawerLayout drawerLayout;
    private SearchFilterFragment filterFragment;

    @Override
    protected int setLayout() {
        return R.layout.fm_search_result;
    }

    @Override
    protected void initData() {
        if (presenter == null) {
            presenter = new SearchResultPresenter();
            presenter.onAttach(this);
        }
        if (dataHelper == null) {
            dataHelper = new ResultDataHelper();
        }

        filterFragment = (SearchFilterFragment) getChildFragmentManager().findFragmentById(R.id.filter_layout);
        if (filterFragment != null) filterFragment.setOnFilterClick(this);
    }

    @Override
    protected void initView(View mRootView) {
        topLinearParent = mRootView.findViewById(R.id.result_top_parent);
        topRv = mRootView.findViewById(R.id.result_top_rv);
        smartRefreshLayout = mRootView.findViewById(R.id.smart_refresh_layout);
        resultRv = mRootView.findViewById(R.id.result_recycler_view);
        topLine = mRootView.findViewById(R.id.top_line);
        drawerLayout = mRootView.findViewById(R.id.search_drawer_layout);

        CrashBugLinearLayoutManager manager = new CrashBugLinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        topRv.setLayoutManager(manager);
        hotAdapter = new SearchResultHotAdapter(mContext, this);
        topRv.setAdapter(hotAdapter);

        resultLinearManager = new CrashBugGridLayoutManager(mContext, SearchResultConstant.alone);
        resultRv.setLayoutManager(resultLinearManager);
        resultAdapter = new SearchResultAdapter(mContext);
        resultRv.setAdapter(resultAdapter);
    }

    public void startToSearch(String inputText) {
        ToastUtils.show(mContext, "开始搜索-"+inputText);
        initData();
        presenter.getHotWords(inputText);
        this.mKeyWords = inputText;
        presenter.startToSearch(inputText, 1, "1", "");
    }

    @Override
    public void getHotWordsSucc(TagModel data) {
        //热搜
        List<TagModel.Tag> tagList = data.hot;
        if (tagList != null && tagList.size() > 0) {
            hotAdapter.setData(tagList);
            hotAdapter.notifyDataSetChanged();
        } else {
            topRv.setVisibility(View.GONE);
        }
    }

    @Override
    public void getHotWordsFailed(String message) {
        topRv.setVisibility(View.GONE);
    }

    @Override
    public void onWordsClick(View view) {
        String title = (String) view.getTag();
        ToastUtils.show(mContext, title);
        this.mKeyWords = title;
        presenter.startToSearch(title, 1, "0" ,"");
    }

    @Override
    public void searchResultSucc(ConditionListModel data) {
        if (data != null) {
//            data.condition.sortList size > 0 显示
            // 顶部销量 todo init=0时不返回，需要本地保存
            ConditionListModel.Condition condition = data.condition;
            if (condition == null || condition.sortList == null || condition.sortList.size() == 0) {
                topLinearParent.setVisibility(View.GONE);
                topLine.setVisibility(View.GONE);
            } else {

                topLinearParent.setVisibility(View.VISIBLE);
                topLine.setVisibility(View.VISIBLE);
                dataHelper.bindTopData(mContext, topLinearParent, condition.sortList, this);
            }
            // recyclerView
            resultAdapter.setData(data.coupon_list);
        }
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


    @Override
    public void onSortClick(View view) {
        // has_switch false 读desc
        // has_switch true 低-高 asc  高-低  decs
        ConditionListModel.SortList bean = (ConditionListModel.SortList) view.getTag();
        if (bean != null) {
            if (bean.has_switch) {
                // todo 记录一下 <name,desc>

            } else {
                //todo page_no
                presenter.startToSearch(mKeyWords,1,"0",bean.desc);
            }
        }
    }

    @Override
    public void onShowTypeClick(View view) {
        SearchResultConstant.alone = SearchResultConstant.alone == 1 ? 2 : 1;
        resultLinearManager.setSpanCount(SearchResultConstant.alone);
        resultAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFilterClick(View view) {
        // 筛选点击
        if (drawerLayout.isDrawerOpen(Gravity.END)) {
            drawerLayout.closeDrawer(Gravity.END);
        } else {
            drawerLayout.openDrawer(Gravity.END);
        }

    }

    @Override
    public void onFilterClick(String price, String brand) {
        if (drawerLayout.isDrawerOpen(Gravity.END)) {
            drawerLayout.closeDrawer(Gravity.END);
        }
        ToastUtils.show(mContext,"brand = "+brand);
    }
}
