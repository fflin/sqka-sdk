package com.hengxin.pay.model;

/**
 * author : fflin
 * date   : 2019/10/15 11:46
 * desc   : 支付后，回传给H5支付结果
 * version: 1.0
 */
public class PayModel {
    public String result;//0 y成功  1 失败
    public String pay_no;
    public String errorCode;
    public PayModel(String result, String pay_no, String errorCode) {
        this.result = result;
        this.pay_no = pay_no;
        this.errorCode = errorCode;
    }

}
