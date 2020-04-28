package com.hengxin.mall.ui.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.hengxin.basic.util.Log;
import com.hengxin.basic.util.ToastUtils;
import com.hengxin.pay.model.AliSignModel;
import com.hengxin.pay.model.PayResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * author : fflin
 * date   : 2019/10/15 10:55
 * desc   : 调起支付宝或者微信
 * version: 1.0
 */
public class PayHelper implements PayContract.PayView {

    private Activity mActivity;
    private static final int SDK_PAY_FLAG = 1;
    private String payType;
    private static final String TYPE_ALI = "ali";
    private static final String TYPE_WX = "wx";
    private IPayResult mResult;
    private String payNo;

    public PayHelper(Activity activity) {
        this.mActivity = activity;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Log.i("fflin", "支付宝支付成功!");
                        mResult.onPayResult("0",payNo,resultStatus);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Log.i("fflin", "支付宝支付失败" + resultStatus + "; " + resultInfo);
                        mResult.onPayResult("1",payNo,resultStatus);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    //{"title":"","price":0,"pay_types":"ali"}
    //{"title":"","price":0,"pay_types":"wx"}
    public void payOrder(String json, IPayResult result) {
        this.mResult = result;
        try {
            JSONObject object = new JSONObject(json);
            payType = (String) object.opt("pay_types");
            String orderId = (String) object.opt("order_id");
            Log.i("fflin","发起支付  payType = "+payType+"; orderId= "+orderId);
            if (TextUtils.isEmpty(orderId)) {
                ToastUtils.show(mActivity,"获取订单信息失败，请稍候重试");
                return;
            }
            if (payType.equals(TYPE_ALI)) {
                payByAli(orderId);
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
        payPresenter.getAliSign(payType, orderId);
    }

    private void payByWx(String orderId) {
        /*if (ShareUtils.checkWxApp(mActivity)) {
            PayPresenter payPresenter = new PayPresenter();
            payPresenter.onAttach(this);
            payPresenter.getAliSign(payType, orderId);
        } else {
            new CommonToastUtils(mActivity).Short(mActivity, "请先安装微信").show();
        }*/
    }

    @Override
    public void onSignSuccStr(AliSignModel data) {
        if (!TextUtils.isEmpty(payType)) {
            if (payType.equals(TYPE_ALI)) {
                this.payNo = data.pay_no;
                Log.i("fflin", "debug data = " + data.pay_sign.toString()+";pay_no = "+data.pay_no);
                final Runnable payRunnable = () -> {
                    // todo 支付调试
                    /*PayTask alipay = new PayTask(mActivity);
                    Map<String, String> result = alipay.payV2(data.pay_sign.toString(), true);
                    Log.i("msp", result.toString());

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);*/
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
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
        ToastUtils.show(mActivity,"获取支付信息失败:"+message);
    }
}
