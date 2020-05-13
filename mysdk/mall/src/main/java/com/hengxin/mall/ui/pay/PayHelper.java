package com.hengxin.mall.ui.pay;

import android.app.Activity;
import android.text.TextUtils;

import com.hengxin.basic.util.Log;
import com.hengxin.basic.util.ToastUtils;
import com.hengxin.pay.model.AliSignModel;
import com.hengxin.pay.model.IPayResult;
import com.hengxin.pay.model.AliPay;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author : fflin
 * date   : 2019/10/15 10:55
 * desc   : 调起支付宝或者微信
 * version: 1.0
 */
public class PayHelper implements PayContract.PayView {

    private Activity mActivity;

    private String payType;
    private static final String TYPE_ALI = "ali";
    private static final String TYPE_WX = "wx";
    private IPayResult mResult;
    private String payNo;

    public PayHelper(Activity activity) {
        this.mActivity = activity;
    }


    //{"title":"","price":100,"pay_types":"ali","order_id":"13202005121049411620021"}
    //{"title":"","price":0,"pay_types":"wx"}
    public void payOrder(String json, IPayResult result) {
        this.mResult = result;
        try {
            JSONObject object = new JSONObject(json);
            payType = (String) object.opt("pay_types");
            String orderId = (String) object.opt("order_id");
            Log.i("fflin", "发起支付  payType = " + payType + "; orderId= " + orderId);
            if (TextUtils.isEmpty(orderId)) {
                ToastUtils.show(mActivity, "获取订单信息失败，请稍候重试");
                return;
            }
            if (payType.equals(TYPE_ALI)) {
                /*payByAli(orderId);*/
                //todo 直接传sign给支付模块支付宝可以支付， 真实场景先调用payByAli(orderId);收到sign后再调用支付方法
                AliPay aliPay = new AliPay();
                aliPay.payBySign(mActivity,
                        "app_id=2019091767467183&biz_content=%7B%22subject%22%3A%22%E5%8C%97%E4%BA%AC%E7%83%A4%E9%B8%AD%E6%8B%BC%E5%9B%A2%E5%95%A6%22%2C%22out_trade_no%22%3A%22ali_202005121133353450782%22%2C%22total_amount%22%3A0.01%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftest.sqka.yunyouduobao.com%2Fapi%2Fapp%2Fcallback%2Forder%2Fpay%2Fali&sign_type=RSA2&timestamp=2020-05-12%2011%3A33%3A35&version=1.0&sign=hZgQFFS5ubLrjBsqX%2FRo5OyVotUoXCJi9ABgloTAh2QndgvGv0V9JX%2F56BXyiQd4XdfiQqklilGjZ5UmZ5GHFl%2BOIn7SatsuCmIcxFiyZL%2BrM3j1R%2Fng7Al0751Mlh92Q3ICd0K%2FcIcVFROgH%2FTXck3v%2F%2BMRDdBl%2BjdnOy4VX0P7DZWxyStGZIPTCAkDrWvdlcxb%2FVmKHM2B%2FZontEcBCdTaPikE%2B81xdU0xjrYISbXDnva17qFAym12xEF1HS70kOBMynWfjGm8S43YtkAsR0EqJXYvy5UzjpV4ZGhEVtFFVHNBn0E9ERWsUtksfmNxQecTWDQoFvQpJU6DFIvbGg%3D%3D",
                        result,
                        "23334");
            } else if (payType.equals(TYPE_WX)) {
                payByWx(orderId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void payByAli(String orderId) {
        PayPresenter payPresenter = new PayPresenter();
        payPresenter.onAttach(this);
        payPresenter.getPaySign(payType, orderId);
    }

    private void payByWx(String orderId) {
        /*if (ShareUtils.checkWxApp(mActivity)) {
            PayPresenter payPresenter = new PayPresenter();
            payPresenter.onAttach(this);
            payPresenter.getPaySign(payType, orderId);
        } else {
            new CommonToastUtils(mActivity).Short(mActivity, "请先安装微信").show();
        }*/
    }

    @Override
    public void onSignSuccStr(AliSignModel data) {
        if (!TextUtils.isEmpty(payType)) {
            if (payType.equals(TYPE_ALI)) {
                this.payNo = data.pay_no;
                Log.i("fflin", "debug data = " + data.pay_sign.toString() + ";pay_no = " + data.pay_no);
                AliPay aliPay = new AliPay();
                aliPay.payBySign(mActivity,
                        "app_id=2019091767467183&biz_content=%7B%22subject%22%3A%22%E5%8C%97%E4%BA%AC%E7%83%A4%E9%B8%AD%E6%8B%BC%E5%9B%A2%E5%95%A6%22%2C%22out_trade_no%22%3A%22ali_202005121133353450782%22%2C%22total_amount%22%3A0.01%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=utf-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftest.sqka.yunyouduobao.com%2Fapi%2Fapp%2Fcallback%2Forder%2Fpay%2Fali&sign_type=RSA2&timestamp=2020-05-12%2011%3A33%3A35&version=1.0&sign=hZgQFFS5ubLrjBsqX%2FRo5OyVotUoXCJi9ABgloTAh2QndgvGv0V9JX%2F56BXyiQd4XdfiQqklilGjZ5UmZ5GHFl%2BOIn7SatsuCmIcxFiyZL%2BrM3j1R%2Fng7Al0751Mlh92Q3ICd0K%2FcIcVFROgH%2FTXck3v%2F%2BMRDdBl%2BjdnOy4VX0P7DZWxyStGZIPTCAkDrWvdlcxb%2FVmKHM2B%2FZontEcBCdTaPikE%2B81xdU0xjrYISbXDnva17qFAym12xEF1HS70kOBMynWfjGm8S43YtkAsR0EqJXYvy5UzjpV4ZGhEVtFFVHNBn0E9ERWsUtksfmNxQecTWDQoFvQpJU6DFIvbGg%3D%3D",
                        mResult,
                        "23334");
            } else if (payType.equals(TYPE_WX)) {
                // 微信的pay_no需要单独保存
                // todo 支付调试
                /*PayReq request = new PayReq();
                Gson gson = new Gson();
                //这里换成了新的包装类
                Log.i("fflin","data.pay_sign.toString() = "+data.pay_sign.toString()+";pay_no = "+data.pay_no);
                PaySign s = gson.fromJson(data.pay_sign.toString(),PaySign.class);
                Log.i("fflin","s = "+s.toString());
                request.appId = s.appid;
                request.partnerId = s.partnerid;//商户号
                request.prepayId= s.prepayid;//微信返回的交易支付会话id
                request.packageValue = "Sign=WXPay";//固定值
                request.nonceStr= s.noncestr;//随机字符串
                request.timeStamp= s.timestamp;//时间戳
                request.sign= s.sign;//签名,不是应用的sign*/
//                CouponApp.api.sendReq(request);
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String message) {
        ToastUtils.show(mActivity, "获取支付信息失败:" + message);
    }
}
