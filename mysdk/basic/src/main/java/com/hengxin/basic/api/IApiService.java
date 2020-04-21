package com.hengxin.basic.api;


import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.model.ConditionListModel;
import com.hengxin.basic.model.HomeModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by chunyang on 2017/11/2.
 */
public interface IApiService {
    /**
     * 今日精选
     * ("/api/home/v2")
     * /api/pdd/home
     */
    @GET("/api/open/{path}")
    Observable<BaseResult<HomeModel>> getTodaySelection(@Path(value = "path", encoded = true) String path, @QueryMap Map<String, String> map);

    /**
     * @param map /api/home/coupon_list
     *            /api/pdd/home/coupon_list
     * @return
     */
    @GET("/api/open/{path}/coupon_list")
    Observable<BaseResult<ConditionListModel>> loadNewPage(@Path(value = "path", encoded = true) String path, @QueryMap Map<String, String> map);

}
