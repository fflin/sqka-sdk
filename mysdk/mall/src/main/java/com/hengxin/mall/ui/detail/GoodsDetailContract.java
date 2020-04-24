package com.hengxin.mall.ui.detail;

import com.hengxin.basic.base.BaseResult;
import com.hengxin.mall.base.IBaseView;
import com.hengxin.mall.base.IPresenter;
import com.hengxin.mall.model.DetailModel;
import com.hengxin.mall.model.HomeModel;

/**
 * author : fflin
 * date   : 2020/4/24 12:55
 * desc   :
 * version: 1.0
 */
public interface GoodsDetailContract {

    interface View extends IBaseView {
        void success(BaseResult<HomeModel> result);

        void failed(String message);

        void onErrorTest(DetailModel model);
    }

    interface Presenter extends IPresenter<View> {
        void getDetail(String goodsId);
    }
}
