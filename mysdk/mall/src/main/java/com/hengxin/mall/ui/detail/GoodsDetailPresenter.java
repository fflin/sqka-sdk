package com.hengxin.mall.ui.detail;

import com.hengxin.basic.base.ApiErrorConsumer;
import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.util.RxUtils;
import com.hengxin.mall.base.BasePresenter;
import com.hengxin.mall.model.DetailModel;
import com.hengxin.mall.model.HomeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * author : fflin
 * date   : 2020/4/24 12:54
 * desc   :
 * version: 1.0
 */
public class GoodsDetailPresenter extends BasePresenter<GoodsDetailContract.View> implements GoodsDetailContract.Presenter {

    @Override
    public void getDetail(String goodsId) {
        mView.showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("goods_id", goodsId);
        mDisposable.add(mApiService.getGoodsDetail("", mApiHelper.getSignSecurity(mApiHelper.addBaseMap(map))).compose(RxUtils.transformResult()).subscribe(new Consumer<BaseResult<HomeModel>>() {
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
                    // 组装个假数据
                    DetailModel model = getTestModel();
                    mView.onErrorTest(model);
                    mView.failed(message);
                    mView.onError(message);
                }
                mView.hideLoading();
            }
        }));
    }

    private DetailModel getTestModel() {
        DetailModel model = new DetailModel();
        model._source = "1";
        model.unique_id = "123";
        model.goods_id = "2";
        model.goods_name = "这是测试数据name，在getTestModel方法生成,很长很长很长很长很长很长很长很长很长很长";
        model.goods_desc = "这是测试数据desc，在getTestModel方法生成,但我不知道怎么用，用在哪";
        model.goods_tag = "tag";
        model.old_price = 1999;
        model.price = 1099.9;

        model.banner_list = new ArrayList<>();
        model.banner_list.add("http://img.alicdn.com/i4/2200662002307/O1CN01xutjNt1SueJoeOnWj_!!2200662002307-2-lubanu-s.png");
        model.banner_list.add("http://img.alicdn.com/i3/2200662002307/O1CN013yJvMZ1SueJj4rpLG_!!2200662002307-2-lubanu-s.png");
        model.banner_list.add("http://img.alicdn.com/i3/2200662002307/O1CN01QprFNP1SueJeSuFtb_!!2200662002307-2-lubanu-s.png");
        model.banner_list.add("http://img.alicdn.com/i4/2200662002307/O1CN01eEmdWV1SueJYgwACe_!!2200662002307-2-lubanu-s.png");

        model.pic_list = new ArrayList<>();
        model.pic_list.add("https://img.alicdn.com/imgextra/i2/2200662002307/O1CN01uLFhyr1SueJVVyrsA_!!2200662002307.png");
        model.pic_list.add("https://img.alicdn.com/imgextra/i3/2200662002307/O1CN01W1aVV91SueJf3KAmT_!!2200662002307.jpg");
        model.pic_list.add("https://img.alicdn.com/imgextra/i4/2200662002307/O1CN018O1Dvo1SueJktDuGZ_!!2200662002307.jpg");

        return model;
    }
}
