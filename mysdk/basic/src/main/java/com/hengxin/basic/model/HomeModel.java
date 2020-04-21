package com.hengxin.basic.model;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/2/5.
 */
public class HomeModel {
    public String cid;
    public ConditionListModel coupon_list;
    public HomePageItem newCateItem;
    public List<HomePageItem> page_list;

    public static class HomePageItem extends HomePageType {
        public String pic;
        public int index;
        public String link;
        public List<HomePageItem> list;
        public List<CouponNewModel> coupon_list;
        public lineMode line;
        public transient boolean isPlacePid;
        public float interval = 2;
        public boolean has_point = true;
        public String tag_right;
        public String short_title;
        public String event_key;
        public List<BroadcastListBean> broadcast_list;

        public void setCurrentPage(HomePageItem overSeas) {
            if (overSeas == null) {
                return;
            }
            if (!TextUtils.isEmpty(overSeas.title)) {
                this.title = overSeas.title;
            }
            if (!TextUtils.isEmpty(overSeas.link)) {
                this.link = overSeas.link;
            }
            if (!TextUtils.isEmpty(overSeas.pic)) {
                this.pic = overSeas.pic;
            }
            if (!TextUtils.isEmpty(overSeas.cid)) {
                this.cid = overSeas.cid;
            }
            if (overSeas.view_type != 0) {
                this.view_type = overSeas.view_type;
            }
            if (overSeas.open_type != 0) {
                this.open_type = overSeas.open_type;
            }
            if (!TextUtils.isEmpty(overSeas.tag_right)) {
                this.tag_right = overSeas.tag_right;
            }

            if (!TextUtils.isEmpty(overSeas.short_title)) {
                this.short_title = overSeas.short_title;
            }

            if (!TextUtils.isEmpty(overSeas.event_key)) {
                this.event_key = overSeas.event_key;
            }
        }
    }

    public static class lineMode {
        public int height;
        public int left;
        public int right;
    }

    public static class BroadcastListBean {
        /**
         * mobile : 158****0350
         * time : 3 天前
         * action : 获得拼多多佣金
         * commi : 0.44元
         */
        public String mobile;
        public String time;
        public String action;
        public String commi;

        public String getShowAction() {
//            <font color='#ff0000'>158****0350</font> 3天前获得拼多多佣金<font color='#ff0000'>0.44元</font>
            return "<font color='#ff0000'>" + mobile + " </font>" + time.replace(" ", "") + action + "<font color='#ff0000'>" + commi + "</font>";
        }
    }
}
