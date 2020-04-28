package com.hengxin.mall.ui.pay;


import com.hengxin.mall.base.IBaseView;
import com.hengxin.mall.base.IPresenter;
import com.hengxin.pay.model.AliSignModel;

/**
 * date: 2018/11/2
 */
public interface PayContract {
    interface PayView extends IBaseView {

        void onSignSuccStr(AliSignModel data);
    }

    interface IPayPresenter extends IPresenter<PayView> {

        void getAliSign(String subject, String totalAmount);
    }
}
