package com.hengxin.mall.ui.saleout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hengxin.basic.util.Log;
import com.hengxin.basic.util.ToastUtils;
import com.hengxin.basic.util.ViewUtil;
import com.hengxin.mall.R;
import com.hengxin.mall.base.BaseActivity;
import com.hengxin.mall.manager.CrashBugGridLayoutManager;
import com.hengxin.mall.model.SaleOutDialogModel;
import com.hengxin.mall.model.UpLoadFileModel;
import com.hengxin.mall.ui.saleout.adapter.OnAdapterClick;
import com.hengxin.mall.ui.saleout.adapter.SelectPicAdapter;
import com.hengxin.mall.ui.saleout.dialog.OnListDialogClick;
import com.hengxin.mall.ui.saleout.dialog.OnPickImageClick;
import com.hengxin.mall.ui.saleout.dialog.PickImageHelper;
import com.hengxin.mall.ui.saleout.dialog.SaleOutDialogHelper;
import com.hengxin.mall.ui.saleout.dialog.UploadDialog;
import com.hengxin.mall.utils.EasyPermissions;
import com.hengxin.mall.view.SpaceItemDecoration;
import com.hengxin.pickimg.PickerAlbumActivity;
import com.hengxin.pickimg.constant.Extras;
import com.hengxin.pickimg.constant.RequestCode;

import java.util.ArrayList;
import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/29 12:43
 * desc   : 申请售后页面
 * version: 1.0
 */
public class AskForSaleActivity extends BaseActivity implements OnAdapterClick, UploadImgContract.View {

    private TextView askType;
    private TextView askReason;
    private List<SaleOutDialogModel> askTypeList = new ArrayList<>();
    private List<SaleOutDialogModel> askReasonList = new ArrayList<>();
    private RecyclerView recyclerView;
    private List<UpLoadFileModel.FilesBean> picModels = new ArrayList<>();
    private UploadPresenter presenter;
    private SelectPicAdapter adapter;
    private final int picMaxCount = 5;
    private TextView textPicCount;
    private UploadDialog uploadDialog;

