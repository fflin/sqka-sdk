package com.hengxin.mall.ui.search;

import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.hengxin.basic.util.ToastUtils;
import com.hengxin.basic.util.ViewUtil;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseFragment;
import com.hengxin.mall.manager.CrashBugGridLayoutManager;
import com.hengxin.mall.manager.CrashBugLinearLayoutManager;
import com.hengxin.mall.model.ConditionListModel;
import com.hengxin.mall.model.CouponNewModel;
import com.hengxin.mall.model.TagModel;
import com.hengxin.mall.ui.search.adapter.SearchResultAdapter;
import com.hengxin.mall.ui.search.adapter.SearchResultHotAdapter;
import com.hengxin.mall.ui.search.helper.ResultDataHelper;
import com.hengxin.mall.ui.search.inter.OnFilterClickListener;
import com.hengxin.mall.ui.search.inter.OnHotWordsClick;
import com.hengxin.mall.ui.search.inter.OnResultTopClick;
import com.hengxin.mall.view.SpaceItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
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
    private static final String searchArg = "searchKey";
    private int pageNo = 1;
    private String initParam = "0";
    private List<ConditionListModel.SortList> sortList;

    public static SearchResultFragment instantiate(String keyword) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(searchArg,keyword);
        fragment.setArguments(bundle);
        return fragment;
    }

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

        Bundle bundle = getArguments();
        String searchKey = bundle.getString(searchArg);
        if (!TextUtils.isEmpty(searchKey)) {
            pageNo = 1;
            startToSearch(searchKey);
        }

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNo = 1;
            startToSearch(mKeyWords);
        });

        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageNo++;
            startToSearch(mKeyWords);
        });
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

        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        resultRv.addItemDecoration(new SpaceItemDecoration(ViewUtil.dp2px(5)));
        resultRv.setLayoutManager(resultLinearManager);
        resultAdapter = new SearchResultAdapter(mContext);
        resultRv.setAdapter(resultAdapter);
    }

    @Override
    protected void onReloadClick() {
        ToastUtils.show(mContext,"onReloadClick");
    }

    public void startToSearch(String inputText) {
        presenter.getHotWords(inputText);
        this.mKeyWords = inputText;
        presenter.startToSearch(inputText, pageNo, "1", "");
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
        pageNo = 1;
        presenter.startToSearch(title, pageNo, "0" ,"");
    }

    private List<CouponNewModel> resultList = new ArrayList<>();

    @Override
    public void searchResultSucc(ConditionListModel data) {
        resetSmartRefreshStatu(smartRefreshLayout);
        if (data != null) {
//            data.condition.sortList size > 0 显示
            // 顶部销量 init=0 时不返回，需要本地保存
            if (pageNo == 1) {
                ConditionListModel.Condition condition = data.condition;
                if (condition != null && condition.sortList != null && condition.sortList.size() > 0) {
                    sortList = condition.sortList;
                }
                resultList.clear();
            }
            if (sortList == null || sortList.size() == 0) {
                topLinearParent.setVisibility(View.GONE);
                topLine.setVisibility(View.GONE);
            } else {
                topLinearParent.setVisibility(View.VISIBLE);
                topLine.setVisibility(View.VISIBLE);
                dataHelper.bindTopData(mContext, topLinearParent, sortList, this);
            }

            // 保存数据
            resultList.addAll(data.coupon_list);
            resultAdapter.setData(resultList);
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
        resetSmartRefreshStatu(smartRefreshLayout);
    }


    @Override
    public void onSortClick(View view) {
        // has_switch false 读desc
        // has_switch true 低-高 asc  高-低  decs
        ConditionListModel.SortList bean = (ConditionListModel.SortList) view.getTag();
        if (bean != null) {
            for (int i = 0; i < sortList.size(); i++) {
                ConditionListModel.SortList b = sortList.get(i);
                if (b.postion == i) {
                    b.postion = -1;
                }
                if (b.equals(bean)) {
                    bean.postion = i;
                }

                if (b.has_switch) {
                    if (b.def_order.equals(b.asc)) {
                        b.def_order = b.desc;
                    } else {
                        b.def_order = b.asc;
                    }
                    // 价格
                    presenter.startToSearch(mKeyWords,pageNo,"0",bean.def_order);
                }
            }

            if (!bean.has_switch) {
                // 销量  综合
                presenter.startToSearch(mKeyWords,pageNo,"0",bean.desc);
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
