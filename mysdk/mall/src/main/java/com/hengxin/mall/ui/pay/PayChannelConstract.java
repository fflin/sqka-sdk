package com.hengxin.mall.ui.pay;

import com.hengxin.basic.base.BaseResult;
import com.hengxin.mall.base.IBaseView;
import com.hengxin.mall.base.IPresenter;
import com.hengxin.mall.model.AliSignModel;
import com.hengxin.mall.model.PayChannelModel;

/**
 * author : fflin
 * date   : 2020/4/27 16:55
 * desc   :
 * version: 1.0
 */
public interface PayChannelConstract {

    interface View extends IBaseView {
        void success(BaseResult<PayChannelModel> result);

    }

    interface Presenter extends IPresenter<View> {
        void getChannel();

    }
}
