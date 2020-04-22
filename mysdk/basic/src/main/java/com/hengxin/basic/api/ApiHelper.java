package com.hengxin.basic.api;

import android.os.Build;
import android.text.TextUtils;

import com.hengxin.basic.BuildConfig;
import com.hengxin.basic.base.BaseRequest;
import com.hengxin.basic.util.FileHelper;
import com.hengxin.basic.util.Log;
import com.hengxin.basic.util.MD5Utiils;
import com.hengxin.basic.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by chunyang on 2017/11/2.
 */
public class ApiHelper {

    private final static String TAG = "ApiHelper";
    private static String HOST = "http://test.sqcr.yunyouduobao.com";
    private static String isChange;
    private OkHttpClient mOkhttpClient;

    public ApiHelper() {
        buildClient();
    }

    public <T> T buildMainService(Class<T> tClass) {
        Retrofit retrofit = new Retrofit.Builder().client(mOkhttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(HOST).build();
        RxJavaPlugins.setErrorHandler(throwable -> {
        });
        return retrofit.create(tClass);
    }

    private void buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(40, TimeUnit.SECONDS);
        builder.writeTimeout(40, TimeUnit.SECONDS);
        builder.readTimeout(40, TimeUnit.SECONDS);
        builder.proxy(Proxy.NO_PROXY);
        File apiCache = new File(FileHelper.get().getCache(), "api");
        Log.i("ApiCache:", apiCache + "" + HOST);

        builder.addNetworkInterceptor(new CacheInterceptor());
        builder.cache(new Cache(apiCache, 1024 * 1024 * 10));
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("HttpLoggingInterceptor", message);
                }
            });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        Interceptor mInterUrlceptor = chain -> {
            if (!TextUtils.isEmpty(isChange)) {
                return chain.proceed(processRequest(chain.request()));
            } else {
                return chain.proceed(chain.request());
            }
        };
        builder.addInterceptor(mInterUrlceptor);
//        builder.addInterceptor(new TokenInterceptor());
        mOkhttpClient = builder.build();
    }

    public static void setHOST(String HOST) {
        isChange = HOST;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            isChange = HOST.replaceFirst("https", "http");
        }
    }

    /*public static void initialize() {
        HOST = BuildConfig.SERVER_LIB_URL;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            HOST = HOST.replaceFirst("https", "http");
        }
    }*/

    public Map<String, String> getSignSecurity(Map<String, String> stringStringMap) {
        String sign = MD5Utiils.getHashMapSign(stringStringMap);
        Log.i("addBaseProperty", sign);
        stringStringMap.put("sign", sign);
        return stringStringMap;
    }

    public Map<String, String> addBaseMap(Map<String, String> security) {
        security.put("t", System.currentTimeMillis() + "");
        security.put("app_name", "rrh");
        security.put("app_version", "2.6.65");
        security.put("channel", "apptest");
        security.put("device_id", "XdeK3NbYQmQDAMuZfbPB4bv%2B");
        security.put("os", "android");
        security.put("os_version", "HUAWEI_YAL-AL00_10%2829%29");
        security.put("user_id", "");
        security.put("app_key", "hr_rrh");
        security.put("app_id", "1");
        return security;
    }


    private Request processRequest(Request request) {
        String url = request.url().url().toString();
        Request.Builder newBuilder = request.newBuilder();
        String replace = url.replace(HOST, isChange);
        Log.i("processRequest", HOST + "processRequest" + url + ".." + replace);
        return newBuilder.url(replace).build();
    }


    /**
     * 加密
     *
     * @param baseRequest 参数
     */
    private Map<String, String> getSecurity(BaseRequest baseRequest) {
        HashMap<String, String> params = new HashMap<>();
        String sign = MD5Utiils.getSign(baseRequest, params);
        Log.i("addBaseProperty", sign);
        params.put("sign", sign);
        return params;
    }

    public class CacheInterceptor implements Interceptor {

        private final String pragma = "pragma";
        private final String cacheControl = "Cache-Control";
        private volatile int maxAge = 60 * 60;
        private volatile int maxStale = 60 * 60 * 24 * 28;
        private String maxStaleString = "public, only-if-cached, max-stale=" + maxStale;
        private String maxAgeString = "public, only-if-cached, max-stale=" + maxAge;

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isNetworkAvailable()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                return chain.proceed(request);
            }
            Response response = chain.proceed(request);
            if (NetworkUtils.isNetworkAvailable()) {
                // read from cache for 1 minute
                response.newBuilder()
                        .removeHeader(pragma)
                        .header(cacheControl, maxAgeString)
                        .build();
            } else {
                // tolerate 4-weeks stale
                response.newBuilder()
                        .removeHeader(pragma)
                        .header(cacheControl, maxStaleString)
                        .build();
            }
            return response;
        }
    }


}
