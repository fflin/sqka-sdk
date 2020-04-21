package com.hengxin.basic.util;

import android.text.TextUtils;

import com.hengxin.basic.BuildConfig;


public class Log {
    public static String customTagPrefix = "";

    public static void i(String tag, String msg) {
        StackTraceElement caller = getCallerStackTraceElement();
        String methodTag = generateTag(caller);
        if (BuildConfig.DEBUG) android.util.Log.i(tag, msg+"--调用位置：["+methodTag+"]");
    }

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, new Object[]{callerClazzName, caller.getMethodName(), Integer.valueOf(caller.getLineNumber())});
        tag = TextUtils.isEmpty(customTagPrefix)?tag:customTagPrefix + ":" + tag;
        return tag;
    }

    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
}
