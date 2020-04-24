package com.hengxin.mall.ui.home;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hengxin.basic.base.BaseResult;
import com.hengxin.mall.model.ConditionListModel;
import com.hengxin.mall.model.CouponModel;
import com.hengxin.mall.model.HomeModel;
import com.hengxin.basic.util.Log;
import com.hengxin.basic.util.NetworkUtils;
import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseFragment;
import com.hengxin.mall.base.VLRAdapter;
import com.hengxin.mall.inter.OnCallBackDetail;
import com.hengxin.mall.manager.CrashBugLinearLayoutManager;
import com.hengxin.mall.ui.detail.GoodsDetailActivity;
import com.hengxin.mall.view.AutoClassicsFooter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/20 17:07
 * desc   :
 * version: 1.0
 */
public class MallHomeFragment extends BaseFragment implements OnCallBackDetail, TodaySelectionContract.View {

    private View rootView;
    private SmartRefreshLayout refreshLayout;
    private boolean isLoanding = false;
    private RecyclerView recycleListView;
    private CrashBugLinearLayoutManager mLayoutManager;
    private View wait_layout;
    private AutoClassicsFooter refreshFooter;
    private String cid;
    private View floatingActionButton;
    private List<Object> AllDate = new ArrayList();
    private VLRAdapter vlrAdapter;
    private boolean isFinshRequest = true;
    private boolean isReload;//点击重新加载时，再显示loading
    private TodaySelectionPresenter mPresenter;
    private int index = 1;

    @Override
    protected int setLayout() {
        return R.layout.fm_mall_home;
    }


    @Override
    protected void initData() {
        mPresenter = new TodaySelectionPresenter();
        mPresenter.onAttach(this);
        requestNewFristHome();

        TodaySelectionTypeRBaseItem mItem = new TodaySelectionTypeRBaseItem(mContext, getChildFragmentManager());
        mItem.setCallBackDetail(this);
        vlrAdapter = new VLRAdapter(mItem);
        recycleListView.setAdapter(vlrAdapter);
    }

