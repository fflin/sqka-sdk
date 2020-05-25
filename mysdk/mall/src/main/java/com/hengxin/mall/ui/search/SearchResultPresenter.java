package com.hengxin.mall.ui.search;


import com.hengxin.basic.base.ApiErrorConsumer;
import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.util.Log;
import com.hengxin.basic.util.RxUtils;
import com.hengxin.mall.base.BasePresenter;
import com.hengxin.mall.model.ConditionListModel;
import com.hengxin.mall.model.TagModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * author : fflin
 * date   : 2020/5/6 16:05
 * desc   :
 * version: 1.0
 */
public class SearchResultPresenter extends BasePresenter<SearchResultContract.View> implements SearchResultContract.Presenter {

    @Override
    public void getHotWords(String words) {
        Map<String, String> map = new HashMap<>();
        mDisposable.add(mApiService.requestHotSearch(mApiHelper.getSignSecurity(mApiHelper.addBaseMap(map))).compose(RxUtils.transformResult()).subscribe(new Consumer<BaseResult<TagModel>>() {
            @Override
            public void accept(BaseResult<TagModel> result) {
                if (isViewAttached()) {
                    if (result.error == 0) {
                        mView.getHotWordsSucc(result.data);
                    } else {
                        mView.onError(result.message);
                    }
                } else {
                    Log.i("fflin", "error = isViewAttached ");
                }
            }
        }, new ApiErrorConsumer() {
            @Override
            public void onFail(int code, String message) {
                Log.i("fflin", "error = " + message);
                if (isViewAttached()) {
                    mView.getHotWordsFailed(message);
                } else {
                    Log.i("fflin", "error = isViewAttached ");
                }
            }
        }));
    }

    @Override
    public void startToSearch(String keyword, int pageNo, String init, String sort) {
        Map<String, String> map = new HashMap<>();
        map.put("init", init);
        map.put("q", keyword);
        map.put("page_no", String.valueOf(pageNo));
        map.put("has_coupon", "1");
        map.put("sort", sort);
        if (pageNo == 1) {
            mView.showLoading();
        }
        mDisposable.add(mApiService.search(mApiHelper.getSignSecurity(mApiHelper.addBaseMap(map))).compose(RxUtils.transformResult()).subscribe(new Consumer<BaseResult<ConditionListModel>>() {
            @Override
            public void accept(BaseResult<ConditionListModel> result) {
                mView.searchResultSucc(result.data);
            }
        }, new ApiErrorConsumer() {
            @Override
            public void onFail(int code, String message) {
                mView.onError(message);
            }
        }));
    }
}
