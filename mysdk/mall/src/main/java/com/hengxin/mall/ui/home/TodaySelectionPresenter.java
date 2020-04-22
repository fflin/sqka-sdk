package com.hengxin.mall.ui.home;


import com.hengxin.basic.api.ApiHelper;
import com.hengxin.basic.base.ApiErrorConsumer;
import com.hengxin.basic.base.BasePresenter;

public class TodaySelectionPresenter extends BasePresenter<TodaySelectionContract.View> implements TodaySelectionContract.Presenter {
    public void request() {
        mView.showLoading();
        mDisposable.add(ApiHelper.get().getTodaySelection("tb/home", "v3").subscribe(result -> {
            if (isViewAttached()) {
                if (result.error == 0) {
                    mView.success(result);
                }
                else {
                    mView.onError(result.message);
                }
            }
            mView.hideLoading();
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
        mDisposable.add(ApiHelper.get().loadNewPage("tb/home", init, cid, sort, page_no).subscribe(conditionListModelBaseResult -> {
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