    @Override
    protected void initView(View rootView) {
        this.rootView = rootView;
        rootView.findViewById(R.id.activity_search_back).setOnClickListener(v -> mActivity.finish());
        EditText etSearch = rootView.findViewById(R.id.et_search);
        View icClear = rootView.findViewById(R.id.iv_clear);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                icClear.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });

        icClear.setOnClickListener(v -> etSearch.setText(""));

        rootView.findViewById(R.id.tv_order).setOnClickListener(v -> ToastUtils.show(mContext, "订单页面，开发中"));

        floatingActionButton = rootView.findViewById(R.id.detail_floating_action_src);
        floatingActionButton.setOnClickListener(v -> {
            mLayoutManager.scrollToPosition(0);
            floatingActionButton.setVisibility(View.GONE);
        });

        wait_layout = rootView.findViewById(R.id.wait_layout);
        TextView empty_textview = rootView.findViewById(R.id.tv_net_reload);
        empty_textview.setOnClickListener(v -> {
            if (MallHomeFragment.this.reLoadEnable()) {
                wait_layout.setVisibility(View.VISIBLE);
                isReload = true;
                MallHomeFragment.this.requestNewFristHome();
            }
        });
        refreshLayout = rootView.findViewById(R.id.smart_refresh_layout);
        refreshFooter = rootView.findViewById(R.id.smart_refresh_layout_footer);
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            refreshFooter.setNoMoreData(false);
            requestNewFristHome();
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> loadNewPage(index));
        recycleListView = ((View) rootView).findViewById(R.id.coupon_list_refresh_lv);
        mLayoutManager = new CrashBugLinearLayoutManager(mContext);
        recycleListView.setLayoutManager(mLayoutManager);
        recycleListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLayoutManager.findFirstVisibleItemPosition() > 2) {
                        floatingActionButton.setVisibility(View.VISIBLE);
                    } else {
                        floatingActionButton.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
                    if (refreshLayout.getState() == RefreshState.Loading) {
                    } else {
                        loadNewPage(index);
                    }
                }
            }
        });

    }

    private void requestNewFristHome() {
        if (AllDate.size() > 0) AllDate.clear();
        index = 1;
        mPresenter.request();
    }

    public void loadNewPage(int arg4) {
        if (!isFinshRequest) {
            return;
        }
        isFinshRequest = false;
        index = arg4;
        mPresenter.loadNewPage(false, cid, "", index);
    }


    @Override
    public void onClickJumpChaoRenDetail(Object date) {
        GoodsDetailActivity.startDetailActivity(mContext, "2");
    }

    @Override
    public void onLinkFestivalTaobao(Object date) {

    }

    @Override
    public void onCheckUpdateApp() {

    }

    @Override
    public void success(BaseResult<HomeModel> result) {
        if (result.error == 0) {
            HomeModel data = result.data;
            cid = data.cid;
            if (data.page_list != null && data.page_list.size() > 0) {
                wait_layout.setVisibility(View.GONE);
                hideLoadError(rootView);
                refreshLayout.setVisibility(View.VISIBLE);
                List<HomeModel.HomePageItem> pageList = data.page_list;
                getSortList(pageList, data.newCateItem);
                vlrAdapter.reLoadData(AllDate, true);
                if (refreshLayout.getState() == RefreshState.Refreshing) {
                    refreshLayout.finishRefresh();
                }
                isFinshRequest = true;
                loadNewPage(1);
            } else {
                if (refreshLayout.getState() == RefreshState.Refreshing) {
                    refreshLayout.finishRefresh();
                }
                wait_layout.setVisibility(View.GONE);
                refreshLayout.setVisibility(View.GONE);
                showLoadError(rootView);
            }
        } else {
            if (refreshLayout.getState() == RefreshState.Refreshing) {
                refreshLayout.finishRefresh();
            }
            wait_layout.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.GONE);
            showLoadError(rootView);
        }
    }

    @Override
    public void loadNewPageSuccess(BaseResult<ConditionListModel> result) {
        int total = result.data.paginator.pages;
        List resultData = result.data.coupon_list;
        if (resultData != null && resultData.size() > 0 && index < total) {
            List pairList = getPairList(resultData);
            if (refreshLayout.getState() == RefreshState.Loading) refreshLayout.finishLoadMore();
            vlrAdapter.reLoadData(pairList, false);
            index++;
        } else {
            hideLoadError(rootView);
            refreshLayout.finishLoadMore(true);
            refreshFooter.setNoMoreData(true);
        }
        isFinshRequest = true;
    }

    @Override
    public void loadNewPageError(String message) {
        hideLoading();
        if (vlrAdapter == null) {
            if (NetworkUtils.getNetworkType(mContext) == NetworkUtils.NET_TYPE.TYPE_NONE) {
                ToastUtils.show(mContext, "网络不通，请检查网络后点击重试");
            }
        } else if (refreshLayout.getState() == RefreshState.Loading) {
            refreshLayout.finishLoadMore(false);
        }
    }

    @Override
    public void showLoading() {
        if (isReload) {
            wait_layout.setVisibility(View.VISIBLE);
            isReload = false;
        }
    }

    @Override
    public void hideLoading() {
        wait_layout.setVisibility(View.GONE);
    }

    @Override
    public void onError(String message) {
        if (refreshLayout.getState() == RefreshState.Refreshing) refreshLayout.finishRefresh();
        wait_layout.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.GONE);
        showLoadError(rootView);
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDetach();
            Log.i("fflin", "--------------------- dispose 33 ");
        }
        super.onDestroy();
    }

    private void getSortList(List<HomeModel.HomePageItem> pageList, HomeModel.HomePageItem newCateItem) {
        for (int i = 0; i < pageList.size(); i++) {
            HomeModel.HomePageItem homePageItem = pageList.get(i);
            int type = homePageItem.view_type;
            if (type == 10) {
                AllDate.addAll(homePageItem.coupon_list);
            } else if (newCateItem != null && type == 2) {
                AllDate.add(newCateItem);
            } else {
                AllDate.add(homePageItem);
            }
        }
    }

    private List<Object> getPairList(List<CouponModel> list) {
        List<Object> pairList = new ArrayList();
        pairList.addAll(list);
        return pairList;
    }
}
