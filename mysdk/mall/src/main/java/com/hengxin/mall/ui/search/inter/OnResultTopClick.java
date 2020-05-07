package com.hengxin.mall.ui.search.inter;

import android.view.View;

/**
 * author : fflin
 * date   : 2020/5/7 18:02
 * desc   : 搜索结果顶部销量等的点击事件,把点击时的对象传回去
 * version: 1.0
 */
public interface OnResultTopClick {

    //销量 综合 价格 ---接口返回的数据
    void onSortClick(View view);

    // 展示方式切换
    void onShowTypeClick(View view);

    //筛选
    void onFilterClick(View view);
}
