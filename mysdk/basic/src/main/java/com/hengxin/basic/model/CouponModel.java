package com.hengxin.basic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CouponModel implements Serializable {


    private String pidNoCoupon;

    private String buyurl;
    private String code;
    @SerializedName(value = "couponAmount", alternate = "couponcount")
    private float couponcount;
    private long id;
    private String iosurl;

    private String itemUrl;
    private String pid;
    private String longurl;
    @SerializedName(value = "zkPrice", alternate = "oldprice")
    private float oldprice;
    private String pic;

    private float price;
    private String title;
    private String url;
    private boolean detail_open = true;
    private String expiry_date;

    @SerializedName(value = "couponEffectiveStartTime", alternate = "couponStartTime")
    private String couponStartTime;
    @SerializedName(value = "couponEffectiveEndTime", alternate = "couponEndTime")
    private String couponEndTime;
    @SerializedName(value = "auctionId", alternate = "item_id")
    private String item_id;
    private boolean isTmall;
    @SerializedName(value = "biz30day", alternate = "salesVolume")
    private int salesVolume;
    private String coupon_end_time;


    private String tag;

    public String getPidNoCoupon() {
        return pidNoCoupon;
    }

    public void setPidNoCoupon(String pidNoCoupon) {
        this.pidNoCoupon = pidNoCoupon;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    //接收参数//
    private int userType;
    private String pictUrl;
    //log 日志参数
    private String log_resource;
    private String log_using_sort;

    public String getLog_using_sort() {
        return log_using_sort;
    }

    public void setLog_using_sort(String log_using_sort) {
        this.log_using_sort = log_using_sort;
    }

    public String getCoupon_end_time() {
        return coupon_end_time;
    }

    public void setCoupon_end_time(String coupon_end_time) {
        this.coupon_end_time = coupon_end_time;
    }


    public String getLog_resource() {
        return log_resource;
    }

    public void setLog_resource(String log_resource) {
        this.log_resource = log_resource;
    }

    public String getPictUrl() {
        return pictUrl;
    }

    public void setPictUrl(String pictUrl) {
        this.pictUrl = pictUrl;
    }


    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getCouponStartTime() {
        return couponStartTime;
    }

    public void setCouponStartTime(String couponStartTime) {
        this.couponStartTime = couponStartTime;
    }

    public String getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        this.couponEndTime = couponEndTime;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public boolean isDetail_open() {
        return detail_open;
    }

    public void setDetail_open(boolean detail_open) {
        this.detail_open = detail_open;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public CouponModel() {
        super();
    }

    public String getBuyurl() {
        return this.buyurl;
    }

    public String getCode() {
        return this.code;
    }

    public float getCouponcount() {
        return this.couponcount;
    }

    public long getId() {
        return this.id;
    }

    public String getIosurl() {
        return this.iosurl;
    }

    public String getLongurl() {
        return this.longurl;
    }

    public float getOldprice() {
        return this.oldprice;
    }

    public String getPic() {
        return this.pic;
    }

    public float getPrice() {
        return this.price;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setBuyurl(String arg1) {
        this.buyurl = arg1;
    }

    public void setCode(String arg1) {
        this.code = arg1;
    }

    public void setCouponcount(float arg1) {
        this.couponcount = arg1;
    }

    public void setId(long arg2) {
        this.id = arg2;
    }

    public void setIosurl(String arg1) {
        this.iosurl = arg1;
    }

    public void setLongurl(String arg1) {
        this.longurl = arg1;
    }

    public void setOldprice(float arg1) {
        this.oldprice = arg1;
    }

    public void setPic(String arg1) {
        this.pic = arg1;
    }

    public void setPrice(float arg1) {
        this.price = arg1;
    }

    public void setTitle(String arg1) {
        this.title = arg1;
    }

    public void setUrl(String arg1) {
        this.url = arg1;
    }

    public boolean isTmall() {
        return isTmall;
    }

    public void setTmall(boolean tmall) {
        isTmall = tmall;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }
}

