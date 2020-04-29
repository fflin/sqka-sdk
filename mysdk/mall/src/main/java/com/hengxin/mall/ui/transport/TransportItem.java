package com.hengxin.mall.ui.transport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hengxin.mall.R;
import com.hengxin.mall.base.RBaseItem;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/29 11:48
 * desc   :
 * version: 1.0
 */
public class TransportItem extends RBaseItem {
    private Context mContext;
    private List mList;
    private boolean fromTransform;
    public TransportItem(Context context, List testList, boolean fromTransform) {
        super(context);
        this.mContext = context;
        this.mList = testList;
        this.fromTransform = fromTransform;
    }

    @Override
    public void binding(Object data, RecyclerView.ViewHolder baseHolder, int position) {
        ((TransportViewHolder)baseHolder).bindViewData(mContext,mList,position, fromTransform);
    }

    @Override
    public RecyclerView.ViewHolder getHolder(View convertView, int itemType) {
        return new TransportViewHolder(convertView);
    }

    @Override
    public int getItemLayout(int itemType) {
        return R.layout.item_transport;
    }
}
