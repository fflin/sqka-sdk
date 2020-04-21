package com.hengxin.basic.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hengxin.basic.R;


/**
 *
 */
public class CommonToastUtils {
    private Toast toast;
    private View toastView;


    public CommonToastUtils(Context context, int duration) {
        toast = new Toast(context);
        toastView = LayoutInflater.from(context).inflate(R.layout.common_toast, null);
        toast.setView(toastView);
        toast.setDuration(duration);
    }

    public CommonToastUtils(Context context) {
        toast = new Toast(context);
        toastView = LayoutInflater.from(context).inflate(R.layout.common_toast, null);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
    }


    /**
     * 设置Toast字体及背景
     */
    /*public CommonToastUtils setToastBackground(int messageColor, int background) {
        View view = toast.getView();
        if (view != null) {
            TextView message = view.findViewById(R.id.message);
            message.setBackgroundResource(background);
            message.setTextColor(messageColor);
        }

        return this;
    }*/

    /**
     * 短时间显示Toast
     */
    public CommonToastUtils Short(Context context, CharSequence message) {
        if (toast != null) {
            ((TextView) toast.getView().findViewById(R.id.toast_message)).setText(message);
        } else {
            toast = new Toast(context);
            if (toastView == null) {
                toastView = LayoutInflater.from(context).inflate(R.layout.common_toast, null);
            } else {
                ((ViewGroup) toastView.getParent()).removeView(toastView);
            }
            toast.setView(toastView);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        return this;
    }

    /**
     * 长时间显示toast
     */
    public CommonToastUtils Long(Context context, CharSequence message) {
        if (toast != null) {
            ((TextView) toast.getView().findViewById(R.id.toast_message)).setText(message);
        } else {
            toast = new Toast(context);
            if (toastView == null) {
                toastView = LayoutInflater.from(context).inflate(R.layout.common_toast, null);
            } else {
                ((ViewGroup) toastView.getParent()).removeView(toastView);
            }
            toast.setView(toastView);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        return this;
    }

    /**
     * 显示Toast
     */
    public CommonToastUtils show() {
        toast.show();
        return this;
    }

    /**
     * 获取Toast
     */
    public Toast getToast() {
        return toast;
    }
}
