package com.hengxin.basic.util;

import android.content.Context;
import androidx.fragment.app.FragmentManager;

import com.hengxin.basic.ContextProvider;
import com.hengxin.basic.base.GotoDate;

/**
 * 
 * date: 2018/11/7
 */
public class MessageShowUtils {
    private static CommonToastUtils toastUtils;

    public static void showMessage(int type, String message, FragmentManager fragmentManager, Context context) {
        checkAndShowToast(context,message);
    }

    public static void showMessage(int type, String message, FragmentManager fragmentManager, Context context, GotoDate gotoDate) {
        try {
            if (context == null) context = ContextProvider.get().getContext();
            if (toastUtils == null) {
                toastUtils = new CommonToastUtils(context);
            }
            toastUtils.Short(context, message).show();
        } catch (Exception e) {

        }
    }


    private static void checkAndShowToast(Context context, String message){
        try {
            if (NetworkUtils.isNetworkAvailable()) {
                if (toastUtils == null) {
                    toastUtils = new CommonToastUtils(context);
                }
                toastUtils.Short(context, message).show();
            }
        } catch (Exception e) {

        }

    }

}
