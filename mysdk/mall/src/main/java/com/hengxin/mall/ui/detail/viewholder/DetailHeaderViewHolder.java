package com.hengxin.mall.ui.detail.viewholder;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hengxin.mall.R;
import com.hengxin.mall.model.DetailModel;
import com.hengxin.mall.view.VerticalImageSpan;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

/**
 * author : fflin
 * date   : 2020/4/24 16:20
 * desc   :
 * version: 1.0
 */
public class DetailHeaderViewHolder extends RecyclerView.ViewHolder {
    private Banner detaiiBanner;//banner
    private TextView curPrice;//现价
    private TextView oriPrice;//原价，要加中划线和货币符号
    private TextView goodsName;//商品名称，加左侧小icon
    private LinearLayout llSpecity;//规格、服务等的父布局，循环加入子布局

    public DetailHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        detaiiBanner = itemView.findViewById(R.id.detail_banner);
        curPrice = itemView.findViewById(R.id.detail_cur_price);
        oriPrice = itemView.findViewById(R.id.detail_ori_price);
        goodsName = itemView.findViewById(R.id.detail_name);
        llSpecity = itemView.findViewById(R.id.detail_specify);
    }


    public void bindData(Context context, DetailModel model) {
        setBannerData(model);
        setGoodsInfo(context,model);
        setGoodsSpecify(context,model);
    }
    // 设置基本信息
    private void setGoodsInfo(Context context,DetailModel model) {
        curPrice.setText(String.valueOf(model.price));
        oriPrice.setText("￥" + model.old_price);
        oriPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        SpannableString spannableString = new SpannableString("  " + model.goods_name);
        Drawable drawable = context.getResources().getDrawable(R.drawable.icon_t_bao);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        spannableString.setSpan(new VerticalImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        goodsName.setText(spannableString);
    }

    // 设置规格等
    private void setGoodsSpecify(Context context, DetailModel model) {
        if (llSpecity.getChildCount() > 0) llSpecity.removeAllViews();
        for (int i = 0; i < 3; i++) {
            View specifyView = LayoutInflater.from(context).inflate(R.layout.detail_specify, null);
            View lineView = specifyView.findViewById(R.id.view_line);
            if (i == 2) {
                lineView.setVisibility(View.INVISIBLE);
            }
            llSpecity.addView(specifyView);
        }
    }

    // banner
    private void setBannerData(DetailModel model) {
        detaiiBanner.setImages(model.banner_list);
        detaiiBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        detaiiBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(context).load(String.valueOf(path)).into(imageView);
            }
        });
        detaiiBanner.isAutoPlay(true);
        detaiiBanner.start();
    }
}
