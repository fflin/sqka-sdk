package com.hengxin.mall.ui.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hengxin.basic.RxBus;
import com.hengxin.basic.util.ToastUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.event.SearchEventModel;
import com.hengxin.mall.ui.order.MallOrderAllFragment;
import com.hengxin.mall.ui.order.MallOrderSaleFragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * author : fflin
 * date   : 2020/4/30 11:44
 * desc   : 搜索页面，展示历史和结果，分为两个Fragment
 * version: 1.0
 */
public class SearchActivity extends BaseActivity {

    private String inputText;
    private SearchResultFragment resultFragment;
    private SearchHomeFragment homeFragment;
    private FragmentManager fragmentManager;
    private Fragment curFragment;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void initData() {
        homeFragment = new SearchHomeFragment();
        resultFragment = new SearchResultFragment();
        fragmentManager = getSupportFragmentManager();
        switchFragment(homeFragment);
        disposable.add(RxBus.getInstance().register(SearchEventModel.class).subscribe(new Consumer<SearchEventModel>() {
            @Override
            public void accept(SearchEventModel searchEventModel) throws Exception {
                switchFragment(resultFragment);
                resultFragment.startToSearch(searchEventModel.inputText);
            }
        }));

    }

    private void switchFragment(Fragment toFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.search_fragment, toFragment);
        curFragment = toFragment;
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (curFragment != homeFragment) {
            switchFragment(homeFragment);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void initView() {
        findViewById(R.id.search_back).setOnClickListener(v -> {
            if (curFragment != homeFragment) {
                switchFragment(homeFragment);
            } else {
                finish();
            }
        });
        ImageView ivClear = findViewById(R.id.iv_clear);
        EditText etSearch = findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputText = s.toString().trim();
                if (inputText.length() > 0) {
                    ivClear.setVisibility(View.VISIBLE);
                } else {
                    ivClear.setVisibility(View.INVISIBLE);
                }

            }
        });

        ivClear.setOnClickListener(v -> {
            if (etSearch.getText().length() > 0) {
                etSearch.setText("");
                ivClear.setVisibility(View.INVISIBLE);
            }
        });


        findViewById(R.id.tv_search).setOnClickListener(v -> {
            //1 保存历史记录
            new SearchSpHelper().addSearchWord(inputText);

            //2 通知Fragment刷新数据
            if (!TextUtils.isEmpty(inputText)) {
                switchFragment(resultFragment);
                resultFragment.startToSearch(inputText);
            } else {
                ToastUtils.show(SearchActivity.this, "请输入想要搜索的商品名称");
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_search;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
