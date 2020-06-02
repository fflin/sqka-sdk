package com.hengxin.mall.ui.car;

import com.hengxin.basic.base.BaseResult;
import com.hengxin.mall.base.IBaseView;
import com.hengxin.mall.base.IPresenter;
import com.hengxin.mall.model.AuthHistoryModel;

/**
 * author : fflin
 * date   : 2020/4/24 12:55
 * desc   : 购物车
 * version: 1.0
 */
public interface ShoppingCarContract {

    interface View extends IBaseView {
        void success(BaseResult<AuthHistoryModel> result);

        void failed(String message);

    }

    interface Presenter extends IPresenter<View> {
        void getGoodsList(String pageNo);
    }
}
