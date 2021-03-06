package com.hengxin.mall.net;


import com.hengxin.basic.base.BaseResult;
import com.hengxin.mall.model.AuthHistoryModel;
import com.hengxin.mall.model.TagModel;
import com.hengxin.mall.model.UpLoadFileModel;
import com.hengxin.pay.model.AliSignModel;
import com.hengxin.mall.model.ConditionListModel;
import com.hengxin.mall.model.HomeModel;
import com.hengxin.mall.model.PayChannelModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * author : fflin
 * date   : 2020/4/22 11:09
 * desc   :
 * version: 1.0
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

    // 商品详情 -测试接口
    @GET("{path}/api/mine/index")
    Observable<BaseResult<HomeModel>> getGoodsDetail(@Path(value = "path", encoded = true) String path, @QueryMap Map<String, String> map);

    //获取支付渠道列表
    @GET("{path}/api/order/pay/channel/list")
    Observable<BaseResult<PayChannelModel>> getPayChannel(@Path(value = "path", encoded = true) String path, @QueryMap Map<String, String> map);

    @GET("{path}/api/order/pay/sign")
    Observable<BaseResult<AliSignModel>> getAliSign(@Path(value = "path", encoded = true) String servicesHost, @QueryMap Map<String, String> map);

    @GET("/api/search/hotwords")
    Observable<BaseResult<TagModel>> requestHotSearch(@QueryMap Map<String, String> map);

    /**
     * 搜索
     */
    @GET("/api/search/coupon")
    Observable<BaseResult<ConditionListModel>> search(@QueryMap Map<String, String> map);

    /**
     * 上传图片
     */
    @Multipart
    @POST("{path}/upload/file")
    Observable<BaseResult<UpLoadFileModel>> uploadFile(@Path(value = "path", encoded = true) String servicesHost, @Part MultipartBody.Part file);

    /**
     * 获取优惠券领取记录，无接口先使用这个接口获取购物车列表
     */
    @GET("{path}/api/auth/coupon/exchange_record")
    Observable<BaseResult<AuthHistoryModel>> authHistory(@Path(value = "path", encoded = true) String servicesHost, @QueryMap Map<String, String> signSecurity);
}
