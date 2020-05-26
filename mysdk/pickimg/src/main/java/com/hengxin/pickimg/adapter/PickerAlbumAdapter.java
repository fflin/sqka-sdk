package com.hengxin.pickimg.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengxin.pickimg.R;
import com.hengxin.pickimg.loader.PickerImageLoader;
import com.hengxin.pickimg.model.AlbumInfo;
import com.hengxin.pickimg.utils.ThumbnailsUtil;

import java.util.List;

/**
 * author: Y_Qing
 * date: 2018/12/28
 */
public class PickerAlbumAdapter extends RecyclerView.Adapter {
    private List<AlbumInfo> albumInfolist;
    View.OnClickListener onClickListener;

    public PickerAlbumAdapter(List<AlbumInfo> albumInfolist, View.OnClickListener onClickListener) {
        this.albumInfolist = albumInfolist;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.picker_image_item, parent, false);
        inflate.setOnClickListener(onClickListener);
        return new PickerViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PickerViewHolder pickerViewHolder = (PickerViewHolder) holder;
        pickerViewHolder.bindView(albumInfolist.get(position));
    }

    @Override
    public int getItemCount() {
        return albumInfolist.size();
    }


    private class PickerViewHolder extends RecyclerView.ViewHolder {
        private ImageView pickerCover;
        private TextView info, coumtTv;

        PickerViewHolder(View inflate) {
            super(inflate);
            pickerCover = inflate.findViewById(R.id.picker_photofolder_cover);
            info = inflate.findViewById(R.id.picker_photofolder_info);
            coumtTv = inflate.findViewById(R.id.picker_photofolder_num);
        }

        void bindView(AlbumInfo albumInfo) {
            info.setText(albumInfo.nameAlbum);
            coumtTv.setText(String.format("共%d张", albumInfo.list.size()));
            String thumbPath = ThumbnailsUtil.getThumbnailWithImageID(albumInfo.imageID, albumInfo.pathFile);
            PickerImageLoader.display(thumbPath, albumInfo.pathAbsolute, pickerCover, R.drawable.nim_image_default);
            itemView.setTag(albumInfo);
        }
    }

}
