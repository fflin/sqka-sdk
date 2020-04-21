package com.hengxin.mall.ui.home;


import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.base.IBaseView;
import com.hengxin.basic.base.IPresenter;
import com.hengxin.basic.model.ConditionListModel;
import com.hengxin.basic.model.HomeModel;

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
