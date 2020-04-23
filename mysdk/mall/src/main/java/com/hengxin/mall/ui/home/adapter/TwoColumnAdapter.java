package com.hengxin.mall.ui.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hengxin.mall.R;
import com.hengxin.mall.inter.OnCallBackDetail;
import com.hengxin.mall.model.CouponNewModel;
import com.hengxin.mall.utils.ConstantUtil;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/4/23 14:52
 * desc   : 两列展示的商品适配 待接口真实数据测试
 * version: 1.0
 */
public class TwoColumnAdapter extends BaseAdapter {

    private Context mContext;
    private List<CouponNewModel> mList;

    public TwoColumnAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ItemViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.view_coupon_item, parent, false);
            holder = new ItemViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
            convertView.setOnClickListener(null);
        }
        CouponNewModel couponNewModel = mList.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        View.OnClickListener onClickListener = view -> {

        };
        holder.view.setOnClickListener(onClickListener);
        String url = couponNewModel.pict_url;
        if (couponNewModel.coupon_mall_type == ConstantUtil.TAOBAOTYPE) {
            url = couponNewModel.pict_url;
        }
        Glide.with(mContext).load(url).into(holder.coupon_item_pic);
        holder.coupon_item_good_title.setText("\u3000 " + couponNewModel.title);
        if (couponNewModel.has_coupon) {
            holder.coupon_item_good_old_price.setVisibility(View.VISIBLE);
            holder.coupon_item_coupon_price.setVisibility(View.VISIBLE);
            holder.coupon_item_coupon_price.setText("可抵扣" + couponNewModel.coupon_amount + "元");
        } else {
            if (TextUtils.isEmpty(couponNewModel.zk_amount)||"0".equals(couponNewModel.zk_amount)) {
                holder.coupon_item_good_old_price.setVisibility(View.INVISIBLE);
                holder.coupon_item_coupon_price.setVisibility(View.INVISIBLE);
            } else {
                holder.coupon_item_good_old_price.setVisibility(View.VISIBLE);
                holder.coupon_item_coupon_price.setVisibility(View.VISIBLE);
                holder.coupon_item_coupon_price.setText(couponNewModel.zk_amount + "折");
            }
        }
        holder.coupon_item_good_price.setText(couponNewModel.final_price);
        holder.view.setOnClickListener(onClickListener);
        String vol = CouponNewModel.transformVolume(couponNewModel.volume);
        if (!TextUtils.isEmpty(vol)) {
            holder.tvSalesCount.setVisibility(View.VISIBLE);
            holder.tvSalesCount.setText("月销 " + vol);
        } else {
            holder.tvSalesCount.setVisibility(View.INVISIBLE);
        }
        if (TextUtils.isEmpty(couponNewModel.video)) {
            holder.videoPic.setVisibility(View.GONE);
            holder.videoPic.setOnClickListener(null);
        } else {
            holder.videoPic.setVisibility(View.VISIBLE);
        }



        return convertView;
    }

    public void setData(Object data) {
        List<CouponNewModel> homePageItem = (List<CouponNewModel>) data;
        this.mList = homePageItem;
    }

    private class ItemViewHolder {
        ImageView coupon_item_pic;
        TextView coupon_item_good_title;
        TextView coupon_item_coupon_price;
        TextView coupon_item_good_price;
        TextView tvSalesCount;
        TextView coupon_item_good_old_price;
        OnCallBackDetail onCallBackDetail;
        private Context mContext;
        ImageView couponNewModeliconTv;
        private View view;
        private View videoPic;
        private TextView couponAfterTv;

        public ItemViewHolder(View itemView) {
            videoPic = itemView.findViewById(R.id.coupon_video_pic);
            couponNewModeliconTv = itemView.findViewById(R.id.view_home_guess_like_item_iconTv);
            coupon_item_pic = itemView.findViewById(R.id.coupon_item_pic);
            coupon_item_good_title = itemView.findViewById(R.id.coupon_item_good_title);
            coupon_item_coupon_price = itemView.findViewById(R.id.coupon_item_coupon_price);
            coupon_item_good_price = itemView.findViewById(R.id.coupon_item_good_price);
            coupon_item_good_old_price = itemView.findViewById(R.id.coupon_item_good_old_price);
            tvSalesCount = itemView.findViewById(R.id.tvSalesCount);
            couponAfterTv = itemView.findViewById(R.id.coupon_after);
        }
    }
}
