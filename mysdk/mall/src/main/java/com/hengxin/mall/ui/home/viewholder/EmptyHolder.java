package com.hengxin.mall.ui.home.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * author:author
 * date: 2018/3/26
 */
public class EmptyHolder extends RecyclerView.ViewHolder {
    public EmptyHolder(View convertView) {
        super(convertView);
        convertView.setVisibility(View.GONE);
    }
}