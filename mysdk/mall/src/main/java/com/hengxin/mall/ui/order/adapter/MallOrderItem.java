package com.hengxin.mall.ui.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hengxin.mall.base.RBaseItem;

/**
 * author : fflin
 * date   : 2020/4/28 15:49
 * desc   : 订单适配，包含全部订单和售后订单，itemType区分 合并 不合并订单
 * version: 1.0
 */
public class MallOrderItem extends RBaseItem {

    public MallOrderItem(Context context, int orderTag) {
        super(context);
    }

    @Override
    public void binding(Object data, RecyclerView.ViewHolder baseHolder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder getHolder(View convertView, int itemType) {
        return null;
    }

    @Override
    public int getItemLayout(int itemType) {
        return 0;
    }
}
