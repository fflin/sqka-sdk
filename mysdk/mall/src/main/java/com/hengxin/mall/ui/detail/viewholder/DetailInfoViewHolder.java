package com.hengxin.mall.ui.detail.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

    private WrapContentDraweeView draweeView;

    public DetailInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        draweeView = itemView.findViewById(R.id.detail_image_view);
    }

    public void bindData(Context mContext, DetailModel model) {
        String image = model.picInfo;
        draweeView.setImageURI(image);
    }
}
