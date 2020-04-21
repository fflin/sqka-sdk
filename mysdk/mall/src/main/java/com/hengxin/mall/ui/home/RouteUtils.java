package com.hengxin.mall.ui.home;

import android.content.Context;

import com.hengxin.mall.inter.OnCallBackDetail;
import com.hengxin.basic.model.HomeModel;


/**
 * Created by Administrator on 2018/1/26.
 */

public class RouteUtils {

    // showType跳拨号 加油 任务 游戏 金币商城
    public static void isNewNative(int showType, Context mContext, HomeModel.HomePageItem dataBean, OnCallBackDetail callBackDetail) {
        switch (showType) {
            /*case 1:
                String url = dataBean.link;
                if (dataBean.title != null && dataBean.title.equals("查劵指南") && KawApp.getLastVersion().isReview) {
                    url = KawApp.getLastVersion().getZqznUrl();
                }
                if (dataBean.mall_type == ConstantUtil.TAOBAOTYPE) {
                    if (url.endsWith("member_recharge_v3")) {
                        if (mContext instanceof FragmentActivity) {
                            if (AppUtils.checkLoginState((FragmentActivity) mContext)) {
                                H5Activity.startActivity(mContext, dataBean.title, url);
                            }
                        }
                    } else *//*BrowserActivity.startActiviy(mContext, url, dataBean.title);*//*
                        H5Activity.startActivity(mContext, dataBean.title, url);
                } else {
                    H5Activity.startActivity(mContext, dataBean.title, url);
                    *//*BrowserCustomActivity.startBrowserCustomActivity(mContext, dataBean.title, url);*//*
                }
                break;
            case 205:
                ChanelActivity.startSubclassInficationActivity(mContext, dataBean.title, dataBean.cid, dataBean.mall_type);
                break;
            case 3:
                dataBean.isPlacePid = false;
                callBackDetail.onLinkFestivalTaobao(dataBean);
//                OpenCouponDetailUtils.alibcOpenCouponUrl(dataBean.title, "", null, dataBean.link, callBackDetail, mContext);
                break;
            case 4://没有点击事件类型
                break;
            case 212:
                //大分类页
                ClassificationActivity.startActivity(mContext, dataBean);
                break;
            case 203:
                //搜索
//                SearchActivity.startSerarchActivity(mContext,dataBean.);
                break;
            case 214:
//                InvateActivity.startActivity(mContext);
                String shareUrl = "https://axssxm.mlinks.cc/AB1Q";
                if (KawApp.getLastVersion() != null && !TextUtils.isEmpty(KawApp.getLastVersion().downLoadUrl))
                    shareUrl = KawApp.getLastVersion().downLoadUrl;
//                ShareUtils.shareUrl(mContext, shareUrl);
//                callBackDetail.onCheckUpdateApp();
                break;//邀请界面
            case 215:
                CollageActivity.startActivity(mContext);
                break;//跳转拼多多界面
            case 216:
                JDMainActivity.startActivity(mContext);
                break;
            case 101:
                H5Activity.startActivity(mContext, dataBean.title, dataBean.link);
                break;//(本地需要签名的Vuex的H5页面)比如充值中心
            case 301:
                dataBean.isPlacePid = true;
                callBackDetail.onLinkFestivalTaobao(dataBean);
                break;// 需要替换pid
            case 213:
            case 21301://cate_pdd
                if (dataBean.mall_type == ConstantUtil.TAOBAOTYPE)
                    GoodsSortActivity.startActivity(mContext, dataBean);
                else
                    LeftCatePddActivity.startActivity(mContext, dataBean, LeftCatePddActivity.CateType, dataBean.mall_type);
                break;
            case 204:
            case 20401: //leaf_cate_pdd
                if (dataBean.mall_type == ConstantUtil.TAOBAOTYPE)
                    SecondNewMenuActivity.startActivity(mContext, dataBean);
                else
                    LeftCatePddActivity.startActivity(mContext, dataBean, LeftCatePddActivity.LeftCate, dataBean.mall_type);
                break;
            case 501://更多列表数据
                MoreListActivity.startActivity(mContext,dataBean);
                break;
            case 601:
                // 拨号
                DialActivity.startActivity(mContext);
                break;
            case 602:
                // 加油
                if (UserCache.get().isLoginTokenValid()) {
                    X5H5Activity.startX5H5Activity(mContext,dataBean.title);
                } else {
                    AppUtils.toLoginPage(mContext);
                }
                break;
            case 603:
                //任务
                RxBus.getInstance().post(new PageChangeEvent("8",""));
                break;
            case 604:
                //游戏
                break;
            case 605:
                //金币商城
                if (UserCache.get().isLoginTokenValid() && UserCache.get().getUser() != null) {
                    Log.i("fflin","coins- router "+UserCache.get().getUser().earn_user_info.coins+"; "+UserCache.get().getUser().earn_user_info.exchange_rate);
                    DrawMoneyActivity.startDrawMoneyActivity(mContext,UserCache.get().getUser().earn_user_info.coins,UserCache.get().getUser().earn_user_info.exchange_rate);
                } else {
                    AppUtils.toLoginPage(mContext);
                }
                break;
            case 606:
                //充电
                if (UserCache.get().isLoginTokenValid()) {
                    MineHelper.getHelper().startToCharge(mContext);
                } else {
                    AppUtils.toLoginPage(mContext);
                }

                break;*/
            default:
                callBackDetail.onCheckUpdateApp();
                break;
        }
    }

//    public static void deatilWithNotificationClick(Activity mContext, NotificationModel notificationModel) {
//        switch (notificationModel.action) {
//            case 1:
//                SystemOpenUtils.lanchBrowWebActivity(mContext, notificationModel);
//                break;
//            case 3:
//                SystemOpenUtils.lanchTaoBaoActivity(mContext, notificationModel);
//                break;
//            case 205:
//                SystemOpenUtils.lanchSumclassActivity(mContext, notificationModel);
//                break;
//            case 201:
//                //详情页
//                SystemOpenUtils.lanchNewDetailActivity(mContext, notificationModel);
//                break;
//            case 203:
//                //搜索
//                SystemOpenUtils.lanchSouSulassActivity(mContext, notificationModel);
//                break;
//            case 204:
//                //分类页
//                NotificationModel.ActionArgsBean action_data = notificationModel.action_data;
//                SecondNewMenuActivity.startActivity(mContext, action_data);
//                break;
//            case 212:
//                //大分类页
//                NotificationModel.ActionArgsBean actiondata = notificationModel.action_data;
//                ClassificationActivity.startActivity(mContext, actiondata);
//                break;
//            case 213:
//                //大分类页
//                NotificationModel.ActionArgsBean dataBean = notificationModel.action_data;
//                GoodsSortActivity.startActivity(mContext, dataBean);
//                break;
//            case 4:
//                break;
//
//
//        }
//    }
}
