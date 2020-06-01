package com.hengxin.mall.ui.search;

import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.hengxin.basic.RxBus;
import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseFragment;
import com.hengxin.mall.event.SearchFilterHideEvent;
import com.hengxin.mall.manager.CrashBugGridLayoutManager;
import com.hengxin.mall.model.SearchFilterModel;
import com.hengxin.mall.ui.search.adapter.SearchFilterAdapter;
import com.hengxin.mall.ui.search.inter.OnFilterBrandClick;

import java.util.ArrayList;

/**
 * author : fflin
 * date   : 2020/5/8 14:51
 * desc   : 筛选 这个页面做好了是一个RecyclerView根据type区分显示
 * version: 1.0
 */
public class SearchFilterFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private EditText maxPrice;
    private EditText minPrice;
    private ArrayList<String> brands = new ArrayList<>();
    private String selectBrand;

    @Override
    protected int setLayout() {
        return R.layout.fragment_filter;
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 50; i++) {
            brands.add("品牌"+i);
        }

        SearchFilterAdapter adapter = new SearchFilterAdapter(mContext, brands, new OnFilterBrandClick() {
            @Override
            public void onBrandClick(View view) {
                selectBrand = (String) view.getTag();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initView(View mRootView) {
        minPrice = mRootView.findViewById(R.id.filter_price_min);
        maxPrice = mRootView.findViewById(R.id.filter_price_max);
        recyclerView = mRootView.findViewById(R.id.service_filters);
        recyclerView.setLayoutManager(new CrashBugGridLayoutManager(mContext,3));
        mRootView.findViewById(R.id.filter_resure_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(mContext,"完成");
                SearchFilterModel model = new SearchFilterModel();
                model.maxPrice = maxPrice.getText().toString().trim();
                model.minPrice = minPrice.getText().toString().trim();
                model.brand = selectBrand;
                RxBus.getInstance().post(new SearchFilterHideEvent(model));
            }
        });
        mRootView.findViewById(R.id.filter_reset_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(mContext,"取消");
                RxBus.getInstance().post(new SearchFilterHideEvent(null));
            }
        });
    }

    @Override
    protected void onReloadClick() {

    }
}
