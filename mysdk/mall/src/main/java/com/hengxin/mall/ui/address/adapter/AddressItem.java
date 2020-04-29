package com.hengxin.mall.ui.address.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hengxin.mall.R;
import com.hengxin.mall.base.RBaseItem;

/**
 * author : fflin
 * date   : 2020/4/29 18:39
 * desc   : 收获地址列表
 * version: 1.0
 */
public class AddressItem extends RBaseItem {
    public AddressItem(Context context) {
        super(context);
    }

    @Override
    public void binding(Object data, RecyclerView.ViewHolder baseHolder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder getHolder(View convertView, int itemType) {
        return new AddressViewHolder(convertView);
    }

    @Override
    public int getItemLayout(int itemType) {
        return R.layout.item_address;
    }
}
