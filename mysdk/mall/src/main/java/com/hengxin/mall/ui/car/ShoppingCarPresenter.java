package com.hengxin.mall.ui.car;

import com.hengxin.basic.base.ApiErrorConsumer;
import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.util.RxUtils;
import com.hengxin.mall.base.BasePresenter;
import com.hengxin.mall.model.AuthHistoryModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * author : fflin
 * date   : 2020/6/1 12:51
 * desc   : 获取购物车列表
 * version: 1.0
 */
public class ShoppingCarPresenter extends BasePresenter<ShoppingCarContract.View> implements ShoppingCarContract.Presenter {
    @Override
    public void getGoodsList(String pageNo) {
        mView.showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("page_no", pageNo);
        mDisposable.add(mApiService.authHistory("http://test.rrh.51youhuihezi.com",mApiHelper.getSignSecurity(mApiHelper.addBaseMap(map))).compose(RxUtils.transformResult()).subscribe(new Consumer<BaseResult<AuthHistoryModel>>() {
            @Override
            public void accept(BaseResult<AuthHistoryModel> homeModelBaseResult) {
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
                    mView.failed(message);
                }
                mView.hideLoading();
            }
        }));
    }
}
