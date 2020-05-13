package com.hengxin.pay.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.hengxin.basic.util.Log;

import java.util.Map;

/**
 * author : fflin
 * date   : 2020/5/12 11:21
 * desc   : 支付宝支付，传签名，返回支付结果
 * version: 1.0
 */
public class AliPay {

    private static final int SDK_PAY_FLAG = 1;
    private IPayResult mResult;
    private String payNo;

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


    public AliPay() {

    }

    /**
     *  调起支付宝支付的方法
     * @param activity  activity
     * @param sign 签名
     * @param result 回调
     * @param payNo 订单号
     */
    public void payBySign(final Activity activity, final String sign, IPayResult result, String payNo) {
        this.mResult = result;
        this.payNo = payNo;
        final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // todo 支付调试
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(sign, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }
}
