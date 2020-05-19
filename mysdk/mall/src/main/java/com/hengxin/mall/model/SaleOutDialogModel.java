package com.hengxin.mall.model;

/**
 * author : fflin
 * date   : 2020/5/18 15:31
 * desc   : 售后页面要求数据格式
 * version: 1.0
 */
public class SaleOutDialogModel {

    public String title;
    public String subTitle;
    public boolean isChecked;
    public SaleOutDialogModel(String title, String subTitle, boolean isChecked) {
        this.title = title;
        this.subTitle = subTitle;
        this.isChecked = isChecked;
    }

}
