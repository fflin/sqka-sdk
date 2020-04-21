package com.hengxin.basic.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 *
 */

public abstract class ApiErrorConsumer implements Consumer<Throwable> {
//    public GotoDate gotoDate;

    @Override
    public void accept(Throwable e) {
        /*if (BuildConfig.DEBUG && e != null) e.printStackTrace();
        if (e instanceof ConnectException ||
                e instanceof HttpException ||
                e instanceof UnknownHostException ||
                e instanceof InterruptedIOException) {
            faile(ServerCode.API_ERROR_NET_WORK, ServerCode.NET_WORK_ERROR);
            *//*RxBus.getInstance().post(new ApiErrorReport(e));*//*
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            int code = exception.error;
            faile(code, exception.serverMessage + "(" + code + ")", exception.gotoDate);
        } else {
            faile(ServerCode.API_ERROR_UNKNOW, ServerCode.UNKNOW_ERROR);
            *//*RxBus.getInstance().post(new ApiErrorReport(e));*//*
        }*/
    }

    private void faile(int apiErrorCode, String netWorkError) {
        /*if (Looper.getMainLooper() != Looper.myLooper())
            new Handler(Looper.getMainLooper()).post(() -> MessageShowUtils.showMessage(0, netWorkError + "(" + apiErrorCode + ")", null, Global.getContext()));
        else
            MessageShowUtils.showMessage(0, netWorkError + "(" + apiErrorCode + ")", null, Global.getContext());
        onFail(apiErrorCode, netWorkError);*/
    }


    /*protected void faile(int code, String message, GotoDate gotoDate) {
        this.gotoDate = gotoDate;
        if (code == 41017 || code == 40011) {
            FragmentManager fragmentManager;
            Activity activity = ActivityManager.getInstance().currentActivity();
            if (activity instanceof AppCompatActivity) {
                fragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();
                new ErrorDialogFragment().show(message, fragmentManager, "error_show", gotoDate);
            }
        }
        onFail(code, message);
    }*/

    public abstract void onFail(int code, String message);
}
