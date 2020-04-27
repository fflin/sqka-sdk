package com.hengxin.mall.ui.pay;


import com.hengxin.basic.base.ApiErrorConsumer;
import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.util.RxUtils;
import com.hengxin.mall.base.BasePresenter;
import com.hengxin.mall.model.AliSignModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * author : fflin
 * date   : 2019/9/30 15:31
 * desc   : 获取支付宝签名
 * version: 1.0
 */
public class PayPresenter extends BasePresenter<PayContract.PayView> implements PayContract.IPayPresenter {
    @Override
    public void getAliSign(String type, String orderId) {
        Map<String, String> map = new HashMap<>();
        map.put("type",type);
        map.put("order_id",orderId);
        mDisposable.add(mApiService.getAliSign("http://test.sqka.yunyouduobao.com", mApiHelper.getSignSecurity(mApiHelper.addBaseMap(map))).compose(RxUtils.transformResult()).subscribe(new Consumer<BaseResult<AliSignModel>>() {
            @Override
            public void accept(BaseResult baseResult) throws Exception {
                mView.onSignSuccStr((AliSignModel) baseResult.data);
            }
        }, new ApiErrorConsumer() {
            @Override
            public void onFail(int code, String message) {
                mView.onError(message);
            }
        }));
    }
}
