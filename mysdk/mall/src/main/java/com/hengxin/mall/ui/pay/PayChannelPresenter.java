package com.hengxin.mall.ui.pay;

import com.hengxin.basic.base.ApiErrorConsumer;
import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.util.RxUtils;
import com.hengxin.mall.base.BasePresenter;
import com.hengxin.mall.model.PayChannelModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * author : fflin
 * date   : 2020/4/27 16:56
 * desc   : 获取支付渠道
 * version: 1.0
 */
public class PayChannelPresenter extends BasePresenter<PayChannelConstract.View> implements PayChannelConstract.Presenter {

    @Override
    public void getChannel() {
        mView.showLoading();
        Map<String, String> map = new HashMap<>();
        mDisposable.add(mApiService.getPayChannel("http://test.sqka.yunyouduobao.com", mApiHelper.getSignSecurity(mApiHelper.addBaseMap(map))).compose(RxUtils.transformResult()).subscribe(new Consumer<BaseResult<PayChannelModel>>() {
            @Override
            public void accept(BaseResult<PayChannelModel> homeModelBaseResult) {
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
                mView.onError(message);
                mView.hideLoading();
            }
        }));
    }

}
