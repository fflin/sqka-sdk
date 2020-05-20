package com.hengxin.mall.ui.saleout;

import com.hengxin.basic.base.ApiErrorConsumer;
import com.hengxin.basic.base.BaseResult;
import com.hengxin.basic.util.Log;
import com.hengxin.basic.util.RxUtils;
import com.hengxin.mall.base.BasePresenter;
import com.hengxin.mall.model.UpLoadFileModel;
import com.hengxin.mall.utils.FileUtils;

import java.io.File;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * author : fflin
 * date   : 2020/5/20 10:19
 * desc   :
 * version: 1.0
 */
public class UploadPresenter extends BasePresenter<UploadImgContract.View> implements UploadImgContract.Presenter{

    @Override
    public void uploadImg(String filePath) {
        mView.showLoading();
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        String extensionName = FileUtils.getExtensionName(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        //上传图片增加时间戳
        MultipartBody.Part body = MultipartBody.Part.createFormData("multipartFiles", "135test" + System.currentTimeMillis() / 1000 +extensionName, requestBody);
        mDisposable.add(mApiService.uploadFile("http://test.rrh.51youhuihezi.com",body).compose(RxUtils.transformResult()).subscribe(new Consumer<BaseResult<UpLoadFileModel>>() {
            @Override
            public void accept(BaseResult<UpLoadFileModel> upLoadFileModelBaseResult) throws Exception {
                Log.i("fflin","upload succ0000");
                mView.uploadSuccess(upLoadFileModelBaseResult.data);
                mView.hideLoading();
            }
        }, new ApiErrorConsumer() {
            @Override
            public void onFail(int code, String message) {
                Log.i("fflin","upload fail  code = "+code+"; message = "+message);
                mView.uploadFailed(message);
                mView.hideLoading();
            }
        }));
    }
}
