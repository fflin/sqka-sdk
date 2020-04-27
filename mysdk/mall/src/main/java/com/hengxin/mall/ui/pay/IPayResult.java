package com.hengxin.mall.ui.pay;

/**
 * author : fflin
 * date   : 2019/10/24 18:33
 * desc   :
 * version: 1.0
 */
public interface IPayResult {
    void onPayResult(String payResult, String payNo, String errorCode);//成功 1
}
