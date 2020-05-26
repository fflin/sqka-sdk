package com.hengxin.mall.ui.order.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.hengxin.mall.R;
import com.hengxin.mall.base.RBaseItem;
import com.hengxin.mall.ui.order.viewholder.OrderMoreViewHolder;

/**
 * author : fflin
 * date   : 2020/4/28 15:49
 * desc   : 订单适配，包含全部订单和售后订单，--不区分是否合并，只区分是否售后
 * version: 1.0
 */
public class MallOrderItem extends RBaseItem {

    private Context mContext;
    private int mOrderTag;

    public MallOrderItem(Context context, int orderTag) {
        super(context);
        this.mContext = context;
        this.mOrderTag = orderTag;
    }

    @Override
    public void binding(Object data, RecyclerView.ViewHolder baseHolder, int position) {
        ((OrderMoreViewHolder) baseHolder).bindData(mContext, data, position, mOrderTag);
    }

    @Override
    public RecyclerView.ViewHolder getHolder(View convertView, int itemType) {
        return new OrderMoreViewHolder(convertView);
    }

    @Override
    public int getItemLayout(int itemType) {
        return R.layout.item_mall_order_more;
    }
}
