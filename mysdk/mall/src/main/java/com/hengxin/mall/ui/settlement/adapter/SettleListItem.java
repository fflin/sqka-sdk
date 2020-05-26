package com.hengxin.mall.ui.settlement.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.hengxin.mall.R;
import com.hengxin.mall.base.RBaseItem;

/**
 * author : fflin
 * date   : 2020/4/27 15:14
 * desc   :
 * version: 1.0
 */
public class SettleListItem extends RBaseItem {
    public SettleListItem(Context context) {
        super(context);
    }

    @Override
    public void binding(Object data, RecyclerView.ViewHolder baseHolder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder getHolder(View convertView, int itemType) {
        return new RecyclerView.ViewHolder(convertView) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }

    @Override
    public int getItemLayout(int itemType) {
        return R.layout.item_settle_single;
    }
}
