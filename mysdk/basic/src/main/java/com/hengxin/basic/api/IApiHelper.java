package com.hengxin.basic.api;

import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.model.ConditionListModel;
import com.hengxin.basic.model.HomeModel;

import io.reactivex.Observable;

/**
 *
 */
public interface IApiHelper {

    /**
     * 今日精选
     */
    Observable<BaseResult<HomeModel>> getTodaySelection(String path, String version);

    Observable<BaseResult<ConditionListModel>> loadNewPage(String path, boolean init, String cid, String sort, int page_no);
}
