package com.hengxin.mall.model;

import android.text.TextUtils;

import com.google.gson.Gson;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/1/26.
 */
//@Table(name = "CouponNewModel")
public class CouponNewModel extends HomePageType implements Serializable {
    //@Column(name = "id", isId = true)
    public String id;

    //@Column(name = "user_id")
    public String user_id;

    //@Column(name = "item_id")
    public String item_id;

    //@Column(name = "coupon_id")
    public String coupon_id;

    //@Column(name = "pid_no_coupon")
    public String pid_no_coupon;

    //@Column(name = "pid")
    public String pid;

    //@Column(name = "item_url")
    public String item_url;

    //@Column(name = "pict_url")
    public String pict_url;

    //@Column(name = "coupon_click_url")
    public String coupon_click_url;

    //@Column(name = "video")
    public String video;

    //@Column(name = "volume")
    public String volume;

    //@Column(name = "provcity")
    public String provcity;

    //@Column(name = "seller_id")
    public String seller_id;

    //@Column(name = "shop_title")
    public String shop_title;

    //@Column(name = "expiry_date")
    public String expiry_date;

    //@Column(name = "is_tmall")
    public boolean is_tmall;

    //@Column(name = "old_price")
    public String old_price;

    //@Column(name = "final_price")
    public String final_price;

    //@Column(name = "estimate_commi")
    public String estimate_commi;

    //@Column(name = "has_coupon")
    public boolean has_coupon;

    //@Column(name = "coupon_amount")
    public String coupon_amount;

    //@Column(name = "zk_amount")
    public String zk_amount;

    //@Column(name = "item_description")
    public String item_description;//推荐语

    public String commission_rate;

    //@Column(name = "commission_type")
    public String commission_type;

    //@Column(name = "coupon_start_time")
    public String coupon_start_time;

    //@Column(name = "coupon_end_time")
    public String coupon_end_time;

    //@Column(name = "coupon_mall_type")
    public int coupon_mall_type;

    //@Column(name = "collection_time")
    private long collection_time;

    //@Column(name = "opt_id")
    public String opt_id;
    //@Column(name = "share_price")
    public String share_price;

    //@Column(name = "coupon_link")
    public String coupon_link;
    //@Column(name = "images")
    private String images;

    //@Column(name = "recommend_tag")
    public String recommend_tag;

    public int rank_number;
    private SmallImagesBean small_images;

    public long getCollection_time() {
        return collection_time == 0 ? System.currentTimeMillis() : collection_time;
    }

    public void setCollection_time(long collection_time) {
        this.collection_time = collection_time;
    }

    public String getImages() {
        return new Gson().toJson(small_images);
    }

    public void setImages(String images) {
        this.images = images;
        small_images = new Gson().fromJson(images, SmallImagesBean.class);
    }

    public SmallImagesBean getSmall_images() {
        return small_images;
    }

    public void setSmall_images(SmallImagesBean small_images) {
        this.small_images = small_images;
    }

    public static class SmallImagesBean implements Serializable {
        private List<String> string;
        private String hideSave;
        private String showDelete;//是否显示删除 1 true
        private boolean isCover;

        public boolean isCover() {
            return isCover;
        }

        public void setCover(boolean cover) {
            isCover = cover;
        }

        public String getShowDelete() {
            return showDelete;
        }

        public void setShowDelete(String showDelete) {
            this.showDelete = showDelete;
        }

        public List<String> getString() {
            return string;
        }

        public void setString(List<String> string) {
            this.string = string;
        }

        public String getHideSave() {
            return hideSave;
        }

        public void setHideSave(String hideSave) {
            this.hideSave = hideSave;
        }
    }

    //转换销量的方法
    public static String transformVolume(String volume) {
        if (!TextUtils.isEmpty(volume)) {
            if (Pattern.matches("^[0-9]*[1-9][0-9]*$", volume)) {
                //匹配上数字，就开始做操作
                int result = Integer.valueOf(volume);
                if (result > 10000) {
                    return new DecimalFormat("#.#").format(result / 10000f) + "万";
                } else {
                    return String.valueOf(result);
                }
            } else {
                return volume;
            }
        } else {
            return "";
        }
    }
}
