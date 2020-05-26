package com.hengxin.pickimg.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hengxin.pickimg.DataHolder;
import com.hengxin.pickimg.R;
import com.hengxin.pickimg.adapter.PickerPhotoAdapter;
import com.hengxin.pickimg.constant.Extras;
import com.hengxin.pickimg.constant.RequestCode;
import com.hengxin.pickimg.crop.CropImageActivity;
import com.hengxin.pickimg.model.AlbumInfo;
import com.hengxin.pickimg.model.PhotoInfo;
import com.hengxin.pickimg.utils.CompressCallBack;
import com.hengxin.pickimg.utils.PhotoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 相册-gridView列表
 * author: Y_Qing
 * date: 2018/12/28
 */
public class PickerImageFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private List<PhotoInfo> photoList;
    private GridView pickerImageGridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picker_image, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        proceedExtra();
        initView();
    }

    private void initView() {
        pickerImageGridView = findView(R.id.picker_images_gridview);
        pickerImageGridView.setAdapter(new PickerPhotoAdapter(context, photoList));
        pickerImageGridView.setOnItemClickListener(this);
    }

    private void proceedExtra() {
        Bundle bundle = getArguments();
        photoList = new ArrayList<>();
        photoList.addAll(getPhotos(bundle));
    }

    private List<PhotoInfo> getPhotos(Bundle bundle) {
        AlbumInfo album_type_value = (AlbumInfo) DataHolder.getInstance().getData(Extras.transAlbumKey);
        if (album_type_value != null) {
            DataHolder.getInstance().setData(Extras.transAlbumKey,null);
            return album_type_value.list;
        } else {
            return new ArrayList<>();
        }
    }

    protected <T extends View> T findView(int resId) {
        return (T) (getView().findViewById(resId));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PhotoInfo photoInfo = photoList.get(position);
        String photoPath = photoInfo.absolutePath;
        Intent intent = context.getIntent();
        int maxSizeLength = intent.getIntExtra(Extras.maxImageSize, 0);
        int height = intent.getIntExtra(Extras.cropOutputImageHeight, 0);
        int width = intent.getIntExtra(Extras.cropOutputImageWidth, 0);
        boolean noCut = intent.getBooleanExtra("noCut", false);
        if (noCut) {
            PhotoUtils.qualityCompress(view.getContext(), photoPath, new CompressCallBack() {
                @Override
                public void onCompressSucc(String filePath) {
                    Intent result = new Intent();
                    result.putExtra(Extras.intputPath, filePath);//返回裁剪图片路径
                    context.setResult(Activity.RESULT_OK, result);
                    context.finish();
                }

                @Override
                public void onCompressFiled(String message) {
                    Log.i("fflin","压缩图片失败------------------"+message);
                }
            });

        } else {
            CropImageActivity.startCropActivity(context, photoPath, width, height, RequestCode.PickPhoteCode, maxSizeLength);
        }
    }

}
