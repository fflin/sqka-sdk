package com.hengxin.basic.util;

import android.content.Context;


/**
 * 全局自定义的toast
 * Created by yiqun on 2016/2/1.
 */
public class ToastUtils {
    private static CommonToastUtils toastUtils;
    public static void show(Context context, String message) {
        if (toastUtils == null && context != null) {
            toastUtils = new CommonToastUtils(context);
        }
        if (context != null) {
            toastUtils.Short(context, message).show();
        }
    }

    /*public static Toast show(Context arg6, String arg7, int arg8) {
        Toast v0_3;
        Toast v2 = null;
        try {
            LayoutInflater v0_1 = LayoutInflater.from(arg6);
            Toast v1 = Toast.makeText(arg6, arg7, arg8);
            v1.setGravity(17, 0, 0);
            View v3 = v0_1.inflate(R.layout.activity_login_toast, null);
            View v0_2 = v3.findViewById(R.id.toastTis);
            if(arg7 != null && !arg7.equals("")) {
                ((TextView)v0_2).setText(arg7);
            }

            v1.setView(v3);
            v1.show();
            v0_3 = v1;
        }
        catch(Exception v0) {
            v0_3 = v2;
        }

        return v0_3;
    }*/
}
