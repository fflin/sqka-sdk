package com.hengxin.mall.ui.search;

import com.hengxin.mall.base.IBaseView;
import com.hengxin.mall.base.IPresenter;
import com.hengxin.mall.model.ConditionListModel;
import com.hengxin.mall.model.TagModel;

/**
 * author : fflin
 * date   : 2020/5/6 16:03
 * desc   : 搜索结果，横向滚动热搜和竖向展示商品
 * version: 1.0
 */
public interface SearchResultContract {

    interface View extends IBaseView {

        void getHotWordsSucc(TagModel data);

        void getHotWordsFailed(String message);

        void searchResultSucc(ConditionListModel data);
    }

    interface Presenter extends IPresenter<View> {

        void getHotWords(String keyword);

        void startToSearch(String keyword, int pageNo, String init, String sort);

    }
}
