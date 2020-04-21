/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain BasicData copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.hengxin.basic.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.hengxin.basic.AppContextProvider;
import com.hengxin.basic.BasicData;
import com.hengxin.basic.ContextProvider;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by janisharali on 27/01/17.
 */

public final class NetworkUtils {

    public enum NET_TYPE {
        TYPE_NONE, //0
        TYPE_WIFI, //1
        TYPE_MOBILE, //2
    }

    private NetworkUtils() {
        // This utility class is not publicly instantiable
    }

    public static boolean isNetworkConnected(Context context) {

        try {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            return false;
        }
    }


    public static boolean isNetworkAvailable() {
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) ContextProvider.get().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isAvailable();
        } catch (Exception e) {
            return false;
        }
    }

    public static NET_TYPE getNetworkType(Context context) {
        NET_TYPE net_type;
        if (context == null) {
            net_type = NET_TYPE.TYPE_NONE;
        } else {
            NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (networkInfo == null) {
                net_type = NET_TYPE.TYPE_NONE;
            } else {
                int type = networkInfo.getType();
                if (type == 0) {
                    net_type = NET_TYPE.TYPE_MOBILE;
                } else if (type == 1) {
                    net_type = NET_TYPE.TYPE_WIFI;
                } else {
                    net_type = NET_TYPE.TYPE_NONE;
                }
            }
        }

        return net_type;
    }


    //判断域名是否可用，注意要在子线程调用
    public static boolean checkUrlIsEnable(String url) {
        try {
            //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
            HttpURLConnection.setFollowRedirects(false);
            //到 URL 所引用的远程对象的连接
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            //从 HTTP 响应消息获取状态码
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }
}
