package com.hengxin.mall.ui.home;


import com.hengxin.basic.base.BaseResult;
import com.hengxin.mall.base.IBaseView;
import com.hengxin.mall.base.IPresenter;
import com.hengxin.mall.model.ConditionListModel;
import com.hengxin.mall.model.HomeModel;

public interface TodaySelectionContract {
    interface View extends IBaseView {
        void success(BaseResult<HomeModel> result);

        void loadNewPageSuccess(BaseResult<ConditionListModel> result);

        void loadNewPageError(String message);
    }

    interface Presenter extends IPresenter<View> {
        void request();

        void loadNewPage(boolean init, String cid, String sort, int page_no);
    }
}
