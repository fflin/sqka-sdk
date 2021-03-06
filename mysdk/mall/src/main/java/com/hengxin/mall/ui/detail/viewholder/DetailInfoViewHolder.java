package com.hengxin.mall.ui.detail.viewholder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hengxin.mall.R;
import com.hengxin.mall.model.DetailModel;
import com.hengxin.mall.view.WrapContentDraweeView;

/**
 * author : fflin
 * date   : 2020/4/24 16:21
 * desc   :
 * version: 1.0
 */
public class DetailInfoViewHolder extends RecyclerView.ViewHolder {

    private ImageView draweeView;

    public DetailInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        draweeView = itemView.findViewById(R.id.detail_image_view);
    }

    public void bindData(Context mContext, DetailModel model) {
        String image = model.picInfo;
        Glide.with(mContext).load(image).into(draweeView);
    }
}
