package com.hengxin.mall.model;

import java.util.List;

/**
 * author:author
 * date: 2018/11/2
 */
public class AuthHistoryModel {


    public long timestamp;
    public List<CouponsBean> coupons;

    public static class CouponsBean {
        /**
         * _id : 5bdaea4da4422f2d2c3b425c
         * exchange_rate : 1
         * fee_amount : 2000
         * item_url : https://detail.tmall.com/item.htm?id=541539077096
         * final_price : 39
         * old_price : 59
         * coupon_amount : 20
         * pict_url : https://img.alicdn.com/bao/uploaded/TB2lMUxj9FjpuFjSspbXXXagVXa_!!2165010829.jpg
         * title : 冬季男士保暖背心加绒加厚纯棉修身打底背心男紧身马甲坎肩无袖潮
         * item_id : 541539077096
         * user_id : 18910783453
         * __v : 0
         * create_time : 1541073485962
         */
        public int coupon_mall_type;
        public String fee_amount;
        public String item_url;
        public String final_price;
        public String old_price;
        public String coupon_amount;
        public String pict_url;
        public String title;
        public String item_id;
        public String user_id;
        public boolean is_tmall;
        public String coupon_link;
        public String commission_type;
        public String coupon_id;
        public String coupon_click_url;
    }
}
