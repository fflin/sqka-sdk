package com.hengxin.mall.ui.saleout;


import com.hengxin.mall.base.IBaseView;
import com.hengxin.mall.base.IPresenter;
import com.hengxin.mall.model.UpLoadFileModel;

public interface UploadImgContract {
    interface View extends IBaseView {

        void uploadSuccess(UpLoadFileModel upLoadFileModel);

        void uploadFailed(String messageStr);
    }

    interface Presenter extends IPresenter<View> {

        void uploadImg(String fileName);

    }
}
