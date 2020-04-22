package com.hengxin.mall.ui.home.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hengxin.mall.R;
import com.hengxin.mall.inter.OnCallBackDetail;
import com.hengxin.mall.model.CouponNewModel;
import com.hengxin.mall.utils.ConstantUtil;

import java.text.DecimalFormat;

/**
 * Created by HFmacmini on 06/12/2017.
 */

public class CouponItemViewHolder extends RecyclerView.ViewHolder {

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.#");

    ImageView coupon_item_pic;
    TextView coupon_item_good_title;
    TextView coupon_item_coupon_price;
    TextView coupon_item_good_price;
    TextView tvSalesCount;
    TextView coupon_item_good_old_price;
    OnCallBackDetail onCallBackDetail;
    private Context mContext;
    ImageView item1iconTv;
    private View view;
    private View videoPic;
    private TextView couponAfterTv;

    public CouponItemViewHolder(View itemView, Context mContext) {
        super(itemView);
        view = itemView;
        this.mContext = mContext;
        videoPic = itemView.findViewById(R.id.coupon_video_pic);
        item1iconTv = itemView.findViewById(R.id.view_home_guess_like_item_iconTv);
        coupon_item_pic = itemView.findViewById(R.id.coupon_item_pic);
        coupon_item_good_title = itemView.findViewById(R.id.coupon_item_good_title);
        coupon_item_coupon_price = itemView.findViewById(R.id.coupon_item_coupon_price);
        coupon_item_good_price = itemView.findViewById(R.id.coupon_item_good_price);
        coupon_item_good_old_price = itemView.findViewById(R.id.coupon_item_good_old_price);
        tvSalesCount = itemView.findViewById(R.id.tvSalesCount);
        couponAfterTv = itemView.findViewById(R.id.coupon_after);
    }

    public void setListDate(Object listDate) {
        final CouponNewModel item1 = (CouponNewModel) listDate;
        if (item1 == null) return;
        View.OnClickListener onClickListener = view -> {
            openDeatil(item1);
        };
        view.setOnClickListener(onClickListener);
        String url = item1.pict_url;
        if (item1.coupon_mall_type == ConstantUtil.TAOBAOTYPE) {
            url = item1.pict_url;/*ImageFormartUtils.getFormartImageStr(item1.pict_url) + "_400x400.jpg";*/
        }
        Glide.with(mContext).load(url).into(coupon_item_pic);
        coupon_item_good_title.setText("\u3000 " + item1.title);
        if (item1.has_coupon) {
            coupon_item_good_old_price.setVisibility(View.VISIBLE);
            coupon_item_coupon_price.setVisibility(View.VISIBLE);
            coupon_item_coupon_price.setText("可抵扣" + item1.coupon_amount + "元");
        } else {
            if (TextUtils.isEmpty(item1.zk_amount)||"0".equals(item1.zk_amount)) {
                coupon_item_good_old_price.setVisibility(View.INVISIBLE);
                coupon_item_coupon_price.setVisibility(View.INVISIBLE);
            } else {
                coupon_item_good_old_price.setVisibility(View.VISIBLE);
                coupon_item_coupon_price.setVisibility(View.VISIBLE);
                coupon_item_coupon_price.setText(item1.zk_amount + "折");
            }
        }
        coupon_item_good_price.setText(item1.final_price);
        view.setOnClickListener(onClickListener);
        String vol = CouponNewModel.transformVolume(item1.volume);
        if (!TextUtils.isEmpty(vol)) {
            tvSalesCount.setVisibility(View.VISIBLE);
            tvSalesCount.setText("月销 " + vol);
        } else {
            tvSalesCount.setVisibility(View.INVISIBLE);
        }
        if (TextUtils.isEmpty(item1.video)) {
            videoPic.setVisibility(View.GONE);
            videoPic.setOnClickListener(null);
        } else {
            videoPic.setVisibility(View.VISIBLE);
//            videoPic.setOnClickListener(v -> VideoActivity.startVideoActivity(mContext, item1.video));
        }
        setUpdateView(item1);
    }

    private void setUpdateView(CouponNewModel item1) {
        int coupon_mall_type = item1.coupon_mall_type;
        if (coupon_mall_type == ConstantUtil.TAOBAOTYPE) {
            if (item1.is_tmall) {
                item1iconTv.setImageResource(R.drawable.view_home_guess_like_item_tmall_icon);
                coupon_item_good_old_price.setText(String.format(mContext.getString(R.string.along_tian_mao), item1.old_price));
            } else {
                item1iconTv.setImageResource(R.drawable.icon_t_bao);
                coupon_item_good_old_price.setText(String.format(mContext.getString(R.string.along_tao_bao), item1.old_price));
            }
            couponAfterTv.setText(item1.has_coupon ? "券后¥ " : "折后¥ ");
        } else if (coupon_mall_type == ConstantUtil.PINDUODUOTYPE) {
            couponAfterTv.setText(item1.has_coupon ? "券后¥ " : "折后¥ ");
            item1iconTv.setImageResource(R.drawable.icon_t_pin);
            coupon_item_good_old_price.setText(String.format(mContext.getString(R.string.along_jd), item1.old_price));
        } else {
            coupon_item_good_old_price.setText(String.format(mContext.getString(R.string.along_jd), item1.old_price));
            item1iconTv.setImageResource(R.drawable.jd_icon);
            couponAfterTv.setText(item1.has_coupon ? "券后¥ " : "折后¥ ");
        }
    }

    private void openDeatil(final CouponNewModel item1) {
        if (onCallBackDetail != null)
            onCallBackDetail.onClickJumpChaoRenDetail(item1);
    }

    public CouponItemViewHolder setOnCallBackDetail(OnCallBackDetail onCallBackDetail) {
        this.onCallBackDetail = onCallBackDetail;
        return this;
    }


}
