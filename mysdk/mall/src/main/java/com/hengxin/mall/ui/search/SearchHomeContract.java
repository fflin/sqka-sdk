package com.hengxin.mall.ui.search;

import com.hengxin.mall.base.IBaseView;
import com.hengxin.mall.base.IPresenter;
import com.hengxin.mall.model.TagModel;

/**
 * author : fflin
 * date   : 2020/5/6 16:03
 * desc   : 搜索主页接口，展示热搜词
 * version: 1.0
 */
public interface SearchHomeContract {

    interface View extends IBaseView {
        void getHotWordsSucc(TagModel data);
    }

    interface Presenter extends IPresenter<View> {
        void getHotWords();
    }
}
