package com.hengxin.mall.ui.saleout.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;

import com.hengxin.basic.api.ApiHelper;
import com.hengxin.basic.base.ApiErrorConsumer;
import com.hengxin.basic.util.Log;
import com.hengxin.basic.util.MessageShowUtils;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.utils.EasyPermissions;

import io.reactivex.disposables.Disposable;

/**
 * author: Y_Qing
 * date: 2019/1/3
 */
public class PickImageHelper {


    private AlertDialog alertDialog;
    private static boolean noCut;

    public void pickImage(Activity context, boolean noCut, OnPickImageClick imageClick) {
        PickImageHelper.noCut = noCut;
        View dialgRootView = context.getLayoutInflater().inflate(R.layout.dialog_picker, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialgRootView);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialgRootView.findViewById(R.id.picker_take_phone).setOnClickListener(v -> {
            if (alertDialog.isShowing()) alertDialog.dismiss();
            imageClick.onCameraClick();

        });
        dialgRootView.findViewById(R.id.picker_phone).setOnClickListener(v -> {
            if (alertDialog.isShowing()) alertDialog.dismiss();
            imageClick.onGalleryClick();

        });
        dialgRootView.findViewById(R.id.picker_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    /*private static class PickPermission implements EasyPermissions.CheckPermListener {
        View view;
        android.support.v4.app.Fragment mFragment;
        int type;//相册

        public PickPermission(View view, Fragment fragment, int type) {
            this.view = view;
            this.mFragment = fragment;
            this.type = type;
        }

        public void grantPermission() {
            if (type == RequestCode.PickTakePhoteCode)
                PickerAlbumActivity.startActivity(mFragment, 0, RequestCode.PickTakePhoteCode, null, RequestCode.PickCallBackRequest, noCut);
            else if (type == RequestCode.PickPhoteCode)
                PickerAlbumActivity.startActivity(mFragment, 0, RequestCode.PickPhoteCode, null, RequestCode.PickCallBackRequest,noCut);
            else MessageShowUtils.showMessage(0, "传入类型不正确!", null, mFragment.getActivity());//
        }

        public void denyPermission() {
            if (view == null) {
                MessageShowUtils.showMessage(0, "传入view不能为空!", null, mFragment.getActivity());
                return;
            }
            SnackBarUtil.show(view, type == RequestCode.PickTakePhoteCode ? "请去设置界面开启相机权限" : "请去设置界面开启写入SD卡权限", "去设置", view -> OpenOutActivityUtils.openSettingActivity(mFragment.getActivity()), null);
        }
    }*/

    /**
     * 处理intent
     *
     * @return
     */
    /*public static String praseIntent(Intent data, int requestCode) {
        if (requestCode == RequestCode.PickCallBackRequest && data != null) {
            return data.getStringExtra(Extras.intputPath);//裁剪目录
        }
        return null;
    }*/

    /*public static Disposable upLoadFile(String fileName, UploadFileBack uploadFileBack) {
        Disposable disposable = null;
        uploadFileBack.showLoading();
        Observable<UpLoadFileModel> observable = ApiHelper.get().uploadFile(fileName);
        if (observable != null) {
            disposable = observable.subscribe(upLoadFileModel -> {
                uploadFileBack.hideLoading();
                uploadFileBack.uploadSucess(fileName, upLoadFileModel);
                Log.i("fflin: ", "uploadFile  success");
            }, new ApiErrorConsumer() {
                @Override
                public void onFail(int code, String message) {
                    uploadFileBack.hideLoading();
                    uploadFileBack.uploadFaile(message);
                    Log.i("fflin: ", "uploadFile  failed");
                }
            });
        } else {
            uploadFileBack.hideLoading();
            uploadFileBack.uploadFaile("");
            Log.i("fflin: ", "uploadFile  failed");
        }
        return disposable;
    }*/

    public interface UploadFileBack {
        void showLoading();

        void hideLoading();

//        void uploadSucess(String fileName, UpLoadFileModel upLoadFileModel);

        void uploadFaile(String messageStr);
    }
}
