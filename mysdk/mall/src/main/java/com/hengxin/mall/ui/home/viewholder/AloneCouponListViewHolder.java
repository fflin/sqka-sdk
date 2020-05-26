package com.hengxin.mall.ui.home.viewholder;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hengxin.mall.R;
import com.hengxin.mall.inter.OnCallBackDetail;
import com.hengxin.mall.model.CouponNewModel;
import com.hengxin.mall.utils.ConstantUtil;
import com.hengxin.mall.view.RoundImageView;

import java.text.DecimalFormat;

/**
 * Created by HFmacmini on 06/12/2017.
 */

public class AloneCouponListViewHolder extends RecyclerView.ViewHolder {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.#");
    RoundImageView coupon_item_pic;
    TextView coupon_item_good_title;
    TextView coupon_item_coupon_price;
    TextView coupon_item_good_price, itemList;
    TextView tvSalesCount;
    TextView estimateCommi;
    TextView coupon_item_good_old_price;
    OnCallBackDetail onCallBackDetail;
    ImageView iconTv;
    private Context mContext;
    private final View videoPic;
    ImageView couponAfterIv;

    public AloneCouponListViewHolder(View itemView, Context mContext) {
        super(itemView);
        this.mContext = mContext;
        itemList = itemView.findViewById(R.id.coupon_item_list);
        videoPic = itemView.findViewById(R.id.coupon_video_pic);
        iconTv = itemView.findViewById(R.id.view_detail_list_item_iconTv);
        coupon_item_pic = itemView.findViewById(R.id.coupon_item_pic);
        coupon_item_good_title = itemView.findViewById(R.id.coupon_item_good_title);
        coupon_item_coupon_price = itemView.findViewById(R.id.coupon_item_coupon_price);
        coupon_item_good_price = itemView.findViewById(R.id.coupon_item_good_price);
        coupon_item_good_old_price = itemView.findViewById(R.id.coupon_item_good_old_price);
        tvSalesCount = itemView.findViewById(R.id.tvSalesCount);
//        estimateCommi = itemView.findViewById(R.id.estimate_commi);
        couponAfterIv = itemView.findViewById(R.id.coupon_item_good_price_after);
    }

    public void setNewListDate(Object listDate) {
        CouponNewModel item1 = (CouponNewModel) listDate;
        if (item1 == null) return;
        updateView(item1);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewDeatil(item1);
            }
        });
        coupon_item_good_title.setText("\u3000  " + item1.title);
        if (item1.has_coupon) {
            coupon_item_good_old_price.setVisibility(View.VISIBLE);
            coupon_item_coupon_price.setVisibility(View.VISIBLE);
            coupon_item_coupon_price.setText("可抵扣" + item1.coupon_amount + "元");
        } else {
            if (TextUtils.isEmpty(item1.zk_amount) || "0".equals(item1.zk_amount)) {
                coupon_item_good_old_price.setVisibility(View.INVISIBLE);
                coupon_item_coupon_price.setVisibility(View.INVISIBLE);
            } else {
                coupon_item_good_old_price.setVisibility(View.VISIBLE);
                coupon_item_coupon_price.setVisibility(View.VISIBLE);
                coupon_item_coupon_price.setText(item1.zk_amount + "折");
            }
        }
        if (!TextUtils.isEmpty(CouponNewModel.transformVolume(item1.volume))) {
            tvSalesCount.setVisibility(View.VISIBLE);
            tvSalesCount.setText("月销 " + CouponNewModel.transformVolume(item1.volume));
            /*if (item1.volume > 10000) {
                tvSalesCount.setText("月销 " + decimalFormat.format(item1.volume / 10000f) + "万");
            } else {
                tvSalesCount.setText("月销 " + item1.volume);
            }*/
        } else {
            tvSalesCount.setVisibility(View.INVISIBLE);
        }
        if (TextUtils.isEmpty(item1.video)) {
            videoPic.setVisibility(View.GONE);
            videoPic.setOnClickListener(null);
        } else {
            videoPic.setVisibility(View.VISIBLE);
            videoPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    VideoActivity.startVideoActivity(mContext, item1.video);
                }
            });
        }
        if (item1.rank_number != 0) {
            itemList.setVisibility(View.VISIBLE);
            itemList.setText(String.valueOf(item1.rank_number));
        } else {
            itemList.setVisibility(View.GONE);
        }
    }

    private void updateView(CouponNewModel item1) {
        if (item1 == null) return;
        if (item1.coupon_mall_type == ConstantUtil.TAOBAOTYPE) {
//            coupon_item_pic.setImageURI(Uri.parse(item1.pict_url + "_400x400"));
            Glide.with(mContext).load(item1.pict_url + "_400x400").into(coupon_item_pic);
            if (item1.is_tmall) {
                iconTv.setImageResource(R.drawable.view_home_guess_like_item_tmall_icon);
                coupon_item_good_old_price.setText(String.format(mContext.getString(R.string.along_tian_mao), item1.old_price));
            } else {
                iconTv.setImageResource(R.drawable.icon_t_bao);
                coupon_item_good_old_price.setText(String.format(mContext.getString(R.string.along_tao_bao), item1.old_price));
            }
            couponAfterIv.setImageResource(item1.has_coupon ? R.drawable.page_sc_gapprice : R.drawable.page_sc_gapprice_zk);
        } else if (item1.coupon_mall_type == ConstantUtil.PINDUODUOTYPE) {
//            coupon_item_pic.setImageURI(Uri.parse(item1.pict_url));
            Glide.with(mContext).load(item1.pict_url).into(coupon_item_pic);
            iconTv.setImageResource(R.drawable.icon_t_pin);
            coupon_item_good_old_price.setText(String.format(mContext.getString(R.string.along_pin_duo_duo), item1.old_price));
            couponAfterIv.setImageResource(R.drawable.pin_duo_gapprice);
        } else {
//            coupon_item_pic.setImageURI(Uri.parse(item1.pict_url));
            Glide.with(mContext).load(item1.pict_url).into(coupon_item_pic);
            iconTv.setImageResource(R.drawable.jd_icon);
            coupon_item_good_old_price.setText(String.format(mContext.getString(R.string.along_jd), item1.old_price));
            couponAfterIv.setImageResource(item1.has_coupon ? R.drawable.page_sc_gapprice : R.drawable.page_sc_gapprice_zk);
        }
        coupon_item_good_price.setText(" ￥" + item1.final_price);
    }

    // 为您推荐的跳转，不需要再开启一个新的activity
    private void openNewDeatil(final CouponNewModel item1) {
        if (onCallBackDetail != null) {
            /*DetailActivity.changeViewData(item1);*/
            onCallBackDetail.onClickJumpChaoRenDetail(item1);
        }

    }

    public AloneCouponListViewHolder setOnCallBackDetail(OnCallBackDetail onCallBackDetail) {
        this.onCallBackDetail = onCallBackDetail;
        return this;
    }


}
