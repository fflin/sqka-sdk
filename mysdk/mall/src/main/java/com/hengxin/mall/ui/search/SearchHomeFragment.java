package com.hengxin.mall.ui.search;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.hengxin.basic.RxBus;
import com.hengxin.basic.sp.SharedPrefUtils;
import com.hengxin.basic.util.Log;
import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseFragment;
import com.hengxin.mall.event.SearchEventModel;
import com.hengxin.mall.model.TagModel;
import com.hengxin.mall.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/30 12:02
 * desc   : 搜索历史和推荐
 * version: 1.0
 */
public class SearchHomeFragment extends BaseFragment implements SearchContract.View {


    private LinearLayout hisParent, hotParent;//历史
    private FlexboxLayout hisFlex, hotFlex;
    private SearchSpHelper searchSpHelper;

    @Override
    protected int setLayout() {
        return R.layout.fm_search;
    }

    @Override
    protected void initData() {
        searchSpHelper = new SearchSpHelper();
        setHistoryView();
        // 网络请求获取热搜
        SearchPresenter presenter = new SearchPresenter();
        presenter.onAttach(this);
        presenter.getHotWords();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setHistoryView();
        }
    }

    private void startToSearch(String text) {
        RxBus.getInstance().post(new SearchEventModel(text));
    }

    private void setHistoryView() {
        setFlexBoxViewData(hisFlex, hisParent, searchSpHelper.getSearchWords());
    }

    private void setFlexBoxViewData(FlexboxLayout flexBoxViewData, LinearLayout parentView, List<String> list) {
        Log.i("fflin", "list.size = " + list.size());
        if (list.size() > 0) {
            parentView.setVisibility(View.VISIBLE);
            flexBoxViewData.removeAllViews();
            for (int i = 0; i < list.size(); i++) {
                String showText = list.get(i);
                int ranHeight = DensityUtil.dip2px(mContext, 30);
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
                lp.setMargins(DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 5));
                final TextView tv = new TextView(mContext);
                tv.setPadding(DensityUtil.dip2px(mContext, 15), 0, DensityUtil.dip2px(mContext, 15), 0);
                tv.setTextColor(Color.parseColor("#ff5a595b"));
                tv.setBackgroundResource(R.drawable.bg_tag);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv.setText(showText);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setLines(1);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startToSearch(showText);
                        searchSpHelper.addSearchWord(showText);
                    }
                });
                flexBoxViewData.addView(tv, lp);
            }
        } else {
            flexBoxViewData.removeAllViews();
            parentView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView(View mRootView) {
        hisParent = mRootView.findViewById(R.id.search_history_parent);
        mRootView.findViewById(R.id.search_history_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(mContext, "清空");
                SharedPrefUtils.remove(SharedPrefUtils.SEARCH_HISTORY_KEY);
                setHistoryView();
            }
        });
        hisFlex = mRootView.findViewById(R.id.search_history_flex);
        hotFlex = mRootView.findViewById(R.id.search_hot_flex);
        hotParent = mRootView.findViewById(R.id.search_hot_parent);
    }



    @Override
    public void getHotWordsSucc(TagModel data) {
        List<TagModel.Tag> hotWords = data.hot;
        Log.i("fflin", "hotWords.size = " + hotWords.size());
        List<String> hotList = new ArrayList<>();
        for (TagModel.Tag tag : hotWords) {
            hotList.add(tag.title);
        }
        setFlexBoxViewData(hotFlex, hotParent, hotList);
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
