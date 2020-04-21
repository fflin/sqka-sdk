package com.hengxin.basic.api;

import android.os.Build;
import android.text.TextUtils;

import com.hengxin.basic.BuildConfig;
import com.hengxin.basic.base.BaseRequest;
import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.model.ConditionListModel;
import com.hengxin.basic.model.HomeModel;
import com.hengxin.basic.util.CryptoUtils;
import com.hengxin.basic.util.FileHelper;
import com.hengxin.basic.util.Log;
import com.hengxin.basic.util.MD5Utiils;
import com.hengxin.basic.util.NetworkUtils;
import com.hengxin.basic.util.RxUtils;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
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
public class ApiHelper implements IApiHelper {

    private final static String TAG = "ApiHelper";
    public static String COUPONTOKE;
    public static String SERVICES_HOST = BuildConfig.BASE_SERVER_URL;//用户
    private static String HOST = BuildConfig.SERVER_LIB_URL;// 商城
    //    private static String HOST = "http://192.168.31.94:7001";
    private static String isChange;
    public IApiService mService;
    private IOtherApiService mOtherApiService;
    private OkHttpClient mOkhttpClient;

    public ApiHelper() {
        buildClient();
        buildMainService();
        buildOtherService();
    }

    private void buildOtherService() {
        Retrofit retrofit = new Retrofit.Builder().client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(IOtherApiService.HOST)
                .build();
        mOtherApiService = retrofit.create(IOtherApiService.class);
    }

    private void buildMainService() {
        Retrofit retrofit = new Retrofit.Builder().client(mOkhttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(HOST).build();
        RxJavaPlugins.setErrorHandler(throwable -> {
        });
        mService = retrofit.create(IApiService.class);
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
        builder.addInterceptor(new TokenInterceptor());
        mOkhttpClient = builder.build();
    }

    public static IApiHelper get() {
        return Holder.IN;
    }

    public static void setHOST(String HOST) {
        isChange = HOST;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            isChange = HOST.replaceFirst("https", "http");
        }
    }

    public static void initialize() {
        HOST = BuildConfig.SERVER_LIB_URL;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            HOST = HOST.replaceFirst("https", "http");
        }
    }

    private static Map<String, String> getSignSecurity(Map<String, String> stringStringMap) {
        String sign = MD5Utiils.getHashMapSign(stringStringMap);
        Log.i("addBaseProperty", sign);
        stringStringMap.put("sign", sign);
        return stringStringMap;
    }

    private static Map<String, String> addBaseMap(Map<String, String> security) {
        /*security.put("t", AppCache.get().getServiceDelyTime() + "");
        security.put("app_name", AppCache.get().getApp_name());
        security.put("app_version", AppCache.get().getApp_version());
        security.put("channel", AppCache.get().getChannel());
        security.put("device_id", AppCache.get().getDevice_id());
        security.put("os", AppCache.get().getOs());
        security.put("os_version", AppCache.get().getOs_version());
        security.put("user_id", UserCache.get().getUser_id());
        security.put("app_key", "hr_rrh");
        security.put("app_id", checkAppByPackageName());*/
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
    /**
     * 增加基础属性
     *
     * @param baseRequest
     * @return
     */
    private BaseRequest addBaseProperty(BaseRequest baseRequest) {
        /*baseRequest.setT(AppCache.get().getServiceDelyTime() + "");
        baseRequest.setApp_name(AppCache.get().getApp_name());
        baseRequest.setApp_version(AppCache.get().getApp_version());
        baseRequest.setChannel(AppCache.get().getChannel());
        baseRequest.setDevice_id(AppCache.get().getDevice_id());
        baseRequest.setOs(AppCache.get().getOs());
        baseRequest.setOs_version(AppCache.get().getOs_version());
        baseRequest.setUser_id(UserCache.get().getUser_id() + "");
        baseRequest.setLogin_token(UserCache.get().getLogin_token());*/
        return baseRequest;
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

    @Override
    public Observable<BaseResult<HomeModel>> getTodaySelection(String path, String version) {
        HashMap<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(version)) map.put("version", version);
        return mService.getTodaySelection(path, getSignSecurity(addBaseMap(map))).compose(RxUtils.transformResult());
    }

    @Override
    public Observable<BaseResult<ConditionListModel>> loadNewPage(String path, boolean init, String cid, String sort, int page_no) {
        HashMap<String, String> map = new HashMap<>();
        map.put("init", init ? "1" : "0");
        map.put("cid", cid);
        map.put("sort", sort);
        map.put("page_no", String.valueOf(page_no));
        return mService.loadNewPage(path, getSignSecurity(addBaseMap(map))).compose(RxUtils.transformResult());
    }

    private static class Holder {
        private static ApiHelper IN = new ApiHelper();
    }

    /**
     * 其他类调用的的签名方法
     */
    public static class OutMethodUtils {
        public static Map<String, String> getCommonSignSecurity(HashMap<String, String> securit) {
            return ApiHelper.getSignSecurity(ApiHelper.addBaseMap(securit));
        }

        public static void getSignSecurity(Map sign) {
            ApiHelper.addBaseMap(sign);
            ApiHelper.getSignSecurity(sign);
        }

        public static String getSignString(Map sign) {
            return MD5Utiils.getHashMapSign(sign);
        }

        //ASE 加密
        public static String getEncryptPasswordString(String password) {
            return CryptoUtils.encryptPassword(password);
        }
    }


    public class LocalInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl cacheControl = new CacheControl.Builder().maxAge(Integer.MAX_VALUE, TimeUnit.SECONDS)
                    .maxStale(0, TimeUnit.SECONDS).build();
            Request request = chain.request();
            Request.Builder builder = request.newBuilder().cacheControl(cacheControl);
            request = builder.build();


            if (NetworkUtils.isNetworkAvailable()) {
                try {
                    Response response = chain.proceed(request);
                    if (response.isSuccessful()) {
                        return response;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //if request failed. always load in cache
            cacheControl = new CacheControl.Builder().maxAge(Integer.MAX_VALUE, TimeUnit.SECONDS).maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();
            request = builder.cacheControl(cacheControl).build();
            return chain.proceed(request);
        }
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

    public class TokenInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            /*Request request = chain.request();
            String requestUrl = request.url().toString();
            String baseUrl = ApiHelper.isChange;
            if (TextUtils.isEmpty(baseUrl)) baseUrl = HOST;
            boolean isSqcrRequest = requestUrl.startsWith(baseUrl);
            boolean isRenRenQuest = requestUrl.startsWith(SERVICES_HOST);
            if (!isSqcrRequest && !isRenRenQuest) return chain.proceed(request);
            String login_token = isSqcrRequest ? COUPONTOKE : UserCache.get().getLogin_token();
            if (!TextUtils.isEmpty(login_token))
                request = request.newBuilder().header("token", login_token)
                        .build();
            String uuid = UserCache.get().getUuid();
            Log.i("HttpLoggingInterceptor", "did==="+uuid + "token=" + login_token);
            if (!TextUtils.isEmpty(uuid))
                request = request.newBuilder().header("did", uuid)
                        .build();
            return chain.proceed(request);*/
            return  null;
        }
    }


}
