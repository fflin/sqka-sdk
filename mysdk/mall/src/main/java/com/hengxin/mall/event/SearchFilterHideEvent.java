package com.hengxin.mall.event;

import com.hengxin.mall.model.SearchFilterModel;

/**
 * author : fflin
 * date   : 2020/5/27 16:50
 * desc   : 搜索结果筛选layout隐藏，并传递数据
 * version: 1.0
 */
public class SearchFilterHideEvent {
    public SearchFilterModel model;
    public SearchFilterHideEvent(SearchFilterModel model) {
        this.model = model;
    }

}