    public static void startAskForSaleAty(Context context) {
        Intent intent = new Intent(context, AskForSaleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void addPlaceImg() {
        UpLoadFileModel.FilesBean model = new UpLoadFileModel.FilesBean();
        model.fileName = Constant.SELECT_PIC_NAME;
        model.fileUrl = "";
        picModels.add(model);
    }

    private void removePlaceImg() {
        UpLoadFileModel.FilesBean removeBean = null;
        for (int i = 0; i < picModels.size(); i++) {
            if (picModels.get(i).fileName.equals(Constant.SELECT_PIC_NAME)) {
                removeBean = picModels.get(i);
            }
        }
        if (removeBean != null) picModels.remove(removeBean);
    }

    @Override
    protected void initData() {
        addPlaceImg();

        adapter = new SelectPicAdapter(this, picModels, this);
        recyclerView.setAdapter(adapter);

        SaleOutDialogModel model1 = new SaleOutDialogModel("换货", "(更换收到的商品)", true);
        SaleOutDialogModel model2 = new SaleOutDialogModel("退款", "(未收到货，只退款)", false);
        SaleOutDialogModel model3 = new SaleOutDialogModel("退款退货", "(已收到货，需要退换已收到的货物)", false);

        askTypeList.add(model1);
        askTypeList.add(model2);
        askTypeList.add(model3);

        SaleOutDialogModel model4 = new SaleOutDialogModel("多拍、错拍", "", false);
        SaleOutDialogModel model5 = new SaleOutDialogModel("质量问题", "", false);
        SaleOutDialogModel model6 = new SaleOutDialogModel("7天无理由", "", false);
        SaleOutDialogModel model7 = new SaleOutDialogModel("其他", "", false);

        askReasonList.add(model4);
        askReasonList.add(model5);
        askReasonList.add(model6);
        askReasonList.add(model7);

        presenter = new UploadPresenter();
        presenter.onAttach(this);
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("申请售后");
        recyclerView = findViewById(R.id.ask_photo_rv);
        recyclerView.addItemDecoration(new SpaceItemDecoration(ViewUtil.dp2px(5)));
        recyclerView.setLayoutManager(new CrashBugGridLayoutManager(this, 3));
        textPicCount = findViewById(R.id.ask_photo_num);
        findViewById(R.id.ask_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskForSaleActivity.this.startActivity(new Intent(AskForSaleActivity.this, AskSuccActivity.class));
            }
        });
        askType = findViewById(R.id.ask_type);
        askReason = findViewById(R.id.ask_reason);
        askType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleOutDialogHelper helper = new SaleOutDialogHelper(AskForSaleActivity.this);
                helper.showListDialog("请选择申请类型", askTypeList, new OnListDialogClick() {
                    @Override
                    public void onItemClick(View view) {
                        SaleOutDialogModel model = (SaleOutDialogModel) view.getTag();
                        if (model != null) {
                            String title = model.title;
                            if (!TextUtils.isEmpty(title)) {
                                askType.setText(title);
                            }
                        }
                        helper.dismissDilog();
                    }
                });
            }
        });

        askReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleOutDialogHelper helper = new SaleOutDialogHelper(AskForSaleActivity.this);
                helper.showListDialog("请选择申请类型", askReasonList, new OnListDialogClick() {
                    @Override
                    public void onItemClick(View view) {
                        SaleOutDialogModel model = (SaleOutDialogModel) view.getTag();
                        if (model != null) {
                            String title = model.title;
                            if (!TextUtils.isEmpty(title)) {
                                askReason.setText(title);
                            }
                        }
                        helper.dismissDilog();
                    }
                });
            }
        });
        uploadDialog = new UploadDialog();
    }

    @Override
    protected int setLayout() {
        return R.layout.aty_ask_sale;
    }


    private UpLoadFileModel.FilesBean updateBean;

    @Override
    public void onUploadClick(View view) {
        this.updateBean = (UpLoadFileModel.FilesBean) view.getTag();
        // 上传照片  更新list
        new PickImageHelper().pickImage(this, true, new OnPickImageClick() {
            @Override
            public void onGalleryClick() {
                EasyPermissions.requestNomalPermissions(AskForSaleActivity.this, 123, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            @Override
            public void onCameraClick() {
                EasyPermissions.requestNomalPermissions(AskForSaleActivity.this, 111, Manifest.permission.CAMERA);
            }
        });
    }

    @Override
    public void onDeleteClick(View view) {
        int position = (int) view.getTag();
        picModels.remove(position);
        if (picModels.size() < picMaxCount && !picModels.get(picModels.size() - 1).fileName.equals(Constant.SELECT_PIC_NAME)) {
            addPlaceImg();
        }
        textPicCount.setText("上传凭证("+(picModels.size()-1)+"/"+picMaxCount+")");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions[0].equals(Manifest.permission.CAMERA)) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 允许拍照
                PickerAlbumActivity.startActivity(this, 0, RequestCode.PickTakePhoteCode, null, RequestCode.PickCallBackRequest, false);
            } else {
                ToastUtils.show(AskForSaleActivity.this, "请打开相机权限拍照上传图片");
            }
        } else if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PickerAlbumActivity.startActivity(this, 0, RequestCode.PickPhoteCode, null, RequestCode.PickCallBackRequest, false);
            } else {
                ToastUtils.show(AskForSaleActivity.this, "请打开存储空间权限以获取设备内照片");
            }
        } else {
            Log.i("fflin", "权限申请出错");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("fflin", "onActivityResult   ; requestCode = " + requestCode + "; resultCode = " + resultCode);
        if (requestCode == RequestCode.PickCallBackRequest && data != null) {
            String filePath = data.getStringExtra(Extras.intputPath);//裁剪目录
            // 上传接口
            presenter.uploadImg(filePath);
        }
    }

    @Override
    public void uploadSuccess(UpLoadFileModel upLoadFileModel) {
        if (upLoadFileModel != null) {
            List<UpLoadFileModel.FilesBean> files = upLoadFileModel.files;
            if (files != null && files.size() > 0) {
                // 处理数据
                UpLoadFileModel.FilesBean filesBean = files.get(0);
                removePlaceImg();
                if (!TextUtils.isEmpty(updateBean.fileUrl)) {
                    upDataPicList(updateBean.fileUrl, filesBean);
                    updateBean.fileUrl = null;
                } else {
                    picModels.add(filesBean);
                }

                if (picModels.size() < picMaxCount) {
                    addPlaceImg();
                }
                // 判断不包含空url。
                int count = 0;
                if (!TextUtils.isEmpty(picModels.get(picModels.size()-1).fileUrl)) {
                    count = picModels.size();
                } else {
                    count = picModels.size() - 1;
                }
                textPicCount.setText("上传凭证("+count+"/"+picMaxCount+")");
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void upDataPicList(String fileUrl, UpLoadFileModel.FilesBean filesBean) {
        int oldBeanPosition = -1;
        for (int i = 0; i < picModels.size(); i++) {
            UpLoadFileModel.FilesBean filesBean1 = picModels.get(i);
            if (filesBean1.fileUrl.equals(fileUrl)) {
                oldBeanPosition = i;
            }
        }
        if (oldBeanPosition != -1) {
            picModels.remove(oldBeanPosition);
            picModels.add(oldBeanPosition, filesBean);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void uploadFailed(String messageStr) {

    }

    @Override
    public void showLoading() {
        uploadDialog.showDialog(this);
    }

    @Override
    public void hideLoading() {
        uploadDialog.hideDialog();
    }

    @Override
    public void onError(String message) {

    }
}
