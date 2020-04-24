package com.hengxin.mall.model;

import java.io.Serializable;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/24 12:02
 * desc   :
 * version: 1.0
 */
public class DetailModel implements Serializable {

    public String _source;// 商品来源 1 自营, 2 企叮咚, 3 有赞
    public String unique_id;// 优惠券唯一ID{_source+goods_id}
    public String goods_id;// 商品id 取值：自增
    public String goods_name;//商品名称
    public String goods_desc;//商品简介
    public String goods_tag;//商品标签
    public List<String> banner_list;//banner图
    public List<String> pic_list;//详情图
    public double old_price;//原价
    public double price;//现价
    public String shop_id;//店铺id


    // 不是接口数据，组装adapter使用
    public String picInfo;
}
