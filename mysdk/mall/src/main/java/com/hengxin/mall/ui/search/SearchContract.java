package com.hengxin.mall.ui.search;

import com.hengxin.mall.base.IBaseView;
import com.hengxin.mall.base.IPresenter;
import com.hengxin.mall.model.TagModel;

/**
 * author : fflin
 * date   : 2020/5/6 16:03
 * desc   :
 * version: 1.0
 */
public interface SearchContract {

    interface View extends IBaseView {
        void getHotWordsSucc(TagModel data);
        void getSearchResultSucc();

    }

    interface Presenter extends IPresenter<View> {
        void getHotWords();

        void startToSearch(String words);
    }
}
