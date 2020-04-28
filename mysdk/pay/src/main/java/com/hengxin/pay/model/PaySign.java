package com.hengxin.pay.model;

/**
 * author : fflin
 * date   : 2019/10/22 16:26
 * desc   : 这是微信支付的bean
 * version: 1.0
 */
public class PaySign {
    public String appid;
    public String partnerid;
    public String prepayid;
//    public String packagevalue;
    public String noncestr;
    public String timestamp;
    public String sign;

    @Override
    public String toString() {
        return "PaySign{" +
                "appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
