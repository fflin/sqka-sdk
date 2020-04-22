package com.hengxin.mall.ui.home;


import com.hengxin.basic.base.ApiErrorConsumer;
import com.hengxin.mall.base.BasePresenter;
import com.hengxin.basic.base.BaseResult;
import com.hengxin.mall.model.HomeModel;
import com.hengxin.basic.util.RxUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class TodaySelectionPresenter extends BasePresenter<TodaySelectionContract.View> implements TodaySelectionContract.Presenter {
    public void request() {
        mView.showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("version", "v3");
        mDisposable.add(mApiService.getTodaySelection("tb/home", mApiHelper.getSignSecurity(mApiHelper.addBaseMap(map))).compose(RxUtils.transformResult()).subscribe(new Consumer<BaseResult<HomeModel>>() {
            @Override
            public void accept(BaseResult<HomeModel> homeModelBaseResult) {
                if (isViewAttached()) {
                    if (homeModelBaseResult.error == 0) {
                        mView.success(homeModelBaseResult);
                    } else {
                        mView.onError(homeModelBaseResult.message);
                    }
                }
                mView.hideLoading();
            }
        }, new ApiErrorConsumer() {
            @Override
            public void onFail(int code, String message) {
                if (isViewAttached()) {
                    mView.onError(message);
                }
                mView.hideLoading();
            }
        }));
    }

    @Override
    public void loadNewPage(boolean init, String cid, String sort, int page_no) {
        HashMap<String, String> map = new HashMap<>();
        map.put("init", init ? "1" : "0");
        map.put("cid", cid);
        map.put("sort", sort);
        map.put("page_no", String.valueOf(page_no));
        mDisposable.add(mApiService.loadNewPage("tb/home", mApiHelper.getSignSecurity(mApiHelper.addBaseMap(map))).compose(RxUtils.transformResult()).subscribe(
                conditionListModelBaseResult -> {
                    if (isViewAttached())
                        mView.loadNewPageSuccess(conditionListModelBaseResult);
                }, new ApiErrorConsumer() {
                    @Override
                    public void onFail(int code, String message) {
                        if (isViewAttached()) mView.loadNewPageError(message);
                    }
                }));
    }
}
