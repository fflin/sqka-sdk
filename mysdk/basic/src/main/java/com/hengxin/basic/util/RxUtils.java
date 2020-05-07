package com.hengxin.basic.util;


import com.google.gson.Gson;
import com.hengxin.basic.api.ApiException;
import com.hengxin.basic.base.BaseResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by chunyang on 2017/10/30.
 */

public final class RxUtils {

    private RxUtils() {
    }


    public static <T> ObservableTransformer<T, T> io2main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    public static <T> ObservableTransformer<T, T> io() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io());
            }
        };
    }



    /**
     * 没有加密的里面的数据T
     *
     * @param type
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<ResponseBody, T> decrypttransform(Type type) {
        return new ObservableTransformer<ResponseBody, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseBody> upstream) {
                return upstream.map(new DecryptFactoryFunc<T>(type)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
            }
        };
    }


    /**
     * 返回整个结构
     *
     * @param type
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<ResponseBody, BaseResult<T>> decrypttransformResult(Type type) {
        return new ObservableTransformer<ResponseBody, BaseResult<T>>() {
            @Override
            public ObservableSource<BaseResult<T>> apply(Observable<ResponseBody> upstream) {
                return upstream.map(new DecryptResultFactoryFunc<T>(type)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
            }
        };
    }

    public static <T> ObservableTransformer<BaseResult<T>, T> transform() {
        return new ObservableTransformer<BaseResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResult<T>> upstream) {
                return upstream.map(new GsonFactoryFunc<T>()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
            }
        };
    }

    public static <T extends BaseResult> ObservableTransformer<T, T> transformResult() {
        ObservableTransformer<T, T> observableTransformer = new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                try {
                    return upstream.map(new GsonFactoryFuncResult<T>()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        return observableTransformer;
    }


    public static class DecryptFactoryFunc<T> implements Function<ResponseBody, T> {
        Type type;

        private DecryptFactoryFunc(Type type) {
            this.type = type;
        }

        @Override
        public T apply(ResponseBody responseBody) throws Exception {
            String string = responseBody.string();
            Log.i("log_response", "response: " + string);
            String decrypt = CryptoUtils.decrypt(string);
            BaseResult<T> result = new Gson().fromJson(decrypt, type);
            if (result.error != 0) {
                throw new ApiException(result);
            }
            return result.data;
        }
    }

    public static class DecryptResultFactoryFunc<T> implements Function<ResponseBody, BaseResult<T>> {
        Type type;

        private DecryptResultFactoryFunc(Type type) {
            this.type = type;
        }

        @Override
        public BaseResult<T> apply(ResponseBody responseBody) throws Exception {
            String string = responseBody.string();
            String decrypt = CryptoUtils.decrypt(string);
            Log.i("log_response", "response: " + decrypt);
            BaseResult<T> result = new Gson().fromJson(decrypt, type);
            if (result.error != 0) {
                throw new ApiException(result);
            }
            return result;
        }
    }

    public static class GsonFactoryFunc<T> implements Function<BaseResult<T>, T> {

        @Override
        public T apply(BaseResult<T> tBaseResult) {
            int state = tBaseResult.error;
            Log.i("log_GsonFactoryFunc", "response: " + tBaseResult.toString());
            if (state != 0)
                throw new ApiException(tBaseResult);
            return tBaseResult.data;
        }
    }

    public static class GsonFactoryFuncResult<T extends BaseResult> implements Function<T, T> {

        @Override
        public T apply(T tBaseResult) {
            int state = tBaseResult.error;
            Log.i("log_response", "response: " + tBaseResult.toString() +"; error = "+state);
            if (state != 0)
                throw new ApiException(tBaseResult);
            return tBaseResult;
        }
    }

}
