package com.hengxin.pickimg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hengxin.pickimg.R;
import com.hengxin.pickimg.loader.PickerImageLoader;
import com.hengxin.pickimg.model.PhotoInfo;
import com.hengxin.pickimg.utils.ThumbnailsUtil;
import com.hengxin.pickimg.utils.ViewUtil;

import java.util.List;


public class PickerPhotoAdapter extends BaseAdapter {

    private List<PhotoInfo> photoList;
    private LayoutInflater inflater;
    private int width;

    public PickerPhotoAdapter(Context context, List<PhotoInfo> photoList) {
        this.photoList = photoList;
        inflater = LayoutInflater.from(context);
        width = ViewUtil.getScreenWidth(context) / 4;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewPhoteHolder viewPhoteHolder;
        if (convertView == null) {
            viewPhoteHolder = new ViewPhoteHolder();
            convertView = inflater.inflate(R.layout.picker_photo_item, parent, false);
            viewPhoteHolder.image = convertView.findViewById(R.id.picker_photo_item_img);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewPhoteHolder.image.getLayoutParams();
            layoutParams.height = layoutParams.width = width;
            convertView.setTag(viewPhoteHolder);
        } else {
            viewPhoteHolder = (ViewPhoteHolder) convertView.getTag();
        }
        PhotoInfo photoInfo = photoList.get(position);
        String thumbPath = ThumbnailsUtil.getThumbnailWithImageID(photoInfo.imageId, photoInfo.filePath);
        PickerImageLoader.display(thumbPath, photoInfo.absolutePath, viewPhoteHolder.image, R.drawable.nim_image_default);
        return convertView;
    }

    public class ViewPhoteHolder {
        public ImageView image;
    }
}
