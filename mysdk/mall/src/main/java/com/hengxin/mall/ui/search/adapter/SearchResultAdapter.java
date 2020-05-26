package com.hengxin.mall.ui.search.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hengxin.mall.R;
import com.hengxin.mall.model.CouponNewModel;
import com.hengxin.mall.ui.search.SearchResultConstant;
import com.hengxin.mall.view.RoundImageView;

import java.util.List;

/**
 * author : fflin
 * date   : 2020/5/7 18:09
 * desc   :
 * version: 1.0
 */
public class SearchResultAdapter extends RecyclerView.Adapter<Holder>{
    private List<CouponNewModel> mList;
    private Context mContext;
    private final int ALONE = 1;
    private final int COLUMN = 2;

    public SearchResultAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //mall_alone_list_item(一列)  mall_column_item(两列)
        View view;
        if (getItemViewType(i) == ALONE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.mall_alone_list_item,viewGroup,false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.mall_column_item,viewGroup,false);
        }
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        CouponNewModel couponNewModel = mList.get(i);
        holder.title.setText("        "+couponNewModel.title);
        holder.curPrice.setText("￥"+couponNewModel.final_price);
        holder.oldPrice.setText("￥"+couponNewModel.old_price);
        Glide.with(mContext).load(couponNewModel.pict_url).into(holder.fontPic);
        //https://www.jianshu.com/p/fe9696ed24b2
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return SearchResultConstant.alone;
    }

    public void setData(List<CouponNewModel> couponList) {
        this.mList = couponList;
        notifyDataSetChanged();
    }
}

class Holder extends RecyclerView.ViewHolder {

    TextView curPrice, title, oldPrice;
    RoundImageView fontPic;
    public Holder(@NonNull View itemView) {
        super(itemView);
        curPrice = itemView.findViewById(R.id.coupon_item_good_price);
        title = itemView.findViewById(R.id.coupon_item_good_title);
        oldPrice = itemView.findViewById(R.id.coupon_item_good_old_price);
        fontPic = itemView.findViewById(R.id.coupon_item_pic);
    }
}
