package com.hengxin.mall.ui.home.viewholder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;

import com.hengxin.mall.R;
import com.hengxin.mall.ui.home.adapter.TwoColumnAdapter;

/**
 * author : fflin
 * date   : 2020/4/23 12:03
 * desc   : 两列展示的商品列表
 * version: 1.0
 */
public class TwoColumnViewHolder extends RecyclerView.ViewHolder {

    private final GridView gridView;
    private final TwoColumnAdapter adapter;

    public TwoColumnViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        gridView = itemView.findViewById(R.id.page_grid_view);
        adapter = new TwoColumnAdapter(mContext);
        gridView.setAdapter(adapter);
    }

    public void setListDate(Object data) {
        adapter.setData(data);
    }
}
