package com.hengxin.pickimg.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 线程切换工具类
 */
public class ThreadHelper {
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Executor sExecutor = Executors.newSingleThreadExecutor();

    public static void runOnSubThread(Runnable runnable) {
        sExecutor.execute(runnable);
    }

    public static void runOnUIThread(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static Handler getHandler() {
        return sHandler;
    }
}
