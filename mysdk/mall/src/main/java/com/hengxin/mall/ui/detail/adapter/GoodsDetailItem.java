package com.hengxin.mall.ui.detail.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.hengxin.mall.R;
import com.hengxin.mall.base.RBaseItem;
import com.hengxin.mall.constant.DetailConstant;
import com.hengxin.mall.model.DetailTypedModel;
import com.hengxin.mall.ui.detail.viewholder.DetailHeaderViewHolder;
import com.hengxin.mall.ui.detail.viewholder.DetailInfoViewHolder;

/**
 * author : fflin
 * date   : 2020/4/24 15:31
 * desc   :
 * version: 1.0
 */
public class GoodsDetailItem extends RBaseItem<DetailTypedModel> {

    private Context mContext;
    public GoodsDetailItem(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void binding(DetailTypedModel data, RecyclerView.ViewHolder baseHolder, int position) {
        int itemViewType = getItemViewType(data, position);
        if (itemViewType == DetailConstant.TYPE_HEADER) {
            DetailHeaderViewHolder holder = (DetailHeaderViewHolder) baseHolder;
            holder.bindData(mContext,data.model);
        } else if(itemViewType == DetailConstant.TYPE_INFO) {
            DetailInfoViewHolder holder = (DetailInfoViewHolder) baseHolder;
            holder.bindData(mContext,data.model);
        }
    }

    @Override
    public RecyclerView.ViewHolder getHolder(View convertView, int itemType) {
        if (itemType == DetailConstant.TYPE_HEADER) {
            return new DetailHeaderViewHolder(convertView);
        } else if (itemType == DetailConstant.TYPE_INFO) {
            return new DetailInfoViewHolder(convertView);
        }
        return new RecyclerView.ViewHolder(convertView) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
    }


    @Override
    public int getItemLayout(int itemType) {
        if (itemType == DetailConstant.TYPE_HEADER) {
            return R.layout.item_detail_header;
        } else if (itemType == DetailConstant.TYPE_INFO) {
            return R.layout.item_detail_info;
        } else if (itemType == DetailConstant.TYPE_SLOGAN) {
            return R.layout.layout_slogan;
        }
        return 0;
    }

    @Override
    public int getItemViewType(DetailTypedModel data, int position) {
        return data.type;
    }
}
