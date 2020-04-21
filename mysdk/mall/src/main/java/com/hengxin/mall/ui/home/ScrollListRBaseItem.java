package com.hengxin.mall.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hengxin.mall.R;
import com.hengxin.mall.base.RBaseItem;
import com.hengxin.basic.model.CouponNewModel;
import com.hengxin.mall.utils.ViewUtil;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/2/1.
 */

public class ScrollListRBaseItem extends RBaseItem {
    protected static final DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private final int screenWidth;

    public ScrollListRBaseItem(Context context) {
        super(context);
        screenWidth = ViewUtil.getScreenWidth(context);
    }

    @Override
    public void binding(Object data, RecyclerView.ViewHolder baseHolder, int position) {
        ScrolListViewHolder viewHolder = (ScrolListViewHolder) baseHolder;
        viewHolder.bindDateView(data);
    }

    @Override
    public RecyclerView.ViewHolder getHolder(View convertView, int itemType) {
        return new ScrolListViewHolder(convertView);
    }

    @Override
    public int getItemLayout(int itemType) {
        return R.layout.include_page_home_fixthree_item;
    }

    public class ScrolListViewHolder extends RecyclerView.ViewHolder {
        private final SimpleDraweeView image;
        private final TextView title, price, amout, itemList;

        public ScrolListViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.page_fix_three_iv);
            title = itemView.findViewById(R.id.page_fix_three_tv_top);
            price = itemView.findViewById(R.id.page_fix_three_tv_price);
            amout = itemView.findViewById(R.id.page_fix_three_tv_amount);
            itemList = itemView.findViewById(R.id.item_list);
        }

        public void bindDateView(Object date) {
            CouponNewModel couponModel = (CouponNewModel) date;
            price.setText("￥" + couponModel.final_price);
            title.setText(couponModel.title);
            String volume = CouponNewModel.transformVolume(couponModel.volume) + "人已买";
            amout.setText(volume);
            image.setImageURI(couponModel.pict_url);
            itemView.setOnClickListener(view -> openNewDeatil(couponModel));
            if (couponModel.rank_number != 0) {
                itemList.setText(String.valueOf(couponModel.rank_number));
                itemList.setVisibility(View.VISIBLE);
            } else {
                itemList.setVisibility(View.GONE);
            }
        }
    }

    private void openNewDeatil(CouponNewModel item1) {
        if (callBackDetail != null)
            callBackDetail.onClickJumpChaoRenDetail(item1);

    }

}
